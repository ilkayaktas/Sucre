package edu.metu.sucre.views.activities.home;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import butterknife.BindView;
import butterknife.ButterKnife;
import edu.metu.sucre.R;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.model.app.ListItem;
import edu.metu.sucre.model.app.SugarMeasurementType;
import edu.metu.sucre.utils.AppConstants;
import edu.metu.sucre.views.activities.base.BaseActivity;
import edu.metu.sucre.views.activities.sugarlevel.SugarLevelActivity;
import edu.metu.sucre.views.widgets.dialogs.rateme.Config;
import edu.metu.sucre.views.widgets.dialogs.rateme.RateMe;

public class MainActivity extends BaseActivity implements MainMvpView {
    
    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @BindView(R.id.welcome) TextView welcome;
    @BindView(R.id.toggleSwitch) ToggleSwitch toggleSwitch;
    @BindView(R.id.datePicker) SingleDateAndTimePicker datepicker;
    @BindView(R.id.button) Button button;
    @BindView(R.id.microphoneRing) ImageView microphoneRing;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        getActivityComponent().inject(this);
        
        setUnBinder(ButterKnife.bind(this));
    
        RateMe.init(new Config(5, 10)); // 5 gün ya da 10 defa uygulama başlattıktan sonra
    
        // Attach presenter
        mPresenter.onAttach(MainActivity.this);
    
        addCustomActionBar();
    
        setFonts();

    }


    @Override
    protected void onStart() {
        super.onStart();
        RateMe.onStart(this);
        RateMe.showRateDialogIfNeeded(this);
    }
    
    @Override
    protected void onStop() {
        super.onStop();
    }
    
    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
    
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
    
            finish();
            
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
    
    private void addCustomActionBar(){
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.layout_actionbar, null);
        ((TextView)v.findViewById(R.id.actionbar_title)).setTypeface(textFont);
        
        mActionBar.setCustomView(v);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
    
    private void setFonts(){
        welcome.setTypeface(typeface);
        button.setTypeface(typeface);
    }

    @Override
    public void updateListView(List<ListItem> sugarValues) {
    }

    @Override
    public void updateUIAfterRecord(BloodSugar bloodSugar) {
        new LovelyInfoDialog(this)
                .setTopColorRes(R.color.mobss_color_green)
                .setIcon(R.drawable.ic_check)
                .setTitle(R.string.record_success_title)
                .setMessage(getString(R.string.record_success) + "\n\n" + bloodSugar.value)
                .show();
    }

    public void showPreviousRecords(View v){
        startActivity(SugarLevelActivity.class);
    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,getString(R.string.welcome));

        try {
            startActivityForResult(intent, AppConstants.REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), getString(R.string.unrecognized_speech), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case AppConstants.REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    try {

                        // save blood sugar value
                        saveBloodSugar(Integer.valueOf(result.get(0)));

                    }catch (NumberFormatException e){
                        Toast.makeText(this, getString(R.string.say_number), Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }

        }
    }

    public void onMicrophoneRingClicked(View view){
        promptSpeechInput();
    }

    public void onManualEntryButtonClicked(View view){
        new LovelyTextInputDialog(this, R.style.EditTextTintTheme)
                .setTopColorRes(R.color.mobss_color_blue)
                .setTitle(R.string.welcome)
                .setIcon(R.drawable.ic_edit)
                .setCancelable(true)
                .setInputFilter(R.string.text_input_error_message, new LovelyTextInputDialog.TextFilter() {
                    @Override
                    public boolean check(String text) {
                        return text.matches("\\d+");
                    }
                })
                .setConfirmButton(R.string.kaydet, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {
                        // save blood sugar value
                        saveBloodSugar(Integer.valueOf(text));
                    }
                })
                .setNegativeButton(R.string.iptal, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .show();
    }

    private void saveBloodSugar(int bloodSugarValue){
        Date date = datepicker.getDate();
        SugarMeasurementType sugarMeasurementType;
        if(toggleSwitch.getCheckedTogglePosition() == 0){
            sugarMeasurementType = SugarMeasurementType.PRE;
        } else{
            sugarMeasurementType = SugarMeasurementType.POST;
        }

        // save record
        mPresenter.saveBloodSugar(new BloodSugar(date, bloodSugarValue, sugarMeasurementType));
    }
}

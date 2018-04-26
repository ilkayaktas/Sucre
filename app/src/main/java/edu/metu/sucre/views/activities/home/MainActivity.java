package edu.metu.sucre.views.activities.home;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;
import edu.metu.sucre.R;
import edu.metu.sucre.model.api.HealthData;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.model.app.ListItem;
import edu.metu.sucre.model.app.SugarMeasurementType;
import edu.metu.sucre.utils.AppConstants;
import edu.metu.sucre.utils.KeyboardUtils;
import edu.metu.sucre.views.activities.base.BaseActivity;
import edu.metu.sucre.views.activities.channels.HealthChannelsActivity;
import edu.metu.sucre.views.activities.healthdatalist.HealthDataListActivity;
import edu.metu.sucre.views.activities.login.LoginActivity;
import edu.metu.sucre.views.activities.sugarlevel.SugarLevelActivity;
import edu.metu.sucre.views.widgets.dialogs.rateme.Config;
import edu.metu.sucre.views.widgets.dialogs.rateme.RateMe;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity implements MainMvpView {
    
    @Inject
    MainMvpPresenter<MainMvpView> presenter;

    @BindView(R.id.toolbar_title)TextView toolbar_title;
    @BindView(R.id.toggleButton) ToggleButton toggleButton;
    @BindView(R.id.microphoneRing) ImageView microphoneRing;

    private Date date = new Date(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));
    
        RateMe.init(new Config(5, 10)); // 5 gün ya da 10 defa uygulama başlattıktan sonra

        // Attach presenter
        presenter.onAttach(MainActivity.this);

        // If MainActivity is reached without the user being logged in, redirect to the Login Activity
        if (!presenter.isFacebookTokenAvailable()) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }

        initUI();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI() {
        setFonts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getFacebookProfile();
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
        presenter.onDetach();
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
    
    private void setFonts(){
        toolbar_title.setTypeface(sketchFont);
    }

    @Override
    public void updateListView(List<ListItem> sugarValues) {
    }

    @Override
    public void updateUIAfterRecord(BloodSugar bloodSugar) {
        String message = bloodSugar.value + " mg/dL " + getString(R.string.record_success);
        Snackbar.make(microphoneRing, message, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    }

    @Override
    public void healthDataSaved(HealthData healthData) {
        Snackbar.make(microphoneRing, "Health Data saved: "+healthData.dataText, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    }

    public void showPreviousRecords(View v){
        YoYo.with(Techniques.Pulse)
                .duration(200)
                .onEnd(animator -> startActivity(SugarLevelActivity.class))
                .playOn(v);
        
    }

    public void activityEntry(View v){
        YoYo.with(Techniques.Pulse)
                .duration(200)
                .onEnd(animator -> {
                    new LovelyTextInputDialog(this, R.style.EditTextTintTheme)
                            .setTopColorRes(R.color.md_deep_orange_800)
                            .setIcon(R.drawable.ic_settings)
                            .setTitle(R.string.activity )
                            .setMessage(R.string.activity_message)
                            .setInputFilter(R.string.text_input_error_message, text -> text.matches("\\w+"))
                            .setConfirmButton(android.R.string.ok, text -> {
                                presenter.saveHealthData(new HealthData(text, text, HealthData.HealthDataType.ACTIVITY));
                            })
                            .show();

                })
                .playOn(v);

        Intent intent = new Intent(this, HealthDataListActivity.class);
        intent.putExtra(AppConstants.SENDER_ID, presenter.getUserId());
        startActivity(intent);
    }

    public void treatmentEntry(View v){
        YoYo.with(Techniques.Pulse)
                .duration(200)
                .onEnd(animator -> {
                    new LovelyTextInputDialog(this, R.style.EditTextTintTheme)
                            .setTopColorRes(R.color.md_deep_orange_800)
                            .setIcon(R.drawable.ic_settings)
                            .setTitle(R.string.treatment)
                            .setMessage(R.string.treatment_message)
                            .setInputFilter(R.string.text_input_error_message, text -> text.matches("\\w+"))
                            .setConfirmButton(android.R.string.ok, text -> {
                                presenter.saveHealthData(new HealthData(text, text, HealthData.HealthDataType.TREATMENT));
                            })
                            .show();
                })
                .playOn(v);
    }

    public void nutritionEntry(View v){
        YoYo.with(Techniques.Pulse)
                .duration(200)
                .onEnd(animator -> {
                    new LovelyTextInputDialog(this, R.style.EditTextTintTheme)
                            .setTopColorRes(R.color.md_deep_orange_800)
                            .setIcon(R.drawable.ic_settings)
                            .setTitle(R.string.nutrition)
                            .setMessage(R.string.nutrition_message)
                            .setInputFilter(R.string.text_input_error_message, text -> text.matches("\\w+"))
                            .setConfirmButton(android.R.string.ok, text -> {
                                presenter.saveHealthData(new HealthData(text, text, HealthData.HealthDataType.NUTRITION));
                            })
                            .show();
                })
                .playOn(v);
    }

    public void openChatScreen(View view){
        YoYo.with(Techniques.Pulse)
                .duration(200)
                .onEnd(animator -> startActivity(HealthChannelsActivity.class))
                .playOn(view);
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


    public void onCalendarClicked(View view) {
        promptDatePicker();
    }

    private void promptDatePicker(){
        new SingleDateAndTimePickerDialog.Builder(this)
                .minutesStep(5)
                .bottomSheet()
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date dateValue) {
                        date = dateValue;
                    }
                }).display();
    }

    public void onManualEntryButtonClicked(View view){
        final Activity activity = this;
        
        LovelyTextInputDialog dialog = new LovelyTextInputDialog(this, R.style.EditTextTintTheme)
                .setTopColorRes(R.color.mobss_color_blue)
                .setTitle(R.string.welcome)
                .setIcon(R.drawable.ic_edit)
                .setCancelable(true)
                .setInputType(InputType.TYPE_CLASS_NUMBER)
                .setInputFilter(R.string.text_input_error_message, new LovelyTextInputDialog.TextFilter() {
                    @Override
                    public boolean check(String text) {
                        return text.matches("\\d+");
                    }
                })
                .setConfirmButton(R.string.kaydet, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {
                        KeyboardUtils.hideSoftInput(activity);
                        // save blood sugar value
                        saveBloodSugar(Integer.valueOf(text));
                    }
                })
                .setNegativeButton(R.string.iptal, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
        
        dialog.show();
    }

    private void saveBloodSugar(int bloodSugarValue){
        SugarMeasurementType sugarMeasurementType;
        if(toggleButton.isChecked()){
            sugarMeasurementType = SugarMeasurementType.PRE;
        } else{
            sugarMeasurementType = SugarMeasurementType.POST;
        }

        // save record
        presenter.saveBloodSugar(new BloodSugar(null, date, bloodSugarValue, sugarMeasurementType));
    }

}

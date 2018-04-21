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
import butterknife.OnClick;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;
import edu.metu.sucre.R;
import edu.metu.sucre.model.app.BloodSugar;
import edu.metu.sucre.model.app.ListItem;
import edu.metu.sucre.model.app.SugarMeasurementType;
import edu.metu.sucre.utils.AppConstants;
import edu.metu.sucre.utils.KeyboardUtils;
import edu.metu.sucre.views.activities.base.BaseActivity;
import edu.metu.sucre.views.activities.channels.HealthChannelsActivity;
import edu.metu.sucre.views.activities.login.LoginActivity;
import edu.metu.sucre.views.widgets.dialogs.rateme.Config;
import edu.metu.sucre.views.widgets.dialogs.rateme.RateMe;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends BaseActivity implements MainMvpView {
    
    @Inject
    MainMvpPresenter<MainMvpView> presenter;

    @BindView(R.id.toolbar_title)TextView toolbar_title;
    @BindView(R.id.toggleButton)
    ToggleButton toggleButton;
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
//        new LovelyInfoDialog(this)
//                .setTopColorRes(R.color.mobss_color_green)
//                .setIcon(R.drawable.ic_check)
//                .setTitle(R.string.record_success_title)
//                .setMessage(getString(R.string.record_success) + "\n\n" + bloodSugar.value)
//                .show();
    
//        String message = getString(R.string.record_success) + "\n" + bloodSugar.value;
        String message = bloodSugar.value + " mg/dL " + getString(R.string.record_success);
        Snackbar.make(microphoneRing, message, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    }

    public void showPreviousRecords(View v){
        // LoginManager.getInstance().logOut();

        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        String to = "APA91bE6WRDFcdj-AG__7ufG6cpP7IhoYMZQ-fjuSDN6H2MHQBuWW7-AVdST-C-TtLCe_nZKunzueScMAuK3NTbLS72hniDk_jkH7ZgsYg-8lqabAqBeh-g";
        AtomicInteger msgId = new AtomicInteger();
        fm.send(new RemoteMessage.Builder(to)
                .setMessageId(String.valueOf(msgId))
                .addData("hello", "world")
                .build());

//        YoYo.with(Techniques.Pulse)
//                .duration(200)
//                .onEnd(new YoYo.AnimatorCallback() {
//                    @Override
//                    public void call(Animator animator) {
//                        startActivity(SugarLevelActivity.class);
//                    }
//                })
//                .playOn(v);
        
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
                //.bottomSheet()
                //.curved()
                //.minutesStep(15)

                //.displayHours(false)
                //.displayMinutes(false)
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

    @OnClick(R.id.toolbar_settings)
    public void open(View view){
        startActivity(HealthChannelsActivity.class);
    }
}

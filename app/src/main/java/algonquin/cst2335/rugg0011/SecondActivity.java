package algonquin.cst2335.rugg0011;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;


import algonquin.cst2335.rugg0011.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding variableBinding;
    SharedPreferences prefs;
    private static final String SHARED_PREF_NAME = "MyData";
    private static final String KEY_PHONE = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        variableBinding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        variableBinding.textView.setText("Welcome back " + emailAddress);

        variableBinding.callButton.setOnClickListener(clk -> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            String phoneNumber = variableBinding.editPhone.getText().toString();
            call.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(call);
        });

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        ActivityResultLauncher<Intent>cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            variableBinding.camImage.setImageBitmap(thumbnail);
                        }
                    }
                }
        );

        variableBinding.picButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                TakePicture(cameraResult);
            }
        });

        prefs = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String phone = prefs.getString(KEY_PHONE, null);

        variableBinding.editPhone.setText(phone);

    }

    void TakePicture(ActivityResultLauncher<Intent> cameraResult) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraResult.launch(cameraIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        prefs = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_PHONE, variableBinding.editPhone.getText().toString());
        editor.apply();
    }
}
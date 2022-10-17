package algonquin.cst2335.rugg0011.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.*;
import android.widget.*;

import algonquin.cst2335.rugg0011.R;


public class MainActivity extends AppCompatActivity {

    ImageView flagView;
    Switch flagSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flagView = findViewById(R.id.flagView);
        flagSwitch = findViewById(R.id.flagSwitch);

        flagSwitch.setOnCheckedChangeListener((btn, isChecked) -> {
            if (isChecked) {
                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(1000);
                rotate.setRepeatCount(Animation.INFINITE);
                rotate.setInterpolator(new LinearInterpolator());

                flagView.startAnimation(rotate);
            } else {
                flagView.clearAnimation();
            }
        });


    }
}


package algonquin.cst2335.rugg0011.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.rugg0011.data.MainViewModel;
import algonquin.cst2335.rugg0011.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private MainViewModel model;
    private ActivityMainBinding variableBinding;
    private String compoundButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        variableBinding.myButton.setOnClickListener(click ->{
            model.editString.postValue(variableBinding.myEditText.getText().toString());
        });

        variableBinding.checkBox.setOnCheckedChangeListener((btn, isSelected) -> {
            model.isSelected.postValue(isSelected);
            compoundButton = "CheckBox";
        });

        variableBinding.radioButton.setOnCheckedChangeListener((btn, isSelected) -> {
            model.isSelected.postValue(isSelected);
            compoundButton = "Radio Button";
        });

        variableBinding.switch1.setOnCheckedChangeListener((btn, isSelected) -> {
            model.isSelected.postValue(isSelected);
            compoundButton = "Switch";
        });

        variableBinding.myImageButton.setOnClickListener(click -> {
            Context context = getApplicationContext();
            CharSequence text = "Width= " + variableBinding.myImageView.getWidth() + "\nHeight= " + variableBinding.myImageView.getHeight();
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
        });

        model.editString.observe(this, s -> {
            variableBinding.myText.setText("Your edit text has: \n" + s);
        });

        model.isSelected.observe(this, selected ->{
            variableBinding.checkBox.setChecked(selected);
            variableBinding.radioButton.setChecked(selected);
            variableBinding.switch1.setChecked(selected);


            Context context = getApplicationContext();
            CharSequence text = "You've clicked on the " + compoundButton + " and it's now: " + selected;
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
        });
    }
}


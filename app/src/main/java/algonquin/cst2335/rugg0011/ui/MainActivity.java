package algonquin.cst2335.rugg0011.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import algonquin.cst2335.rugg0011.data.MainViewModel;
import algonquin.cst2335.rugg0011.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private MainViewModel model;
    private ActivityMainBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        variableBinding.myButton.setOnClickListener(click ->{
            model.editString.postValue(variableBinding.myEditText.getText().toString());
        });

        model.editString.observe(this, s -> {
            variableBinding.myText.setText("Your edit text has: \n" + s);
        });
    }
}


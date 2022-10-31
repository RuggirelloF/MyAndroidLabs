package algonquin.cst2335.rugg0011;

import android.content.*;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import algonquin.cst2335.rugg0011.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding variableBinding;
    SharedPreferences prefs;
    private static final String SHARED_PREF_NAME = "MyData";
    private static final String KEY_LOGIN = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        variableBinding.loginButton.setOnClickListener(clk -> {
            secondActivity();
        });

        prefs = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String email = prefs.getString(KEY_LOGIN, null);

        variableBinding.editEmail.setText(email);

    }

    public void secondActivity(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_LOGIN, variableBinding.editEmail.getText().toString());
        editor.apply();
        Intent nextPage = new Intent( MainActivity.this, SecondActivity.class);
        nextPage.putExtra("EmailAddress", variableBinding.editEmail.getText().toString());
        startActivity(nextPage);
    }
}
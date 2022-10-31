package algonquin.cst2335.rugg0011;

import static java.lang.Character.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Fabrizio Ruggirello
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This holds the text and the centre of the screen
     */
    private TextView logInTextView = null;

    /**
     * This holds the editText field below the textView
     */
    private EditText logInEditText = null;

    /**
     * This holds the LOG IN button at the bottom of the screen
     */
    private Button logInButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView logInTextView = findViewById(R.id.logInText);
        EditText logInEditText = findViewById(R.id.logInEditText);
        Button logInButton = findViewById(R.id.logInButton);

        logInButton.setOnClickListener(clk -> {
            String password = logInEditText.getText().toString();

            checkPasswordComplexity(password);
            if(checkPasswordComplexity(password) == false){
                logInTextView.setText("YOU... SHALL NOT... PASS!!!");
            }else{
                logInTextView.setText("A fool, but an honest fool you remain... You shall pass");
            }
        });
    }

    /**
     * Checks if the password is complex enough
     * @param password The String object that we are checking
     * @return Returns true if the password:<br>
     * --Is longer than 8 digits<br>
     * --Contains an upper case letter<br>
     * --Contains a lowe case letter<br>
     * --Contains a number<br>
     * --Contains a special character
     */
    boolean checkPasswordComplexity(String password){
        int passLen = password.length();
        boolean foundUpperCase = false;
        boolean foundLowerCase = false;
        boolean foundNumber = false;
        boolean foundSpecial = false;
        for(int i = 0; i < passLen; i++){
            Character c = password.charAt(i);
            if(isUpperCase(c)){
                foundUpperCase = true;
            }
            else if(isLowerCase(c)){
                foundLowerCase = true;
            }
            else if(isDigit(c)){
                foundNumber = true;
            }
            else if(isSpecialCharacter(c)){
                foundSpecial = true;
            }
        }
        if(passLen < 8){
            Toast.makeText(getApplicationContext(), "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!foundUpperCase){
            Toast.makeText(getApplicationContext(), "Password must contain an upper case letter", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!foundLowerCase){
            Toast.makeText(getApplicationContext(), "Password must contain a lower case letter", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!foundNumber){
            Toast.makeText(getApplicationContext(), "Password must contain a number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!foundSpecial){
            Toast.makeText(getApplicationContext(), "Password must contain a special character", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            Toast.makeText(getApplicationContext(), "Congratulations! This is a valid password!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    /**
     * Checks if the specific character is a special character
     * @param c The character that we wish to check
     * @return Returns true if the Character matches any<br>
     * of the special characters
     */
    boolean isSpecialCharacter(char c){
        switch(c){
            case '#':
            case '$':
            case '%':
            case '&':
            case '*':
            case '?':
            case '!':
            case '@':
            case '^':
                return true;
            default:
                return false;
        }
    }
}
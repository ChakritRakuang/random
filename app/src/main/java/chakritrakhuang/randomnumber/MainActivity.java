package chakritrakhuang.randomnumber;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create instance of SharedPreferences
        SharedPreferences sharedPreferences  = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        // Get value of preferences
        String minValuePref = sharedPreferences.getString("min_number","1");
        String maxValuePref = sharedPreferences.getString("max_number","10");
        String repValuePref = sharedPreferences.getString("repeat","1");

        // Create editTexts
        final EditText minNum = findViewById(R.id.min);
        final EditText maxNum = findViewById(R.id.max);
        final EditText reps = findViewById(R.id.reps);
        minNum.setText(minValuePref);
        maxNum.setText(maxValuePref);
        reps.setText(repValuePref);

        // Create Button
        Button generateButton = findViewById(R.id.generate);

        // If button is pressed
        generateButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        // Declare variables
                        int minimum;
                        int maximum;
                        int repetitions;
                        StringBuilder rNumbers= new StringBuilder();

                        // Create textViews

                        TextView numbers = findViewById(R.id.numbers);

                        // Test if any of the editTexts are empty
                        String minimumTest = minNum.getText().toString();
                        String maximumTest = maxNum.getText().toString();
                        String repetitionsTest = reps.getText().toString();

                        // Check if any of the editTexts are empty, if true then toast to fill in values
                        if (minimumTest.length() == 0 || maximumTest.length() == 0 || repetitionsTest.length() == 0){
                            Toast msg2 = Toast.makeText(MainActivity.this, "Fill in all values", Toast.LENGTH_LONG);
                            msg2.show();
                        } else if (Integer.parseInt(repetitionsTest) < 0){
                            Toast msg4 = Toast.makeText(MainActivity.this, "Can't repeat negative times", Toast.LENGTH_LONG);
                            msg4.show();
                        } else {
                            // Get editText values
                            minimum = Integer.parseInt(minNum.getText().toString());
                            maximum = Integer.parseInt(maxNum.getText().toString());
                            repetitions = Integer.parseInt(reps.getText().toString());

                            // Check if minimum is greater than maximum, if true then toast that minimum is greater than maximum
                            int randomNum[] = new int[repetitions];
                            if (minimum > maximum){
                                Toast msg = Toast.makeText(MainActivity.this, "Minimum is Greater than Maximum", Toast.LENGTH_LONG);
                                msg.show();
                            } else {
                                // Create random numbers
                                Random r = new Random();

                                for (int i = 1; i <= repetitions; i++){
                                    if (i%2 == 0){
                                        randomNum[i-1] = r.nextInt(maximum - minimum + 1) + minimum;
                                    } else {
                                        randomNum[i-1] = minimum + (int)(Math.random() * ((maximum - minimum) + 1));
                                    }
                                }

                                // Fill textView with numbers
                                for (int i = 1; i <= repetitions; i++){
                                    if (i == 1){
                                        rNumbers.append(Integer.toString(randomNum[i - 1]));
                                    } else {
                                        rNumbers.append("\n").append(Integer.toString(randomNum[i - 1]));
                                    }
                                }
                                numbers.setText(rNumbers.toString());
                            }
                        }
                    }
                });

    }
    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            // Settings option selected
            case R.id.settings:
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;
            // Roll Dice option selected
            case R.id.dice:
                int dice_roll = getRandomNumber(6);
                Toast msg_dice = Toast.makeText(MainActivity.this, "Dice Roll: " + dice_roll, Toast.LENGTH_SHORT);
                msg_dice.show();
                return true;
            // Toss coin option selected
            case R.id.coin:
                int coin_toss = getRandomNumber(2);
                Toast msg_coin = Toast.makeText(MainActivity.this, "Coin Toss: " + coin_toss, Toast.LENGTH_SHORT);
                msg_coin.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Generates a random number in the range [min,max] and returns it
    public int getRandomNumber(int max){
        Random r2 = new Random();
        return r2.nextInt(max - 1 + 1) + 1;
    }
}
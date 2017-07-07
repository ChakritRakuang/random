package chakritrakhuang.randomnumber

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.preference.PreferenceManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import java.util.Random

class MainActivity : Activity() {

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Create instance of SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext)
        // Get value of preferences
        val minValuePref = sharedPreferences.getString("min_number" , "1")
        val maxValuePref = sharedPreferences.getString("max_number" , "10")
        val repValuePref = sharedPreferences.getString("repeat" , "1")

        // Create editTexts
        val minNum = findViewById<EditText>(R.id.min)
        val maxNum = findViewById<EditText>(R.id.max)
        val reps = findViewById<EditText>(R.id.reps)
        minNum.setText(minValuePref)
        maxNum.setText(maxValuePref)
        reps.setText(repValuePref)

        // Create Button
        val generateButton = findViewById<Button>(R.id.generate)

        // If button is pressed
        generateButton.setOnClickListener {
            // Declare variables
            val minimum : Int
            val maximum : Int
            val repetitions : Int
            val rNumbers = StringBuilder()

            // Create textViews

            val numbers = findViewById<TextView>(R.id.numbers)

            // Test if any of the editTexts are empty
            val minimumTest = minNum.text.toString()
            val maximumTest = maxNum.text.toString()
            val repetitionsTest = reps.text.toString()

            // Check if any of the editTexts are empty, if true then toast to fill in values
            if (minimumTest.isEmpty() || maximumTest.isEmpty() || repetitionsTest.isEmpty()) {
                val msg2 = Toast.makeText(this@MainActivity , "Fill in all values" , Toast.LENGTH_LONG)
                msg2.show()
            } else if (Integer.parseInt(repetitionsTest) < 0) {
                val msg4 = Toast.makeText(this@MainActivity , "Can't repeat negative times" , Toast.LENGTH_LONG)
                msg4.show()
            } else {
                // Get editText values
                minimum = Integer.parseInt(minNum.text.toString())
                maximum = Integer.parseInt(maxNum.text.toString())
                repetitions = Integer.parseInt(reps.text.toString())

                // Check if minimum is greater than maximum, if true then toast that minimum is greater than maximum
                val randomNum = IntArray(repetitions)
                if (minimum > maximum) {
                    val msg = Toast.makeText(this@MainActivity , "Minimum is Greater than Maximum" , Toast.LENGTH_LONG)
                    msg.show()
                } else {
                    // Create random numbers
                    val r = Random()

                    for (i in 1 .. repetitions) {
                        if (i % 2 == 0) {
                            randomNum[i - 1] = r.nextInt(maximum - minimum + 1) + minimum
                        } else {
                            randomNum[i - 1] = minimum + (Math.random() * (maximum - minimum + 1)).toInt()
                        }
                    }

                    // Fill textView with numbers
                    for (i in 1 .. repetitions) {
                        if (i == 1) {
                            rNumbers.append(Integer.toString(randomNum[i - 1]))
                        } else {
                            rNumbers.append("\n").append(Integer.toString(randomNum[i - 1]))
                        }
                    }
                    numbers.text = rNumbers.toString()
                }
            }
        }
    }

    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu : Menu) : Boolean {
        val inflater = menuInflater
        inflater.inflate(R.layout.menu , menu)
        return true
    }

    @SuppressLint("ShowToast")
    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        // Handle item selection
        when (item.itemId) {
        // Settings option selected
            R.id.settings -> {
                val intent = Intent(this , Settings::class.java)
                startActivity(intent)
                return true
            }
        // Roll Dice option selected
            R.id.dice -> {
                val dice_roll = getRandomNumber(6)
                val msg_dice = Toast.makeText(this@MainActivity , "Dice Roll: " + dice_roll , Toast.LENGTH_SHORT)
                msg_dice.show()
                return true
            }
        // Toss coin option selected
            R.id.coin -> {
                val coin_toss = getRandomNumber(2)
                val msg_coin = Toast.makeText(this@MainActivity , "Coin Toss: " + coin_toss , Toast.LENGTH_SHORT)
                msg_coin.show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // Generates a random number in the range [min,max] and returns it
    fun getRandomNumber(max : Int) : Int {
        val r2 = Random()
        return r2.nextInt(max - 1 + 1) + 1
    }

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
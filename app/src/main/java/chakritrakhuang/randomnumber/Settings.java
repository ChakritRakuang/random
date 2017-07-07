package chakritrakhuang.randomnumber;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceActivity;

@SuppressWarnings("ALL")
@SuppressLint("Registered")
public class Settings extends PreferenceActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
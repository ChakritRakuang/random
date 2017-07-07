package chakritrakhuang.randomnumber

import android.annotation.SuppressLint
import android.os.Bundle
import android.preference.PreferenceActivity

@Suppress("DEPRECATION")
@SuppressLint("Registered")
class Settings : PreferenceActivity() {
    /**
     * Called when the activity is first created.
     */
    public override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)
    }
}
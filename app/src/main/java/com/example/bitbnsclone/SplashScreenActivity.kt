package com.example.bitbnsclone

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.bitbnsclone.fragments.LogInFragement

class SplashScreenActivity : AppCompatActivity() {
    val activity: MainActivity = MainActivity()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            var sharedPreferences: SharedPreferences =
                this.getSharedPreferences(LogInFragement::PREFS_NAME.toString(), 0)
            // fetching the data of shared prefrenece that wether user has logged in or not
            var hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn", false)

            //checking if hasLoggedIn is true then it will launch the MainActivity otherwise LoginActivity
            if (hasLoggedIn) {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            } else {
                val i = Intent(this, LogInSignUpActivity::class.java)
                startActivity(i)
                finish()
            }
        }, 1500)

    }

    // Overiding OnBackPressed to kill the actvity threads and close the appplication
    override fun onBackPressed() {
        activity.finish()
        System.exit(0)
    }

}
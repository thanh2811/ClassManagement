package com.example.dclassmanagement.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dclassmanagement.MainActivity
import com.example.dclassmanagement.R
import com.example.dclassmanagement.ui.auth.LoginActivity
import com.example.dclassmanagement.ui.base.BaseActivity
import com.example.dclassmanagement.ui.landing_page.LandingPageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity() {
    private lateinit var mAuth: FirebaseAuth

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_splash_screen)
        mAuth = FirebaseAuth.getInstance()

        GlobalScope.launch(Dispatchers.Main) {
            delay(300)
            if(mAuth.currentUser != null)
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            else
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            finish()
        }


        super.onCreate(savedInstanceState)
    }
}
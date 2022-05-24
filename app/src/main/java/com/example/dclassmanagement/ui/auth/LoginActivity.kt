package com.example.dclassmanagement.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.dclassmanagement.MainActivity
import com.example.dclassmanagement.R
import com.example.dclassmanagement.databinding.ActivityLoginBinding
import com.example.dclassmanagement.ui.base.BaseActivity
import com.example.dclassmanagement.ui.landing_page.LandingPageActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,  R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
    }

    override fun initListener() {
        super.initListener()
        binding.btLogin.setOnClickListener {
            val email: String = binding.etUsername.text.toString()
            val pass: String = binding.etPassword.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                showToast("Please fill all the field")
                return@setOnClickListener
            } else{
                showLoading()
                mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else {
                            showToast(task.exception?.message.toString())
                        }
                        hideLoading()
                    }
            }
        }

        bt_register.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

}
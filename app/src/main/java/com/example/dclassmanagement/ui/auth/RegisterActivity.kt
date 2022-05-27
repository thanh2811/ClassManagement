package com.example.dclassmanagement.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.dclassmanagement.R
import com.example.dclassmanagement.databinding.ActivityRegisterBinding
import com.example.dclassmanagement.ui.base.BaseActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.bt_register
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mRef: DatabaseReference
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
    }

    override fun initListener() {
        super.initListener()
        binding.btRegister.setOnClickListener {
            Log.d("btRegister clicked", "true")
            val username: String = binding.etUsername.text.toString()
            val pass: String = binding.etPassword.text.toString()
            val email: String = binding.etEmail.text.toString()
            if (username.isEmpty() || pass.isEmpty() || email.isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Please fill all fields!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                register(username, pass, email)
            }
        }
    }

    private fun register(username: String, password: String, email: String) {
//        Toast.makeText(RegisterActivity.this, (username + "" + password + "" +email), Toast.LENGTH_SHORT).show();
        showLoading()
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = mAuth.currentUser!!
                    val userId = firebaseUser.uid
                    mRef = FirebaseDatabase.getInstance()
                        .getReference("User").child(userId)
                    val hashMap = HashMap<String, String>()
                    hashMap["id"] = userId
                    hashMap["username"] = username
                    hashMap["email"] = email
                    hashMap["password"] = password
                    hashMap["imageUrl"] = ""
                    hashMap["background"] = ""
                    hashMap["fullname"] = username
                    val hashMap1 = HashMap<String, Int>()
                    hashMap1["avatarId"] = 0
                    hashMap1["backgroundId"] = 0
                    mRef.setValue(hashMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            finish()
                        }
                    }
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Invalid Email or Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                hideLoading()
            })
    }

}
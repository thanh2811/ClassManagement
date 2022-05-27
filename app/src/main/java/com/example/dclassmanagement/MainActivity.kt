package com.example.dclassmanagement

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.dclassmanagement.data.FragmentChatARGS
import com.example.dclassmanagement.data.Table
import com.example.dclassmanagement.data.model.User
import com.example.dclassmanagement.databinding.ActivityMainBinding
import com.example.dclassmanagement.navigation.Navigator.getCurrentDestinationId
import com.example.dclassmanagement.navigation.Navigator.requestNavigate
import com.example.dclassmanagement.navigation.Navigator.requestNavigateDeeplink
import com.example.dclassmanagement.ui.base.BaseActivity
import com.example.dclassmanagement.ui.landing_page.FragmentLandingPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_landing_page.*

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private val userRef = FirebaseDatabase.getInstance().getReference(Table.USER)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun initListener() {
        super.initListener()
        binding.itemLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
            navController.navigate(R.id.loginActivity)
        }
        binding.itemMessage.setOnClickListener {
            if(getCurrentDestinationId(R.id.nav_host_fragment) == R.id.fragmentChat)
                return@setOnClickListener
            requestNavigate(R.id.fragmentChat)
        }
        binding.itemClass.setOnClickListener {
            if(getCurrentDestinationId(R.id.nav_host_fragment) == R.id.fragmentLandingPage)
                return@setOnClickListener
            requestNavigate(R.id.fragmentLandingPage)
        }

        userRef.child(uid).get().addOnCompleteListener {
            if(it.isSuccessful){
                val user = it.result.getValue(User::class.java)
                if (user != null) {
                    binding.tvFullName.text = user.fullname
                    binding.tvUserName.text = "@${user.fullname}"
                    if(user.imageUrl.isNotEmpty()){
                        Glide.with(baseContext)
                            .load(user.imageUrl)
                            .into(binding.ivDrawerAvatar)
                    }
                }
            }
        }
    }
}
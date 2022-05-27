package com.example.dclassmanagement.ui.member

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dclassmanagement.R
import com.example.dclassmanagement.data.FragmentChatARGS
import com.example.dclassmanagement.data.FragmentLandingPageARGS
import com.example.dclassmanagement.data.Table
import com.example.dclassmanagement.data.model.ActionWrapper
import com.example.dclassmanagement.data.model.User
import com.example.dclassmanagement.data.model.UserClass
import com.example.dclassmanagement.databinding.FragmentMemberBinding
import com.example.dclassmanagement.navigation.Navigator.requestNavigate
import com.example.dclassmanagement.ui.base.ActionDispatcher
import com.example.dclassmanagement.ui.base.ActionExecutor
import com.example.dclassmanagement.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentMember : BaseFragment() {

    private lateinit var binding: FragmentMemberBinding
    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private val userRef = FirebaseDatabase.getInstance().getReference(Table.USER)
    private val userClassRef = FirebaseDatabase.getInstance().getReference(Table.USER_CLASS)
    private var memberList = mutableListOf<User>()

    var classId = ""

    private lateinit var memberAdapter: MemberAdapter

    private val actionDispatcher = ActionDispatcher(object : ActionExecutor {
        override fun dispatch(actionWrapper: ActionWrapper?) {
            val user = actionWrapper?.payload as User
            requestNavigate(R.id.fragmentMessage, bundleOf(
                FragmentChatARGS.KEY.USER_ID to user.id
            ))
        }
    })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData() {
        classId = arguments?.getString(FragmentLandingPageARGS.KEY.CLASS_ID).toString()
        super.initData()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initView() {
        super.initView()
        memberAdapter = MemberAdapter(actionDispatcher, memberList)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvMember.layoutManager = layoutManager
        binding.rvMember.adapter = memberAdapter
        memberList.clear()

        userClassRef.orderByChild("classId").equalTo(classId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { uc ->
                        val userClass = uc.getValue(UserClass::class.java)
                        Log.d("userClass3", userClass.toString())

                        //getUserById
                        userRef.child(userClass?.userId.toString()).get()
                            .addOnCompleteListener { u ->
                                val user = u.result.getValue(User::class.java)
                                if (user != null) {
                                    memberList.add(user)
                                }
                                memberAdapter.notifyDataSetChanged()
                            }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
            )
    }

    override fun initListener() {
        super.initListener()
        binding.btAddMember.setOnClickListener {
            requestNavigate(id = R.id.fragmentAddMember, bundleOf(
                FragmentLandingPageARGS.KEY.CLASS_ID to classId
            ))
        }
    }


}
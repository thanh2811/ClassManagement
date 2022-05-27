package com.example.dclassmanagement.ui.member

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dclassmanagement.data.Action
import com.example.dclassmanagement.data.FragmentLandingPageARGS
import com.example.dclassmanagement.data.Table
import com.example.dclassmanagement.data.model.ActionWrapper
import com.example.dclassmanagement.data.model.User
import com.example.dclassmanagement.data.model.UserClass
import com.example.dclassmanagement.databinding.FragmentAddMemberBinding
import com.example.dclassmanagement.databinding.FragmentMemberBinding
import com.example.dclassmanagement.navigation.Navigator.popBack
import com.example.dclassmanagement.ui.base.ActionDispatcher
import com.example.dclassmanagement.ui.base.ActionExecutor
import com.example.dclassmanagement.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentAddMember : BaseFragment() {

    private lateinit var binding: FragmentAddMemberBinding
    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private val userRef = FirebaseDatabase.getInstance().getReference(Table.USER)
    private val userClassRef = FirebaseDatabase.getInstance().getReference(Table.USER_CLASS)
    private var memberList = mutableListOf<User>()
    private var addList = mutableListOf<User>()

    var classId = ""

    private lateinit var memberAdapter: AddMemberAdapter

    private val actionDispatcher = ActionDispatcher(object : ActionExecutor {
        override fun dispatch(actionWrapper: ActionWrapper?) {
            val user = actionWrapper?.payload as User
            when(actionWrapper.name){
                Action.Item.ITEM_CHECK -> {
                    addList.add(user)
                }
                Action.Item.ITEM_UNCHECK -> {
                    addList.remove(user)
                }
            }
            Log.d("userList", addList.toString())
//            requestNavigate(
//                R.id.fragmentAssignmentDetail, bundleOf(
//                    FragmentAssignmentARGS.KEY.ASSIGNMENT to assignment,
//                    FragmentLandingPageARGS.KEY.CLASS_ID to classId,
//                )
//            )

        }
    })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData() {
        classId = arguments?.getString(FragmentLandingPageARGS.KEY.CLASS_ID).toString()
        super.initData()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initView() {
        super.initView()
        memberAdapter = AddMemberAdapter(actionDispatcher, memberList)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvMember.layoutManager = layoutManager
        binding.rvMember.adapter = memberAdapter

        userClassRef.orderByChild("classId").equalTo(classId)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { uc ->
                        val userClass = uc.getValue(UserClass::class.java)

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

            })

        //getUserById
        userRef.get().addOnCompleteListener { u ->
            u.result.children.forEach {
                val user = it.getValue(User::class.java)
                if (user != null) {
                    memberList.add(user)
                }
                memberAdapter.notifyDataSetChanged()
            }

        }
    }

    override fun initListener() {
        super.initListener()
        binding.btAddMember.setOnClickListener {
            addList.forEach {
                val userClass = UserClass(classId, it.id)
                userClassRef.push().setValue(userClass).addOnCompleteListener {
                    popBack()
                }
            }
        }
    }


}
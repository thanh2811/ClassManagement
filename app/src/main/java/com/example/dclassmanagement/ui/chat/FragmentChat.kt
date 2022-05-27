package com.example.dclassmanagement.ui.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dclassmanagement.R
import com.example.dclassmanagement.data.*
import com.example.dclassmanagement.data.model.ActionWrapper
import com.example.dclassmanagement.data.model.Assignment
import com.example.dclassmanagement.data.model.Chat
import com.example.dclassmanagement.data.utils.show
import com.example.dclassmanagement.databinding.FragmentChatBinding
import com.example.dclassmanagement.navigation.Navigator.requestNavigate
import com.example.dclassmanagement.ui.base.ActionDispatcher
import com.example.dclassmanagement.ui.base.ActionExecutor
import com.example.dclassmanagement.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentChat : BaseFragment() {

    private lateinit var binding: FragmentChatBinding
    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private val assignmentRef = FirebaseDatabase.getInstance().getReference(Table.ASSIGNMENT)
    private val chatRef = FirebaseDatabase.getInstance().getReference(Table.CHAT)

    private var chatList = mutableListOf<Chat>()

    private lateinit var chatAdapter: ChatAdapter

    private val actionDispatcher = ActionDispatcher(object : ActionExecutor {
        override fun dispatch(actionWrapper: ActionWrapper?) {
            val item = actionWrapper?.payload as String
            requestNavigate(R.id.fragmentMessage, bundleOf(
                FragmentChatARGS.KEY.USER_ID to item
            ))

        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = com.example.dclassmanagement.databinding.FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData() {
        super.initData()
    }

    override fun initView() {
        super.initView()
        chatAdapter = ChatAdapter(actionDispatcher, chatList)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvChat.layoutManager = layoutManager
        binding.rvChat.adapter = chatAdapter

        chatRef.addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    chatList.clear()
                    snapshot.children.forEach {
                        val chat = it.getValue(Chat::class.java)
                        if (chat != null) {
                            chatList.add(chat)
                        }
                    }
                    chatAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    override fun initListener() {
        super.initListener()


    }


}
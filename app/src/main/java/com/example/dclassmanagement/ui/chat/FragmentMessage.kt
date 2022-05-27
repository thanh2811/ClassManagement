package com.example.dclassmanagement.ui.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dclassmanagement.data.*
import com.example.dclassmanagement.data.model.*
import com.example.dclassmanagement.data.utils.Utils
import com.example.dclassmanagement.data.utils.gone
import com.example.dclassmanagement.data.utils.show
import com.example.dclassmanagement.databinding.FragmentMessageBinding
import com.example.dclassmanagement.ui.base.ActionDispatcher
import com.example.dclassmanagement.ui.base.ActionExecutor
import com.example.dclassmanagement.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentMessage : BaseFragment() {

    private lateinit var binding: FragmentMessageBinding
    private val uid = FirebaseAuth.getInstance().currentUser?.uid
    private val messageRef = FirebaseDatabase.getInstance().getReference(Table.MESSAGE)
    private val chatRef = FirebaseDatabase.getInstance().getReference(Table.CHAT)
    private val userRef = FirebaseDatabase.getInstance().getReference(Table.USER)

    private var messageList = mutableListOf<Message>()
    var uid2 = ""
    var chatId = ""

    private lateinit var messageAdapter: MessageAdapter

    private val actionDispatcher = ActionDispatcher(object : ActionExecutor {
        override fun dispatch(actionWrapper: ActionWrapper?) {
            val assignment = actionWrapper?.payload as Assignment

        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData() {
        super.initData()
        uid2 = arguments?.getString(FragmentChatARGS.KEY.USER_ID).toString()
    }

    override fun initView() {
        super.initView()
        messageAdapter = MessageAdapter(actionDispatcher, messageList)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvMessage.layoutManager = layoutManager
        binding.rvMessage.adapter = messageAdapter
        messageList.clear()
        chatRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.childrenCount == 0L)
                    binding.tvNoMessage.show()
                else
                    binding.tvNoMessage.gone()
                snapshot.children.forEach {
                    val chat = it.getValue(Chat::class.java)
                    if (chat != null) {
                        if (chat.uid1 == uid && chat.uid2 == uid2 ||
                            chat.uid2 == uid && chat.uid1 == uid2
                        ) {
                            chatId = chat.id
                            messageRef.orderByChild("chatId").equalTo(chatId)
                                .addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        val oldSize = messageList.size
                                        messageList.clear()
                                        snapshot.children.forEach {
                                            val message = it.getValue(Message::class.java)
                                            if (message != null) {
                                                messageList.add(message)
                                            }
                                            messageAdapter.notifyItemRangeInserted(
                                                oldSize,
                                                messageList.size - oldSize
                                            )
                                            binding.rvMessage.smoothScrollToPosition(messageList.size - 1)
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {

                                    }
                                })


                        }

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        userRef.child(uid2).get().addOnCompleteListener {
            if(it.isSuccessful){
                val user2 = it.result.getValue(User::class.java)
                binding.tvUserName.text = user2?.username
                if(user2?.imageUrl?.isNotEmpty() == true){
                    Glide.with(requireContext())
                        .load(user2.imageUrl)
                        .into(binding.ivUser)
                }
            }
        }


    }

    override fun initListener() {
        super.initListener()
        binding.btnSend.setOnClickListener {
            val msg = binding.etInputMessage.text.toString()
            if(msg.isNotEmpty()){
                binding.etInputMessage.text = Editable.Factory.getInstance().newEditable("")
                if(chatId.isEmpty()){
                    chatId = Utils.createId()
                    chatRef.child(chatId).setValue(Chat(chatId, uid.toString(), uid2, msg, System.currentTimeMillis())).addOnCompleteListener {
                        val message = Message(msg, chatId, uid.toString(), System.currentTimeMillis())
                        messageRef.push().setValue(message)
                    }
                }
                else{
                    chatRef.child(chatId).child("lastMessage").setValue(msg).addOnCompleteListener {

                    }
                    val message = Message(msg, chatId, uid.toString(), System.currentTimeMillis())
                    messageRef.push().setValue(message)
                }

            }
        }
    }


}
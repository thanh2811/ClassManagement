package com.example.dclassmanagement.ui.chat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dclassmanagement.R
import com.example.dclassmanagement.data.Table
import com.example.dclassmanagement.data.model.Chat
import com.example.dclassmanagement.data.model.CommentData
import com.example.dclassmanagement.data.model.Message
import com.example.dclassmanagement.data.model.User
import com.example.dclassmanagement.ui.base.ActionDispatcher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_msg_left.view.*

class MessageAdapter constructor(
    private val actionDispatcher: ActionDispatcher,
    private val data: List<Message>
) :
    RecyclerView.Adapter<MessageAdapter.CommentViewHolder>() {

    private var userRef = FirebaseDatabase.getInstance().getReference(Table.USER)
    private val uid = FirebaseAuth.getInstance().currentUser?.uid

    private val MSG_LEFT = 0
    private val MSG_RIGHT = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return if(viewType == MSG_RIGHT) CommentViewHolder(
            actionDispatcher,
            LayoutInflater.from(parent.context).inflate(R.layout.item_msg_right, parent, false)
        ) else CommentViewHolder(
        actionDispatcher,
        LayoutInflater.from(parent.context).inflate(R.layout.item_msg_left, parent, false)
        )
    }

    override fun getItemViewType(position: Int): Int {
        if(data[position].sentBy == uid)
            return MSG_RIGHT
        return MSG_LEFT
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class CommentViewHolder(private val actionDispatcher: ActionDispatcher, itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bindView(item: Message) {
            itemView.tv_message.text = item.msg
//
//            // who is the commenter
//            userRef.child(item.userId).addListenerForSingleValueEvent(
//                object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        val user = snapshot.getValue(User::class.java)
//                        if (user?.id == item.userId) {
//                            if (user.imageUrl.isNotEmpty()) {
//                                Glide.with(itemView.context)
//                                    .load(user.imageUrl)
//                                    .into(itemView.iv_comment_user)
//                            }
//                            itemView.tv_comment_username.text = user.username
//                        }
//
//
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        TODO("Not yet implemented")
//                    }
//
//                }
//            )

//            itemView.bt_comment.setOnClickListener {
//                actionDispatcher.dispatch(
//                    ActionWrapper(
//                        name = Action.Item.COMMENT_POST,
//                        payload = item
//                    )
//                )
//            }
        }
    }

}


package com.example.dclassmanagement.ui.chat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dclassmanagement.R
import com.example.dclassmanagement.data.Table
import com.example.dclassmanagement.data.model.ActionWrapper
import com.example.dclassmanagement.data.model.Chat
import com.example.dclassmanagement.data.model.User
import com.example.dclassmanagement.data.utils.Utils
import com.example.dclassmanagement.ui.base.ActionDispatcher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.item_chat.view.*

class ChatAdapter constructor(
    private val actionDispatcher: ActionDispatcher,
    private val data: List<Chat>
) :
    RecyclerView.Adapter<ChatAdapter.CommentViewHolder>() {

    private var userRef = FirebaseDatabase.getInstance().getReference(Table.USER)
    private val likeRef = FirebaseDatabase.getInstance().getReference(Table.LIKE)
    private val cmtRef = FirebaseDatabase.getInstance().getReference(Table.COMMENT)
    private val uid = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            actionDispatcher,
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        )
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
        fun bindView(item: Chat) {
            var userId = uid
            if (item.uid1 == uid)
                userId = item.uid2

            userRef.child(userId.toString()).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = it.result.getValue(User::class.java)
                    if (user?.imageUrl?.isNotEmpty() == true) {
                        Glide.with(itemView.context)
                            .load(user.imageUrl)
                            .into(itemView.iv_chat_user)
                    }
                    itemView.tv_username.text = user?.username
                }
            }

            itemView.tv_last_message.text = item.lastMessage
            itemView.tv_last_sent_date.text = Utils.getDateFromMillisecond(item.lastSentDate)
            itemView.setOnClickListener {
                actionDispatcher.dispatch(actionWrapper = ActionWrapper(payload = userId))
            }
        }
    }

}


package com.example.dclassmanagement.ui.comment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dclassmanagement.R
import com.example.dclassmanagement.data.Table
import com.example.dclassmanagement.data.model.CommentData
import com.example.dclassmanagement.data.model.User
import com.example.dclassmanagement.ui.base.ActionDispatcher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_post.view.*

class CommentAdapter constructor(
    private val actionDispatcher: ActionDispatcher,
    private val data: List<CommentData>
) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private var userRef = FirebaseDatabase.getInstance().getReference(Table.USER)
    private val likeRef = FirebaseDatabase.getInstance().getReference(Table.LIKE)
    private val cmtRef = FirebaseDatabase.getInstance().getReference(Table.COMMENT)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            actionDispatcher,
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
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
        fun bindView(item: CommentData) {
            itemView.tv_comment_content.text = item.content

            // who is the commenter
            userRef.child(item.userId).addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(User::class.java)
                        if (user?.id == item.userId) {
                            if (user.imageUrl.isNotEmpty()) {
                                Glide.with(itemView.context)
                                    .load(user.imageUrl)
                                    .into(itemView.iv_comment_user)
                            }
                            itemView.tv_comment_username.text = user.username
                        }


                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                }
            )

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


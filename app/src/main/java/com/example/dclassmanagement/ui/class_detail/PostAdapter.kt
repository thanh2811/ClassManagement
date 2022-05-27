package com.example.dclassmanagement.ui.class_detail

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dclassmanagement.R
import com.example.dclassmanagement.data.ACTION_CLASS_PICK
import com.example.dclassmanagement.data.Action
import com.example.dclassmanagement.data.Table
import com.example.dclassmanagement.data.model.ActionWrapper
import com.example.dclassmanagement.data.model.Post
import com.example.dclassmanagement.data.model.User
import com.example.dclassmanagement.data.utils.Utils
import com.example.dclassmanagement.data.utils.show
import com.example.dclassmanagement.ui.base.ActionDispatcher
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter constructor(
    private val actionDispatcher: ActionDispatcher,
    private val data: List<Post>
) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var userRef = FirebaseDatabase.getInstance().getReference(Table.USER)
    private val likeRef = FirebaseDatabase.getInstance().getReference(Table.LIKE)
    private val cmtRef = FirebaseDatabase.getInstance().getReference(Table.COMMENT)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            actionDispatcher,
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class PostViewHolder(private val actionDispatcher: ActionDispatcher, itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bindView(item: Post) {
            itemView.tv_post_content.text = item.content
            itemView.tv_created_date.text = Utils.getDateFromMillisecond(item.createdDate)
            // set class background
            if (item.thumb.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(item.thumb)
                    .into(itemView.iv_post_thumb)
                itemView.iv_post_thumb.show()
            }

            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val user = it.getValue(User::class.java)
                        if (user?.id == item.ownerId) {
                            itemView.tv_user_full_name.text = user.fullname
                            itemView.tv_user_name.text = user.username
                            if (user.imageUrl.isNotEmpty()) {
                                Glide.with(itemView.context)
                                    .load(user.imageUrl)
                                    .into(itemView.iv_user)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

            // init like count
            val likeQuery = likeRef.orderByChild("postId").equalTo(item.id)
            likeQuery.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    itemView.tv_like_count.text = snapshot.childrenCount.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

            // init comment count
            val commentQuery = cmtRef.orderByChild("postId").equalTo(item.id)
            commentQuery.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    itemView.tv_comment_count.text = snapshot.childrenCount.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

            itemView.bt_like.setOnClickListener {
                actionDispatcher.dispatch(ActionWrapper(name = Action.Item.LIKE_POST, payload = item))
                (it as ImageView).setImageResource(R.drawable.ic_baseline_favorite_24)

            }
            itemView.bt_comment.setOnClickListener {
                 actionDispatcher.dispatch(ActionWrapper(name = Action.Item.COMMENT_POST, payload = item))
            }
        }
    }

}


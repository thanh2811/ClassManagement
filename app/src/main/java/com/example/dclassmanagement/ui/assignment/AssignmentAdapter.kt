package com.example.dclassmanagement.ui.assignment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dclassmanagement.R
import com.example.dclassmanagement.data.Table
import com.example.dclassmanagement.data.model.ActionWrapper
import com.example.dclassmanagement.data.model.Assignment
import com.example.dclassmanagement.data.utils.Utils
import com.example.dclassmanagement.ui.base.ActionDispatcher
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.item_assignment.view.*

class AssignmentAdapter constructor(
    private val actionDispatcher: ActionDispatcher,
    private val data: List<Assignment>
) :
    RecyclerView.Adapter<AssignmentAdapter.CommentViewHolder>() {

    private val assignmentUserRef =
        FirebaseDatabase.getInstance().getReference(Table.ASSIGNMENT_USER)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            actionDispatcher,
            LayoutInflater.from(parent.context).inflate(R.layout.item_assignment, parent, false)
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
        @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
        fun bindView(item: Assignment) {
            itemView.tv_assignment_title.text = item.title
            itemView.tv_assignment_date.text =
                "Từ ${Utils.getDateFromMillisecond(item.createdDate)} đến ${
                    Utils.getDateFromMillisecond(item.dueDate)
                }"

            // who is the commenter
//            userRef.child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addListenerForSingleValueEvent(
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


            itemView.setOnClickListener {
                actionDispatcher.dispatch(actionWrapper = ActionWrapper(payload = item))
            }
        }
    }

}


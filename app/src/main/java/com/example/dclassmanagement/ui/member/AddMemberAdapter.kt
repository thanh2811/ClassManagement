package com.example.dclassmanagement.ui.member

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dclassmanagement.R
import com.example.dclassmanagement.data.Action
import com.example.dclassmanagement.data.Table
import com.example.dclassmanagement.data.model.ActionWrapper
import com.example.dclassmanagement.data.model.User
import com.example.dclassmanagement.ui.base.ActionDispatcher
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.item_add_user.view.*
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_member.view.*
import kotlinx.android.synthetic.main.item_member.view.tv_full_name
import kotlinx.android.synthetic.main.item_member.view.tv_username

class AddMemberAdapter constructor(
    private val actionDispatcher: ActionDispatcher,
    private val data: List<User>
) :
    RecyclerView.Adapter<AddMemberAdapter.MemberViewHolder>() {

    private val userRef = FirebaseDatabase.getInstance().getReference(Table.USER)
    private val assignmentUserRef =
        FirebaseDatabase.getInstance().getReference(Table.ASSIGNMENT_USER)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        return MemberViewHolder(
            actionDispatcher,
            LayoutInflater.from(parent.context).inflate(R.layout.item_add_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MemberViewHolder(private val actionDispatcher: ActionDispatcher, itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
        fun bindView(item: User) {
            itemView.tv_username_add.text = "@${item.username}"
            itemView.tv_full_name_add.text = item.fullname
            if (item.imageUrl.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(item.imageUrl)
                    .into(itemView.iv_user)
            }
            itemView.cb_add.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    actionDispatcher.dispatch(
                        actionWrapper = ActionWrapper(
                            name = Action.Item.ITEM_CHECK, payload = item
                        )
                    )
                } else{
                    actionDispatcher.dispatch(
                        actionWrapper = ActionWrapper(
                            name = Action.Item.ITEM_UNCHECK, payload = item
                        )
                    )
                }
            }
        }
    }

}


package com.example.dclassmanagement.ui.landing_page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dclassmanagement.R
import com.example.dclassmanagement.data.ACTION_CLASS_PICK
import com.example.dclassmanagement.data.Table
import com.example.dclassmanagement.data.model.ActionWrapper
import com.example.dclassmanagement.data.model.ClassData
import com.example.dclassmanagement.data.model.User
import com.example.dclassmanagement.ui.base.ActionDispatcher
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.item_class.view.*

class ClassAdapter constructor(
    private val actionDispatcher: ActionDispatcher,
    private val data: List<ClassData>
) :
    RecyclerView.Adapter<ClassAdapter.ClassViewHolder>() {

    private var userRef = FirebaseDatabase.getInstance().getReference(Table.USER)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        return ClassViewHolder(
            actionDispatcher,
            LayoutInflater.from(parent.context).inflate(R.layout.item_class, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ClassViewHolder(val actionDispatcher: ActionDispatcher, itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bindView(item: ClassData) {
            itemView.tv_class_name.text = item.name
            // set class background
            if (item.thumb.isNotEmpty())
                Glide.with(itemView.context)
                    .load(item.thumb)
                    .into(itemView.iv_group_thumb)

            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val user = it.getValue(User::class.java)
                        if (user?.id == item.ownerId) {
                            itemView.tv_class_owner.text = user.fullname
                            if(user.imageUrl != "default") {
                                Glide.with(itemView.context)
                                    .load(user.imageUrl )
                                    .into(itemView.iv_owner_thumb)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
            itemView.setOnClickListener {
                actionDispatcher.dispatch(ActionWrapper(name = ACTION_CLASS_PICK, payload = item))
            }
        }
    }

}


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
import com.example.dclassmanagement.data.model.MultiChoice
import com.example.dclassmanagement.data.model.MultiChoiceData
import com.example.dclassmanagement.data.utils.Utils
import com.example.dclassmanagement.ui.base.ActionDispatcher
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.item_assignment.view.*
import kotlinx.android.synthetic.main.item_multi_choice.view.*

class AssignmentMultiChoiceAdapter constructor(
    private val actionDispatcher: ActionDispatcher,
    private val data: List<MultiChoiceData>
) :
    RecyclerView.Adapter<AssignmentMultiChoiceAdapter.CommentViewHolder>() {

    private val assignmentUserRef =
        FirebaseDatabase.getInstance().getReference(Table.ASSIGNMENT_USER)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            actionDispatcher,
            LayoutInflater.from(parent.context).inflate(R.layout.item_multi_choice, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindView(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class CommentViewHolder(private val actionDispatcher: ActionDispatcher, itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
        fun bindView(item: MultiChoiceData, position: Int) {
            itemView.tv_mc_title.text = item.question
            itemView.rb_a.text = item.a
            itemView.rb_b.text = item.b
            itemView.rb_c.text = item.c
            itemView.rb_d.text = item.d

            var ans = ""

            itemView.rb_a.setOnClickListener {
                it.isSelected = true
                ans = "a"
                actionDispatcher.dispatch(ActionWrapper(name = ans, payload = position+1))
            }
            itemView.rb_b.setOnClickListener {
                it.isSelected = true
                ans = "b"
                actionDispatcher.dispatch(ActionWrapper(name = ans, payload = position+1))
            }
            itemView.rb_c.setOnClickListener {
                it.isSelected = true
                ans = "c"
                actionDispatcher.dispatch(ActionWrapper(name = ans, payload = position+1))
            }
            itemView.rb_d.setOnClickListener {
                it.isSelected = true
                ans = "d"
                actionDispatcher.dispatch(ActionWrapper(name = ans, payload = position+1))
            }

        }
    }

}


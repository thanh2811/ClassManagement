package com.example.dclassmanagement.ui.assignment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.dclassmanagement.R
import com.example.dclassmanagement.data.Table
import com.example.dclassmanagement.data.model.Assignment
import com.example.dclassmanagement.data.utils.Utils
import com.example.dclassmanagement.ui.base.BaseLoadingDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_create_assignment.*
import kotlinx.android.synthetic.main.fragment_create_class.*
import kotlinx.android.synthetic.main.fragment_create_class.bt_cancel
import kotlinx.android.synthetic.main.fragment_create_class.bt_create
import kotlinx.android.synthetic.main.fragment_landing_page.*
import java.util.*

class FragmentCreateAssignment(val classId: String) : DialogFragment() {

    private lateinit var mRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private val assignmentRef = FirebaseDatabase.getInstance().getReference(Table.ASSIGNMENT)

    var dueDate = 0

    private val loadingDialog by lazy {
        BaseLoadingDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_create_assignment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_cancel.setOnClickListener {
            dismiss()
        }

        bt_create.setOnClickListener {
            createClass()
        }

        et_due_date.setOnClickListener{
            val selectedYear = 2022
            val selectedMonth = 5
            val selectedDayOfMonth = 4

            val dateSetListener =
                OnDateSetListener { view, year, month, dayOfMonth ->
                    et_due_date.text = Editable.Factory.getInstance().newEditable(dayOfMonth.toString() + "/" + (month + 1) + "/" + year)
                    val c = Calendar.getInstance()
                    c.set(year, month, dayOfMonth)
                    dueDate = c.timeInMillis.toInt()
                }

            val datePickerDialog = context?.let { it1 ->
                DatePickerDialog(
                    it1,
                    dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth
                )
            }

            datePickerDialog?.show()
        }
    }

    private fun createClass() {
        val assignmentName = et_assignment_name.text.toString()
        val assignmentDetail = et_assignment_detail.text.toString()
        val assignmentDate = dueDate
        if(assignmentName.isEmpty() || assignmentDetail.isEmpty()) {
            Toast.makeText(context, "All fields must be filled!", Toast.LENGTH_SHORT).show()
            return
        }
        loadingDialog.show(childFragmentManager, null)
        val owner = auth.currentUser?.uid
        val assignment = Assignment(Utils.createId(), 0, assignmentName, assignmentDetail, System.currentTimeMillis(), assignmentDate.toLong(), classId)

        assignmentRef.child(classId).push().setValue(assignment).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(context, "Create Assignment successfully!", Toast.LENGTH_SHORT).show()
                dismiss()
                // create User_Class table, because it's many to many relationship
            }
            loadingDialog.dismiss()
        }.addOnFailureListener {
            loadingDialog.dismiss()
        }
    }

}
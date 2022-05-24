package com.example.dclassmanagement.ui.landing_page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.dclassmanagement.R
import com.example.dclassmanagement.data.utils.Utils
import com.example.dclassmanagement.ui.base.BaseLoadingDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_create_class.*
import kotlinx.android.synthetic.main.fragment_landing_page.*

class FragmentCreateClass: DialogFragment() {

    private lateinit var fireStore: FirebaseFirestore
    private lateinit var mRef: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private val loadingDialog by lazy {
        BaseLoadingDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fireStore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_create_class, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_cancel.setOnClickListener {
            dismiss()
        }

        bt_create.setOnClickListener {
            createClass()
        }
    }

    private fun createClass() {
        val className = et_class_name.text.toString()
        if(className.isEmpty()) {
            Toast.makeText(context, "Name must be filled!", Toast.LENGTH_SHORT).show()
            return
        }
        loadingDialog.show(childFragmentManager, null)
        val classDes = et_class_des.text.toString()
        val owner = auth.currentUser?.uid
        val classId = Utils.createId()
        val myClass = hashMapOf(
            "id" to classId,
            "name" to className,
            "des" to classDes,
            "ownerId" to owner,
            "schedule" to "default",
            "thumb" to "",
            "createdDate" to System.currentTimeMillis().toString()
        )
        mRef = FirebaseDatabase.getInstance().getReference("Class").child(classId)
        mRef.setValue(myClass).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(context, "Create class successfully!", Toast.LENGTH_SHORT).show()
                // create User_Class table, because it's many to many relationship
                val mRefUserClass = FirebaseDatabase.getInstance().getReference("UserClass").child(Utils.createId())
                val userClass = hashMapOf(
                    "userId" to owner,
                    "classId" to classId,
                )
                mRefUserClass.setValue(userClass).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(context, "Class is created successfully!", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }
            }
            loadingDialog.dismiss()
        }.addOnFailureListener {
            loadingDialog.dismiss()
        }
    }

}
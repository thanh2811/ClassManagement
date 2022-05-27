package com.example.dclassmanagement.ui.assignment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import kotlinx.android.synthetic.main.fragment_mark_success.*
import java.util.*

class DialogMarkSuccess(val score: Double) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_mark_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_cancel.setOnClickListener {
            dismiss()
        }
        view.findViewById<TextView>(R.id.tv_score).text =
            "Bạn đã hoàn thành bài thi với số điểm $score"
    }


}
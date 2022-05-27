package com.example.dclassmanagement.ui.assignment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dclassmanagement.R
import com.example.dclassmanagement.data.FragmentAssignmentARGS
import com.example.dclassmanagement.data.FragmentHomeARGS
import com.example.dclassmanagement.data.FragmentLandingPageARGS
import com.example.dclassmanagement.data.Table
import com.example.dclassmanagement.data.model.ActionWrapper
import com.example.dclassmanagement.data.model.Assignment
import com.example.dclassmanagement.data.utils.show
import com.example.dclassmanagement.databinding.FragmentAssignmentBinding
import com.example.dclassmanagement.navigation.Navigator.requestNavigate
import com.example.dclassmanagement.ui.base.ActionDispatcher
import com.example.dclassmanagement.ui.base.ActionExecutor
import com.example.dclassmanagement.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentAssignment : BaseFragment() {

    private lateinit var binding: FragmentAssignmentBinding
    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private val assignmentRef = FirebaseDatabase.getInstance().getReference(Table.ASSIGNMENT)
    private val assignmentUserRef =
        FirebaseDatabase.getInstance().getReference(Table.ASSIGNMENT_USER)
    private var assignmentList = mutableListOf<Assignment>()
    var classId = ""
    var ownerId = ""

    private lateinit var assignmentAdapter: AssignmentAdapter

    private val actionDispatcher = ActionDispatcher(object : ActionExecutor {
        override fun dispatch(actionWrapper: ActionWrapper?) {
            val assignment = actionWrapper?.payload as Assignment
            requestNavigate(
                R.id.fragmentAssignmentDetail, bundleOf(
                    FragmentAssignmentARGS.KEY.ASSIGNMENT to assignment,
                    FragmentLandingPageARGS.KEY.CLASS_ID to classId,
                    FragmentLandingPageARGS.KEY.OWNER_ID to ownerId
                )
            )

        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAssignmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData() {
        classId = arguments?.getString(FragmentLandingPageARGS.KEY.CLASS_ID).toString()
        ownerId = arguments?.getString(FragmentLandingPageARGS.KEY.OWNER_ID).toString()
        super.initData()
    }

    override fun initView() {
        super.initView()
        assignmentAdapter = AssignmentAdapter(actionDispatcher, assignmentList)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvAssignment.layoutManager = layoutManager
        binding.rvAssignment.adapter = assignmentAdapter

        assignmentRef.child(classId)
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    assignmentList.clear()
                    snapshot.children.forEach {
                        val assignment = it.getValue(Assignment::class.java)
                        if (assignment != null) {
                            assignmentList.add(assignment)
                        }
                    }
                    assignmentAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    override fun initListener() {
        super.initListener()
        if (firebaseUser?.uid == ownerId) {
            binding.btCreateAssignment.show()
            binding.btCreateAssignment.setOnClickListener {
                FragmentCreateAssignment(classId).show(childFragmentManager, null)
            }
        }

    }


}
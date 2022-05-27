package com.example.dclassmanagement.ui.assignment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dclassmanagement.data.FragmentAssignmentARGS
import com.example.dclassmanagement.data.FragmentLandingPageARGS
import com.example.dclassmanagement.data.Table
import com.example.dclassmanagement.data.model.*
import com.example.dclassmanagement.data.utils.Utils
import com.example.dclassmanagement.data.utils.show
import com.example.dclassmanagement.databinding.FragmentAssignmentDetailBinding
import com.example.dclassmanagement.ui.base.ActionDispatcher
import com.example.dclassmanagement.ui.base.ActionExecutor
import com.example.dclassmanagement.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FragmentAssignmentDetail : BaseFragment() {

    val assignmentDetailViewModel: AssignmentDetailViewModel by viewModels()

    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private val assignmentRef = FirebaseDatabase.getInstance().getReference(Table.ASSIGNMENT)
    private val assignmentUserRef =
        FirebaseDatabase.getInstance().getReference(Table.ASSIGNMENT_USER)
    private val userRef = FirebaseDatabase.getInstance().getReference(Table.USER)
    private val storageRef = FirebaseStorage.getInstance()
    var classId = ""
    var ownerId = ""
    var finalAns = ""
    lateinit var selectedFile: Uri
    private lateinit var assignment: Assignment
    private lateinit var binding: FragmentAssignmentDetailBinding

    private lateinit var multiChoiceAdapter: AssignmentMultiChoiceAdapter
    private var ans = hashMapOf<Int, String>()

    private val actionDispatcher = ActionDispatcher(object : ActionExecutor {
        override fun dispatch(actionWrapper: ActionWrapper?) {
            val position = actionWrapper?.payload as Int
            when (actionWrapper.name) {
                "a" -> {
                    ans[position] = "a"
                }
                "b" -> {
                    ans[position] = "b"
                }
                "c" -> {
                    ans[position] = "c"
                }
                "d" -> {
                    ans[position] = "d"
                }
            }

        }
    })

    override fun initData() {
        super.initData()
        classId = arguments?.getString(FragmentLandingPageARGS.KEY.CLASS_ID).toString()
        ownerId = arguments?.getString(FragmentLandingPageARGS.KEY.OWNER_ID).toString()
        assignment = arguments?.getSerializable(FragmentAssignmentARGS.KEY.ASSIGNMENT) as Assignment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAssignmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        super.initView()

        userRef.child(ownerId).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val user = it.result.getValue(User::class.java)
                binding.tvAgmUsername.text = user?.fullname
                Glide.with(requireContext())
                    .load(user?.imageUrl)
                    .into(binding.ivAssignmentOwner)
            }
        }

        if (assignment.type == 1) {
            Log.d("abcabsbdasd", "initView")
            multiChoiceAdapter = AssignmentMultiChoiceAdapter(actionDispatcher, MultiChoice().data)
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.rvAgmMultiChoice.layoutManager = layoutManager
            binding.rvAgmMultiChoice.adapter = multiChoiceAdapter
        } else
            binding.btChooseFile.show()

        binding.tvAgmTitle.text = assignment.title
        binding.tvAgmDetail.text = assignment.content
        binding.tvAgmDueDate.text = "Đến hạn " + Utils.getDateFromMillisecond(assignment.dueDate)
        binding.tvAgmCreatedDate.text =
            "Lúc " + Utils.getDateFromMillisecond(assignment.createdDate)
    }

    override fun initListener() {
        super.initListener()
        binding.btSubmit.setOnClickListener {
            showLoading()
            if (assignment.type == 1) {
                finalAns = ""
                ans.forEach { (key, value) ->
                    finalAns += "${key}-$value,"
                }
                Log.d("final ans", finalAns)
                assignmentDetailViewModel.getScore(MarkRequest(1, finalAns))
            } else {
                submitFile()
            }
        }

        assignmentDetailViewModel.agmResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            if (it.statusCode == 200) {

                GlobalScope.launch(Dispatchers.Main) {
                    delay(2000)
                    DialogMarkSuccess(score = it.score).show(childFragmentManager, null)
                }
            }
        })

        binding.btChooseFile.setOnClickListener {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)


            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
        }
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "super.onActivityResult(requestCode, resultCode, data)",
            "com.example.dclassmanagement.ui.base.BaseFragment"
        )
    )
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == RESULT_OK) {
            selectedFile = data?.data!! //The uri with the location of the file
            Log.d("selected", selectedFile.toString())

        }
    }

    private fun submitFile() {
        if (this::selectedFile.isInitialized)
            storageRef.getReference("Assignment").child(assignment.id)
                .child(firebaseUser?.uid.toString())
                .putFile(selectedFile).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast("Upload Successfully!")
                        hideLoading()
                    }
                }
    }

}
package com.example.dclassmanagement.ui.class_detail

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dclassmanagement.R
import com.example.dclassmanagement.data.Action
import com.example.dclassmanagement.data.FragmentHomeARGS
import com.example.dclassmanagement.data.FragmentLandingPageARGS
import com.example.dclassmanagement.data.Table
import com.example.dclassmanagement.data.model.*
import com.example.dclassmanagement.data.utils.Utils
import com.example.dclassmanagement.databinding.FragmentHomeBinding
import com.example.dclassmanagement.navigation.Navigator.requestNavigate
import com.example.dclassmanagement.ui.base.ActionDispatcher
import com.example.dclassmanagement.ui.base.ActionExecutor
import com.example.dclassmanagement.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentHome : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    private val classRef = FirebaseDatabase.getInstance().getReference(Table.CLASS)
    private val userRef = FirebaseDatabase.getInstance().getReference(Table.USER)
    private val postRef = FirebaseDatabase.getInstance().getReference(Table.POST)
    private val likeRef = FirebaseDatabase.getInstance().getReference(Table.LIKE)
    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private var postList = mutableListOf<Post>()
    var classId = ""
    var ownerId = ""

    private lateinit var postAdapter: PostAdapter

    private val actionDispatcher = ActionDispatcher(object : ActionExecutor {
        override fun dispatch(actionWrapper: ActionWrapper?) {
            val post = actionWrapper?.payload as Post
            when (actionWrapper.name) {
                Action.Item.LIKE_POST -> {
                    val likeData = LikeData(post.id, firebaseUser?.uid ?: "")
                    likeRef.push().setValue(likeData).addOnCompleteListener {

                    }
                }
                Action.Item.COMMENT_POST -> {
                    requestNavigate(R.id.fragmentComment, bundleOf(
                        FragmentHomeARGS.KEY.POST to post
                    ))

                }
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        Log.d("abcabsbdasd", "onCreateView")
        return binding.root
    }

    override fun initData() {
        Log.d("abcabsbdasd", "initData")
        classId = arguments?.getString(FragmentLandingPageARGS.KEY.CLASS_ID).toString()
        ownerId = arguments?.getString(FragmentLandingPageARGS.KEY.OWNER_ID).toString()

//        userClassRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                snapshot.children.forEach {
//                    val userClass = it.getValue(UserClass::class.java)
//                    if(user?.id == ownerId){
//                        binding.tvClassOwner.text = user.fullname
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//        })
        super.initData()
    }

    override fun initView() {
        super.initView()

        Log.d("abcabsbdasd", "initView")
        postAdapter = PostAdapter(actionDispatcher, postList)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPost.layoutManager = layoutManager
        binding.rvPost.adapter = postAdapter

        classRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val classDataIterator = it.getValue(ClassData::class.java)
                    if (classDataIterator?.id == classId) {
                        binding.tvGroupName.text = classDataIterator.name
                        // set class background
                        if (this@FragmentHome.isVisible)
                            Glide.with(requireContext())
                            .load(classDataIterator.thumb)
                            .into(binding.ivClassThumb)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val user = it.getValue(User::class.java)
                    if (user?.id == ownerId) {
                        binding.tvClassOwner.text = user.fullname
                        if (user.imageUrl != "default") {
                            Glide.with(requireContext())
                                .load(user.imageUrl)
                                .into(binding.ivUser)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        postRef.orderByChild("classId").equalTo(classId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val post = it.getValue(Post::class.java)
                        if (post != null) {
                            postList.add(post)

                        }
                    }
                    postAdapter.notifyItemInserted(postList.size)

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


    }

    override fun initListener() {
        super.initListener()
        binding.btPost.setOnClickListener {
            val content = binding.etPostStatus.text.toString()
            if (content.isEmpty()) {
                showToast("Content must be filled!")
            } else {
                showLoading()
                val postId = Utils.createId()
                val ownerId = firebaseUser?.uid.toString()
                val type = 0L
                val thumb = ""
                val createdDate = System.currentTimeMillis()

                val postData = Post(postId, ownerId, classId, type, thumb, content, createdDate)


                postRef.push().setValue(postData).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast("Your status is posted successfully!")
                        binding.etPostStatus.text = Editable.Factory.getInstance().newEditable("")


                    }
                    hideLoading()
                }
                val a = 3
            }
        }
    }


}
package com.example.dclassmanagement.ui.comment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.compose.animation.core.snap
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dclassmanagement.data.FragmentHomeARGS
import com.example.dclassmanagement.data.Table
import com.example.dclassmanagement.data.model.ActionWrapper
import com.example.dclassmanagement.data.model.CommentData
import com.example.dclassmanagement.data.model.Post
import com.example.dclassmanagement.data.utils.gone
import com.example.dclassmanagement.data.utils.show
import com.example.dclassmanagement.databinding.FragmentCommentBinding
import com.example.dclassmanagement.ui.base.ActionDispatcher
import com.example.dclassmanagement.ui.base.ActionExecutor
import com.example.dclassmanagement.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentComment : BaseFragment() {

    private val likeRef = FirebaseDatabase.getInstance().getReference(Table.LIKE)
    private val commentRef = FirebaseDatabase.getInstance().getReference(Table.COMMENT)
    private val firebaseUser = FirebaseAuth.getInstance().currentUser

    private lateinit var post: Post
    private lateinit var binding: FragmentCommentBinding

    private lateinit var commentAdapter: CommentAdapter
    private var commentList = mutableListOf<CommentData>()

    private val actionDispatcher = ActionDispatcher(object : ActionExecutor {
        override fun dispatch(actionWrapper: ActionWrapper?) {
            val post = actionWrapper?.payload as CommentData
            when (actionWrapper.name) {

            }
        }
    })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData() {
        super.initData()
        post = arguments?.getSerializable(FragmentHomeARGS.KEY.POST) as Post
    }

    override fun initView() {
        super.initView()

        // init rv
        commentAdapter = CommentAdapter(actionDispatcher, commentList)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvComment.layoutManager = layoutManager
        binding.rvComment.adapter = commentAdapter

        // init like count
        val likeQuery = likeRef.orderByChild("postId").equalTo(post.id)
        likeQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.childrenCount == 0L)
                    binding.tvLikeCount.text = "Hãy là người đầu tiên thích bài viết này"
                else
                    binding.tvLikeCount.text = "${snapshot.childrenCount} người thích bài viết này"
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        // init comment count
        val commentQuery = commentRef.orderByChild("postId").equalTo(post.id)
        commentQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.childrenCount == 0L)
                    binding.tvNoComment.show()
                else {
                    binding.tvNoComment.gone()
                    val oldSize = commentList.size
                    commentList.clear()
                    snapshot.children.forEach { snap ->
                        snap.getValue(CommentData::class.java)?.let { commentList.add(it) }
                    }
                    commentAdapter.notifyItemRangeInserted(oldSize, commentList.size)
                    binding.rvComment.smoothScrollToPosition(commentAdapter.itemCount - 1)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun initListener() {
        super.initListener()
        binding.btSendComment.setOnClickListener {
            val content = binding.etComment.text.toString()
            if (content.isNotEmpty()) {
                commentRef.push().setValue(
                    CommentData(
                        postId = post.id, userId = firebaseUser?.uid
                            ?: "", content = content
                    )
                ).addOnCompleteListener {

                }
                binding.etComment.text = Editable.Factory.getInstance().newEditable("")
            }
        }
    }

}
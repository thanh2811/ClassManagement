package com.example.dclassmanagement.ui.landing_page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dclassmanagement.R
import com.example.dclassmanagement.data.FragmentLandingPageARGS
import com.example.dclassmanagement.data.Table
import com.example.dclassmanagement.data.model.ActionWrapper
import com.example.dclassmanagement.data.model.ClassData
import com.example.dclassmanagement.data.model.UserClass
import com.example.dclassmanagement.navigation.Navigator.requestNavigate
import com.example.dclassmanagement.ui.base.ActionDispatcher
import com.example.dclassmanagement.ui.base.ActionExecutor
import com.example.dclassmanagement.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_landing_page.*

class FragmentLandingPage : BaseFragment() {

    private lateinit var adapter: ClassAdapter
    private var classList = mutableListOf<ClassData>()
    private lateinit var mRef: DatabaseReference
    private lateinit var firebaseUser: FirebaseUser

    private val actionDispatcher = ActionDispatcher(object : ActionExecutor {
        override fun dispatch(actionWrapper: ActionWrapper?) {
            val classItem = actionWrapper?.payload as ClassData
            val args = bundleOf(
                FragmentLandingPageARGS.KEY.CLASS_ID to classItem.id,
                FragmentLandingPageARGS.KEY.OWNER_ID to classItem.ownerId
            )
            requestNavigate(R.id.fragmentClassDetail, args)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing_page, container, false)
    }

    override fun initData() {
        super.initData()
        adapter = ClassAdapter(actionDispatcher, classList)
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
    }

    override fun initView() {
        super.initView()
        val layoutManager = GridLayoutManager(context, 2)
        rv_class.layoutManager = layoutManager
        rv_class.adapter = adapter
    }

    override fun initListener() {
        super.initListener()
        showLoading()
        bt_create_class.setOnClickListener {
            FragmentCreateClass().show(childFragmentManager, null)
        }

        mRef = FirebaseDatabase.getInstance().getReference(Table.USER_CLASS)
        val query = mRef.orderByChild("userId").equalTo(firebaseUser.uid)
        query.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val userClass = snapshot.getValue(UserClass::class.java)
                classList.clear()
                if (userClass != null) {
                    getAllClassByUserId(userClass.classId)
                }
                hideLoading()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
//        mRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.d("snapshot", snapshot.toString())
//                classList.clear()
//                snapshot.children.forEach {
//                    val userClass = it.getValue(UserClass::class.java)
//                    if (userClass?.userId == firebaseUser.uid) getAllClassByUserId(userClass.classId)
//                }
//                hideLoading()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                hideLoading()
//
//            }
//
//        })
    }

    private fun getAllClassByUserId(classId: String) {
        val classRef = FirebaseDatabase.getInstance().getReference(Table.CLASS)
        classRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    if (it.key == classId) {
                        val classData = it.getValue(ClassData::class.java)
                        classList.add(classData!!)
                    }
                }
                classList.sortBy { it.createdDate.toLong() }
                classList.reverse()
                adapter.notifyDataSetChanged()
                hideLoading()

            }

            override fun onCancelled(error: DatabaseError) {
                hideLoading()

            }

        })
    }

}
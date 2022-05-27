package com.example.dclassmanagement.ui.class_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dclassmanagement.R
import com.example.dclassmanagement.data.FragmentLandingPageARGS
import com.example.dclassmanagement.ui.assignment.FragmentAssignment
import com.example.dclassmanagement.ui.base.BaseFragment
import com.example.dclassmanagement.ui.member.FragmentMember
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_class_detail.*

class FragmentClassDetail : BaseFragment() {
    private var classId = ""
    private var ownerId = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_detail, container, false)
    }

    override fun initData() {
        classId = arguments?.getString(FragmentLandingPageARGS.KEY.CLASS_ID).toString()
        ownerId = arguments?.getString(FragmentLandingPageARGS.KEY.OWNER_ID).toString()
        super.initData()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun initView() {
        super.initView()
        vp_container.adapter = CustomViewPagerAdapter(this, classId, ownerId)
        TabLayoutMediator(bottom_menu, vp_container) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Home"
                    tab.icon = resources.getDrawable(R.drawable.ic_home, context?.theme)
                }
                1 -> {
                    tab.text = "Assignment"
                    tab.icon =
                        resources.getDrawable(R.drawable.ic_baseline_assignment_24, context?.theme)
                }
                2 -> {
                    tab.text = "Member"
                    tab.icon =
                        resources.getDrawable(R.drawable.ic_baseline_people_24, context?.theme)
                }
            }
        }.attach()
    }


}

private class CustomViewPagerAdapter(fragment: Fragment, val classId: String, val ownerId: String) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                val fragmentHome = FragmentHome()
                fragmentHome.arguments = bundleOf(
                    FragmentLandingPageARGS.KEY.CLASS_ID to classId,
                    FragmentLandingPageARGS.KEY.OWNER_ID to ownerId
                )
                return fragmentHome
            }
            1 -> {
                val fragmentAssignment = FragmentAssignment()
                fragmentAssignment.arguments = bundleOf(
                    FragmentLandingPageARGS.KEY.CLASS_ID to classId,
                    FragmentLandingPageARGS.KEY.OWNER_ID to ownerId
                )
                return fragmentAssignment
            }
        }
        val fragmentMember = FragmentMember()
        fragmentMember.arguments = bundleOf(
            FragmentLandingPageARGS.KEY.CLASS_ID to classId,
            FragmentLandingPageARGS.KEY.OWNER_ID to ownerId
        )
        return fragmentMember
//        return mCurrentFragment
    }
}

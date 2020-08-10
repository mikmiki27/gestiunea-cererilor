package com.example.gestiuneacererilor.ui.cereri

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gestiuneacererilor.ui.cereri.cereriStudent.CereriStudentFragment
import com.example.gestiuneacererilor.ui.cereri.listaprofesori.ListaProfesori
import com.example.gestiuneacererilor.ui.sedinte.OnRequestItemClicked


class CereriPagerAdapter(
    fa: FragmentActivity,
    private var onRequestItemClicked: OnRequestItemClicked
) : FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment = when (position) {
        MY_REQUESTS_POSITION -> ListaProfesori(onRequestItemClicked)
        PENDING_REQUESTS_POSITION -> CereriStudentFragment(onRequestItemClicked)
        else -> throw IllegalStateException("Invalid adapter position")
    }

    override fun getItemCount(): Int = NUM_PAGES

    companion object {
        internal const val NUM_PAGES = 2
        internal const val MY_REQUESTS_POSITION = 0
        internal const val PENDING_REQUESTS_POSITION = 1
    }

}
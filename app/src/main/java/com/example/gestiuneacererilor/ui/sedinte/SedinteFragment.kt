package com.example.gestiuneacererilor.ui.sedinte

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SedinteFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sedinte, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as? BaseActivity<*>)?.supportActionBar?.title = getString(R.string.menu_sedinte)

        viewPager = view.findViewById(R.id.viewPager)
        viewPager.isSaveEnabled = false
        tabLayout = view.findViewById(R.id.tabLayout)
        setViewPager()
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Solicitari"
                else -> "Sedinte planificate"
            }
        }.attach()
    }

    private fun setViewPager() {
        val eventsViewPagerAdapter = EventsViewPagerAdapter(requireActivity(), null)
        viewPager.adapter = eventsViewPagerAdapter
    }
}
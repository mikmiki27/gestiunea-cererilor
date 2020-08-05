package com.example.gestiuneacererilor.ui.cereri

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.sedinte.EventsViewPagerAdapter
import com.example.gestiuneacererilor.ui.sedinte.OnRequestItemClicked
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_cereri.*
import kotlinx.android.synthetic.main.fragment_profile.*

class CereriFragment : Fragment(), View.OnClickListener{

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cereri, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as? BaseActivity<*>)?.supportActionBar?.title = getString(R.string.menu_cereri)

        viewPager = view.findViewById(R.id.viewPager)
        viewPager.isSaveEnabled = false
        tabLayout = view.findViewById(R.id.tabLayout)
        setViewPager()
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Profesori"
                else -> "Cererile mele"
            }
        }.attach()
    }


    private fun setViewPager() {
        val eventsViewPagerAdapter = CereriPagerAdapter(requireActivity(), object : OnRequestItemClicked {
            override fun onRequestItemClicked(id: String) {
                val bundle= bundleOf(Pair("id",id))
                view?.findNavController()?.navigate(R.id.action_menu_my_events_to_menu_details, bundle)
            }
        })
        viewPager.adapter = eventsViewPagerAdapter
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.card_view_student) {
           /* AlertDialog.Builder(context)
                .setTitle("Doresti sa adaugi studentul in echipa?")
                .setCancelable(false)
                .setPositiveButton("DA", null)
                .setNegativeButton("NU", null)
                .show()*/
        }
    }
}

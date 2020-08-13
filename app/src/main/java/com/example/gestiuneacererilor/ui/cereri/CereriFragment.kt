package com.example.gestiuneacererilor.ui.cereri

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.cereremanager.CerereManagerImplementation
import com.example.gestiuneacererilor.data.restmanager.CerereService
import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.base.BaseFragment
import com.example.gestiuneacererilor.ui.sedinte.OnRequestItemClicked
import com.example.gestiuneacererilor.utils.Constants
import com.example.gestiuneacererilor.utils.determineCurrentTypeUser
import com.example.gestiuneacererilor.utils.getCurrentUserEmail
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_cereri.*
import java.util.*
import kotlin.collections.ArrayList

class CereriFragment : BaseFragment<CereriMvp.Presenter>(),
    CereriMvp.View {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerViewForProfesor: RecyclerView
    private lateinit var myCereriForProfesorListAdapter: CereriForProfesorListAdapter
    private var requestsList: ArrayList<Cerere> = arrayListOf()

    companion object {
        private var onRequestItemClicked: ((Any) -> Unit)? = null

        fun newInstance(onRequestItemClicked: (Any) -> Unit): CereriFragment {
            this.onRequestItemClicked = onRequestItemClicked

            val fragment = CereriFragment()
            val args = Bundle()
            fragment.arguments = args

            return fragment
        }
    }

    override fun initializePresenter(): CereriMvp.Presenter {
        return CereriPresenter(
            this,
            requireContext(),
            CerereManagerImplementation.getInstance(CerereService.create())
        )
    }

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
        tabLayout = view.findViewById(R.id.tabLayout)
        recyclerViewForProfesor = view.findViewById(R.id.recyclerView_for_profesor)

        presenter.getAllCerere()

        when (determineCurrentTypeUser(getCurrentUserEmail(requireContext()))) {
            Constants.UserType.STUDENT -> {
                setViewsVisibilityForStudent()

                setViewPager()
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = when (position) {
                        0 -> "Profesori disponibili"
                        else -> "Cererile mele"
                    }
                }.attach()
            }
            Constants.UserType.PROFESSOR -> {
                setViewsVisibilityForProfesor()

                myCereriForProfesorListAdapter = CereriForProfesorListAdapter(
                    requireContext(),
                    requestsList
                )
                { tip_cerere ->
                    AlertDialog.Builder(context)
                        .setTitle(
                            String.format(
                                resources.getString(R.string.add_student_to_the_team),
                                tip_cerere.toString().toLowerCase(Locale.getDefault())
                            )
                        ) //todo test this
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.da)) { p0, p1 ->
                            Toast.makeText(requireContext(), "test DA", Toast.LENGTH_SHORT).show()
                            //todo status update to accepted, student fields updates, profesor team number update.
                        }
                        .setNegativeButton(getString(R.string.nu)) { p0, p1 ->
                            //todo status update to respins!!
                            Toast.makeText(requireContext(), "test NU", Toast.LENGTH_SHORT).show()
                        }
                        .show()
                    //todo test this
                }
                setupRecyclerView()
            }
        }
    }

    private fun setViewsVisibilityForStudent() {
        viewPager.isSaveEnabled = false
        viewPager.visibility = View.VISIBLE
        tabLayout.visibility = View.VISIBLE
        recyclerViewForProfesor.visibility = View.GONE
        textView_echipa_master.visibility = View.GONE
        textView_echipa_licenta.visibility = View.GONE
    }

    private fun setViewsVisibilityForProfesor() {
        recyclerViewForProfesor.visibility = View.VISIBLE
        textView_echipa_master.visibility = View.VISIBLE
        textView_echipa_licenta.visibility = View.VISIBLE
        viewPager.visibility = View.GONE
        tabLayout.visibility = View.GONE
    }

    private fun setViewPager() {
        val eventsViewPagerAdapter =
            CereriPagerAdapter(requireActivity(), object : OnRequestItemClicked {
                override fun onRequestItemClicked(id: String) {
                    val bundle = bundleOf(Pair("id", id))
                    view?.findNavController()
                        ?.navigate(R.id.action_menu_my_events_to_menu_details, bundle)
                }
            })
        viewPager.adapter = eventsViewPagerAdapter
    }

    private fun setupRecyclerView() {
        recyclerViewForProfesor.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = myCereriForProfesorListAdapter
            visibility = View.VISIBLE
        }
    }

    override fun filterListOfRequests(list: List<Cerere>): List<Cerere> {
        val filteredList = arrayListOf<Cerere>()
        for (cerere in list) {
            if (cerere.status == Constants.StatusCerere.PROGRES.name) {
                filteredList.add(cerere)
            }
        }
        return filteredList
    }

    override fun showCereriDisponibileForProfesor(list: List<Cerere>) {
        myCereriForProfesorListAdapter.apply {
            requestsList = ArrayList(filterListOfRequests(list))
            notifyDataSetChanged()
        }
    }

    override fun showPlaceholderForEmptylist() {
        placeholderEmpty.visibility = View.VISIBLE
    }

    override fun showPlaceholderForNetwork() {
        placeholderNetwork.visibility = View.VISIBLE
    }
}

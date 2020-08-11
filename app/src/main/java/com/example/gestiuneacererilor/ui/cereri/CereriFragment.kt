package com.example.gestiuneacererilor.ui.cereri

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

class CereriFragment : BaseFragment<CereriMvp.Presenter>(), View.OnClickListener,
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
                { _ ->
                    Toast.makeText(requireContext(), "test test 2 dialog", Toast.LENGTH_SHORT).show()
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
}

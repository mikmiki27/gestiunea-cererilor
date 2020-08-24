package com.example.gestiuneacererilor.ui.cereri

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.cereremanager.CerereManagerImplementation
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManagerImplementation
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManagerImplementation
import com.example.gestiuneacererilor.data.restmanager.CerereService
import com.example.gestiuneacererilor.data.restmanager.ProfesorService
import com.example.gestiuneacererilor.data.restmanager.StudentService
import com.example.gestiuneacererilor.data.restmanager.data.Cerere
import com.example.gestiuneacererilor.data.restmanager.data.Professor
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.base.BaseFragment
import com.example.gestiuneacererilor.utils.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.custom_alert_dialog_profesor.view.*
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
            CerereManagerImplementation.getInstance(CerereService.create()),
            ProfesorManagerImplementation.getInstance(ProfesorService.create()),
            StudentManagerImplementation.getInstance(StudentService.create())
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

        when (determineCurrentTypeUser(getCurrentUserEmail(requireContext()))) {
            Constants.UserType.STUDENT -> {
              //  presenter.getAllCerereForStudent()
                setViewsVisibilityForStudent()

                setViewPager()
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = when (position) {
                        0 -> requireContext().resources.getString(R.string.profesori_disponibili)
                        else -> requireContext().resources.getString(R.string.cererile_mele)
                    }
                }.attach()
            }
            Constants.UserType.PROFESSOR -> {
                setViewsVisibilityForProfesor()
                presenter.getAllCerereForProfesor(requireActivity())

                myCereriForProfesorListAdapter = CereriForProfesorListAdapter(
                    requireContext(),
                    requestsList
                )
                { cerereSelectata: Any ->
                    val myDialogView = LayoutInflater.from(context)
                        .inflate(R.layout.custom_alert_dialog_profesor, null)

                    lateinit var builder: AlertDialog.Builder

                    if ((cerereSelectata as Cerere).tip_cerere.toLowerCase(Locale.getDefault()) == Constants.TipCerere.LICENTA.name.toLowerCase(Locale.getDefault())) {
                        var text = resources.getString(R.string.add_student_to_the_team)
                        if (getProfesorLicentaEchipa(requireContext()).toInt() == 14) {
                            text = resources.getString(R.string.add_student_to_the_team_last_spot)
                            builder = AlertDialog.Builder(context)
                                .setView(myDialogView)
                                .setTitle(
                                    String.format(
                                        text,
                                        resources.getString(R.string.licenta)
                                    )
                                )
                                .setCancelable(true)
                            myDialogView.custom_alert_dialog_title.visibility = View.VISIBLE
                            myDialogView.custom_alert_dialog_title.text = String.format(
                                resources.getString(R.string.add_student_to_the_team),
                                resources.getString(R.string.licenta)
                            )
                        } else {
                            myDialogView.custom_alert_dialog_title.visibility = View.INVISIBLE
                            builder = AlertDialog.Builder(context)
                                .setView(myDialogView)
                                .setTitle(
                                    String.format(
                                        text,
                                        resources.getString(R.string.licenta)
                                    )
                                )
                                .setCancelable(true)
                        }
                    }

                    if (cerereSelectata.tip_cerere.toLowerCase(Locale.getDefault()) == Constants.TipCerere.DISERTATIE.name.toLowerCase(Locale.getDefault())) {
                        var text = resources.getString(R.string.add_student_to_the_team)
                        if (getProfesorMasterEchipa(requireContext()).toInt() == 14) {
                            text = resources.getString(R.string.add_student_to_the_team_last_spot)
                            builder = AlertDialog.Builder(context)
                                .setView(myDialogView)
                                .setTitle(
                                    String.format(
                                        text,
                                        resources.getString(R.string.disertatie)
                                    )
                                )
                                .setCancelable(true)
                            myDialogView.custom_alert_dialog_title.visibility = View.VISIBLE
                            myDialogView.custom_alert_dialog_title.text = String.format(
                                resources.getString(R.string.add_student_to_the_team),
                                resources.getString(R.string.disertatie)
                            )
                        } else {
                            myDialogView.custom_alert_dialog_title.visibility = View.INVISIBLE
                            builder = AlertDialog.Builder(context)
                                .setView(myDialogView)
                                .setTitle(
                                    String.format(
                                        text,
                                        resources.getString(R.string.disertatie)
                                    )
                                )
                                .setCancelable(true)
                        }
                    }

                    val myAlertDialog = builder.show()

                    myDialogView.custom_alert_dialog_buttonNo.setOnClickListener {
                        myAlertDialog.dismiss()
                        presenter.updateCerereToRespins(
                            Cerere(
                                cerereSelectata.id,
                                cerereSelectata.student_solicitant,
                                cerereSelectata.id_student,
                                cerereSelectata.email_student_solicitat,
                                cerereSelectata.facultate_student,
                                cerereSelectata.profesor_solicitat,
                                cerereSelectata.email_profesor_solicitat,
                                cerereSelectata.id_profesor,
                                Constants.StatusCerere.RESPINSA.name,
                                cerereSelectata.tip_cerere,
                                myDialogView.custom_alert_dialog_edittext.text.toString(),
                                cerereSelectata.mentiuni
                            )
                        )
                    }
                    myDialogView.custom_alert_dialog_buttonYes.setOnClickListener {
                        myAlertDialog.dismiss()
                        presenter.updateCerereToAccepted(
                            Cerere(
                                cerereSelectata.id,
                                cerereSelectata.student_solicitant,
                                cerereSelectata.id_student,
                                cerereSelectata.email_student_solicitat,
                                cerereSelectata.facultate_student,
                                cerereSelectata.profesor_solicitat,
                                cerereSelectata.email_profesor_solicitat,
                                cerereSelectata.id_profesor,
                                Constants.StatusCerere.ACCEPTATA.name,
                                cerereSelectata.tip_cerere,
                                myDialogView.custom_alert_dialog_edittext.text.toString(),
                                cerereSelectata.mentiuni
                            )
                        )
                    }
                }
                setupRecyclerView()
            }
        }
    }

    private fun setViewsVisibilityForStudent() {
        recyclerViewForProfesor.visibility = View.GONE
        textView_echipa_master.visibility = View.GONE
        textView_echipa_licenta.visibility = View.GONE
        viewPager.isSaveEnabled = false
        viewPager.visibility = View.VISIBLE
        tabLayout.visibility = View.VISIBLE
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
            CereriPagerAdapter(requireActivity())
        //todo verifica daca mai nmerge proful
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
            if (cerere.status.toLowerCase(Locale.getDefault()) == Constants.StatusCerere.PROGRES.name.toLowerCase(Locale.getDefault())
                && cerere.facultate_student == getCurrentUserFacultate(requireContext())
                && cerere.email_profesor_solicitat == getCurrentUserEmail(requireContext()) && cerere.id_profesor == getCurrentUserId(requireContext())) {
                filteredList.add(cerere)
            }
        }

        return filteredList
    }

    override fun showCereriDisponibileForProfesor(list: List<Cerere>) {
        val listaFiltrata = filterListOfRequests(list)
        if (listaFiltrata.isNotEmpty()) {
            Handler().postDelayed({
                if (doubleFilter(listaFiltrata).isNotEmpty()) {
                    myCereriForProfesorListAdapter.apply {
                        requestsList = ArrayList(doubleFilter(listaFiltrata))
                        notifyDataSetChanged()
                    }
                } else {
                    showPlaceholderForFullTeams()
                }
            }, 800)
        } else {
            showPlaceholderForEmptylist()
        }
    }

    private fun doubleFilter(filteredList: List<Cerere>): List<Cerere> {
        val filteredListFaraLicenta = arrayListOf<Cerere>()
        val filteredListFaraMaster = arrayListOf<Cerere>()
        val filteredListFaraAmbele = emptyList<Cerere>()

        val lungimeL = textView_echipa_licenta.text.length
        val lungimeD = textView_echipa_master.text.length

        if (textView_echipa_licenta.text.subSequence(lungimeL - 2, lungimeL).toString().trim().toInt() >= 15 &&
            textView_echipa_master.text.subSequence(lungimeD - 2, lungimeD).toString().trim().toInt() >= 15) {
            return filteredListFaraAmbele
        }

        if (textView_echipa_licenta.text.subSequence(lungimeL - 2, lungimeL).toString().trim().toInt() >= 15) {
            for (cerere in filteredList) {
                if (cerere.tip_cerere.toLowerCase(Locale.getDefault()) != Constants.TipCerere.LICENTA.name.toLowerCase(Locale.getDefault())) {
                    filteredListFaraLicenta.add(cerere)
                }
            }
            return filteredListFaraLicenta
        }

        if (textView_echipa_master.text.subSequence(lungimeD - 2, lungimeD).toString().trim().toInt() >= 15) {
            for (cerere in filteredList) {
                if (cerere.tip_cerere.toLowerCase(Locale.getDefault()) != Constants.TipCerere.DISERTATIE.name.toLowerCase(Locale.getDefault())) {
                    filteredListFaraMaster.add(cerere)
                }
            }
            return filteredListFaraMaster
        }
        return filteredList
    }

    private fun showPlaceholderForFullTeams() {
        placeholderEchipeComplete.visibility = View.VISIBLE
    }

    override fun showPlaceholderForEmptylist() {
        placeholderEmpty.visibility = View.VISIBLE
    }

    override fun showPlaceholderForNetwork() {
        placeholderNetwork.visibility = View.VISIBLE
    }

    override fun hideViewsForTeams() {
        textView_echipa_licenta.visibility = View.GONE
        textView_echipa_master.visibility = View.GONE
    }

    override fun showViewsForTeams(it: List<Professor>) {
        textView_echipa_licenta.visibility = View.VISIBLE
            textView_echipa_licenta.text = String.format(context?.resources!!.getString(R.string.numar_studenti_echipa_licenta, it[0].nr_studenti_echipa_licenta))
        textView_echipa_master.visibility = View.VISIBLE
            textView_echipa_master.text = String.format(context?.resources!!.getString(R.string.numar_studenti_echipa_master, it[0].nr_studenti_echipa_disertatie))
    }

    override fun goToEchipe() {
        view?.findNavController()?.navigate(R.id.action_menu_cereri_to_menu_echipa)
    }

    override fun goToCereri() {
        view?.findNavController()?.navigate(R.id.action_menu_cereri_self)
    }
}

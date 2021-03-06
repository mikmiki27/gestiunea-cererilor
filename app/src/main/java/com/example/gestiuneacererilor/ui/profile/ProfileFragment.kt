package com.example.gestiuneacererilor.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManagerImpl
import com.example.gestiuneacererilor.data.managers.profesormanager.ProfesorManagerImplementation
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManagerImplementation
import com.example.gestiuneacererilor.data.restmanager.ProfesorService
import com.example.gestiuneacererilor.data.restmanager.StudentService
import com.example.gestiuneacererilor.data.restmanager.data.Professor
import com.example.gestiuneacererilor.data.restmanager.data.Student
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.base.BaseFragment
import com.example.gestiuneacererilor.ui.onboarding.OnBoardingActivity
import com.example.gestiuneacererilor.utils.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment<ProfileMvp.Presenter>(), View.OnClickListener,
    ProfileMvp.View {

    private lateinit var typeUser: Constants.UserType
    var titluLucrareText: String = ""
    var titluLucrareTextBefore: String = ""
    var titluLucrareTextFixed: String = ""
    var cerinteLicentaText: String = ""
    var cerinteLicentaTextBefore: String = ""
    var cerinteLicentaTextFixed: String = ""
    var cerinteMasterText: String = ""
    var cerinteMasterTextBefore: String = ""
    var cerinteMasterTextFixed: String = ""

    override fun initializePresenter(): ProfileMvp.Presenter {
        return ProfilePresenter(
            this,
            requireContext(),
            FirebaseAuthManagerImpl.getInstance(),
            StudentManagerImplementation.getInstance(StudentService.create()),
            ProfesorManagerImplementation.getInstance(ProfesorService.create())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as? BaseActivity<*>)?.supportActionBar?.title = getString(R.string.menu_profile)

        sing_out_btn_profile.setOnClickListener(this)
        update_btn_profile.setOnClickListener(this)
        typeUser = determineCurrentTypeUser(FirebaseAuth.getInstance().currentUser?.email.toString())

        when (typeUser) {
            Constants.UserType.STUDENT -> {
                setVisibilityViewsForStudent()
                presenter.getStudentByEmail(requireActivity())
            }
            Constants.UserType.PROFESSOR -> {
                setVisibilityViewsForProfessor()
                presenter.getProfessorByEmail(requireActivity())
            }
        }

        titluLucrareTextFixed = titlu_lucrare_text.text.toString()
        cerinteLicentaTextFixed = cerinte_licenta_text.text.toString()
        cerinteMasterTextFixed = cerinte_master_text.text.toString()

        titlu_lucrare_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (!titlu_lucrare_text.text?.equals(titluLucrareTextFixed)!!) {
                    update_btn_profile.isEnabled = true
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                titluLucrareText = titlu_lucrare_text.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                titluLucrareTextBefore = titlu_lucrare_text.text.toString()
                if (!titlu_lucrare_text.text?.equals(titluLucrareTextFixed)!!) {
                    update_btn_profile.isEnabled = true
                }
            }
        })
        cerinte_licenta_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (!cerinte_licenta_text.text?.equals(cerinteLicentaTextFixed)!!) {
                    update_btn_profile.isEnabled = true
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                cerinteLicentaText = cerinte_licenta_text.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                cerinteLicentaTextBefore = cerinte_licenta_text.text.toString()
            }
        })
        cerinte_master_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (!cerinte_master_text?.equals(cerinteMasterTextFixed)!!) {
                    update_btn_profile.isEnabled = true
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                cerinteMasterText = cerinte_master_text.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                cerinteMasterTextBefore = cerinte_master_text.text.toString()
            }
        })
    }

    override fun setViewsForStudent(student: Student) {
        display_name_text.setText(
            String.format(
                resources.getString(R.string.template_for_two),
                student.prenume,
                student.nume
            )
        )
        facultate_text.setText(student.facultate)
        email_text.setText(student.email)
        an_text.setText(student.an)
        ciclu_text.setText(student.ciclu)
        profesor_coordonator_text.setText(student.profesor_coordonator)
        titlu_lucrare_text.setText(student.titlu_lucrare ?: "")
        activity?.let {
            SharedPrefUtil.addKeyValue(
                it, SharedPrefUtil.STUDENT_CURRENT_ID, student.id
                    ?: ""
            )
            SharedPrefUtil.addKeyValue(
                it, SharedPrefUtil.STUDENT_CURRENT_NUME, student.nume
                    ?: ""
            )
            SharedPrefUtil.addKeyValue(
                it, SharedPrefUtil.STUDENT_CURRENT_PRENUME, student.prenume
                    ?: ""
            )
        }
    }

    override fun getCurrentStudentDetails(): Student {
        return Student(
            id = getStudentCurrentID(requireContext()),
            nume = getStudentCurrentNume(requireContext()),
            prenume = getStudentCurrentPrenume(requireContext()),
            email = email_text.text.toString(),
            facultate = facultate_text.text.toString(),
            an = an_text.text.toString(),
            ciclu = ciclu_text.text.toString(),
            profesor_coordonator = profesor_coordonator_text.text.toString(),
            profesor_coordonator_full_name = getStudentProfesorCoordonatorFullName(requireContext()),
            id_profesor_coordonator = getStudentProfesorCoordonatorID(requireContext()),
            titlu_lucrare = titlu_lucrare_text.text.toString()
        )
    }

    override fun getCurrentProfesorDetails(): Professor {
        return Professor(
            id = getProfesorCurrentID(requireContext()),
            nume = getProfesorCurrentNume(requireContext()),
            prenume = getProfesorCurrentPrenume(requireContext()),
            email = email_text.text.toString(),
            cerinte_suplimentare_licenta = cerinte_licenta_text.text.toString(),
            cerinte_suplimentare_disertatie = cerinte_master_text.text.toString(),
            facultate = facultate_text.text.toString(),
            nr_studenti_echipa_licenta = echipa_licenta_text.text.toString(),
            nr_studenti_echipa_disertatie = echipa_master_text.text.toString(),
            studenti_licenta_acceptati = getCurrentLicentaAcceptati(requireContext()),
            studenti_disertatie_acceptati = getCurrentDisertatieAcceptati(requireContext())
        )
    }

    override fun setViewsForProfesor(profesor: Professor) {
        display_name_text.setText(
            String.format(
                resources.getString(R.string.template_for_two),
                profesor.prenume,
                profesor.nume
            )
        )
        facultate_text.setText(profesor.facultate)
        email_text.setText(profesor.email)
        cerinte_master_text.setText(profesor.cerinte_suplimentare_disertatie ?: "")
        cerinte_licenta_text.setText(profesor.cerinte_suplimentare_licenta ?: "")
        echipa_master_text.setText(profesor.nr_studenti_echipa_disertatie ?: "")
        echipa_licenta_text.setText(profesor.nr_studenti_echipa_licenta ?: "")
        activity?.let {
            SharedPrefUtil.addKeyValue(
                it, SharedPrefUtil.PROFESOR_CURRENT_ID, profesor.id
                    ?: ""
            )
            SharedPrefUtil.addKeyValue(
                it, SharedPrefUtil.PROFESOR_CURRENT_NUME, profesor.nume
                    ?: ""
            )
            SharedPrefUtil.addKeyValue(
                it, SharedPrefUtil.PROFESOR_CURRENT_PRENUME, profesor.prenume
                    ?: ""
            )
            SharedPrefUtil.addKeyValue(
                it, SharedPrefUtil.PROFESOR_LICENTA_ACCEPTATI, profesor.studenti_licenta_acceptati
                    ?: ""
            )
            SharedPrefUtil.addKeyValue(
                it, SharedPrefUtil.PROFESOR_DISERTATIE_ACCEPTATI, profesor.studenti_disertatie_acceptati
                    ?: ""
            )
        }
    }

    private fun setVisibilityViewsForStudent() {
        cerinte_master_profil.visibility = View.GONE
        profil_cerinte_master.visibility = View.GONE
        cerinte_licenta_profil.visibility = View.GONE
        profil_cerinte_licenta.visibility = View.GONE
        echipa_master_profil.visibility = View.GONE
        profil_echipa_master.visibility = View.GONE
        echipa_licenta_profil.visibility = View.GONE
        profil_echipa_licenta.visibility = View.GONE

        titlu_lucrare_profil.visibility = View.VISIBLE
        profil_titlu_lucrare.visibility = View.VISIBLE
        profesor_coordonator_profil.visibility = View.VISIBLE
        profile_profesor_coordoantor.visibility = View.VISIBLE
        ciclu_profil.visibility = View.VISIBLE
        ciclu_text.visibility = View.VISIBLE
        an_profil.visibility = View.VISIBLE
        an_text.visibility = View.VISIBLE
    }

    private fun setVisibilityViewsForProfessor() {
        cerinte_master_profil.visibility = View.VISIBLE
        profil_cerinte_master.visibility = View.VISIBLE
        cerinte_licenta_profil.visibility = View.VISIBLE
        profil_cerinte_licenta.visibility = View.VISIBLE
        echipa_master_profil.visibility = View.VISIBLE
        profil_echipa_master.visibility = View.VISIBLE
        echipa_licenta_profil.visibility = View.VISIBLE
        profil_echipa_licenta.visibility = View.VISIBLE

        titlu_lucrare_profil.visibility = View.GONE
        profil_titlu_lucrare.visibility = View.GONE
        profesor_coordonator_profil.visibility = View.GONE
        profile_profesor_coordoantor.visibility = View.GONE
        profile_ciclu.visibility = View.GONE
        ciclu_profil.visibility = View.GONE
        ciclu_text.visibility = View.GONE
        echipa_student_master.visibility = View.GONE
        an_profil.visibility = View.GONE
        an_text.visibility = View.GONE
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.sing_out_btn_profile -> {
                removeAllSharedPref()
                presenter.singOut()
            }
            R.id.update_btn_profile -> {
                when (typeUser) {
                    Constants.UserType.STUDENT -> {
                        presenter.updateStudent()
                    }
                    Constants.UserType.PROFESSOR -> {
                        presenter.updateProfesor()
                    }
                }
            }
        }
    }

    private fun removeAllSharedPref() {
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.STUDENT_CURRENT_ID)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.STUDENT_CURRENT_NUME)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.STUDENT_CURRENT_PRENUME)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.STUDENT_FULL_NAME)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.STUDENT_CURRENT_EMAIL)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.STUDENT_CURRENT_FACULTATE)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.STUDENT_CICLU)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.STUDENT_AN)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.STUDENT_PROFESOR_COORDONATOR_EMAIL)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.STUDENT_PROFESOR_COORDONATOR_FULL_NAME)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.STUDENT_PROFESOR_COORDONATOR_EMAIL)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.STUDENT_PROFESOR_COORDONATOR_ID)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.STUDENT_TITLU_LUCRARE)

        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.PROFESOR_CURRENT_ID)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.PROFESOR_CURRENT_NUME)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.PROFESOR_CURRENT_PRENUME)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.PROFESOR_FULL_NAME)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.PROFESOR_CURRENT_EMAIL)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.PROFESOR_CERINTE_LICENTA)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.PROFESOR_CERINTE_MASTER)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.PROFESOR_CURRENT_FACULTATE)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.PROFESOR_ECHIPA_LICENTA)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.PROFESOR_ECHIPA_MASTER)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.PROFESOR_LICENTA_ACCEPTATI)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.PROFESOR_DISERTATIE_ACCEPTATI)
    }

    override fun goToMainActivity() {
        startActivity(OnBoardingActivity.getIntent(requireContext()))
    }

    override fun disableUpdateButton() {
        update_btn_profile.isEnabled = false
    }

    override fun disableUpdateButtonAndSetOldTextForProfesor() {
        update_btn_profile.isEnabled = false
        titlu_lucrare_text.setText(titluLucrareTextFixed)
    }

    override fun disableUpdateButtonAndSetOldTextForStudent() {
        update_btn_profile.isEnabled = false
        cerinte_licenta_text.setText(cerinteLicentaTextFixed)
        cerinte_master_text.setText(cerinteMasterTextFixed)
    }
}

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
import com.example.gestiuneacererilor.data.restmanager.data.NewProfesorRequestBody
import com.example.gestiuneacererilor.data.restmanager.data.NewStudentRequestBody
import com.example.gestiuneacererilor.data.restmanager.data.Profesor
import com.example.gestiuneacererilor.data.restmanager.data.Student
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.base.BaseFragment
import com.example.gestiuneacererilor.ui.onboarding.OnBoardingActivity
import com.example.gestiuneacererilor.utils.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.email_text
import kotlinx.android.synthetic.main.fragment_profile.facultate_text

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
        typeUser = determineCurrentTypeUser(getCurrentUserEmail(requireContext()))

        when (typeUser) {
            Constants.UserType.STUDENT -> {
                setVisibilityViewsForStudent()
                presenter.getStudentByEmail()
            }
            Constants.UserType.PROFESSOR -> {
                setVisibilityViewsForProfessor()
                presenter.getProfessorByEmail()
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

    override fun setViewsForStudent(student: NewStudentRequestBody) {
        display_name_text.setText(
            String.format(
                resources.getString(R.string.template_for_two),
                student.nume,
                student.prenume
            )
        )
        facultate_text.setText(student.facultate)
        email_text.setText(student.email)
        an_text.setText(student.an)
        ciclu_text.setText(student.ciclu)
        titlu_lucrare_text.setText(student.titlu_lucrare ?: "")
        activity?.let {
            SharedPrefUtil.addKeyValue(
                it, SharedPrefUtil.CURRENT_USER_ID, student.id
                    ?: ""
            )
            SharedPrefUtil.addKeyValue(
                it, SharedPrefUtil.CURRENT_USER_NUME, student.nume
                    ?: ""
            )
            SharedPrefUtil.addKeyValue(
                it, SharedPrefUtil.CURRENT_USER_PRENUME, student.prenume
                    ?: ""
            )
        }
    }

    override fun getCurrentStudentDetails(): Student {
        return Student(
            getCurrentUserId(requireContext()),
            email_text.text.toString(),
            getCurrentUserNume(requireContext()),
            getCurrentUserPrenume(requireContext()),
            profesor_coordonator_text.text.toString(),
            facultate_text.text.toString(),
            an_text.text.toString(),
            ciclu_text.text.toString(),
            titlu_lucrare_text.text.toString()
        )
    }

    override fun getCurrentProfesorDetails(): Profesor {
        return Profesor(
            getCurrentUserId(requireContext()),
            email_text.text.toString(),
            getCurrentUserNume(requireContext()),
            getCurrentUserPrenume(requireContext()),
            facultate_text.text.toString(),
            cerinte_licenta_text.text.toString(),
            cerinte_master_text.text.toString(),
            echipa_licenta_text.text.toString(),
            echipa_master_text.text.toString()
        )
    }

    override fun setViewsForProfesor(profesor: NewProfesorRequestBody) {
        display_name_text.setText(
            String.format(
                resources.getString(R.string.template_for_two),
                profesor.nume,
                profesor.prenume
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
                it, SharedPrefUtil.CURRENT_USER_ID, profesor.id
                    ?: ""
            )
            SharedPrefUtil.addKeyValue(
                it, SharedPrefUtil.CURRENT_USER_NUME, profesor.nume
                    ?: ""
            )
            SharedPrefUtil.addKeyValue(
                it, SharedPrefUtil.CURRENT_USER_PRENUME, profesor.prenume
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
        profile_an.visibility = View.GONE
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
        SharedPrefUtil.removeStringValue(
            requireContext(),
            SharedPrefUtil.CURRENT_FIREBASE_USER_NAME
        )
        SharedPrefUtil.removeStringValue(
            requireContext(),
            SharedPrefUtil.CURRENT_FIREBASE_USER_EMAIL
        )
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_NUME)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_ID)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_PRENUME)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_AN)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_CICLU)
        SharedPrefUtil.removeStringValue(
            requireContext(),
            SharedPrefUtil.CURRENT_USER_FACULTATE
        )
        SharedPrefUtil.removeStringValue(
            requireContext(),
            SharedPrefUtil.CURRENT_USER_PROFESOR_COORDONATOR
        )
        SharedPrefUtil.removeStringValue(
            requireContext(),
            SharedPrefUtil.CURRENT_USER_TITLU_LUCRARE
        )
        SharedPrefUtil.removeStringValue(
            requireContext(),
            SharedPrefUtil.CURRENT_USER_ECHIPA_LICENTA
        )
        SharedPrefUtil.removeStringValue(
            requireContext(),
            SharedPrefUtil.CURRENT_USER_ECHIPA_MASTER
        )
        SharedPrefUtil.removeStringValue(
            requireContext(),
            SharedPrefUtil.CURRENT_USER_CERINTE_LICENTA
        )
        SharedPrefUtil.removeStringValue(
            requireContext(),
            SharedPrefUtil.CURRENT_USER_CERINTE_MASTER
        )
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

package com.example.gestiuneacererilor.ui.profile

import android.os.Bundle
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
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.base.BaseFragment
import com.example.gestiuneacererilor.ui.onboarding.OnBoardingActivity
import com.example.gestiuneacererilor.utils.Constants
import com.example.gestiuneacererilor.utils.SharedPrefUtil
import com.example.gestiuneacererilor.utils.determineCurrentTypeUser
import com.example.gestiuneacererilor.utils.getCurrentUserEmail
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment<ProfileMvp.Presenter>(), View.OnClickListener,
    ProfileMvp.View {

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

        when (determineCurrentTypeUser(getCurrentUserEmail(requireContext()))) {
            Constants.UserType.STUDENT.name -> {
                setVisibilityViewsForStudent()
                presenter.getStudentByEmail()
            }
            Constants.UserType.PROFESSOR.name -> {
                setVisibilityViewsForProfessor()
                presenter.getProfessorByEmail()
            }
        }

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
        if (view?.id == R.id.sing_out_btn_profile) {
            removeAllSharedPref()
            presenter.singOut()
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
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_PRENUME)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_AN)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_CICLU)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_FACULTATE)
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
}

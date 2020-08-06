package com.example.gestiuneacererilor.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManagerImpl
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.base.BaseFragment
import com.example.gestiuneacererilor.ui.onboarding.OnBoardingActivity
import com.example.gestiuneacererilor.utils.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment<ProfileMvp.Presenter>(), View.OnClickListener,
    ProfileMvp.View {

    override fun initializePresenter(): ProfileMvp.Presenter {
        return ProfilePresenter(
            this,
            requireContext(),
            FirebaseAuthManagerImpl.getInstance()
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

        val currentUserType = determineCurrentTypeUser(getCurrentUserEmail(requireContext()))
        setViews(currentUserType)

        sing_out_btn_profile.setOnClickListener(this)
        update_btn_profile.setOnClickListener(this)
    }

    private fun setViews(currentUserType: String) {
        display_name_text.setText(getCurrentUserDisplayName(requireContext()))
        facultate_text.setText(getCurrentUserFacultate(requireContext()))
        email_text.setText(getCurrentUserEmail(requireContext()))

        when (currentUserType) {
            Constants.UserType.STUDENT.name -> {
                setVisibilityViewsForStudent()
                an_text.setText(getCurrentStudentAn(requireContext()))
                ciclu_text.setText(getCurrentStudentCiclu(requireContext()))
            }
            Constants.UserType.PROFESSOR.name ->
                setVisibilityViewsForProfessor()
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
        if (view?.id == R.id.sing_out_btn_profile) {
            removeAllSharedPref()
            presenter.singOut()
        }
    }

    private fun removeAllSharedPref() {
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_FIREBASE_USER_NAME)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_FIREBASE_USER_EMAIL)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_NUME)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_PRENUME)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_AN)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_CICLU)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_FACULTATE)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_PROFESOR_COORDONATOR)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_TITLU_LUCRARE)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_ECHIPA_LICENTA)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_ECHIPA_MASTER)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_CERINTE_LICENTA)
        SharedPrefUtil.removeStringValue(requireContext(), SharedPrefUtil.CURRENT_USER_CERINTE_MASTER)
    }

    override fun goToMainActivity() {
        startActivity(OnBoardingActivity.getIntent(requireContext()))
    }
}

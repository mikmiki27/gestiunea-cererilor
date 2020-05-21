package com.example.gestiuneacererilor.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManagerImpl
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.base.BaseFragment
import com.example.gestiuneacererilor.ui.main.MainActivity
import com.example.gestiuneacererilor.ui.onboarding.OnBoardingActivity
import com.example.gestiuneacererilor.utils.SharedPrefUtil
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
        var text = SharedPrefUtil.getStringValue(
            context,
            SharedPrefUtil.CURRENT_FIREBASE_USER_EMAIL
        ) + SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_FIREBASE_USER_NAME)
        profile_text.text = text

        sing_out_btn_profile.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.sing_out_btn_profile) {
            presenter.singOut()
        }
    }

    override fun goToMainActivity() {
        startActivity(OnBoardingActivity.getIntent(context!!))
    }
}

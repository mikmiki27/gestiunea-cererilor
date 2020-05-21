package com.example.gestiuneacererilor.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.utils.SharedPrefUtil
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_progress.*

class ProfileFragment : Fragment() {

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
    }
}

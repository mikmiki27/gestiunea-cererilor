package com.example.gestiuneacererilor.ui.disertatie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.ui.base.BaseActivity

class DisertatieFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_disertatie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as? BaseActivity<*>)?.supportActionBar?.title = getString(R.string.menu_disertatie)
    }
}

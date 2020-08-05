package com.example.gestiuneacererilor.ui.cereri.detaliiProfesor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.sedinte.OnRequestItemClicked

class DetaliiProfesorFragment(private var onRequestItemClicked: OnRequestItemClicked?): Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detalii_profesor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as? BaseActivity<*>)?.supportActionBar?.title = "Depune cerere"
    }

}
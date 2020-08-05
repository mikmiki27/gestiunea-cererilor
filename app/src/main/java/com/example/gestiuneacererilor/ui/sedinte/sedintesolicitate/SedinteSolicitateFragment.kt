package com.example.gestiuneacererilor.ui.sedinte.sedintesolicitate

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gestiuneacererilor.R
import kotlinx.android.synthetic.main.fragment_cereri.*

class SedinteSolicitateFragment(/*private var onRequestItemClicked: OnRequestItemClicked*/)  : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sedinte_solicitate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // (context as? BaseActivity<*>)?.supportActionBar?.title = getString(R.string.menu_cereri)

        //card_view_student.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.card_view_student) {
            AlertDialog.Builder(context)
                .setTitle("Doresti sa adaugi sedinta in calendar?")
                .setCancelable(false)
                .setPositiveButton("DA", null)
                .setNegativeButton("NU", null)
                .show()
        }
    }
}
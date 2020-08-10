package com.example.gestiuneacererilor.ui.cereri.listaprofesori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.ui.cereri.detaliiProfesor.DetaliiProfesorFragment
import com.example.gestiuneacererilor.ui.sedinte.OnRequestItemClicked
import kotlinx.android.synthetic.main.fragment_lista_profesori.*

class ListaProfesori(private var onRequestItemClicked: OnRequestItemClicked): Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lista_profesori, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       //  (context as? BaseActivity<*>)?.supportActionBar?.title = getString(R.string.menu_cereri)

          //card_view_student.setOnClickListener(this)
    }

       override fun onClick(view: View?) {
           if (view?.id == R.id.card_view_student) {
               fragmentManager?.beginTransaction()
                   ?.add(android.R.id.content, DetaliiProfesorFragment(null))
                   ?.commit()
           }
       }
}
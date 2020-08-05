package com.example.gestiuneacererilor.ui.onboarding.registration

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.example.gestiuneacererilor.R
import com.example.gestiuneacererilor.data.managers.authmanager.FirebaseAuthManagerImpl
import com.example.gestiuneacererilor.data.managers.studentmanager.StudentManagerImplementation
import com.example.gestiuneacererilor.data.restmanager.StudentService
import com.example.gestiuneacererilor.data.restmanager.data.Profesor
import com.example.gestiuneacererilor.data.restmanager.data.Student
import com.example.gestiuneacererilor.ui.base.BaseActivity
import com.example.gestiuneacererilor.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_registration.*


class RegistrationActivity : BaseActivity<RegistrationMvp.Presenter>(), RegistrationMvp.View {

    override var presenter: RegistrationMvp.Presenter =
        RegistrationActivityPresenter(this,
            FirebaseAuthManagerImpl.getInstance(),
            StudentManagerImplementation.getInstance(StudentService.create()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        setContentView(R.layout.activity_registration)
    }

    override fun onResume() {
        super.onResume()

        user_type_layout?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (user_type_layout.selectedItemId) {
                    0L -> {//student
                        ciclu_layout.visibility = View.VISIBLE
                        an_layout.visibility = View.VISIBLE
                        if (email_layout.isErrorEnabled) {
                            email_layout.error =
                                "Foloseste adresa de email institutionala pentru studenti!!"
                        }
                    }
                    1L -> {//professor //hide all student's views
                        ciclu_layout.visibility = View.GONE
                        an_layout.visibility = View.GONE
                        if (email_layout.isErrorEnabled) {
                            email_layout.error =
                                "Foloseste adresa de email institutionala pentru profesori!!"
                        }
                    }
                }
            }
        }

        ciclu_layout?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (user_type_layout.selectedItemId == 0L) {
                    when (ciclu_layout.selectedItemId) {
                        0L -> { //licenta
                            val adapter = createSpinnerArray(R.array.an_licenta_type)
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            an_layout.adapter = adapter
                        }
                        1L -> { //master
                            val adapter = createSpinnerArray(R.array.an_master_type)
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            an_layout.adapter = adapter
                        }
                    }
                }
            }
        }

        sign_up_registration_btn.setOnClickListener {
            if (validation()) {
                if (user_type_layout.selectedItemId == 0L) { //student
                    presenter.singUpUser(
                        Student(
                            email_text.text.toString(),
                            last_name_text.text.toString(),
                            first_name_text.text.toString(),
                            "",
                            facultate_text.text.toString(),
                            an_layout.selectedItem.toString(),
                            ciclu_layout.selectedItem.toString(),
                            ""
                        ),
                        password_text.text.toString(),
                        this
                    )
                } else { //professor
                    presenter.singUpUser(
                        Profesor(
                            email_text.text.toString(),
                            last_name_text.text.toString(),
                            first_name_text.text.toString(),
                            facultate_text.text.toString(),
                            "", "", "", ""
                        ),
                        password_text.text.toString(),
                        this
                    )
                }
            }
        }
    }

    private fun validation(): Boolean {
        var i = 0
        if (first_name_text.text.isNullOrEmpty()) {
            first_name_layout.error = "Camp obligatoriu!"
            i++
        } else {
            first_name_layout.error = null
        }
        if (last_name_text.text.isNullOrEmpty()) {
            last_name_layout.error = "Camp obligatoriu!"
            i++
        } else {
            last_name_layout.error = null
        }
        if (facultate_layout.isVisible && facultate_text.text.isNullOrEmpty()) {
            facultate_layout.error = "Camp obligatoriu!"
            i++
        } else {
            facultate_layout.error = null
        }
        if (password_text.text?.length!! < 8) {
            password_layout.error = "Parola trebuie sa aiba minim 8 caractere"
            i++
        } else {
            password_layout.error = null
        }
        if (!email_text.text.isNullOrEmpty()) {
            if (user_type_layout.selectedItemId == 0L) {
                if (email_text.text?.contains("@stud.ase.ro")!! && email_text.text!!.length > 12) {
                    email_layout.error = null
                } else {
                    email_layout.error = "Foloseste adresa de email institutionala pentru studenti"
                    i++
                }
            }
            if (user_type_layout.selectedItemId == 1L) {
                if (email_text.text?.contains("ase.ro")!! && email_text.text!!.length > 7) {
                    email_layout.error = null
                } else {
                    email_layout.error = "Foloseste adresa de email institutionala pentru profesori"
                    i++
                }
            }
        } else {
            email_layout.error = "Camp obligatoriu!"
        }
        return i == 0
    }

    override fun goToMainActivity() {
        startActivity(MainActivity.getIntent(this))
    }

    private fun createSpinnerArray(entries: Int): ArrayAdapter<CharSequence> {
        return ArrayAdapter.createFromResource(
            this,
            entries,
            android.R.layout.simple_spinner_item
        )
    }
}
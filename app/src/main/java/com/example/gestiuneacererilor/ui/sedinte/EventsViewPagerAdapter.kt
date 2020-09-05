package com.example.gestiuneacererilor.ui.sedinte

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gestiuneacererilor.ui.sedinte.programareSedinte.ProgramareSedinteFragment
import com.example.gestiuneacererilor.ui.sedinte.sedinteConfirmate.SedinteConfirmateFragment
import com.example.gestiuneacererilor.ui.sedinte.sedinteconfirmatestudent.SedinteConfirmateStudentFragment
import com.example.gestiuneacererilor.ui.sedinte.sedintesolicitate.SedinteSolicitateFragment
import com.example.gestiuneacererilor.utils.Constants
import com.example.gestiuneacererilor.utils.determineCurrentTypeUser
import com.google.firebase.auth.FirebaseAuth

class EventsViewPagerAdapter(
    var context: Context,
    fa: FragmentActivity
) : FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment {
        return when (determineCurrentTypeUser(FirebaseAuth.getInstance().currentUser?.email.toString())) {
            Constants.UserType.PROFESSOR -> {
                when (position) {
                    SEDINTE_SOLICITATE -> SedinteSolicitateFragment()
                    SEDINTE_CONFIRMATE_PROF -> SedinteConfirmateFragment()
                    else -> throw IllegalStateException("Invalid adapter position")
                }
            }
            Constants.UserType.STUDENT -> {
                when (position) {
                    SEDINTE_CONFIRMATE_STUD -> SedinteConfirmateStudentFragment()
                    SEDINTE_PROGRAMARE -> ProgramareSedinteFragment()
                    else -> throw IllegalStateException("Invalid adapter position")
                }
            }
        }
    }

    override fun getItemCount(): Int = NUM_PAGES

    companion object {
        internal const val NUM_PAGES = 2
        internal const val SEDINTE_CONFIRMATE_STUD = 0
        internal const val SEDINTE_PROGRAMARE = 1
        internal const val SEDINTE_SOLICITATE = 0
        internal const val SEDINTE_CONFIRMATE_PROF = 1
    }

}
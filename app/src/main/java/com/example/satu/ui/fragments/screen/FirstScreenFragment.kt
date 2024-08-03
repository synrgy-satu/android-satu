package com.example.satu.ui.fragments.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.satu.R


/**
 * A simple [Fragment] subclass.
 * Use the [FirstScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstScreenFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_screen, container, false)
    }


}
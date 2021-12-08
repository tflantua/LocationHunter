package com.bijgepast.locationhunter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bijgepast.locationhunter.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
    private var binding: FragmentSignupBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        binding?.signupData = this
        // Inflate the layout for this fragment
        return binding?.root
    }

    fun login() {
        val ft = requireActivity().supportFragmentManager.beginTransaction()
        ft.replace(R.id.FragmentHost, LoginFragment())
        ft.commit()
        onDestroyView()
    }

    fun signup() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
package com.bijgepast.locationhunter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bijgepast.locationhunter.databinding.FragmentLoginBinding
import com.bijgepast.locationhunter.utils.NetworkHandler
import okhttp3.FormBody

class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding?.loginData = this

        return binding?.root
    }

    fun login() {
        Thread {
            val networkHandler = NetworkHandler.getInstance()
            val rb = FormBody.Builder()
                .add("userName", "ThomasFlantua")
                .add("password", "Welkom01")
                .build()

            val response = networkHandler.POST("loginRequest.php", rb)
            println(response)
        }.start()
    }

    fun signup() {
        val ft = requireActivity().supportFragmentManager.beginTransaction()
        ft.replace(R.id.FragmentHost, SignupFragment())
        ft.commit()

        this.onDestroyView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
package com.bijgepast.locationhunter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bijgepast.locationhunter.databinding.FragmentSignupBinding
import com.bijgepast.locationhunter.interfaces.CallbackListener
import com.bijgepast.locationhunter.utils.DataManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject


class SignupFragment : Fragment(), CallbackListener {
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
        val userName = binding?.UserName?.text.toString()
        val password = binding?.Password?.text.toString()
        val verifyPassword = binding?.VerifyPassword?.text.toString()
        var errorMessage: String = ""

        if (userName.isEmpty()) {
            errorMessage = buildErrorMessage(errorMessage, R.string.InvalidUsername)
            binding?.UserName?.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.mainRed);
        }
        if (password.isEmpty()) {
            errorMessage = buildErrorMessage(errorMessage, R.string.InvalidPassword)
            binding?.Password?.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.mainRed);
        }
        if (verifyPassword.isEmpty() || password != verifyPassword) {
            errorMessage = buildErrorMessage(errorMessage, R.string.PasswordMisMatch)
            binding?.VerifyPassword?.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.mainRed);
        }


        if (errorMessage.isEmpty())
            Thread { DataManager.getInstance().signup(userName, password, this) }.start()
        else
            Snackbar.make(this.requireView(), errorMessage, Snackbar.LENGTH_LONG).show()
    }

    private fun buildErrorMessage(currentMessage: String, stringId: Int) : String {
        return if(currentMessage.isNotEmpty()){
            currentMessage + getString(R.string.MessageSeperator) + getString(stringId)
        }else
            getString(stringId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onSucces(obj: Any) {
        var json: JsonObject = obj as JsonObject
        json = json.get("data").asJsonObject
        val username: String = json.get("Name").asString
        val score: Int = json.get("Score").asInt
        val key: String = json.get("Key").asString
        val id: Int = json.get("ID").asInt

        val sharedPref: SharedPreferences =
            this.requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)

        sharedPref.edit()
            .putString("Name", username)
            .putInt("Score", score)
            .putString("Key", key)
            .putInt("Id", id)
            .apply()

        val intent = Intent(this.requireContext(), OverviewActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onFailure(obj: Any) {
        Snackbar.make(this.requireView(), obj as String, Snackbar.LENGTH_LONG).show()
    }
}
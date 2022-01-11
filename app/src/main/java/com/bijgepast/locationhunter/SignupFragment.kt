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
        var verificationSuccessful = true

        if (userName.isEmpty()) {
            Snackbar.make(this.requireView(), R.string.InvalidUsername, Snackbar.LENGTH_LONG).show()
            binding?.UserName?.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.mainRed);
            verificationSuccessful = false
        }
        if (password.isEmpty()) {
            Snackbar.make(this.requireView(), R.string.InvalidPassword, Snackbar.LENGTH_LONG).show()
            binding?.Password?.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.mainRed);
            verificationSuccessful = false
        }
        if (verifyPassword != verifyPassword) {
            Snackbar.make(this.requireView(), R.string.PasswordMisMatch, Snackbar.LENGTH_LONG).show()
            binding?.VerifyPassword?.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.mainRed);
            verificationSuccessful = false
        }

        if (verificationSuccessful)
            DataManager.getInstance().signUp(userName, password, this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onSucces(obj: Any) {
        val json: JsonObject = obj as JsonObject
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
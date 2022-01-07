package com.bijgepast.locationhunter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bijgepast.locationhunter.databinding.FragmentLoginBinding
import com.bijgepast.locationhunter.interfaces.CallbackListener
import com.bijgepast.locationhunter.models.LoginModel
import com.bijgepast.locationhunter.utils.ApiHandler
import com.bijgepast.locationhunter.utils.NetworkHandler
import com.google.gson.JsonObject

class LoginFragment : Fragment(), CallbackListener {
    private var binding: FragmentLoginBinding? = null
    var isLoading: Boolean = false
    val loginModel = LoginModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding?.loginData = this
        binding?.loginModel = this.loginModel

        return binding?.root
    }

    fun login() {
        val usernamBox = binding?.UserName
        val passwordBox = binding?.Password

        ApiHandler.getInstance(NetworkHandler.getInstance())
            .login(usernamBox?.text.toString(), passwordBox?.text.toString(), this)
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

    override fun onSucces(obj: Any) {
        val json: JsonObject = obj as JsonObject
        val username: String = json.get("Name").asString
        val score: Int = json.get("Score").asInt
        val key: String = json.get("Key").asString

        val sharedPref: SharedPreferences =
            this.requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)

        sharedPref.edit()
            .putString("Name", username)
            .putInt("Score", score)
            .putString("Key", key)
            .apply()

        val intent = Intent(this.requireContext(), OverviewActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onFailure(obj: Any) {
        val failure: String = obj as String

        this.loginModel.setIsLoading(false)
        Looper.prepare()
        Toast.makeText(this.requireContext(), failure, Toast.LENGTH_LONG).show()
    }
}
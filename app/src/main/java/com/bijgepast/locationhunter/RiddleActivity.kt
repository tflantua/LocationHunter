package com.bijgepast.locationhunter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bijgepast.locationhunter.databinding.ActivityRiddleBinding
import com.bijgepast.locationhunter.models.RiddleModel
import com.bijgepast.locationhunter.viewmodels.RiddleViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class RiddleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiddleBinding
    private lateinit var riddleViewModel: RiddleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val riddleModel: RiddleModel? = intent.extras?.get("riddle") as RiddleModel?

        if (riddleModel == null) onBackPressed()

        riddleViewModel = ViewModelProvider(this)[RiddleViewModel::class.java]

        if (this.riddleViewModel.getRiddles().value == null)
            riddleViewModel.setRiddles(riddleModel!!)

        super.onCreate(savedInstanceState)

        binding = ActivityRiddleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activity = this
        binding.riddleModel = this.riddleViewModel.getRiddles().value

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_riddle)
        navView.setupWithNavController(navController)
    }
}
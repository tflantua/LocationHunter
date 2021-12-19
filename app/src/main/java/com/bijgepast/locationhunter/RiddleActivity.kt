package com.bijgepast.locationhunter

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bijgepast.locationhunter.databinding.ActivityRiddleBinding
import com.bijgepast.locationhunter.models.RiddleModel
import com.bijgepast.locationhunter.utils.GpsManager
import com.bijgepast.locationhunter.viewmodels.RiddleViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class RiddleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRiddleBinding
    private lateinit var riddleViewModel: RiddleViewModel

    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val riddleModel: RiddleModel? = intent.extras?.get("riddle") as RiddleModel?

        if (riddleModel == null) onBackPressed()

        riddleViewModel = ViewModelProvider(this)[RiddleViewModel::class.java]

        if (this.riddleViewModel.getRiddle().value == null)
            riddleViewModel.setRiddle(riddleModel!!)
        if (this.riddleViewModel.getGpsManager() == null) {
            riddleViewModel.setGpsManager(GpsManager(this))
            riddleViewModel.getGpsManager()!!.addListener(riddleModel!!)
        }

        this.sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        this.sensorManager.registerListener(riddleModel, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL )
        this.sensorManager.registerListener(riddleModel, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL )

        super.onCreate(savedInstanceState)

        binding = ActivityRiddleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activity = this
        binding.riddleModel = this.riddleViewModel.getRiddle().value

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_riddle)
        navView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        val riddleModel = riddleViewModel.getRiddle().value
        this.sensorManager.registerListener(riddleModel, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL )
        this.sensorManager.registerListener(riddleModel, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(riddleViewModel.getRiddle().value)
    }
}
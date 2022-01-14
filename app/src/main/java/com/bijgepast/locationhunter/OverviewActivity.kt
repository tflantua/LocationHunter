package com.bijgepast.locationhunter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bijgepast.locationhunter.adapters.RiddleAdapter
import com.bijgepast.locationhunter.databinding.ActivityOverviewBinding
import com.bijgepast.locationhunter.models.RiddleModel
import com.bijgepast.locationhunter.utils.DataManager
import com.bijgepast.locationhunter.utils.logOut
import com.bijgepast.locationhunter.viewmodels.OverviewViewModel

class OverviewActivity : AppCompatActivity(), RiddleAdapter.OnItemClick {

    private lateinit var overviewViewModel: OverviewViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        overviewViewModel = ViewModelProvider(this)[OverviewViewModel::class.java]

        super.onCreate(savedInstanceState)

        this.sharedPreferences = this.getSharedPreferences("UserInfo", MODE_PRIVATE)

        val binding: ActivityOverviewBinding = ActivityOverviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activity = this

        val riddleRecyclerView = findViewById<RecyclerView>(R.id.riddleRecycleView)

        val adapter = RiddleAdapter(this)

        riddleRecyclerView.adapter = adapter

        binding.accountInfo.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

        binding.Name.text =  getSharedPreferences("UserInfo", Context.MODE_PRIVATE).getString("Name", "")!!

        overviewViewModel.getRiddles().observe(this) { s ->
            adapter.setRouteModels(s)
        }

        binding.refreshing.setOnRefreshListener {
            val key: String? = sharedPreferences.getString("Key", "")
            this.overviewViewModel.getRiddles().value?.forEach { r -> r.updateProperties() }
            Thread {
                loading(key!!)

                binding.refreshing.isRefreshing = false
            }.start()

        }
    }

    override fun onResume() {
        super.onResume()
        val key: String? = sharedPreferences.getString("Key", "")
        this.overviewViewModel.getRiddles().value?.forEach { r -> r.updateProperties() }
        Thread {
            loading(key!!)
        }.start()
    }

    private fun loading(key: String) {
        val dbManager = DataManager.getInstance()
        val riddles = dbManager.getRiddles(key)
        if (riddles != null)
            this.overviewViewModel.setRiddles(riddles)
        else {
            this.logOut()
        }
    }

    override fun onClick(riddle: RiddleModel) {
        Log.d(null, "Clicked on riddle")
        val intent = Intent(this, RiddleActivity::class.java)
        intent.putExtra("riddle", riddle)
        startActivity(intent)
    }
}
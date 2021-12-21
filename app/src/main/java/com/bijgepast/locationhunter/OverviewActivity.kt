package com.bijgepast.locationhunter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bijgepast.locationhunter.adapters.RiddleAdapter
import com.bijgepast.locationhunter.databinding.ActivityOverviewBinding
import com.bijgepast.locationhunter.models.RiddleModel
import com.bijgepast.locationhunter.utils.DataBaseManager
import com.bijgepast.locationhunter.viewmodels.OverviewViewModel

class OverviewActivity : AppCompatActivity(), RiddleAdapter.OnItemClick {

    private lateinit var overviewViewModel: OverviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        overviewViewModel = ViewModelProvider(this)[OverviewViewModel::class.java]

        super.onCreate(savedInstanceState)

        val binding: ActivityOverviewBinding = ActivityOverviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activity = this

        val riddleRecyclerView = findViewById<RecyclerView>(R.id.riddleRecycleView)

        val adapter = RiddleAdapter(this)

        riddleRecyclerView.adapter = adapter

        overviewViewModel.getRiddles().observe(this) { s ->
            adapter.setRouteModels(s)
        }

        if (this.overviewViewModel.getRiddles().value == null || this.overviewViewModel.getRiddles().value!!.isEmpty()) {
            Thread {
                val dbManager = DataBaseManager.getInstance()
                dbManager.createDB(this)
                this.overviewViewModel.setRiddles(dbManager.getRiddels())
            }.start()
        }

    }

    override fun onResume() {
        super.onResume()
        this.overviewViewModel.getRiddles().value?.forEach { r -> r.updateProperties() }
    }

    override fun onClick(riddle: RiddleModel) {
        Log.d(null, "Clicked on riddle")
        val intent = Intent(this, RiddleActivity::class.java)
        intent.putExtra("riddle", riddle)
        startActivity(intent)
    }
}
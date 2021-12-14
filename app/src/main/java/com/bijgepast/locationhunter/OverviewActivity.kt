package com.bijgepast.locationhunter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bijgepast.locationhunter.adapters.RiddleAdapter
import com.bijgepast.locationhunter.databinding.ActivityOverviewBinding
import com.bijgepast.locationhunter.enums.DistanceStatus
import com.bijgepast.locationhunter.models.HintModel
import com.bijgepast.locationhunter.models.LocationModel
import com.bijgepast.locationhunter.models.RiddleModel
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
            // TODO: 14/12/2021 get riddles from database
            this.overviewViewModel.setRiddles(generateData())
        }

    }

    private fun generateData(): List<RiddleModel> {
        val value: MutableList<RiddleModel> = ArrayList(5)
        value.add(
            RiddleModel(
                LocationModel(0.0, 0.0, "ZeroPoint"),
                "Ocean",
                "The start point",
                6,
                generateHints(),
                300,
                DistanceStatus.HOTTER_THEN_HELL,
                false
            )
        )
        value.add(
            RiddleModel(
                LocationModel(0.0, 0.0, "ZeroPoint"),
                "Ocean",
                "The start point",
                3,
                generateHints(),
                300,
                DistanceStatus.HOT,
                false
            )
        )
        value.add(
            RiddleModel(
                LocationModel(0.0, 0.0, "ZeroPoint"),
                "Ocean",
                "The start point",
                3,
                generateHints(),
                300,
                DistanceStatus.HOT,
                false
            )
        )
        value.add(
            RiddleModel(
                LocationModel(0.0, 0.0, "ZeroPoint"),
                "Ocean",
                "The start point",
                3,
                generateHints(),
                300,
                DistanceStatus.HOT,
                false
            )
        )
        value.add(
            RiddleModel(
                LocationModel(0.0, 0.0, "ZeroPoint"),
                "Ocean",
                "The start point",
                3,
                generateHints(),
                300,
                DistanceStatus.HOT,
                false
            )
        )
        return value
    }

    private fun generateHints(): List<HintModel> {
        val value: MutableList<HintModel> = ArrayList(5)
        value.add(
            HintModel(
                false,
                false,
                "links -getallen rechts + getallen boven + getallen onder - getallen",
                40
            )
        )
        value.add(
            HintModel(
                false,
                false,
                "null Island",
                400
            )
        )
        return value
    }

    override fun onClick(riddle: RiddleModel) {
        Log.d(null, "Clicked on riddle")
        val intent = Intent(this, RiddleActivity::class.java)
        intent.putExtra("riddle", riddle)
        startActivity(intent)
    }
}
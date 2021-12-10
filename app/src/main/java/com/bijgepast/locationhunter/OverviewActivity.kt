package com.bijgepast.locationhunter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bijgepast.locationhunter.adapters.RiddleAdapter
import com.bijgepast.locationhunter.models.LocationModel
import com.bijgepast.locationhunter.models.RiddleModel

class OverviewActivity : AppCompatActivity(), RiddleAdapter.OnItemClick {

    private lateinit var overviewViewModel: OverviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        overviewViewModel = ViewModelProvider(this)[OverviewViewModel::class.java]


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        val riddleRecyclerView = findViewById<RecyclerView>(R.id.riddleRecycleView)

        val adapter = RiddleAdapter(this)

        riddleRecyclerView.adapter = adapter

        overviewViewModel.getRiddles().observe(this) { s ->
            adapter.setRouteModels(s)
        }

        if (this.overviewViewModel.getRiddles().value == null || this.overviewViewModel.getRiddles().value!!.isEmpty())
            this.overviewViewModel.setRiddles(generateData())

    }

    private fun generateData(): List<RiddleModel> {
        val value: MutableList<RiddleModel> = ArrayList(5)

        value.add(
            RiddleModel(
                LocationModel(0.0, 0.0, "ZeroPoint"),
                "The start point",
                3,
                ArrayList(),
                300
            )
        )
        value.add(
            RiddleModel(
                LocationModel(0.0, 0.0, "ZeroPoint"),
                "The start point",
                3,
                ArrayList(),
                300
            )
        )
        value.add(
            RiddleModel(
                LocationModel(0.0, 0.0, "ZeroPoint"),
                "The start point",
                3,
                ArrayList(),
                300
            )
        )
        value.add(
            RiddleModel(
                LocationModel(0.0, 0.0, "ZeroPoint"),
                "The start point",
                3,
                ArrayList(),
                300
            )
        )
        value.add(
            RiddleModel(
                LocationModel(0.0, 0.0, "ZeroPoint"),
                "The start point",
                3,
                ArrayList(),
                300
            )
        )
        return value
    }

    override fun onClick(riddle: RiddleModel) {
//        TODO("Not yet implemented")
        Log.d(null, "Clicked on riddle")
        println("Clicked")
    }
}
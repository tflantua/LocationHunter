package com.bijgepast.locationhunter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.bijgepast.locationhunter.databinding.ActivityUserBinding
import com.bijgepast.locationhunter.utils.DataManager
import com.bijgepast.locationhunter.utils.logOut
import com.bijgepast.locationhunter.viewmodels.UserViewModel
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.*

class UserActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var userViewModel: UserViewModel
    private var _binding: ActivityUserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this._binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        if (userViewModel.getUserModel().value == null) {
            this.userViewModel.setUserMode(applicationContext)
        }


        binding.activity = this
        binding.userModel = userViewModel.getUserModel().value


        mapView = binding.tomtomMap
        mapView.addOnMapReadyCallback { map ->
            map.isMyLocationEnabled = true
            map.markerSettings.setMarkersClustering(true)


            val drawable = ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_twotone_location_on_24,
                resources.newTheme()
            )!!

            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)

            val bitmapDrawable = BitmapDrawable(resources, bitmap)
            val icon = Icon.Factory.fromDrawable("Marker", bitmapDrawable)

            Thread {
                DataManager.getInstance()
                    .getRiddles(this.userViewModel.getUserModel().value?.getKey()!!)
                    ?.forEach { riddleModel ->
                        if (riddleModel.getCompleted()) {
                            val location =
                                LatLng(
                                    riddleModel.locationModel.north,
                                    riddleModel.locationModel.east
                                )
                            val markerBuilder = MarkerBuilder(location)
                                .markerBalloon(SimpleMarkerBalloon(riddleModel.riddleName))
                                .iconAnchor(MarkerAnchor.Bottom)
                                .tag(riddleModel.id)
                                .decal(true)
                            val marker = map.addMarker(markerBuilder)
                            map.markerSettings.updateMarkerIcon(marker, icon)
                        }
                    }
            }.start()

        }

    }

    fun navigateFriends() {
        startActivity(Intent(this, FriendsActivty::class.java))
    }

    fun logOff() {
        this.logOut()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onHostSaveInstanceState()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onStop() {
        mapView.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
        this._binding = null
    }

}
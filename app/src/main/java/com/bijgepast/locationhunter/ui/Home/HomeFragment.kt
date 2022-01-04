package com.bijgepast.locationhunter.ui.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bijgepast.locationhunter.databinding.FragmentHomeBinding
import com.bijgepast.locationhunter.utils.PopUpClass
import com.bijgepast.locationhunter.utils.TomTomManager
import com.bijgepast.locationhunter.viewmodels.RiddleViewModel
import com.tomtom.online.sdk.map.MapView


class HomeFragment : Fragment() {

    private lateinit var mapView: MapView
    private lateinit var riddleViewModel: RiddleViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var tomTomManager: TomTomManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        riddleViewModel = ViewModelProvider(this.requireActivity())[RiddleViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        mapView = binding.tomtomMap

        this.tomTomManager = TomTomManager(requireContext(), riddleViewModel.getGpsManager()!!)

        mapView.addOnMapReadyCallback(this.tomTomManager!!)

        binding.riddleModel = riddleViewModel.getRiddle().value

        binding.searchButton.setOnClickListener {
            PopUpClass(layoutInflater, this.tomTomManager!!)
        }

        val root: View = binding.root

        return root
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
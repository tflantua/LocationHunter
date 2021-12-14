package com.bijgepast.locationhunter.ui.Compass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bijgepast.locationhunter.databinding.FragmentCompassBinding
import com.bijgepast.locationhunter.viewmodels.RiddleViewModel

class CompassFragment : Fragment() {

    private lateinit var riddleViewModel: RiddleViewModel
    private var _binding: FragmentCompassBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        riddleViewModel = ViewModelProvider(this.requireActivity())[RiddleViewModel::class.java]


        _binding = FragmentCompassBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
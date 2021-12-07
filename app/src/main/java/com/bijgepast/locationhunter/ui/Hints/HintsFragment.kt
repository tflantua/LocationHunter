package com.bijgepast.locationhunter.ui.Hints

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bijgepast.locationhunter.databinding.FragmentHintsBinding

class HintsFragment : Fragment() {

    private lateinit var hintsViewModel: HintsViewModel
    private var _binding: FragmentHintsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hintsViewModel =
            ViewModelProvider(this).get(HintsViewModel::class.java)

        _binding = FragmentHintsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
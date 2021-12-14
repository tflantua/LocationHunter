package com.bijgepast.locationhunter.ui.Hints

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bijgepast.locationhunter.adapters.HintsAdapter
import com.bijgepast.locationhunter.databinding.FragmentHintsBinding
import com.bijgepast.locationhunter.viewmodels.RiddleViewModel

class HintsFragment : Fragment() {

    private lateinit var riddleViewModel: RiddleViewModel
    private var _binding: FragmentHintsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        riddleViewModel = ViewModelProvider(this.requireActivity())[RiddleViewModel::class.java]

        _binding = FragmentHintsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.riddleModel = riddleViewModel.getRiddles().value

        val hintsAdapter: HintsAdapter =
            HintsAdapter(this.riddleViewModel.getRiddles().value!!.hints)

        binding.hintsRecyclerview.adapter = hintsAdapter

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
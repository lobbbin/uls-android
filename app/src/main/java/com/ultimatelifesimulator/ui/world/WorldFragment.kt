package com.ultimatelifesimulator.ui.world

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ultimatelifesimulator.databinding.FragmentWorldBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorldFragment : Fragment() {

    private var _binding: FragmentWorldBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorldBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

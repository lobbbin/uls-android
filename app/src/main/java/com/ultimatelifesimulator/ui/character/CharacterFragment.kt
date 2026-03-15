package com.ultimatelifesimulator.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ultimatelifesimulator.databinding.FragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : Fragment() {

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.character.observe(viewLifecycleOwner) { character ->
            binding.textName.text = character.name
            binding.textAge.text = "Age: ${character.age}"
            binding.textDay.text = "Day ${character.day}"
        }

        viewModel.stats.observe(viewLifecycleOwner) { stats ->
            binding.progressHealth.progress = stats.health
            binding.progressEnergy.progress = stats.energy
            binding.progressStress.progress = stats.stress
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

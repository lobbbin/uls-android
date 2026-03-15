package com.ultimatelifesimulator.ui.relationships

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ultimatelifesimulator.databinding.FragmentRelationshipsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RelationshipsFragment : Fragment() {

    private var _binding: FragmentRelationshipsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRelationshipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

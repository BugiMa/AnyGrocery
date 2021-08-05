package com.example.anygrocery.view.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.anygrocery.databinding.FragmentShoppingListBinding
import com.example.anygrocery.viewModel.SharedViewModel


class ShoppingListFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel

    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        //binding.toolbar.title = SharedViewModel.getCurrentListName()
        return binding.root
    }
}
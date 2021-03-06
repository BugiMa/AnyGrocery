package com.example.anygrocery.ui.fragment.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.anygrocery.R
import com.example.anygrocery.databinding.FragmentShoppingListBinding
import com.example.anygrocery.data.local.model.ShoppingList
import com.example.anygrocery.ui.fragment.recyclerViewFab.ShoppingListRecyclerViewFabFragment
import com.example.anygrocery.ui.viewModel.SharedViewModel
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_SHOPPING_LIST = "shopping_list"

@AndroidEntryPoint
class ShoppingListFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel

    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    private lateinit var shoppingList: ShoppingList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<ShoppingList>(ARG_SHOPPING_LIST)?.let{
            shoppingList = it
        }
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)

        val newShoppingListFragment = ShoppingListRecyclerViewFabFragment.newInstance(shoppingList)

        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .disallowAddToBackStack()
            .replace(R.id.products_container, newShoppingListFragment)
            .commit()

        val toolbar: MaterialToolbar = binding.toolbar
        toolbar.title = shoppingList.name
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(shoppingList: ShoppingList): ShoppingListFragment {
            return ShoppingListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SHOPPING_LIST, shoppingList)
                }
            }
        }
    }
}
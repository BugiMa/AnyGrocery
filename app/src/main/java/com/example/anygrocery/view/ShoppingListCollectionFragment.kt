package com.example.anygrocery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.anygrocery.databinding.FragmentRecycelerViewFabBinding
import com.example.anygrocery.model.ShoppingListModel
import com.example.anygrocery.viewModel.ShoppingListCollectionViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ShoppingListCollectionFragment : Fragment() {

    private lateinit var shoppingListCollectionViewModel: ShoppingListCollectionViewModel
    private var _binding: FragmentRecycelerViewFabBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shoppingListCollectionViewModel = ViewModelProvider(this).get(
            ShoppingListCollectionViewModel::class.java).apply {
            //setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecycelerViewFabBinding.inflate(inflater, container, false)
        val root = binding.root
        val recyclerView: RecyclerView = binding.recyclerView

        val fab: FloatingActionButton = binding.fab
       // shoppingListCollectionViewModel.text.observe(viewLifecycleOwner, Observer {
       //     textView.text = it
       // })
        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(shoppingListCollection: ArrayList<ShoppingListModel>): ShoppingListCollectionFragment {
            return ShoppingListCollectionFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("ShoppingListCollection",shoppingListCollection)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
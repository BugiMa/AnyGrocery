package com.example.anygrocery.view.recyclerViewFab

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anygrocery.R
import com.example.anygrocery.adapter.ListCollectionAdapter
import com.example.anygrocery.databinding.FragmentRecyclerViewFabBinding
import com.example.anygrocery.model.ShoppingList
import com.example.anygrocery.view.base.ShoppingListFragment
import com.example.anygrocery.viewModel.SharedViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


private const val ARG_IS_ARCHIVED_VIEW = "is_archived_lists_view"

class ListCollectionRecyclerViewFabFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel

    private var _binding: FragmentRecyclerViewFabBinding? = null
    private val binding get() = _binding!!

    private var isArchivedListsView = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getBoolean(ARG_IS_ARCHIVED_VIEW)?.let{
            isArchivedListsView = it
        }
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewFabBinding.inflate(inflater, container, false)
        val root = binding.root

        val adapter = ListCollectionAdapter(
            itemClickCallback = {
                navigateToFragment(ShoppingListFragment.newInstance(it))
            },
            itemLongClickCallback = {
                if (!isArchivedListsView) deleteListDialog(it)
            },
            archiveButtonClickCallback = {
                if (!isArchivedListsView) archiveListDialog(it)
            }
        )
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(requireContext())
        }

        if (!isArchivedListsView) {
            viewModel.getActiveListsLiveData().observe(viewLifecycleOwner, { adapter.setData(it) })
        } else {
            viewModel.getArchivedListsLiveData().observe(viewLifecycleOwner, { adapter.setData(it) })
        }


        val fab: FloatingActionButton = binding.fab
        if (isArchivedListsView) {
            fab.visibility = View.GONE
        }

        fab.setOnClickListener {
            if (!isArchivedListsView) addListDialog()
        }
        return root
    }

    companion object {

        @JvmStatic
        fun newInstance(isArchivedListsView: Boolean): ListCollectionRecyclerViewFabFragment {
            return ListCollectionRecyclerViewFabFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_IS_ARCHIVED_VIEW, isArchivedListsView)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addListDialog() {

        val nameInput = EditText(context).apply{
            inputType = InputType.TYPE_CLASS_TEXT
            hint = ("New List")
            filters += InputFilter.LengthFilter(24)
        }
        AlertDialog.Builder(context)
            .setTitle("Add New Shopping List")
            .setView(nameInput)
            .setPositiveButton("Add") { dialog, _ -> if (insertNewListToDatabase(nameInput.text.toString())) dialog.dismiss() }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun insertNewListToDatabase(listName: String): Boolean {
        return if (listName.isNotEmpty()) {
            val newList = ShoppingList(id, listName)

            viewModel.addList(newList)

            Toast.makeText(requireContext(), "List created successfully.", Toast.LENGTH_LONG).show()
            false
        } else {
            Toast.makeText(requireContext(), "Name field must be filled.", Toast.LENGTH_LONG).show()
            true
        }
    }

    private fun deleteListDialog(list: ShoppingList) {

        val builder = AlertDialog.Builder(context)
            builder.setTitle("Do You really want to delete this list?")
            builder.setPositiveButton("Yes") { dialog, _ ->

                viewModel.deleteList(list)

                Toast.makeText(requireContext(), "List deleted.", Toast.LENGTH_LONG).show()
                dialog.dismiss() }
            builder.setNegativeButton("No") { dialog, _ -> dialog.cancel() }
            builder.show()
    }

    private fun archiveListDialog(list: ShoppingList) {

        val builder = AlertDialog.Builder(context)
            builder.setTitle("Do You really want to archive this list?")
            builder.setPositiveButton("Yes") { dialog, _ ->

                    viewModel.deleteList(list)
                    list.isArchived = true

                    viewModel.archiveList(list)


                    Toast.makeText(requireContext(), "List Archived.", Toast.LENGTH_LONG).show()
                    dialog.dismiss() }
            builder.setNegativeButton("No") { dialog, _ -> dialog.cancel() }
            builder.show()
    }

    private fun navigateToFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack("ShoppingListFragment")
            .commit()
    }
}
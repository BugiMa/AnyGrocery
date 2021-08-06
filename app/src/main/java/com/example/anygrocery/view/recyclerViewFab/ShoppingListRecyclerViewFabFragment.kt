package com.example.anygrocery.view.recyclerViewFab

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anygrocery.adapter.ShoppingListAdapter
import com.example.anygrocery.databinding.FragmentRecyclerViewFabBinding
import com.example.anygrocery.model.Product
import com.example.anygrocery.model.ShoppingList
import com.example.anygrocery.viewModel.SharedViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val ARG_PRODUCT_LIST = "shopping_list"

class ShoppingListRecyclerViewFabFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel

    private var _binding: FragmentRecyclerViewFabBinding? = null
    private val binding get() = _binding!!

    private lateinit var shoppingList: ShoppingList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<ShoppingList>(ARG_PRODUCT_LIST)?.let{
            shoppingList = it
        }
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewFabBinding.inflate(inflater, container, false)
        val root = binding.root


        val adapter = ShoppingListAdapter(
            itemClickCallback = {
                viewModel.checkProduct(it)
            },
            itemLongClickCallback = {
                deleteProductDialog(it)
            }
        )
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(requireContext())
        }


        viewModel.getListsWithProducts().observe(viewLifecycleOwner, {
            adapter.setData(it, shoppingList.id)
        })

        val fab: FloatingActionButton = binding.fab
        if (shoppingList.isArchived!!) {
            fab.visibility = View.GONE
        }
        fab.setOnClickListener() {
            addProductDialog()
        }

        return root
    }

    companion object {

        @JvmStatic
        fun newInstance(shoppingList: ShoppingList): ShoppingListRecyclerViewFabFragment {
            return ShoppingListRecyclerViewFabFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PRODUCT_LIST, shoppingList)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addProductDialog() {

        val nameInput = EditText(context)
            nameInput.inputType = InputType.TYPE_CLASS_TEXT
            nameInput.hint = ("Product")
            nameInput.filters += InputFilter.LengthFilter(24)

        val amountInput = EditText(context)
            amountInput.inputType = InputType.TYPE_CLASS_NUMBER
            amountInput.hint = ("Amount")
            amountInput.filters += InputFilter.LengthFilter(4)

        val linearLayout = LinearLayout(context)
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.addView(nameInput)
        linearLayout.addView(amountInput)

        val amount = if (amountInput.text.isNotEmpty()) amountInput.text.toString() else "1"

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Add New Product")
        builder.setView(linearLayout)
        builder.setPositiveButton("Add") { dialog, _ -> if (insertNewProductToDatabase(nameInput.text.toString(), amount)) dialog.dismiss() }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    private fun insertNewProductToDatabase(productName: String, productAmount: String): Boolean {
        return if (productName.isNotEmpty()) {
            val newProduct = Product(  productName, shoppingList.id,productAmount.toInt())

            viewModel.addProduct(newProduct, shoppingList.id)
            //viewModel.addOrUpdateProduct(newProduct, shoppingList.id)

            Toast.makeText(requireContext(), "Product added successfully.", Toast.LENGTH_LONG).show()
            false
        } else {
            Toast.makeText(requireContext(), "Name field must be filled.", Toast.LENGTH_LONG).show()
            true
        }
    }

    private fun deleteProductDialog(product: Product) {

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Do You really want to delete this product?")
        builder.setPositiveButton("Yes") { dialog, _ ->

            viewModel.deleteProduct(product)
            //viewModel.deleteProduct(product, shoppingList.id)

            Toast.makeText(requireContext(), "Product deleted.", Toast.LENGTH_LONG).show()
            dialog.dismiss() }
        builder.setNegativeButton("No") { dialog, _ -> dialog.cancel()}
        builder.show()
    }
}
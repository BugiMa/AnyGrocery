package com.example.anygrocery.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.anygrocery.data.local.model.Product
import com.example.anygrocery.data.local.model.ShoppingList
import com.example.anygrocery.getOrAwaitValueTest
import com.example.anygrocery.repository.FakeShoppingListRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class SharedViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SharedViewModel

    @Before
    fun setup() {
        viewModel = SharedViewModel(ApplicationProvider.getApplicationContext() , FakeShoppingListRepository())
    }

    @Test
    fun addList() {
        val shoppingList = ShoppingList(1, "list")
        viewModel.addList(shoppingList)

        val listsWithProducts = viewModel.getListsWithProducts().getOrAwaitValueTest()
        assertThat(listsWithProducts.find{ it.shoppingList == shoppingList}?.shoppingList == shoppingList )
    }
    @Test
    fun deleteList() {
        val shoppingList = ShoppingList(1, "list")
        viewModel.addList(shoppingList)
        viewModel.deleteList(shoppingList)

        val listsWithProducts = viewModel.getListsWithProducts().getOrAwaitValueTest()
        assertThat(listsWithProducts.find{ it.shoppingList == shoppingList} == null )
    }
    @Test
    fun archiveList() {
        val shoppingList = ShoppingList(1, "list", isArchived = false)
        viewModel.addList(shoppingList)
        viewModel.archiveList(shoppingList)

        val listsWithProducts = viewModel.getListsWithProducts().getOrAwaitValueTest()
        assertThat(listsWithProducts.find{ it.shoppingList == shoppingList}
            ?.shoppingList
            ?.isArchived == true )
    }

    @Test
    fun addProduct() {
        val shoppingList = ShoppingList(1, "list", isArchived = false)
        viewModel.addList(shoppingList)

        val product = Product("product", 1, 1)
        viewModel.addProduct(product, shoppingList)

        val listsWithProducts = viewModel.getListsWithProducts().getOrAwaitValueTest()
        assertThat(listsWithProducts.find{ it.shoppingList == shoppingList}
            ?.products?.find { it == product} == product)
    }
    @Test
    fun deleteProduct() {
        val shoppingList = ShoppingList(1, "list", isArchived = false)
        viewModel.addList(shoppingList)

        val product = Product("product", 1, 1)
        viewModel.addProduct(product, shoppingList)
        viewModel.deleteProduct(product, shoppingList)

        val listsWithProducts = viewModel.getListsWithProducts().getOrAwaitValueTest()
        assertThat(listsWithProducts.find{ it.shoppingList == shoppingList}
            ?.products?.find{ it == product } == null)
    }
    @Test
    fun checkProduct() {
        val shoppingList = ShoppingList(1, "list", isArchived = false)
        viewModel.addList(shoppingList)

        val product = Product("product", 1, 1, isChecked = false)
        viewModel.addProduct(product, shoppingList)
        viewModel.checkProduct(product, shoppingList)

        val listsWithProducts = viewModel.getListsWithProducts().getOrAwaitValueTest()
        assertThat(listsWithProducts.find{ it.shoppingList == shoppingList}
            ?.products?.find{ it == product}
            ?.isChecked == true)
    }

}
package com.example.anygrocery.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.*
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.anygrocery.data.local.database.AnyGroceryDatabase
import com.example.anygrocery.getOrAwaitValue
import com.example.anygrocery.data.local.model.Product
import com.example.anygrocery.data.local.model.ShoppingList
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
@ExperimentalCoroutinesApi
class ShoppingListDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AnyGroceryDatabase
    private lateinit var dao: ShoppingListDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AnyGroceryDatabase::class.java,
        ).allowMainThreadQueries().build()

        dao = database.shoppingListDao()
    }
    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertShoppingList() = runBlockingTest {
        val shoppingList = ShoppingList(1, "list" )
        dao.insert(shoppingList)

        val allLists = dao.allLists().getOrAwaitValue()

        assertThat(allLists.contains(shoppingList))
    }
    @Test
    fun deleteShoppingList() = runBlockingTest {
        val shoppingList = ShoppingList(1, "list" )
        dao.insert(shoppingList)
        dao.delete(shoppingList)

        val allLists = dao.allLists().getOrAwaitValue()

        assertThat(!allLists.contains(shoppingList))
    }
    @Test
    fun updateShoppingList() = runBlockingTest {
        val shoppingList = ShoppingList(1,"list")
        val updatedShoppingList = ShoppingList(1, "updatedList", isArchived = true)

        dao.insert(shoppingList)
        dao.update(shoppingList.apply {
            name = "updatedList"
            isArchived = true
        })

        val allLists = dao.allLists().getOrAwaitValue()

        assertThat(allLists.contains(updatedShoppingList))
    }

    @Test
    fun insertProduct() = runBlockingTest {
        val shoppingList = ShoppingList(1, "list" )
        dao.insert(shoppingList)

        val product = Product("product", 1, 1)
        dao.insert(product)

        val allProducts = dao.allProducts().getOrAwaitValue()

        assertThat(allProducts.contains(product))
    }
    @Test
    fun deleteProduct() = runBlockingTest {
        val shoppingList = ShoppingList(1, "list" )
        dao.insert(shoppingList)

        val product = Product("product", 1, 1)
        dao.insert(product)
        dao.delete(product)

        val allProducts = dao.allProducts().getOrAwaitValue()

        assertThat(!allProducts.contains(product))
    }
    @Test
    fun updateProduct() = runBlockingTest {
        val shoppingList = ShoppingList(1, "list" )
        dao.insert(shoppingList)

        val product = Product("product", 1, 1)
        val updatedProduct = Product("updatedProduct", 1, 2, true)

        dao.insert(product)
        dao.update(product.apply {
            name = "updatedProduct"
            amount = 2
            isChecked = true
        })

        val allProducts = dao.allProducts().getOrAwaitValue()

        assertThat(allProducts.contains(updatedProduct))
    }

    @Test
    fun listsOfType() = runBlockingTest {
        val shoppingList1 = ShoppingList(1,"list1", isArchived = true)
        val shoppingList2 = ShoppingList(2,"list2", isArchived = false)
        dao.insert(shoppingList1)
        dao.insert(shoppingList2)

        val activeLists   = dao.ofType(false).getOrAwaitValue()
        val archivedLists = dao.ofType(true).getOrAwaitValue()

        assertThat(activeLists.all {it.isArchived == false} && archivedLists.all {it.isArchived == true})
    }

    @Test
    fun listsWithProducts() = runBlockingTest {
        val shoppingList1 = ShoppingList(1,"list1")
        val shoppingList2 = ShoppingList(2,"list2")
        dao.insert(shoppingList1)
        dao.insert(shoppingList2)

        val product1 = Product("product1", 1,1)
        val product2 = Product("product2", 2,1)
        dao.insert(product1)
        dao.insert(product2)

        val listsWithProducts = dao.listsWithProducts().getOrAwaitValue()

        assertThat(
            listsWithProducts.find { shoppingList1.id == 1 }?.products?.contains(product1) == true &&
            listsWithProducts.find { shoppingList1.id == 2 }?.products?.contains(product2) == true
        )
    }
}
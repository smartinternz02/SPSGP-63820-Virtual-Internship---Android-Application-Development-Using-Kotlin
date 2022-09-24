package com.example.grocerryapp

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(),GrocerryRVAdapter.GrocerryItemsClickInterface {
    lateinit var itemRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var list: List<GrocerryItems>
    lateinit var grocerryRVAdapter: GrocerryRVAdapter
    lateinit var grocerryViewModel: GrocerryViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemRV=findViewById(R.id.idRVItems)
        addFAB=findViewById(R.id.idFABADd)
        list=ArrayList<GrocerryItems>()
        grocerryRVAdapter= GrocerryRVAdapter(list,this)
        itemRV.layoutManager=LinearLayoutManager(this)
        itemRV.adapter=grocerryRVAdapter
        val grocerryRepository=GrocerryRepository(GrocerryDatabase(this))
        val factory=GrocerryViewModelFactory(grocerryRepository)
        grocerryViewModel=ViewModelProvider(this,factory).get(GrocerryViewModel::class.java)
        grocerryViewModel.getAllGrocerryItems().observe(this, Observer {
                grocerryRVAdapter.list=it
            grocerryRVAdapter.notifyDataSetChanged()
        })
        addFAB.setOnClickListener{
            openDialog()
        }

    }

    fun openDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocerry_add_dialog)
        val cancelBtn =dialog.findViewById<Button>(R.id.idBtnCancel)
        val addBtn =dialog.findViewById<Button>(R.id.idBtnAdd)
        val itemEdit=dialog.findViewById<EditText>(R.id.idEditItemName)
        val itemPriceEdit=dialog.findViewById<EditText>(R.id.idEditItemPrice)
        val  itemQuantityEdit=dialog.findViewById<EditText>(R.id.idEditItemQuantity)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        addBtn.setOnClickListener {
            val itemName : String=itemEdit.text.toString()
            val itemPrice: String=itemPriceEdit.text.toString()
            val itemQuantity:String=itemQuantityEdit.text.toString()
            val qty:Int=itemQuantity.toInt()
            val pr:Int=itemPrice.toInt()

            if(itemName.isNotEmpty() && itemPrice.isNotEmpty() && itemQuantity.isNotEmpty()){
                val items=GrocerryItems(itemName,qty,pr)
                grocerryViewModel.insert(items)
                Toast.makeText(applicationContext,"Item Inserted...", Toast.LENGTH_SHORT).show()
                grocerryRVAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }else{
                Toast.makeText(applicationContext,"Please Enter All Data...", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()

    }

    override fun onItemClick(grocerryItems: GrocerryItems) {
        grocerryViewModel.delete(grocerryItems)
        grocerryRVAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext,"Item Deleted...",Toast.LENGTH_SHORT).show()
    }
}
package com.example.grocerryapp

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GrocerryRVAdapter(
    var list: List<GrocerryItems>,
    val grocerryItemsClickInterface: GrocerryItemsClickInterface
    ): RecyclerView.Adapter<GrocerryRVAdapter.GrocerryViewHolder>() {

inner class GrocerryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val  nameTv=itemView.findViewById<TextView>(R.id.idTVItemName)
    val  quantityTv=itemView.findViewById<TextView>(R.id.idTVQuantity)
    val  rateTv=itemView.findViewById<TextView>(R.id.idTVRate)
    val  amountTv=itemView.findViewById<TextView>(R.id.idTVTotalAmt)
    val  deleteTv=itemView.findViewById<ImageView>(R.id.idIVDelete)
}

    interface GrocerryItemsClickInterface{
        fun onItemClick(grocerryItems: GrocerryItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrocerryViewHolder {
        val  view=LayoutInflater.from(parent.context).inflate(R.layout.grocerry_rv_item,parent,false)
        return GrocerryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrocerryViewHolder, position: Int) {
        holder.nameTv.text=list.get(position).itemName
        holder.quantityTv.text=list.get(position).itemQuantity.toString()
        holder.rateTv.text="Rs. " + itemCount.toString()
        val itemTotal:Int= list.get(position).itemPrice*list.get(position).itemQuantity
        holder.amountTv.text="Rs. " + itemTotal.toString()
        holder.deleteTv.setOnClickListener {
            grocerryItemsClickInterface.onItemClick(list.get(position))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
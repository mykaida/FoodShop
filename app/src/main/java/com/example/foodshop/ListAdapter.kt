package com.example.foodshop

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter (context: Context, productList: MutableList<Product>):
ArrayAdapter<Product>(context,R.layout.product_list,productList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        var product = getItem(position)
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.product_list, parent, false)
        }
        val imageIV = view?.findViewById<ImageView>(R.id.imageIV)
        val productNameTV = view?.findViewById<TextView>(R.id.productNameTV)
        val productPriceTV = view?.findViewById<TextView>(R.id.productPriceTV)

        imageIV?.setImageBitmap(product?.image)
        productNameTV?.text = product?.name
        productPriceTV?.text = product?.price.toString()

        return view!!
    }
}
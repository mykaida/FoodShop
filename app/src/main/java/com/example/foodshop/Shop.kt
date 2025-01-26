package com.example.foodshop

import android.annotation.SuppressLint
import android.app.ComponentCaller
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class Shop : AppCompatActivity() {

    private lateinit var productImageIV: ImageView
    private lateinit var productNameET: EditText
    private lateinit var productPriceET: EditText
    private lateinit var productListLV: ListView
    private lateinit var productAddBTN: Button
    private lateinit var toolbarTB: Toolbar
    private val GALLERY_REQUEST = 101
    var bitmap: Bitmap? = null
    private final var products: MutableList<Product> = mutableListOf()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shop)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        productImageIV = findViewById(R.id.productImageIV)
        productNameET = findViewById(R.id.productNameET)
        productPriceET = findViewById(R.id.productPriceET)
        productListLV = findViewById(R.id.productListLV)
        productAddBTN = findViewById(R.id.productAddBTN)
        toolbarTB = findViewById(R.id.toolbarTB)
        setSupportActionBar(toolbarTB)

        //Нажатие на иконку
        productImageIV.setOnClickListener {
            val photoIntent = Intent(Intent.ACTION_PICK)
            photoIntent.type = "image/*"
            startActivityForResult(photoIntent, GALLERY_REQUEST)
        }
        //Нажатие на кнопку
        productAddBTN.setOnClickListener {
            val productName = productNameET.text.toString()
            var error = true
            val productImage = bitmap
            var productPrice = 0.0
            try{
                productPrice = productPriceET.text.toString().toDouble()
                error = false
            } catch (e:Exception){
                error = true
               }
            if (error || productName.isEmpty()) {
                Toast.makeText(this,"Плохие данные, совсем плохие!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            products.add(Product(productName, productPrice, productImage))

            val adapter = ListAdapter(this, products)
            productListLV.adapter = adapter
            adapter.notifyDataSetChanged()
            //Очищаем поля
            productNameET.text.clear()
            productPriceET.text.clear()
            productImageIV.setImageResource(R.drawable.ic_bug)
        }
    }

//Поход за картинкой в галерею
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        productImageIV = findViewById(R.id.productImageIV)
        when(requestCode){
            GALLERY_REQUEST -> if(resultCode == RESULT_OK){
                val selectImage : Uri? = data?.data
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectImage)
                } catch (e:IOException){
                    e.printStackTrace()
                }
                productImageIV.setImageBitmap(bitmap)
            }
        }
    }


    //Это про меню
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shop_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.exitMN -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }
}
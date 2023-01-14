package com.example.mynous.Activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException
import java.io.InputStream
import androidx.appcompat.widget.SearchView
import com.example.mynous.R
import com.example.mynous.RecyclerAdapter
import com.example.mynous.Data.Items

class ListActivity : AppCompatActivity() {

    private var lngRV: RecyclerView? = null
    private var recyclerAdapter: RecyclerAdapter? = null
    internal lateinit var itemsArray: ArrayList<Items>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        lngRV = findViewById(R.id.recycler_view)
        itemsArray = ArrayList()
        recyclerAdapter = RecyclerAdapter(this, itemsArray)
        lngRV!!.adapter = recyclerAdapter
        lngRV!!.layoutManager = GridLayoutManager(applicationContext, 2)

        fetchingJSON()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)
        val searchView: SearchView = searchItem.getActionView() as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                // calling method to filter recycler view.
                filter(msg)
                return false
            }
        })
        return true
    }

    private fun fetchingJSON() {
        try {
            // get JSONObject from JSON file
            val jsonObject = JSONTokener(loadJSONFromAsset()).nextValue() as JSONObject
            // fetch JSONObject named items
            val jsonArray = jsonObject.getJSONArray("items")
            // get id and title
            for (i in 0 until jsonArray.length()){
                val items = Items()
                val id = jsonArray.getJSONObject(i).getInt("id")
                Log.i("ID: ", id.toString())
                val title = jsonArray.getJSONObject(i).getString("title")
                Log.i("title", title)
                val description = jsonArray.getJSONObject(i).getString("description")
                Log.i("title", description)
                val imageUrl = jsonArray.getJSONObject(i).getString("imageUrl")
                Log.i("title", imageUrl)

                // set all item data to data class
                items.id = id
                items.title = title
                items.description = description
                items.imageUrl = imageUrl
                // add item data to arraylist
                itemsArray.add(items)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    private fun loadJSONFromAsset(): String? {
        var json: String? = null
        json = try {
            //fetch data from json file from assets folder
            val jsonString: InputStream = assets.open("items.json")
            val size: Int = jsonString.available()
            val buffer = ByteArray(size)
            jsonString.read(buffer)
            jsonString.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    private fun filter(text: String) {
        val filteredList: ArrayList<Items> = ArrayList()
        for (item in itemsArray) {
            // filter title and description name
            if (item.title!!.toLowerCase().contains(text.toLowerCase()) || item.description!!.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // list to adapter class.
            recyclerAdapter?.filterList(filteredList)
        }
    }


}

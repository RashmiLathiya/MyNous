package com.example.mynous.Data

import com.google.gson.annotations.SerializedName


data class Main (

  @SerializedName("items" ) var items : ArrayList<Items> = arrayListOf()

)
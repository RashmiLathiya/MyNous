package com.example.mynous.Data

import com.google.gson.annotations.SerializedName


data class Items (

  @SerializedName("id"          ) var id          : Int?    = null,
  @SerializedName("title"       ) var title       : String? = null,
  @SerializedName("description" ) var description : String? = null,
  @SerializedName("imageUrl"    ) var imageUrl    : String? = null

)
package com.frate.luxup.model

import com.google.gson.JsonObject

data class Article(
        val id: Int?,
        val name: String,
        val image: String,
        val price: Float
) {
        fun toJson(): JsonObject {
                val jsonObject = JsonObject()
                jsonObject.addProperty("name", name)
                jsonObject.addProperty("image", image)
                jsonObject.addProperty("price", price)
                return jsonObject
        }
}

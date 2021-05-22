package com.example.vehiclecontacting.Web.UserController

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class PostUserPhoto(
    var code: Int,
    var data: Any,
    var msg: String) {

    class DataStateDeserializer : JsonDeserializer<PostUserPhoto> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): PostUserPhoto {
            val response = Gson().fromJson(json, PostUserPhoto::class.java)
            val jsonObject = json!!.asJsonObject

            if (jsonObject.has("data")) {
                val elem = jsonObject.get("data")
                if (elem != null && !elem.isJsonNull) {
                    if (elem.isJsonPrimitive)
                        response.msg = elem.asString
                    else {
                        if (elem.asJsonObject.has("url"))
                            response.data = elem.asJsonObject.get("url").asString
                    }
                }
            }
            return response
        }
    }
}
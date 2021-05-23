package com.example.vehiclecontacting.Web.DiscussController

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class PostDiscussPhoto(
    var code: Int,
    var data: Any,
    var msg: String) {

    class DataStateDeserializer : JsonDeserializer<PostDiscussPhoto> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): PostDiscussPhoto {
            val response = Gson().fromJson(json, PostDiscussPhoto::class.java)
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

package com.example.vehiclecontacting.Web.UserController

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class PostLogin(
    var code: Int,
    var data: Any,
    var msg: String) {

    class DataStateDeserializer : JsonDeserializer<PostLogin> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): PostLogin {
            val response = Gson().fromJson(json, PostLogin::class.java)
            val jsonObject = json!!.asJsonObject

            if (jsonObject.has("data")) {
                val elem = jsonObject.get("data")
                if (elem != null && !elem.isJsonNull) {
                    if (elem.isJsonPrimitive)
                        response.msg = elem.asString
                    else {
                        if (elem.asJsonObject.has("token"))
                            response.data = elem.asJsonObject.get("token").asString
                        else if (elem.asJsonObject.has("frozenDate"))
                            response.data = elem.asJsonObject.get("frozenDate").asString
                    }
                }
            }
            return response
        }
    }
}


package com.example.vehiclecontacting.Web.VehicleController

import com.example.vehiclecontacting.Web.DiscussController.PostDiscussPhoto
import com.example.vehiclecontacting.Web.UserController.Data
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class PostVehiclePhoto(
    var code: Int,
    var data: Any,
    var msg: String) {

    class DataStateDeserializer : JsonDeserializer<PostVehiclePhoto> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): PostVehiclePhoto {
            val response = Gson().fromJson(json, PostVehiclePhoto::class.java)
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

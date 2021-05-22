package com.example.vehiclecontacting.Web.UserController

import com.google.gson.*
import java.lang.reflect.Type

data class PostLoginByCode(
    var code: Int,
    var data: Any,
    var msg: String) {

    class DataStateDeserializer : JsonDeserializer<PostLoginByCode> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): PostLoginByCode {
            val response = Gson().fromJson(json, PostLoginByCode::class.java)
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

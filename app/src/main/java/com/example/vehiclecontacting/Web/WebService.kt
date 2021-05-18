package com.example.vehiclecontacting.Web

import com.example.vehiclecontacting.Web.UserController.UserJson
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface WebService {

    /***
     *  User Controller
     */
    @GET("user")
    fun getUser(
        @Query("id") id: Int
    )
        : UserJson

    @PATCH("user")
    fun patchUser(
        @Query("id") id: Int,
        @Query("sex") sex: String = "",
        @Query("username") username: String = "")

    @POST("changePassword")
    fun userChangePassword()

    @POST("code")
    fun userCode()

    @POST("findPassword")
    fun userFindPassword()

    @POST("loginByCode")
    fun userLoginByCode()

    @POST("register")
    fun userRegister()

    @POST("userPhoto")
    fun userPhoto()

    companion object Factory {
        fun create() : WebService {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val retrofit: Retrofit =  Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(WebService::class.java)
        }
    }
}
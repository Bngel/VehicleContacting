package com.example.vehiclecontacting.Web

import com.example.vehiclecontacting.SSLSocketClient
import com.example.vehiclecontacting.Web.UserController.*
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface WebService {

    /***
     *  User Controller
     */
    @GET("user")
    fun getUser(
        @Query("phone") phone: String
    )
        :Call<GetUser>

    @PATCH("user")
    fun patchUser(
        @Query("id") id: Int,
        @Query("sex") sex: String = "",
        @Query("username") username: String = ""
    )
        : Call<PatchUser>

    @POST("changePassword")
    fun postChangePassword(
        @Query("code") code: String,
        @Query("newPassword") newPassword: String,
        @Query("oldPassword") oldPassword: String,
        @Query("phone") phone: String
    )
        : Call<PostChangePassword>

    @POST("code")
    fun postCode(
        @Query("phone") phone: String,
        @Query("type") type: Int
    )
        : Call<PostCode>

    @POST("findPassword")
    fun postFindPassword(
        @Query("code") code: String,
        @Query("newPassword") newPassword: String,
        @Query("phone") phone: String
    )
        : Call<PostFindPassword>

    @POST("loginByCode")
    fun postLoginByCode(
        @Query("code") code: String,
        @Query("phone") phone: String
    )
        : Call<PostLoginByCode>

    @POST("register")
    fun postRegister(
        @Query("code") code: String,
        @Query("password") password: String,
        @Query("phone") phone: String
    )
        : Call<PostRegister>

    @Multipart
    @POST("userPhoto")
    fun postUserPhoto(
        @Part("id") id: String,
        @Part("photo") photo: MultipartBody.Part
    )
        : Call<PostUserPhoto>


    companion object Factory {
        fun create() : WebService {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val mHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.SSLSocketFactory)//配置
                .hostnameVerifier(SSLSocketClient.hostnameVerifier)//配置
                .build();

            val retrofit: Retrofit =  Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(mHttpClient)
                .build()
            return retrofit.create(WebService::class.java)
        }
    }
}
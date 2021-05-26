package com.example.vehiclecontacting.Web

import com.example.vehiclecontacting.Repository.SSLSocketClient
import com.example.vehiclecontacting.Web.DiscussController.*
import com.example.vehiclecontacting.Web.UserController.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
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

    @GET("user")
    fun getUserById(
        @Query("id") id: String
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

    @POST("login")
    fun postLogin(
        @Query("phone") phone: String,
        @Query("password") password: String
    )
            : Call<PostLogin>

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
        @Part photo: MultipartBody.Part
    )
        : Call<PostUserPhoto>

    /***
     *  Discuss Controller
     */
    @Multipart
    @POST("discussPhoto")
    fun postDiscussPhoto(
        @Part photo: MultipartBody.Part
    )
            : Call<PostDiscussPhoto>


    @POST("discuss")
    fun postDiscuss(
        @Query("description") description: String,
        @Query("id") id: String,
        @Query("photo1") photo1: String,
        @Query("photo2") photo2: String,
        @Query("photo3") photo3: String,
        @Query("title") title: String
    )
            : Call<PostDiscuss>

    @GET("discuss")
    fun getDiscuss(
        @Query("id") id: String,
        @Query("cnt") description: Int,
        @Query("isOrderByTime") isOrderByTime: Int,
        @Query("keyword") keyword: String,
        @Query("page") page: Int,
        @Query("isFollow") isFollow: Int
    )
            : Call<GetDiscuss>

    @DELETE("discuss")
    fun deleteDiscuss(
        @Query("id") id: String,
        @Query("number") number: String
    )
            : Call<DeleteDiscuss>

    @GET("comment")
    fun getComment(
        @Query("cnt") description: Int,
        @Query("isOrderByTime") isOrderByTime: Int,
        @Query("number") number: String,
        @Query("page") page: Int,
    )
            : Call<GetComment>


    @POST("fans")
    fun postFans(
        @Query("fromId") fromId: String,
        @Query("toId") toId: String
    )
            : Call<PostFans>

    @DELETE("fans")
    fun deleteFans(
        @Query("fromId") fromId: String,
        @Query("toId") toId: String
    )
            : Call<DeleteFans>

    @POST("judgeFavor")
    fun postJudgeFavor(
        @Query("fromId") fromId: String,
        @Query("toId") toId: String
    )
            : Call<PostJudgeFavor>

    @POST("likeAndFavor")
    fun postLikeAndFavor(
        @Query("id") id: String,
        @Query("number") number: String
    )
            : Call<PostLikeAndFavor>

    @POST("like")
    fun postLike(
        @Query("id") id: String,
        @Query("number") number: String
    )
            : Call<PostLike>

    @DELETE("like")
    fun deleteLike(
        @Query("id") id: String,
        @Query("number") number: String
    )
            : Call<DeleteLike>

    @POST("likeDiscuss")
    fun postLikeDiscuss(
        @Query("id") id: String,
        @Query("number") number: String
    )
            : Call<PostLikeDiscuss>

    @DELETE("likeDiscuss")
    fun deleteLikeDiscuss(
        @Query("id") id: String,
        @Query("number") number: String
    )
            : Call<DeleteLikeDiscuss>

    @POST("favorDiscuss")
    fun postFavorDiscuss(
        @Query("id") id: String,
        @Query("number") number: String
    )
            : Call<PostFavorDiscuss>

    @DELETE("favorDiscuss")
    fun deleteFavorDiscuss(
        @Query("id") id: String,
        @Query("number") number: String
    )
            : Call<DeleteFavorDiscuss>

    @GET("follow")
    fun getFollow(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    )
            : Call<GetFollow>

    @GET("fans")
    fun getFans(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    )
            : Call<GetFans>


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
                .build()

            val retrofit: Retrofit =  Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(mHttpClient)
                .build()
            return retrofit.create(WebService::class.java)
        }

        fun create(gson: Gson): WebService {
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
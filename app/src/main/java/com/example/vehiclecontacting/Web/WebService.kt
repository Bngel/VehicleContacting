package com.example.vehiclecontacting.Web

import com.example.vehiclecontacting.Repository.SSLSocketClient
import com.example.vehiclecontacting.Web.AdministratorController.*
import com.example.vehiclecontacting.Web.BoxController.DeleteBoxMessage
import com.example.vehiclecontacting.Web.BoxController.GetAllBox
import com.example.vehiclecontacting.Web.DiscussController.*
import com.example.vehiclecontacting.Web.TalkController.DeleteTalk
import com.example.vehiclecontacting.Web.TalkController.GetTalk
import com.example.vehiclecontacting.Web.TalkController.GetTalkList
import com.example.vehiclecontacting.Web.UserController.*
import com.example.vehiclecontacting.Web.VehicleController.*
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
    fun patchUserDescription(
        @Query("id") id: String,
        @Query("introduction") description: String
    )
        : Call<PatchUser>

    @PATCH("user")
    fun patchUserSex(
        @Query("id") id: String,
        @Query("sex") sex: String
    )
            : Call<PatchUser>

    @PATCH("user")
    fun patchUserUsername(
        @Query("id") id: String,
        @Query("username") username: String
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

    @GET("firstDiscuss")
    fun getFirstDiscuss(
        @Query("cnt") cnt: Int,
        @Query("number") number: String
    )
            : Call<GetFirstDiscuss>

    @GET("firstDiscuss")
    fun getFirstDiscuss(
        @Query("id") id: String,
        @Query("cnt") cnt: Int,
        @Query("number") number: String
    )
            : Call<GetFirstDiscuss>

    @GET("secondDiscuss")
    fun getSecondDiscuss(
        @Query("cnt") cnt: Int,
        @Query("isOrderByHot") isOrderByHot: Int,
        @Query("number") number: String,
        @Query("page") page: Int
    )
            : Call<GetSecondDiscuss>

    @GET("thirdDiscuss")
    fun getThirdDiscuss(
        @Query("cnt") cnt: Int,
        @Query("number") number: String,
        @Query("page") page: Int
    )
            : Call<GetThirdDiscuss>

    @POST("comment")
    fun postComment(
        @Query("comments") comments: String,
        @Query("fatherNumber") fatherNumber: String,
        @Query("id") id: String,
        @Query("number") number: String,
        @Query("replyNumber") replyNumber: String
    )
            : Call<PostComment>

    @POST("commentLike")
    fun postCommentLike(
        @Query("id") id: String,
        @Query("number") number: String
    )
            : Call<PostCommentLike>

    @GET("admin/vehicleList")
    fun getVehicleList(
        @Query("cnt") cnt: Int,
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    )
            : Call<GetJudgeVehicleList>

    @POST("/admin/judgeVehicle")
    fun postJudgeVehicle(
        @Query("isPass") isPass: Int,
        @Query("license") license: String,
        @Query("vehicleBrand") vehicleBrand: String,
        @Query("reason") reason: String = ""
    )
            : Call<PostJudgeVehicle>

    @POST("/admin/frozeUser")
    fun postFrozeUser(
        @Query("id") id: String,
        @Query("minutes") minutes: Int
    )
            : Call<PostFrozeUser>

    @GET("/admin/frozenList")
    fun getFrozenList(
        @Query("cnt") cnt: Int,
        @Query("page") page: Int
    )
            : Call<GetFrozenList>

    @POST("/admin/reopenUser")
    fun postReopenUser(
        @Query("id") id: String
    )
            : Call<PostReopenUser>

    @GET("hotDiscuss")
    fun getHotDiscuss()
            : Call<GetHotDiscuss>

    @POST("vehiclePhoto")
    @Multipart
    fun postVehiclePhoto(
        @Part("id") id: String,
        @Part photo: MultipartBody.Part
    )
            : Call<PostVehiclePhoto>

    @POST("vehicle")
    fun postVehicle(
        @Query("description") description: String,
        @Query("id") id: String,
        @Query("license") license: String,
        @Query("licensePhoto") licensePhoto: String,
        @Query("type") type: String,
        @Query("vehicleBrand") vehicleBrand: String
    )
            : Call<PostVehicle>

    @GET("vehicleList")
    fun getVehicleList(
        @Query("id") id: String
    )
            : Call<GetVehicleList>

    @GET("userDiscuss")
    fun getUserDiscuss(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("page") page: Int
    )
            : Call<GetUserDiscuss>

    @GET("favorDiscuss")
    fun getFavorDiscuss(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("page") page: Int
    )
            : Call<GetFavorDiscuss>

    @GET("history")
    fun getHistory(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("page") page: Int
    )
            : Call<GetHistory>

    @DELETE("allHistory")
    fun deleteAllHistory(
        @Query("id") id: String
    )
            : Call<DeleteAllHistory>

    @GET("firstPageDiscuss")
    fun getFirstPageDiscuss(
        @Query("cnt") cnt: Int
    )
            : Call<GetFirstPageDiscuss>

    @POST("judgeFriend")
    fun postJudgeFriend(
        @Query("fromId") fromId: String,
        @Query("toId") toId: String
    )
            : Call<PostJudgeFriend>

    @POST("friend")
    fun postFriend(
        @Query("fromId") fromId: String,
        @Query("reason") reason: String,
        @Query("toId") toId: String
    )
            : Call<PostFriend>

    @GET("friendList")
    fun getFriendList(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("page") page: Int,
        @Query("type") type: Int
    )
            : Call<GetFriendList>

    @DELETE("friend")
    fun deleteFriend(
        @Query("fromId") fromId: String,
        @Query("toId") toId: String
    )
            : Call<DeleteFriend>

    @POST("verifyFriend")
    fun postVerifyFriend(
        @Query("fromId") fromId: String,
        @Query("isPass") isPass: Int,
        @Query("toId") toId: String
    )
            : Call<PostVerifyFriend>

    @GET("postFriendList")
    fun getPostFriendList(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("page") page: Int,
        @Query("type") type: Int
    )
            : Call<GetPostFriendList>

    @GET("userQRcode")
    fun getUserQRCode(
        @Query("id") id: String
    )
            : Call<String>

    @GET("searchUser")
    fun getSearchUser(
        @Query("cnt") cnt: Int,
        @Query("page") page: Int,
        @Query("username") username: String
    )
            : Call<GetSearchUser>

    @GET("searchVehicle")
    fun getSearchVehicle(
        @Query("cnt") cnt: Int,
        @Query("page") page: Int,
        @Query("type") type: Int,
        @Query("keyword") keyword: String
    )
            : Call<GetSearchVehicle>

    @POST("linkUser")
    fun postLinkUser(
        @Query("fromId") fromId: String,
        @Query("relationship") relationship: String,
        @Query("toId") toId: String
    )
            : Call<PostLinkUser>

    @GET("postLinkUser")
    fun getPostLinkUser(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("page") page: Int,
        @Query("type") type: Int
    )
            : Call<GetPostLinkUser>

    @POST("judgeLinkUser")
    fun postJudgeLinkUser(
        @Query("fromId") fromId: String,
        @Query("isPass") isPass: Int,
        @Query("toId") toId: String
    )
            : Call<PostJudgeLinkUser>

    @GET("linkUser")
    fun getLinkUser(
        @Query("id") id: String
    )
            : Call<GetLinkUser>

    @GET("talk")
    fun getTalk(
        @Query("cnt") cnt: Int,
        @Query("fromId") fromId: String,
        @Query("page") page: Int,
        @Query("toId") toId: String
    )
            : Call<GetTalk>

    @DELETE("removeLink")
    fun deleteRemoveLink(
        @Query("fromId") fromId: String,
        @Query("toId") toId: String
    )
            : Call<DeleteRemoveLink>

    @GET("judgeLink")
    fun getJudgeLink(
        @Query("fromId") fromId: String,
        @Query("toId") toId: String
    )
            : Call<GetJudgeLink>

    @GET("talkList")
    fun getTalkList(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("page") page: Int
    )
            : Call<GetTalkList>

    @DELETE("talk")
    fun deleteTalk(
        @Query("fromId") fromId: String,
        @Query("toId") toId: String
    )
            : Call<DeleteTalk>

    @GET("allBox")
    fun getAllBox(
        @Query("cnt") cnt: Int,
        @Query("id") id: String,
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    )
            : Call<GetAllBox>

    @DELETE("boxMessage")
    fun deleteBoxMessage(
        @Query("id") id: String,
        @Query("numbers") numbers: List<String>
    )
            : Call<DeleteBoxMessage>

    @DELETE("vehicle")
    fun deleteVehicle(
        @Query("id") id: String,
        @Query("license") license: String
    )
            : Call<DeleteVehicle>




    companion object Factory {
        fun create() : WebService {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val mHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.SSLSocketFactory)//??????
                .hostnameVerifier(SSLSocketClient.hostnameVerifier)//??????
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
                .sslSocketFactory(SSLSocketClient.SSLSocketFactory)//??????
                .hostnameVerifier(SSLSocketClient.hostnameVerifier)//??????
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
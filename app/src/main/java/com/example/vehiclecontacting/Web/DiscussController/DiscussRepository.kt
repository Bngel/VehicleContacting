package com.example.vehiclecontacting.Web.DiscussController

import android.util.Log
import com.example.vehiclecontacting.Repository.LogRepository
import com.example.vehiclecontacting.Repository.StatusRepository
import com.example.vehiclecontacting.Web.WebService
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import retrofit2.http.Query
import java.lang.Exception
import kotlin.concurrent.thread

object DiscussRepository {

    private val discussService = WebService.create()

    val imageUrl = ArrayList<String>()

    val discussList = ArrayList<Discuss>()
    val commentList = ArrayList<Comment>()
    val firstCommentList = ArrayList<FirstComment>()
    val secondCommentList = ArrayList<SecondComment>()
    var secondPage = 0
    var secondCount = 0
    val thirdCommentList = ArrayList<ThirdComment>()
    var thirdPage = 0
    var thirdCount = 0

    lateinit var ownerComment: OwnerComment
    lateinit var thirdOwnerComment: CommentOwner

    var pageCount = 0

    const val LIKE_STATUS = 0
    const val LIKED_STATUS = 1
    var like = 0

    const val FAVOR_STATUS = 0
    const val FAVORED_STATUS = 1
    var favor = 0

    /***
     * msg:
     * fileWrong：文件为空
     * typeWrong：文件类型错误
     * success：成功（返回json带url）
     */
    fun postDiscussPhoto(photo: MultipartBody.Part): Int {
        val gson = GsonBuilder()
            .registerTypeAdapter(PostDiscussPhoto::class.java, PostDiscussPhoto.DataStateDeserializer())
            .setLenient()
            .create()
        val photoService = WebService.create(gson)
        val data = photoService.postDiscussPhoto(photo)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.updateDiscussPhotoLog(body)
                if (msg == "success")
                    imageUrl.add(body.data as String)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "fileWrong" -> StatusRepository.FILE_WRONG
            "typeWrong" -> StatusRepository.TYPE_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * repeatWrong：该用户短期创建新帖子太多（2小时5次以上）
     * success：成功（返回json带url）
     */
    fun postDiscuss(description: String, id: String, title: String,
                    photo1: String = "", photo2: String = "", photo3: String = ""): Int {
        val data = discussService.postDiscuss(description, id, photo1, photo2, photo3, title)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.sendDiscussLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "repeatWrong" -> StatusRepository.TYPE_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * repeatWrong：该用户短期创建新帖子太多（2小时5次以上）
     * success：成功（返回json带url）
     */
    fun postDiscuss(description: String, id: String, title: String,
                    photos: List<String>): Int {
        var photo1 = ""
        var photo2 = ""
        var photo3 = ""
        for (photo in photos.indices) {
            when (photo) {
                0 -> photo1 = photos[photo]
                1 -> photo2 = photos[photo]
                2 -> photo3 = photos[photo]
            }
        }
        val data = discussService.postDiscuss(description, id, photo1, photo2, photo3, title)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.sendDiscussLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "repeatWrong" -> StatusRepository.TYPE_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * success：成功（返回json discussList（主页帖子信息列表） pages（页面总数） counts（帖子总数））
     */
    fun getDiscuss(cnt: Int, isOrderByTime: Int, page: Int, isFollow: Int, keyword: String = "", id: String = ""): Int {
        val data = discussService.getDiscuss(id, cnt,isOrderByTime, keyword, page, isFollow)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                discussList.addAll(body.data.discussList)
                pageCount = body.data.pages
                LogRepository.getDiscussLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * existWrong：帖子不存在（可能是重复删除）
     * userWrong：用户不是帖子的主人
     * success：成功
     */
    fun deleteDiscuss(id: String, number: String): Int {
        val data = discussService.deleteDiscuss(id, number)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                LogRepository.deleteDiscussLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "existWrong" -> StatusRepository.EXIST_WRONG
            "userWrong" -> StatusRepository.USER_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * existWrong：帖子不存在（可能被删了，提示一下）
     * success：成功（返回json ownerComment（楼主的评论和帖子的相关信息） pages（页面总数） counts（帖子总数） commentList（评论列表））
     */
    fun getComment(cnt: Int, isOrderByTime: Int, page: Int, number: String): Int {
        val data = discussService.getComment(cnt,isOrderByTime, number, page)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                commentList.clear()
                commentList.addAll(body.data.commentList)
                ownerComment = body.data.OwnerComment
                pageCount = body.data.pages
                LogRepository.getCommentLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "existWrong" -> StatusRepository.EXIST_WRONG
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * success：成功 （返回json isLike（是否点赞） isFavor（是否收藏））
     */
    fun postLikeAndFavor(id: String, number: String): Int {
        val data = discussService.postLikeAndFavor(id, number)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success"){
                    favor = body.data.isFavor
                    like = body.data.isLike
                }
                LogRepository.postLikeAndFavorLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * repeatWrong：评论已被点赞（可能是重复请求）
     * existWrong：评论不存在
     * success：成功
     */
    fun postLike(id: String, number: String): Int {
        val data = discussService.postLike(id, number)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success")
                    like = LIKED_STATUS
                LogRepository.postLikeLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            "repeatWrong" -> StatusRepository.REPEAT_WRONG
            "existWrong" -> StatusRepository.EXIST_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * repeatWrong：评论未被点赞（可能是重复请求）
     * existWrong：评论不存在
     * success：成功
     */
    fun deleteLike(id: String, number: String): Int {
        val data = discussService.deleteLike(id, number)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success")
                    like = LIKE_STATUS
                LogRepository.deleteLikeLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            "repeatWrong" -> StatusRepository.REPEAT_WRONG
            "existWrong" -> StatusRepository.EXIST_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * existWrong：帖子不存在
     * success：成功
     */
    fun postLikeDiscuss(id: String, number: String): Int {
        val data = discussService.postLikeDiscuss(id, number)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success")
                    like = LIKED_STATUS
                LogRepository.postLikeDiscussLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            "existWrong" -> StatusRepository.EXIST_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * existWrong：帖子不存在
     * success：成功
     */
    fun deleteLikeDiscuss(id: String, number: String): Int {
        val data = discussService.deleteLikeDiscuss(id, number)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success")
                    like = LIKE_STATUS
                LogRepository.deleteLikeDiscussLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            "existWrong" -> StatusRepository.EXIST_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * repeatWrong：帖子已被收藏（可能是重复收藏）
     * existWrong：帖子不存在
     * success：成功
     */
    fun postFavorDiscuss(id: String, number: String): Int {
        val data = discussService.postFavorDiscuss(id, number)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success")
                    like = LIKED_STATUS
                LogRepository.postFavorDiscussLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            "existWrong" -> StatusRepository.EXIST_WRONG
            "repeatWrong" -> StatusRepository.REPEAT_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * repeatWrong：帖子未被收藏（可能是重复请求）
     * existWrong：帖子不存在
     * success：成功
     */
    fun deleteFavorDiscuss(id: String, number: String): Int {
        val data = discussService.deleteFavorDiscuss(id, number)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success")
                    like = LIKE_STATUS
                LogRepository.deleteFavorDiscussLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            "existWrong" -> StatusRepository.EXIST_WRONG
            "repeatWrong" -> StatusRepository.REPEAT_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * existWrong：帖子不存在
     * success：成功 （返回json ownerComment：帖子主人写的内容 firstCommentList：下面的评论列表（2-3个就好））
     */
    fun getFirstDiscuss(cnt: Int, number: String): Int {
        val data = discussService.getFirstDiscuss(cnt, number)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success"){
                    ownerComment = body.data.ownerComment
                    firstCommentList.clear()
                    firstCommentList.addAll(body.data.firstCommentList)
                }
                LogRepository.getFirstDiscussLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            "existWrong" -> StatusRepository.EXIST_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * existWrong：帖子不存在
     * success：成功 （返回json secondCommentList：二级评论列表 pages：页面数 counts：数据总数）
     */
    fun getSecondDiscuss(cnt: Int, isOrderByHot: Int, number: String, page: Int): Int {
        val data = discussService.getSecondDiscuss(cnt, isOrderByHot, number, page)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success"){
                    secondPage = body.data.pages
                    secondCount = body.data.counts
                    secondCommentList.clear()
                    secondCommentList.addAll(body.data.secondCommentList)
                }
                LogRepository.getSecondDiscussLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            "existWrong" -> StatusRepository.EXIST_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }

    /***
     * msg:
     * existWrong：评论不存在
     * success：成功 （返回json thirdCommentList：三级评论列表 OwnerComment：父级评论（显示在最上面） pages：页面数 counts：页面数据量）
     */
    fun getThirdDiscuss(cnt: Int, number: String, page: Int): Int {
        val data = discussService.getThirdDiscuss(cnt, number, page)
        var msg = ""
        try {
            thread {
                val body = data.execute().body()!!
                msg = body.msg
                if (msg == "success"){
                    thirdPage = body.data.pages
                    thirdCount = body.data.counts
                    thirdCommentList.clear()
                    thirdCommentList.addAll(body.data.thirdCommentList)
                    thirdOwnerComment = body.data.ownerComment
                }
                LogRepository.getThirdDiscussLog(body)
            }.join(4000)
        } catch (e: Exception) {}
        return when (msg) {
            "success" -> StatusRepository.SUCCESS
            "existWrong" -> StatusRepository.EXIST_WRONG
            else -> StatusRepository.UNKNOWN_WRONG
        }
    }
}
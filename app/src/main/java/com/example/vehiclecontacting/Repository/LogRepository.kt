package com.example.vehiclecontacting.Repository

import android.util.Log
import com.example.vehiclecontacting.Web.AdministratorController.*
import com.example.vehiclecontacting.Web.BoxController.DeleteBoxMessage
import com.example.vehiclecontacting.Web.BoxController.GetAllBox
import com.example.vehiclecontacting.Web.DiscussController.*
import com.example.vehiclecontacting.Web.TalkController.DeleteTalk
import com.example.vehiclecontacting.Web.TalkController.GetTalk
import com.example.vehiclecontacting.Web.TalkController.GetTalkList
import com.example.vehiclecontacting.Web.UserController.*
import com.example.vehiclecontacting.Web.VehicleController.*
import java.lang.StringBuilder

object LogRepository {

    fun getUserLog(body: GetUser) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取用户信息接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取用户信息接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 用户不存在\t-\n")
            }
            "success" -> {
                stringBuilder.append("-\tmsg: 获取用户信息成功\t-\n")
                stringBuilder.append("-\tuser: ${body.data.user}\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 登录发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun loginLog(body: PostLoginByCode) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t登录接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t登录接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "codeExistWrong" -> {
                stringBuilder.append("-\tmsg: 验证码不存在或已失效\t-\n")
            }
            "userWrong" -> {
                stringBuilder.append("-\tmsg: 用户名或密码错误\t-\n")
            }
            "frozenWrong" -> {
                stringBuilder.append("-\tmsg: 用户已被封号\t-\n")
                stringBuilder.append("-\t封禁时间: ${body.data}\t-\n")
            }
            "success" -> {
                stringBuilder.append("-\tmsg: 登录成功\t-\n")
                stringBuilder.append("-\ttoken: ${body.data}\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 登录发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun loginLog(body: PostLogin) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t登录接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t登录接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "codeExistWrong" -> {
                stringBuilder.append("-\tmsg: 验证码不存在或已失效\t-\n")
            }
            "userWrong" -> {
                stringBuilder.append("-\tmsg: 用户名或密码错误\t-\n")
            }
            "frozenWrong" -> {
                stringBuilder.append("-\tmsg: 用户已被封号\t-\n")
                stringBuilder.append("-\t封禁时间: ${body.data}\t-\n")
            }
            "success" -> {
                stringBuilder.append("-\tmsg: 登录成功\t-\n")
                stringBuilder.append("-\ttoken: ${body.data}\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 登录发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun updateAvtLog(body: PostUserPhoto) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t修改头像接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t修改头像接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 用户不存在\t-\n")
            }
            "fileWrong" -> {
                stringBuilder.append("-\tmsg: 文件为空\t-\n")
            }
            "success" -> {
                stringBuilder.append("-\tmsg: 上传成功\t-\n")
                stringBuilder.append("-\turl: ${body.data}\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun updateDiscussPhotoLog (body: PostDiscussPhoto) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t上传帖子图片接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t上传帖子图片接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "typeWrong" -> {
                stringBuilder.append("-\tmsg: 文件类型错误\t-\n")
            }
            "fileWrong" -> {
                stringBuilder.append("-\tmsg: 文件为空\t-\n")
            }
            "success" -> {
                stringBuilder.append("-\tmsg: 上传成功\t-\n")
                stringBuilder.append("-\turl: ${body.data}\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun sendDiscussLog (body: PostDiscuss) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t发表帖子接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t发表帖子接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "repeatWrong" -> {
                stringBuilder.append("-\tmsg: 该用户短期创建新帖子太多\t-\n")
            }
            "success" -> {
                stringBuilder.append("-\tmsg: 发表成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getDiscussLog (body: GetDiscuss) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取帖子接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取帖子接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取成功\t-\n")
                stringBuilder.append("-\tpages: ${body.data.pages}\t-\n")
                for (discuss in body.data.discussList)
                    stringBuilder.append("-\ttitle: ${discuss.title}  number: ${discuss.number}\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun deleteDiscussLog (body: DeleteDiscuss) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t删除帖子接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t删除帖子接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 帖子不存在\t-\n")
            }
            "userWrong" -> {
                stringBuilder.append("-\tmsg: 用户不是帖子的主人\t-\n")
            }
            "success" -> {
                stringBuilder.append("-\tmsg: 删除帖子成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getCommentLog (body: GetComment) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取评论一级界面接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取评论一级界面接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取成功\t-\n")
                stringBuilder.append("-\tfromId: ${body.data.OwnerComment.fromId}\t-\n")
                stringBuilder.append("-\tnumber: ${body.data.OwnerComment.number}\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 帖子不存在\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postFansLog (body: PostFans) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t用户关注接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t用户关注接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 关注成功\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 用户不存在\t-\n")
            }
            "repeatWrong" -> {
                stringBuilder.append("-\tmsg: 重复关注\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun deleteFansLog (body: DeleteFans) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t用户取消关注接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t用户取消关注接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 取消关注成功\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 用户不存在\t-\n")
            }
            "repeatWrong" -> {
                stringBuilder.append("-\tmsg: 重复取消关注\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postJudgeFavorLog(body: PostJudgeFavor) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t查询关注状态接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t查询关注状态接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 查询关注状态成功\t-\n")

                when (UserRepository.followStatus) {
                    UserRepository.FOLLOW_NOT -> {
                        stringBuilder.append("-\tstatus: 未关注\t-\n")
                    }
                    UserRepository.FOLLOW_ED -> {
                        stringBuilder.append("-\tstatus: 已关注\t-\n")
                    }
                    UserRepository.FOLLOW_BOTH -> {
                        stringBuilder.append("-\tstatus: 已相互关注\t-\n")
                    }
                }
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postLikeAndFavorLog(body: PostLikeAndFavor) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t查询帖子点赞与收藏状态接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t查询帖子点赞与收藏状态接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 查询帖子点赞与收藏状态成功\t-\n")
                stringBuilder.append("-\tlike: 点赞状态 : ${body.data.isLike}\t-\n")
                stringBuilder.append("-\tfavor: 收藏状态 : ${body.data.isFavor}\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postLikeLog(body: PostLike) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t评论点赞接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t评论点赞接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "repeatWrong" -> {
                stringBuilder.append("-\tmsg: 评论已被点赞\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 评论不存在\t-\n")
            }
            "success" -> {
                stringBuilder.append("-\tmsg: 评论点赞成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun deleteLikeLog(body: DeleteLike) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t评论取消点赞接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t评论取消点赞接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "repeatWrong" -> {
                stringBuilder.append("-\tmsg: 评论已被取消点赞(重复操作)\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 评论不存在\t-\n")
            }
            "success" -> {
                stringBuilder.append("-\tmsg: 评论成功取消点赞\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postLikeDiscussLog(body: PostLikeDiscuss) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t帖子点赞接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t帖子点赞接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 帖子不存在\t-\n")
            }
            "success" -> {
                stringBuilder.append("-\tmsg: 帖子点赞成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun deleteLikeDiscussLog(body: DeleteLikeDiscuss) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t帖子取消点赞接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t帖子取消点赞接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 帖子不存在\t-\n")
            }
            "success" -> {
                stringBuilder.append("-\tmsg: 帖子成功取消点赞\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postFavorDiscussLog(body: PostFavorDiscuss) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t帖子收藏接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t帖子收藏接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 帖子不存在\t-\n")
            }
            "repeatWrong" -> {
                stringBuilder.append("-\tmsg: 帖子已经收藏\t-\n")
            }
            "success" -> {
                stringBuilder.append("-\tmsg: 帖子收藏成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun deleteFavorDiscussLog(body: DeleteFavorDiscuss) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t帖子取消收藏接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t帖子取消收藏接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 帖子不存在\t-\n")
            }
            "repeatWrong" -> {
                stringBuilder.append("-\tmsg: 帖子未被收藏\t-\n")
            }
            "success" -> {
                stringBuilder.append("-\tmsg: 帖子成功取消收藏\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getFollowLog(body: GetFollow) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取用户关注信息接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取用户关注信息接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取用户关注信息成功\t-\n")
                stringBuilder.append("-\tcount: ${body.data.counts}\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getFansLog(body: GetFans) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取用户粉丝信息接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取用户粉丝信息接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取用户粉丝信息成功\t-\n")
                stringBuilder.append("-\tcount: ${body.data.counts}\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun patchUserLog(body: PatchUser, type: String) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t用户${type}信息修改接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t用户${type}信息修改接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 用户${type}信息修改成功\t-\n")
            }
            "repeatWrong" -> {
                stringBuilder.append("-\tmsg: 用户${type}信息修改成功(输入相同信息)\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 用户不存在\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getFirstDiscussLog(body: GetFirstDiscuss) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t一级帖子界面接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t一级帖子界面接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 一级帖子界面信息获取成功\t-\n")
                stringBuilder.append("-\tnumber: ${body.data.ownerComment.number}\t-\n")
                stringBuilder.append("-\ttitle: ${body.data.ownerComment.title}\t-\n")
                stringBuilder.append("-\tlikeCount: ${body.data.ownerComment.likeCounts}\t-\n")
                stringBuilder.append("-\tcommentCount: ${body.data.ownerComment.commentCounts}\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 帖子不存在\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getSecondDiscussLog(body: GetSecondDiscuss) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t二级帖子评论接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t二级帖子评论接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 二级帖子评论获取成功\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 帖子不存在\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getThirdDiscussLog(body: GetThirdDiscuss) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t三级帖子评论接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t三级帖子评论接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 三级帖子评论获取成功\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 帖子不存在\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postCommentLog(body: PostComment) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t发表帖子评论接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t发表帖子评论接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 发表帖子评论成功\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 帖子或评论不存在\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postCommentLikeLog(body: PostCommentLike) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t查看帖子评论点赞状态接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t查看帖子评论点赞状态接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 查看帖子评论点赞状态成功\t-\n")
                stringBuilder.append("-\tstatus: ${when(body.data.isLike){
                    DiscussRepository.LIKE -> "LIKE"
                    DiscussRepository.NOT_LIKE -> "NOT_LIKE"
                    else -> ""
                }}\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 评论不存在\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getJudgeVehicleListLog(body: GetJudgeVehicleList) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取需要审核的车辆列表接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取需要审核的车辆列表接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取需要审核的车辆列表成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postJudgeVehicleLog(body: PostJudgeVehicle) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t审核车辆信息接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t审核车辆信息接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 审核车辆信息成功\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 车辆信息不存在\t-\n")
            }
            "repeatWrong" -> {
                stringBuilder.append("-\tmsg: 车辆信息已被审核\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postFrozeUserLog(body: PostFrozeUser) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t封禁用户接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t封禁用户接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 封禁用户成功\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 用户不存在\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getFrozenListLog(body: GetFrozenList) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取被封禁用户接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取被封禁用户接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取被封禁用户成功\t-\n")
                for (user in body.data.frozenUserList) {
                    stringBuilder.append("-\tusername: ${user.username}\t-\n")
                }
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postReopenUserLog(body: PostReopenUser) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t解封用户接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t解封用户接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 解封用户成功\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 用户不存在\t-\n")
            }
            "repeatWrong" -> {
                stringBuilder.append("-\tmsg: 用户已被解封\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getHotDiscussLog(body: GetHotDiscuss) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取热榜接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
            for (discuss in body.data.hotDiscussList) {
                stringBuilder.append("-\ttitle: ${discuss.title}\t-\n")
            }
        }
        else {
            stringBuilder.append("-\t获取热榜接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取热榜成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postVehiclePhotoLog(body: PostVehiclePhoto) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t上传车辆照片接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t上传车辆照片访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 上传车辆照片成功\t-\n")
                stringBuilder.append("-\turl: ${body.data}}\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 用户不存在\t-\n")
            }
            "fileWrong" -> {
                stringBuilder.append("-\tmsg: 文件为空\t-\n")
            }
            "typeWrong" -> {
                stringBuilder.append("-\tmsg: 上传格式错误\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postVehicleLog(body: PostVehicle) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t提交车辆审核接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t提交车辆审核接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 提交车辆审核成功\t-\n")
            }
            "repeatWrong" -> {
                stringBuilder.append("-\tmsg: 车牌号已被申请\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 用户不存在\t-\n")
            }
            "amountWrong" -> {
                stringBuilder.append("-\tmsg: 用户上传车辆超过4个\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getVehicleListLog(body: GetVehicleList) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取车辆信息列表接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取车辆信息列表接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取车辆信息列表成功\t-\n")
                for (vehicle in body.data.vehicleList)
                    stringBuilder.append("-\tlicense: ${vehicle.license}\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun webSocketStatusLog(body: String, msg: String = "") {
        val stringBuilder = StringBuilder()
            stringBuilder.append("-\t获取WebSocket连接状态: $body\t-\n")
        when (body) {
            "onOpen" -> {
                stringBuilder.append("-\t与服务器建立WebSocket连接\t-\n")
            }
            "onMessage" -> {
                stringBuilder.append("-\t接收服务器WebSocket消息: $msg\t-\n")
            }
            "onClose" -> {
                stringBuilder.append("-\t与服务器WebSocket连接关闭\t-\n")
            }
            "onError" -> {
                stringBuilder.append("-\t与服务器WebSocket连接发生异常\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getUserDiscussLog(body: GetUserDiscuss) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取用户文章列表接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取用户文章列表接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取用户文章列表成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getFavorDiscussLog(body: GetFavorDiscuss) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取用户收藏列表接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取用户收藏列表接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取用户收藏列表成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getHistoryLog(body: GetHistory) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取用户浏览记录接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取用户浏览记录接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取用户浏览记录成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun deleteAllHistoryLog(body: DeleteAllHistory) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t清空用户浏览记录接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t清空用户浏览记录接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 清空用户浏览记录成功\t-\n")
            }
            "repeatWrong" -> {
                stringBuilder.append("-\tmsg: 历史记录已被清空, 重复请求\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getFirstPageDiscussLog(body: GetFirstPageDiscuss) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取推荐帖子接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取推荐帖子接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取推荐帖子成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postFriendLog(body: PostFriend) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t申请加好友接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t申请加好友接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 申请加好友成功\t-\n")
            }
            "blackWrong" -> {
                stringBuilder.append("-\tmsg: 用户已被拉黑\t-\n")
            }

            "repeatWrong" -> {
                stringBuilder.append("-\tmsg: 已是好友, 无需重复添加\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postJudgeFriendLog(body: PostJudgeFriend) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t判断是否为好友接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t判断是否为好友接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 判断是否为好友成功\t-\n")
                stringBuilder.append("-\tstatus: ${body.data.status}\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getFriendListLog(body: GetFriendList) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取好友列表接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取好友列表接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取好友列表成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun deleteFriendLog(body: DeleteFriend) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t删除好友接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t删除好友接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 删除好友成功\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 好友不存在\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postVerifyFriendLog(body: PostVerifyFriend) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t审核加好友请求接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t审核加好友请求接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 审核加好友请求成功\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 好友申请不存在\t-\n")
            }
            "repeatWrong" -> {
                stringBuilder.append("-\tmsg: 好友审核已被同意\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getPostFriendListLog(body: GetPostFriendList) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取好友请求列表接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取好友请求列表接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取好友请求列表成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getSearchUserLog(body: GetSearchUser) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t根据用户昵称搜索用户接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t根据用户昵称搜索用户接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 根据用户昵称搜索用户成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getSearchVehicleLog(body: GetSearchVehicle) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t根据用户车牌搜索用户接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t根据用户车牌搜索用户接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 根据用户车牌搜索用户成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postLinkUserLog(body: PostLinkUser) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t申请联结用户接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t申请联结用户接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 申请联结用户成功\t-\n")
            }
            "userWrong" -> {
                stringBuilder.append("-\tmsg: 用户连接已达到3个\t-\n")
            }
            "blackWrong" -> {
                stringBuilder.append("-\tmsg: 用户在黑名单内\t-\n")
            }
            "repeatWrong" -> {
                stringBuilder.append("-\tmsg: 已在连接列表内\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getPostLinkUserLog(body: GetPostLinkUser) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取申请联结用户列表接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取申请联结用户列表接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取申请联结用户列表成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun postJudgeLinkUserLog(body: PostJudgeLinkUser) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t审核联结用户申请接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t审核联结用户申请接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 审核联结用户申请成功\t-\n")
            }
            "userWrong" -> {
                stringBuilder.append("-\tmsg: 用户或对方已拥有3个联结用户\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 请求不存在或已被审核\t-\n")
            }
            "repeatWrong" -> {
                stringBuilder.append("-\tmsg: 对方已经联结\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getLinkUserLog(body: GetLinkUser) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取用户联结列表接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取用户联结列表接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取用户联结列表成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getTalkLog(body: GetTalk) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取用户一对一聊天记录接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取用户一对一聊天记录接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取用户一对一聊天记录成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun deleteRemoveLinkLog(body: DeleteRemoveLink) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t解除联结关系接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t解除联结关系接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 解除联结关系成功\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 联结关系不存在\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getJudgeLinkLog(body: GetJudgeLink) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t判断是否为联结关系接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t判断是否为联结关系接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 判断是否为联结关系成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }


    fun getTalkListLog(body: GetTalkList) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取用户聊天列表接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取用户聊天列表接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取用户聊天列表成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun deleteTalkLog(body: DeleteTalk) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t删除某用户聊天列表显示接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t删除某用户聊天列表显示接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 删除某用户聊天列表显示成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun getAllBoxLog(body: GetAllBox) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t获取消息盒子接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t获取消息盒子接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 获取消息盒子显示成功\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun deleteBoxMessageLog(body: DeleteBoxMessage) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t批量删除消息盒子的消息接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t批量删除消息盒子的消息接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 批量删除消息盒子的消息成功\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 消息不存在\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }

    fun deleteVehicleLog(body: DeleteVehicle) {
        val stringBuilder = StringBuilder()
        if (body.code == 200) {
            stringBuilder.append("-\t解除车牌绑定接口访问成功\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        else {
            stringBuilder.append("-\t解除车牌绑定接口访问失败\t-\n" +
                    "-\tcode: ${body.code}\t-\n")
        }
        when (body.msg) {
            "success" -> {
                stringBuilder.append("-\tmsg: 解除车牌绑定成功\t-\n")
            }
            "existWrong" -> {
                stringBuilder.append("-\tmsg: 用户并未绑定该车牌\t-\n")
            }
            else -> {
                stringBuilder.append("-\tmsg: 访问接口发生未知错误\t-\n")
            }
        }
        Log.d(StatusRepository.VehicleLog, stringBuilder.toString())
    }
}
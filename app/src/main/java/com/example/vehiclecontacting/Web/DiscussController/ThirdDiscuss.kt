package com.example.vehiclecontacting.Web.DiscussController

data class ThirdDiscuss(
    val counts: Int,
    val ownerComment: CommentOwner,
    val pages: Int,
    val thirdCommentList: List<ThirdComment>
)
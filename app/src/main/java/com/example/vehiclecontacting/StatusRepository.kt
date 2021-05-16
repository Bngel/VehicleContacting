package com.example.vehiclecontacting

object StatusRepository {

    /***
     * Status for TabView
     */
    // 首页, 社区, 个人中心
    enum class HomeTab {
        HOME, COMMUNITY, USER
    }
    // 当前HomeTab选择状态
    var homeTabStatus: HomeTab = HomeTab.HOME

    const val PAGE_HOME = 0
    const val PAGE_COMMUNITY = 1
    const val PAGE_USER = 2

    // 推荐, 关注
    enum class CommunityTab {
        RECOMMEND, FOLLOW
    }
    // 当前CommunityTab选择状态
    var communityTabStatus: CommunityTab = CommunityTab.RECOMMEND
}
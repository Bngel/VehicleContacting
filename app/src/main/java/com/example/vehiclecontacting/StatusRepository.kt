package com.example.vehiclecontacting

object StatusRepository {

    const val VehicleLog = "vehicleLog"
    const val loginStatus = "loginStatus"

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

    const val PAGE_RECOMMEND = 0
    const val PAGE_FOLLOW = 1

    /***
     * CONST for msg types
     */
    const val EXIST_WRONG = 0
    const val OLD_PASSWORD_WRONG = 1
    const val CODE_EXIST_WRONG = 2
    const val CODE_WRONG = 3
    const val SUCCESS = 4
    const val REPEAT_WRONG = 5
    const val FROZEN_WRONG = 6
    const val FILE_WRONG = 7
    const val TYPE_WRONG = 8
    const val USER_WRONG = 9
    const val UNKNOWN_WRONG = 0xFF

}
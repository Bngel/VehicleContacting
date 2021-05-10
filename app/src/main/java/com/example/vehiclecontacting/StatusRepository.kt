package com.example.vehiclecontacting

object StatusRepository {

    /***
     * Status for TabLayout
     */
    // 首页, 社区, 个人中心
    enum class Tab {
        HOME, COMMUNITY, USER
    }
    // 当前Tab选择状态
    var tabStatus: Tab = Tab.HOME

    const val PAGE_HOME = 0
    const val PAGE_COMMUNITY = 1
    const val PAGE_USER = 2
}
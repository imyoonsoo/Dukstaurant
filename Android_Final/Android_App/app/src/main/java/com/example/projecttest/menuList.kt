package com.example.projecttest

data class menuList (
    val menu: String,
    val menuID: Int,
    val menuPic: Int,
    var desc: String? = null,
    var isExpandable: Boolean = false
)
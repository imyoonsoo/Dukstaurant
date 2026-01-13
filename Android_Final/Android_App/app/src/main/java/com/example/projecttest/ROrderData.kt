package com.example.projecttest

data class ROrderData(
    val orderedDate_deatail:String,
    val orderedNumber : String,
    val orderedMenu : String,
    val CookDone: Boolean = false,
    var isExpandable: Boolean = false
)
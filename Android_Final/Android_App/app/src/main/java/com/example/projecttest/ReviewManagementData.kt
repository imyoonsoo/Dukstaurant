package com.example.projecttest

data class ReviewManagementData(
    val storeImage : Int,
    val orderedDate : String,
    val orderedStore : String,
    val orderedNumber : String,
    val orderedMenu : String,
    val reviewWirteButtonOn : Boolean = false,
    var isExpandable: Boolean = false
)

package com.example.projecttest

data class ReceivedGiftData(
    val giftID : Int,
    val receivedstoreImage : Int,
    val giftSender : String,
    val receivedMenu : String,
    val giftUsingStatus : Boolean = false
)

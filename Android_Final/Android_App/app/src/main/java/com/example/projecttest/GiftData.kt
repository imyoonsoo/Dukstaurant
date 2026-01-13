package com.example.projecttest

data class GiftData(
    val giftstoreImage : Int,
    val giftMenu : String,
    val giftPrice : String,
    val giftPayStatus : Boolean = false //선물하기 내 결제하기 버튼 변수
)
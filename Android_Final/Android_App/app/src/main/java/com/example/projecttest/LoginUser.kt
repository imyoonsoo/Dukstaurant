package com.example.projecttest

import android.provider.ContactsContract.CommonDataKinds.Email

//서버에서 가져온 유저의 정보 -> 로그인 또는 회원가입 할 때 저장
class LoginUser {
    companion object {
        var id : Int = 0
        var email : String = ""
        var password : String = ""
        var nickname : String = ""
        var restaurant_on : Boolean = false
        var restaurant_num : Int = 0  //손님으로 로그인하면 그대로 0 유지  //가게면 email에 따라 id 넣어주기
        fun set_restaurant_num() {    //restaurant_num 변수 쓰기 전에 set_restaurant_num()함수 호출 해주기
            if(email == "pasta@pasta.com") {   //DB에 따라 값 넣어주기 
                restaurant_num = 1
            }
            else if(email == "mala@mala.com"){
                restaurant_num = 2
            }
            else if(email == "gunsan@gunsan.com") {
                restaurant_num = 3
            }
            else if(email == "masung@masung.com") {
                restaurant_num = 4
            }
            else if(email == "omea@omea.com") {
                restaurant_num = 5
            }
            else if(email == "omeb@omeb.com") {
                restaurant_num = 6
            }
        }
        var location : Int = 0
    }
}

object Cart {     //어디둘지 몰라서 일단 여기 둠
    var menu = mutableListOf<String>()
    var rest = mutableListOf<Int>()
    var price = mutableListOf<Int>()
    var num = 0
    var total_price = 0
    fun cartReset() {
        num = 0
        total_price = 0
    }
}




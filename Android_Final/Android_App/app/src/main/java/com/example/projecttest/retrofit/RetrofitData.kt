package com.example.projecttest.retrofit

class User {
    var id: Int = 0
    val email: String = ""
    val password: String = ""
    val nickname: String = ""
    val restaurant_on: Boolean = false
}

class Restaurant {
    val restaurant_name: String = ""
}

class Menu {
    val id : Int = 0
    val restaurant: Int = 0
    val menu_name : String = ""
    val price: Int = 0
}

class Order {
    val id :Int = 0
    val menu: String = ""
    val restaurant : Int = 0
    val customer : String = ""
    val done : Boolean = false
    val time : String = ""
}


class Notification {
    val title: String = ""
    val content: String = ""
    val date: String = ""
    val restaurant: Int = 0
}

class Review {
    val customer: String = ""
    val content: String = ""
    val restaurant: Int = 0
    val order: Int = 0
}


class Myhealth_pre {
    val user: Int = 0
    val kcal: Float = 0.0F
}

class Myhealth_post {
    val id: Int = 0
    val user: Int = 0
    val date: String = ""
    val totalKcal: Float = 0.0F
    val total_car: Float = 0.0F
    val total_pro: Float = 0.0F
    val total_fat: Float = 0.0F
}

class Nutrition {
    val menu : String = ""
    val kcal: Float = 0.0F
    val car: Float = 0.0F
    val pro: Float = 0.0F
    val fat: Float = 0.0F
}

class Recommend {
    val menu_name : String = ""
}

class Reccomend(
    val most_ordered_menu_list: List<String>
)

class Reccomend2(
    val recommended_menus: List<String>
)

class Step{
    val id: Int = 0
    val user: Int = 0
    val date: String = ""
    val step: Int = 0
}
class Gift{
    val id: Int = 0
    val sender: String = ""
    val recipient: String = ""
    val giftMenu : String = ""
    val used : Boolean = false
}
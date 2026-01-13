package com.example.projecttest.retrofit

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @get : GET("/common/user/")
    val getUser: Call<List<User>>

    @FormUrlEncoded
    @POST("/common/user_drf/")
    fun postUser(
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("nickname") nickname : String,
        @Field("restaurant_on") restaurant_on : Boolean
    ) : Call<JSONObject>  //;

    @FormUrlEncoded
    @POST("/common/user/")
    fun getUserInf(
        @Field("email") email : String,   //email 입력 받아서 해당 유저만 정보 가져오기
    ) : Call<List<User>>
}

interface RestaurantService {
    @get : GET("/order/restaurant_drf/")
    val getRestaurant: Call<List<Restaurant>>
}

interface MenuService {
    @get : GET("/order/menu_drf/")
    val getAllMenu: Call<List<Menu>>

    @FormUrlEncoded
    @POST("/order/menu/")
    fun getMenu(
        @Field("restaurant") restaurant : Int  //가게 입력 받아서 해당 가게 메뉴만 출력
    ): Call<List<Menu>>

    @FormUrlEncoded
    @POST("/order/menu_info/")
    fun getMenuInfo(
        @Field("menu_name") menu_name : String  //메뉴 입력 받아서 해당 메뉴 정보 출력
    ): Call<List<Menu>>
}

interface OrderService {
    @get : GET("/order/order_drf/")
    val getOrder: Call<List<Order>>

    @FormUrlEncoded
    @POST("/order/order_res/")
    fun getROrder(
        @Field("restaurant") restaurant : Int  //가게 입력 받아서 해당 가게 주문내역만 출력
    ): Call<List<Order>>

    @FormUrlEncoded
    @POST("/order/order_user/")
    fun getUOrder(
        @Field("email") email : String  //이메일 입력 받아서 해당 유저 주문내역만 출력
    ): Call<List<Order>>

    @FormUrlEncoded
    @POST("/order/order_drf/")
    fun postOrder(
        @Field("menu") menu : String,
        @Field("restaurant") restaurant: Int,
        @Field("customer") customer : String,
    )  : Call<JSONObject>

    //알림 -> 조리 완료 확인
    @FormUrlEncoded
    @POST("/order/alarm/")
    fun getDone(
        @Field("id") id : Int,
    ): Call<List<Order>>

    //가게에서 done을 true로
    @FormUrlEncoded
    @PUT("order/order_drf/{id}/")
    fun setDone(
        @Path("id") id : Int,
        @Field("menu") menu : String,
        @Field("restaurant") restaurant : Int,
        @Field("customer") customer : String,
        @Field("done") done : Boolean,
    ) : Call<JSONObject>
}

interface NotificationService {
    @get : GET("/nr/notification_drf/")
    val getNotification: Call<List<Notification>>

    @FormUrlEncoded
    @POST("/nr/notification_drf/")
    fun postNotification(
        @Field("title") title : String,
        @Field("content") content : String,
        @Field("date") date : String,
        @Field("restaurant") restaurant : Int
    ): Call<JSONObject>
}

interface ReviewService {
    @FormUrlEncoded
    @POST("/nr/review/")
    fun getReview(
        @Field("restaurant") restaurant : Int  //가게 입력 받아서 해당 가게 리뷰만 출력
    ): Call<List<Review>>

    @FormUrlEncoded
    @POST("/nr/review_drf/")
    fun postReview(
        @Field("customer") customer : String,
        @Field("content") content : String,
        @Field("restaurant") restaurant : Int,
        @Field("order") order : Int
    ): Call<JSONObject>
}


interface MyHealthService {
    @FormUrlEncoded
    @POST("/health/Myhealth_pre/")
    fun getMyHealth_pre(
        @Field("user") user : Int,
    ): Call<List<Myhealth_pre>>
    @FormUrlEncoded
    @POST("/health/Myhealth_post/")
    fun getMyHealth_post(
        @Field("user") user : Int,
        @Field("date") date : String
    ): Call<List<Myhealth_post>>

    @FormUrlEncoded
    @POST("/health/Myhealth_pre_drf/")
    fun postMyHealth_pre(
        @Field("user") user : Int,
        @Field("kcal") kcal : Float
    ): Call<JSONObject>
    @FormUrlEncoded
    @POST("/health/Myhealth_post_drf/")
    fun postMyHealth_post(
        @Field("user") user : Int,
        @Field("date") date : String,
        @Field("totalKcal") totalKcal : Float,
        @Field("total_car") total_car : Float,
        @Field("total_pro") total_pro : Float,
        @Field("total_fat") total_fat : Float
    ): Call<JSONObject>

    @FormUrlEncoded
    @PUT("/health/Myhealth_post_drf/{id}/")
    fun putMyHealth_post(
        @Path("id") id: Int,
        @Field("user") user : Int,
        @Field("date") date : String,
        @Field("totalKcal") totalKcal: Float,
        @Field("total_car") totalCar: Float,
        @Field("total_pro") totalPro: Float,
        @Field("total_fat") totalFat: Float
    ): Call<JSONObject>
}


interface NutritionService {
    @FormUrlEncoded
    @POST("/health/nutrition/")
    fun getNutrition(
        @Field("menu") menu : String  //메뉴 입력 받아서 해당 메뉴 영양성분만 출력
    ): Call<List<Nutrition>>
}

interface RecommendService {
    @get : GET("/recommend/todaypreference/")
    val getTodaypreference: Call<Reccomend>

    @FormUrlEncoded
    @POST("/recommend/mypreference/")
    fun getMypreference(
        @Field("email") email: String
    ): Call<Reccomend>

    @FormUrlEncoded
    @POST("/recommend/recommendedmenuview/")
    fun getRecommendedmenu(
        @Field("user_id") user_id: Int
    ): Call<Reccomend2>
}

interface StepService {
    @FormUrlEncoded
    @POST("/footstep/footstep_post/")
    fun getStep(
        @Field("user") user : Int,
        @Field("date") date : String
    ): Call<List<Step>>
    @FormUrlEncoded
    @POST("/footstep/footstep_drf/")
    fun postStep(
        @Field("user") user : Int,
        @Field("date") date : String,
        @Field("step") step : Int
    ): Call<JSONObject>
    @FormUrlEncoded
    @PUT("/footstep/footstep_drf/{id}/")
    fun putStep(
        @Path("id") id: Int,
        @Field("user") user : Int,
        @Field("date") date : String,
        @Field("step") step : Int
    ): Call<JSONObject>
}

interface GiftService {
    @FormUrlEncoded
    @POST("/gift/send/")
    fun getSend(
        @Field("sender") sender : String
    ): Call<List<Gift>>

    @FormUrlEncoded
    @POST("/gift/recipient/")
    fun getRecipient(
        @Field("recipient") recipient : String
    ): Call<List<Gift>>

    @FormUrlEncoded
    @POST("/gift/gift_drf/")
    fun postGift(
        @Field("sender") sender : String,
        @Field("recipient") recipient : String,
        @Field("giftMenu") giftMenu : String
    ): Call<JSONObject>

    @FormUrlEncoded
    @PUT("/gift/gift_drf/{id}/")
    fun putGift(
        @Path("id") id: Int,
        @Field("sender") sender : String,
        @Field("recipient") recipient : String,
        @Field("giftMenu") giftMenu : String,
        @Field("used") used : Boolean
    ): Call<JSONObject>
}

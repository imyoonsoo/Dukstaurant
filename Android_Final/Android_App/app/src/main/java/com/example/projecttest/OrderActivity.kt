package com.example.projecttest

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.SlidingDrawer
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.retrofit.*
import com.example.wheelview.WheelView
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.ZoneId

class OrderActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<menuList>()
    private lateinit var adapter: menuAdapter

    //cart
    private lateinit var cartlistView: RecyclerView
    private var cartmenu_list = ArrayList<cartList>()
    private lateinit var cartListViewAdapter : cartAdapter  ////////++

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_order)

        val wheelView = findViewById<WheelView>(R.id.wheel_view)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //cart
        cartlistView = findViewById(R.id.cartlistView)
        cartlistView.setHasFixedSize(true)
        cartlistView.layoutManager = LinearLayoutManager(this)
        cartListViewAdapter = cartAdapter(cartmenu_list)
        cartlistView.adapter = cartListViewAdapter   /////////++

        adapter = menuAdapter(mList)
        recyclerView.adapter = adapter

        //var cartDrawer : SlidingDrawer = findViewById(R.id.cartLayout)  /////////++
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button15 = findViewById<Button>(R.id.button15)
        val button16 = findViewById<Button>(R.id.button16)
        val button17 = findViewById<Button>(R.id.button17)

        button6.setOnClickListener {
            val intent = Intent(this@OrderActivity, MyHealthActivity::class.java)  //My health 클릭 했을 때
            startActivity(intent)
        }

        button7.setOnClickListener {
            val intent = Intent(this@OrderActivity, NotificationActivity::class.java)  //공지 보기 버튼 클릭 했을 때//리뷰 보기 버튼 클릭 했을 때
            startActivity(intent)
        }

        button8.setOnClickListener {
            val intent = Intent(this@OrderActivity, ReviewActivity::class.java)  //리뷰 보기 버튼 클릭 했을 때
            startActivity(intent)
        }

        button15.setOnClickListener {
            val intent = Intent(this@OrderActivity, ReviewSelect::class.java)  //리뷰 쓰기 버튼 클릭 했을 때
            startActivity(intent)
        }

        button16.setOnClickListener {
            val intent = Intent(this@OrderActivity, OrderDetailActivity::class.java)  //유저 주문내역 버튼 클릭 했을 때
            startActivity(intent)
        }

        button17.setOnClickListener {
            val intent = Intent(this@OrderActivity, MyInfoActivity::class.java)  //내정보 버튼 클릭 했을 때
            startActivity(intent)
        }

        button18.setOnClickListener {
            val intent = Intent(this@OrderActivity, GiftActivity::class.java)
            startActivity(intent)
        }

        // 로그아웃 버튼 클릭 시
        val logoutButton: Button = findViewById(R.id.buttonlogout)
        logoutButton.setOnClickListener {

            // 로그아웃 전용 xml을 가져와서 AlertDialog 생성
            val alertDialog = AlertDialog.Builder(this).create()
            val dialogView = layoutInflater.inflate(R.layout.logout_design, null)

            // "아니오" 버튼 클릭 시
            val logoutNoButton = dialogView.findViewById<Button>(R.id.noButton)
            logoutNoButton.setOnClickListener {
                // 아무것도 하지 않고 다이얼로그를 닫음
                alertDialog.dismiss()
            }

            // "예" 버튼 클릭 시
            val logoutYesButton = dialogView.findViewById<Button>(R.id.yesButton)
            logoutYesButton.setOnClickListener {
                // 로그인 화면으로 이동하는 인텐트 생성
                val intent = Intent(this@OrderActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  //로그아웃 -> 스택에 들어있는 모든 액티비티 종료
                startActivity(intent)
                finish() // 현재 화면 종료
                alertDialog.dismiss() // 다이얼로그 닫기
            }
            alertDialog.setView(dialogView)
            alertDialog.show()
        }

        /*
        val RestaurantService = RetrofitClient.getClient()?.create(RestaurantService::class.java)


        fun restaurant_show(){
            lateinit var rastaurant_list : List<Restaurant>  //Restaurant 받을 곳

            val call = RestaurantService!!.getRestaurant
            call.enqueue(object : Callback<List<Restaurant>> {
                override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    rastaurant_list = response.body()!!
                    for (rastaurant in rastaurant_list) {
                        var content = ""
                        content += """
                            ${rastaurant.restaurant_name}
                        """.trimIndent()

                        //textView2.append(content)
                    }
                }

                override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }
        restaurant_show()

         */
        val MenuService = RetrofitClient.getClient()?.create(MenuService::class.java)

        fun cart(menu_name : String) {      ///////////++
            val call = MenuService!!.getMenuInfo(menu_name)  //메뉴 이름 넣어서 조회
            call.enqueue(object : Callback<List<Menu>>{
                override fun onResponse(call: Call<List<Menu>>, response: Response<List<Menu>>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    lateinit var menu_list : List<Menu>
                    menu_list = response.body()!!
                    for (menu in menu_list) {
                        //선택한 메뉴 가격, 메뉴 아이디와 가게 ID 불러와야함
                        //selectedMenuId, selectedMenuPrice, restID
                        var search = cartmenu_list.find { it.menu == menu_name }
                        if (search == null) {
                            //cartmenu_list.add(cartList(menu_name, selectedMenuId, selectedMenuPrice, 2, restID))
                            cartmenu_list.add(cartList(menu_name, menu.id, menu.price, 1, menu.restaurant))  //DB에서 얻은 데이터로 저장
                        } else {
                            search.menuCount++
                        }
                    }
                    //카트 변동사항 업데이트
                    cartListViewAdapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<List<Menu>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
            //DB 없이 불러오는 예시
            //cartmenu_list.add(cartList(menu_name, 1, 1, 1, 1))
        }

        /*
        val call = MenuService!!.getMenuInfo(menu_name)  //메뉴 이름 넣어서 조회
        call.enqueue(object : Callback<List<Menu>>{
            override fun onResponse(call: Call<List<Menu>>, response: Response<List<Menu>>) {
                if (!response.isSuccessful) {
                    return
                }
                menu_list = response.body()!!
                for (menu in menu_list) {
                    var content = ""
                    content += """
                            메뉴 : ${menu.menu_name}
                            가격 : ${menu.price}


                        """.trimIndent()
                    textViewCart.append(content)
                    Cart.menu.add(menu.menu_name)
                    Cart.rest.add(menu.restaurant)
                    Cart.price.add(menu.price)
                    Cart.total_price += menu.price
                    Cart.num++
                    Toast.makeText(this@OrderActivity, "담기 완료", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Menu>>, t: Throwable) {
                Log.d("Debug", "onFailure 실행$t")
            }
        })
        */

        fun menu_show(restaurant_num : Int) {
            lateinit var menu_list : List<Menu>
            val call = MenuService!!.getMenu(restaurant_num)  //식당 ID 넣어서 조회
            call.enqueue(object : Callback<List<Menu>>{
                override fun onResponse(call: Call<List<Menu>>, response: Response<List<Menu>>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    menu_list = response.body()!!

                    mList.clear()   //다른 가게 메뉴 추가 전에 다른 메뉴 지우기
                    for (menu in menu_list) {
                        mList.add(menuList(menu.menu_name, menu.id, res_idToImage(menu.restaurant), menu.price.toString()))
                    }
                    adapter.notifyDataSetChanged()
                    ////////////////////

                    adapter.setItemClickListener(object : menuAdapter.OnItemClickListener{
                        override fun onClick(v: View, position: Int) {
                            //Toast.makeText(v.context, "${mList[position].menu} 클릭", Toast.LENGTH_SHORT).show()

                            //담기버튼
                            wheelView.centerClickListener = {
                                cart(mList[position].menu)
                                Toast.makeText(v.context, "담음", Toast.LENGTH_SHORT).show()
                                //Toast.makeText(v.context, "${cartmenu_list.size}", Toast.LENGTH_SHORT).show()

                                //카트 변동사항 업데이트
                                cartListViewAdapter.notifyDataSetChanged()
                            }
                        }
                    })
                }

                override fun onFailure(call: Call<List<Menu>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }
        menu_show(1)  //맨 처음 보여주는 가게 -> 파스타

        wheelView.titles = listOf("파스타", "마라탕", "군산카츠", "마성\n떡볶이", "오늘의 메뉴A", "오늘의 메뉴B")

        wheelView.selectListener = { // selected position
            //Toast.makeText(this,"selected on ${it}",Toast.LENGTH_SHORT).show()
            menu_show(it+1)
        }
        /*
        fun cart(menu_name : String) {       //장바구니 3개까지 담을수있음           ///////////////겹치는 코드 -> 예전버전
            if(Cart.num>=3) {
                Toast.makeText(this@OrderActivity, "음식 3개까지 주문 가능", Toast.LENGTH_SHORT).show()
                return
            }
            lateinit var menu_list : List<Menu>
            val call = MenuService!!.getMenuInfo(menu_name)  //메뉴 이름 넣어서 조회
            call.enqueue(object : Callback<List<Menu>>{
                override fun onResponse(call: Call<List<Menu>>, response: Response<List<Menu>>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    menu_list = response.body()!!
                    for (menu in menu_list) {
                        var content = ""
                        content += """
                            메뉴 : ${menu.menu_name}
                            가격 : ${menu.price}


                        """.trimIndent()
                        //textViewCart.append(content)
                        Cart.menu.add(menu.menu_name)
                        Cart.rest.add(menu.restaurant)
                        Cart.price.add(menu.price)
                        Cart.total_price += menu.price
                        Cart.num++
                        Toast.makeText(this@OrderActivity, "담기 완료", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Menu>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }

        fun cartPlus(cart_num : Int) {
            cart(Cart.menu[cart_num])
        }
        fun cartUpdate() {
            //textViewCart.text = ""    //-----------------------나중에 수정
            var content = ""
            for(i in 0..(Cart.num-1)) {
                content += """
                    메뉴 : ${Cart.menu[i]}
                    가격 : ${Cart.price[i]}


                """.trimIndent()
            }
            //textViewCart.append(content)   //-----------------------나중에 수정
        }
        fun cartDelete(cart_num : Int) {
            Cart.num--
            Cart.total_price -= Cart.price[cart_num]
            Cart.menu.removeAt(cart_num)
            Cart.rest.removeAt(cart_num)
            Cart.price.removeAt(cart_num)
            cartUpdate()
        }

         */

        /////////////////////////////////////////////////////////////////////////////////////////////////

        val NutritionService = RetrofitClient.getClient()?.create(NutritionService::class.java)

        fun nutrition_show(menu_name : String) {
            lateinit var nutrition_list : List<Nutrition>

            val call = NutritionService!!.getNutrition(menu_name)  //메뉴 이름 넣어서 조회
            call.enqueue(object : Callback<List<Nutrition>>{
                override fun onResponse(call: Call<List<Nutrition>>, response: Response<List<Nutrition>>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    nutrition_list = response.body()!!
                    for (nutrition in nutrition_list) {
                        var content = ""
                        content += """
                            kcal : ${nutrition.kcal}
                            car : ${nutrition.car}
                            pro : ${nutrition.pro}
                            fat : ${nutrition.fat}
                            
                        """.trimIndent()
                        //textView7.append(content)       //-----------------------나중에 수정
                    }
                }

                override fun onFailure(call: Call<List<Nutrition>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }

        /*

        button2.setOnClickListener {
            var restaurant_input = 1   //파스타 눌렀다고 가정 -> id = 1 이 파스타
            menu_show(restaurant_input)
        }
        button3.setOnClickListener {
            var restaurant_input = 7   //마라탕 눌렀다고 가정 -> id = 7 이 마라탕
            menu_show(restaurant_input)
        }

        //임의의 데이터 -> 나중에 버튼 클릭했을 때 메뉴이름, 식당 id, 손님 email 받아오게 구현
        var menu_name = "고기리들기름파스타"
        //var restaurant_id = 1
        //var customer = "pybo@pybo.com"

        button13.setOnClickListener{   //메뉴 칼로리 보기 버튼
            nutrition_show(menu_name)
        }

        button20.setOnClickListener{   //메뉴 담기 버튼
            cart(menu_name)
        }
        button23.setOnClickListener {     //추가 버튼 눌렀을 때
            cartPlus(0)
        }
        button24.setOnClickListener {     //삭제 버튼 눌렀을 때
            cartDelete(0)
        }
         */

        /////////////////카트에 담긴 메뉴 주문하기/////////////////////

        var mKcal = 0.0f; var mCar = 0.0f; var mPro = 0.0f; var mFat = 0.0f;
        val OrderService = RetrofitClient.getClient()?.create(OrderService::class.java)
        fun menuOrder(orderMenu : String, orderRes : Int) {    //메뉴 이름 & 식당 id & 사용자 이메일 넘겨주면 서버에 주문 db에 저장하는 함수   *장바구니에 담긴대로 주문

            val call = OrderService!!.postOrder(orderMenu, orderRes, LoginUser.email)
            call.enqueue(object : Callback<JSONObject> {
                override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    val data = response.body()

                    lateinit var nutrition_list : List<Nutrition>

                    val call2 = NutritionService!!.getNutrition(orderMenu)  //메뉴 이름 넣어서 조회
                    call2.enqueue(object : Callback<List<Nutrition>>{
                        override fun onResponse(call: Call<List<Nutrition>>, response: Response<List<Nutrition>>) {
                            if (!response.isSuccessful) {
                                return
                            }
                            nutrition_list = response.body()!!
                            for (nutrition in nutrition_list) {
                                mKcal += nutrition.kcal; mCar += nutrition.car;
                                mPro += nutrition.pro; mFat += nutrition.fat;
                            }
                        }

                        override fun onFailure(call: Call<List<Nutrition>>, t: Throwable) {
                            Log.d("Debug", "onFailure 실행$t")
                        }
                    })
                }

                override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }

        fun nutrition_put() {         //주문한 메뉴 영양정보 myhealth 오늘먹은양에 PUT
            val MyHealthService = RetrofitClient.getClient()?.create(MyHealthService::class.java)
            lateinit var myhealth_post_list: List<Myhealth_post>
            val currentDate = LocalDate.now(ZoneId.of("Asia/Seoul")).toString()

            val call2 = MyHealthService!!.getMyHealth_post(LoginUser.id, currentDate)
            call2.enqueue(object : Callback<List<Myhealth_post>> {
                override fun onResponse(call: Call<List<Myhealth_post>>, response: Response<List<Myhealth_post>>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    var kcal = 0.0f; var car = 0.0f; var pro = 0.0f; var fat = 0.0f; var id = 0
                    myhealth_post_list = response.body()!!
                    if(myhealth_post_list.isEmpty()) {
                        val call3 = MyHealthService!!.postMyHealth_post(LoginUser.id, currentDate, mKcal, mCar, mPro, mFat)
                        call3.enqueue(object : Callback<JSONObject> {
                            override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                                if (!response.isSuccessful) {
                                    return
                                }
                                //Toast.makeText(this@OrderActivity, "MyHealth_post 등록 완료", Toast.LENGTH_SHORT).show()
                                val data = response.body()
                                mKcal = 0.0f; mCar = 0.0f; mPro = 0.0f; mFat = 0.0f;
                            }
                            override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                                Log.d("Debug", "onFailure 실행$t")
                            }
                        })
                        return
                    }
                    for (myhealth_post in myhealth_post_list) {
                        kcal = myhealth_post.totalKcal + mKcal
                        car = myhealth_post.total_car + mCar
                        pro = myhealth_post.total_pro + mPro
                        fat = myhealth_post.total_fat + mFat
                        id = myhealth_post.id
                    }

                    val call4 = MyHealthService!!.putMyHealth_post(id, LoginUser.id, currentDate, kcal, car, pro, fat)
                    call4.enqueue(object : Callback<JSONObject> {
                        override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                            if (!response.isSuccessful) {
                                return
                            }
                            //Toast.makeText(this@OrderActivity, "MyHealth_post 추가 완료", Toast.LENGTH_SHORT).show()
                            val data = response.body()
                            mKcal = 0.0f; mCar = 0.0f; mPro = 0.0f; mFat = 0.0f;
                        }
                        override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                            Log.d("Debug", "onFailure 실행$t")
                        }
                    })
                }
                override fun onFailure(call: Call<List<Myhealth_post>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }

        button4.setOnClickListener {     //결제창 버튼 눌렀을 때
            if(LoginUser.location == 0){
                Toast.makeText(this@OrderActivity, "위치 정보를 확인해주세요", Toast.LENGTH_SHORT).show()
            }
            else if(LoginUser.location == 1){
                Toast.makeText(this@OrderActivity, "주문 불가능한 위치입니다", Toast.LENGTH_SHORT).show()
            }
            else if(LoginUser.location == 2) {
                if (cartmenu_list.isEmpty())
                    Toast.makeText(this@OrderActivity, "메뉴를 담아주세요", Toast.LENGTH_SHORT).show()
                else {
                    //intent.putExtra("cartmenu_list", ArrayList(cartmenu_list))
                    //intent.putExtra("cartmenu_list", cartmenu_list)
                    //startActivity(intent)
                    //Toast.makeText(this@OrderActivity, "결제창 클릭", Toast.LENGTH_SHORT).show()
                    //Toast.makeText(this@OrderActivity, cartmenu_list.isEmpty().toString(), Toast.LENGTH_SHORT).show()
                    //Toast.makeText(this@OrderActivity, cartmenu_list.last().menuCount.toString(), Toast.LENGTH_SHORT).show()

                    while (!cartmenu_list.isEmpty()) {  //장바구니 모두 주문
                        for (i: Int in 1..cartmenu_list.last().menuCount) {  //카트에 담긴 개수만큼
                            menuOrder(cartmenu_list.last().menu, cartmenu_list.last().rest)
                        }
                        cartmenu_list.removeAt(cartmenu_list.size - 1)
                    }
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(1000) // 1초 대기
                        nutrition_put()
                    }
                    cartListViewAdapter.notifyDataSetChanged()  //결제하기 누르고 나서 장바구니 업데이트
                    val intent = Intent(this@OrderActivity, PaymentActivity::class.java)
                    startActivity(intent)
                }
            }

        }
        locationCheckButton.setOnClickListener{
            val intent = Intent(this@OrderActivity, LocationLoadingActivity::class.java)
            startActivity(intent)
        }



        /*
        button5.setOnClickListener {     //기타 기능 버튼 눌렀을 때
            val intent = Intent(this@OrderActivity, EtcActivity::class.java)
            startActivity(intent)
        }

         */
    }
}
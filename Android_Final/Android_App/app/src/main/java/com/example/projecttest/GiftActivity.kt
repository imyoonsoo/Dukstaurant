package com.example.projecttest

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.retrofit.*
import kotlinx.android.synthetic.main.activity_gift.*
import kotlinx.android.synthetic.main.rv_gift.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GiftActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gift)

        //선물보내기 리사이클러뷰 내 데이터 삽입 후 어댑터 연결
        val gift_DataList = mutableListOf<GiftData>()
//        gift_DataList.add(GiftData(R.drawable.malatang_img_radius, "선물 메뉴 : 바삭 달콤 꿔바로우 小", "메뉴 가격 : 5,000원"))
//        gift_DataList.add(GiftData("Yoonsoo@duksung.ac.kr", "선물 메뉴 : 마성떡볶이 싱글 세트", "메뉴 가격 : 6,500원"))
//        gift_DataList.add(GiftData("SeoYoonsoo@duksung.ac.kr", "선물 메뉴 : 스팸마요덮밥 1개", "메뉴 가격 : 6,000원"))
        val Gift_recyclerView : RecyclerView = findViewById(R.id.giftrecyclerView)
        Gift_recyclerView.layoutManager = LinearLayoutManager(this)
        val giftAdapter = GiftAdapter(gift_DataList)
        Gift_recyclerView.adapter = giftAdapter

        // 받은 선물 리사이클러뷰 내 데이터 삽입 후 어댑터 연결
        val receivedGift_DataList = mutableListOf<ReceivedGiftData>()
//        receivedGift_DataList.add(ReceivedGiftData(1, R.drawable.malatang_img_radius,  "보낸 사람 : 오다은", "받은 메뉴 : 마라탕 1인 세트"))
//        receivedGift_DataList.add(ReceivedGiftData(R.drawable.todaymenu_img_radius,  "보낸 사람 : 전은채", "받은 메뉴 : 오늘의 메뉴A"))
//        receivedGift_DataList.add(ReceivedGiftData(R.drawable.don_img_radius,  "보낸 사람 : 김가윤", "받은 메뉴 : 고구마치즈돈까스 1개"))
        val receivedGift_recyclerView: RecyclerView = findViewById(R.id.receivedrecyclerView)
        receivedGift_recyclerView.layoutManager = LinearLayoutManager(this)
        val receivedGiftAdapter = ReceivedGiftAdapter(receivedGift_DataList)
        receivedGift_recyclerView.adapter = receivedGiftAdapter

        // 보낸 선물 리사이클러뷰 내 데이터 삽입 후 어댑터 연결
        val sendGift_DataList = mutableListOf<SendGiftData>()
//        sendGift_DataList.add(SendGiftData(R.drawable.tteokbokki_img_radius, "받는 사람 : 서윤수", "보낸 메뉴 : 참치김밥 1개"))
//        sendGift_DataList.add(SendGiftData(R.drawable.don_img_radius, "받는 사람 : 서윤수", "보낸 메뉴 : 군산카츠 정식 2개"))
//        sendGift_DataList.add(SendGiftData(R.drawable.todaymenu_img_radius, "받는 사람 : 경다은", "보낸 메뉴 : 오늘의 메뉴B"))
        val sendGift_recyclerView: RecyclerView = sendrecyclerView
        sendGift_recyclerView.layoutManager = LinearLayoutManager(this@GiftActivity)
        val sendGiftAdapter = SendGiftAdapter(sendGift_DataList)
        sendGift_recyclerView.adapter = sendGiftAdapter


        val GiftService = RetrofitClient.getClient()?.create(GiftService::class.java)
        val MenuService = RetrofitClient.getClient()?.create(MenuService::class.java)
        val OrderService = RetrofitClient.getClient()?.create(OrderService::class.java)
        val UserService = RetrofitClient.getClient()?.create(UserService::class.java)

        fun send_show() {     //보낸선물 내역
            lateinit var gift_list: List<Gift>

            val call = GiftService!!.getSend(LoginUser.email)
            call.enqueue(object : Callback<List<Gift>> {
                override fun onResponse(call: Call<List<Gift>>, response: Response<List<Gift>>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    gift_list = response.body()!!
                    for (gift in gift_list) {
                        var drawableId  = menuTores_id(gift.giftMenu)
                        var image = res_idToImage(drawableId)
                        sendGift_DataList.add(SendGiftData(image, "받는 사람 : "+ gift.recipient, "보낸 메뉴 : " + gift.giftMenu))
                    }
                    sendGiftAdapter.change()
                }

                override fun onFailure(call: Call<List<Gift>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }
//        sendGift_DataList.clear()
        send_show()

        fun gift_send(email_input : String, giftMenu : String) {     //선물하기

            val call = GiftService!!.postGift(LoginUser.email, email_input, giftMenu)
            call.enqueue(object : Callback<JSONObject> {
                override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    val data = response.body()
                    giftToEmail.text.clear()
                    sendGift_DataList.clear()
                    send_show()
                }

                override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }

        fun menu_show() {     //선물하기 전체메뉴내역
            lateinit var menu_list: List<Menu>

            val call = MenuService!!.getAllMenu
            call.enqueue(object : Callback<List<Menu>> {
                override fun onResponse(call: Call<List<Menu>>, response: Response<List<Menu>>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    menu_list = response.body()!!
                    for (menu in menu_list) {
                        var drawableId  = menuTores_id(menu.menu_name)
                        var image = res_idToImage(drawableId)
                        gift_DataList.add(GiftData(image, "선물 메뉴 : ${menu.menu_name}", "메뉴 가격 : ${menu.price}"))
                    }
                    giftAdapter.notifyDataSetChanged()

                    //결제하기 클릭하면 이메일 확인해서 선물
                    giftAdapter.setItemClickListener(object : GiftAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            var email_input = giftToEmail.text.toString()
                            var email_true = false
                            lateinit var data_list : List<User>
                            val call = UserService!!.getUser                //이메일 있는지 확인
                            call.enqueue(object : Callback<List<User>> {
                                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                                    if (!response.isSuccessful) {
                                        return
                                    }
                                    data_list = response.body()!!
                                    for (data in data_list) {
                                        if (email_input == data.email) email_true = true
                                    }
                                    if (email_input == "") Toast.makeText(this@GiftActivity, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
                                    else if(email_true == false) Toast.makeText(this@GiftActivity, "잘못된 이메일입니다", Toast.LENGTH_SHORT).show()
                                    else {
                                        val gift_data = gift_DataList[position]
                                        //Toast.makeText(v.context, "${gift_data.giftMenu} 클릭", Toast.LENGTH_SHORT).show()
                                        val cutString = gift_data.giftMenu.substring("선물 메뉴 : ".length)
                                        gift_send(email_input, cutString)

                                        val intent = Intent(this@GiftActivity, PaymentActivity2::class.java)
                                        startActivity(intent)
                                    }
                                }
                                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                                    Log.d("Debug", "onFailure 실행$t")
                                }
                            })
                        }
                    })
                }
                override fun onFailure(call: Call<List<Menu>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }
//        gift_DataList.clear()
        menu_show()

        fun gift_order(id : Int, sender : String, giftMenu : String) {    //받은선물 사용하기

            val call = OrderService!!.postOrder(giftMenu, menuTores_id(giftMenu), LoginUser.email)  //받은선물 주문
            call.enqueue(object : Callback<JSONObject> {
                override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    val data = response.body()

                    val call2 = GiftService!!.putGift(id, sender, LoginUser.email, giftMenu, true) //사용함표시
                    call2.enqueue(object : Callback<JSONObject> {
                        override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                            if (!response.isSuccessful) {
                                return
                            }
                            val data = response.body()
                        }
                        override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                            Log.d("Debug", "onFailure 실행$t")
                        }
                    })
                }
                override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }

        fun received_show() {     //받은선물 내역
            lateinit var gift_list: List<Gift>

            val call = GiftService!!.getRecipient(LoginUser.email)
            call.enqueue(object : Callback<List<Gift>> {
                override fun onResponse(call: Call<List<Gift>>, response: Response<List<Gift>>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    gift_list = response.body()!!
                    for (gift in gift_list) {
                        var drawableId  = menuTores_id(gift.giftMenu)
                        var image = res_idToImage(drawableId)
                        receivedGift_DataList.add(ReceivedGiftData(gift.id, image,  "보낸 사람 : ${gift.sender}", "받은 메뉴 : ${gift.giftMenu}"))
                        //gift id 저장
                    }
                    receivedGiftAdapter.notifyDataSetChanged()

                    //사용하기 클릭하면 주문하기
                    receivedGiftAdapter.setItemClickListener(object : ReceivedGiftAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            val received_data = receivedGift_DataList[position]
                            //Toast.makeText(v.context, "${received_data.receivedMenu} 클릭", Toast.LENGTH_SHORT).show()
                            val cutString = received_data.receivedMenu.substring("받은 메뉴 : ".length)
                            val cutString2 = received_data.giftSender.substring("보낸 사람 : ".length)
                            gift_order(received_data.giftID, cutString2, cutString)

                            val intent = Intent(this@GiftActivity, PaymentActivity::class.java)
                            startActivity(intent)
                        }
                    })
                }
                override fun onFailure(call: Call<List<Gift>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }
//        receivedGift_DataList.clear()
        received_show()


        //뒤로가기 클릭 시
        val backButton : ImageButton = findViewById(R.id.backButton_gift)
        backButton.setOnClickListener {
            finish()
        }

        //선물하기, 받은 선물, 보낸 선물 버튼
        val gift_Button : Button = findViewById(R.id.giftButton)
        val receivedGift_Button : Button = findViewById(R.id.receivedGiftButton)
        val gaveGift_Button : Button = findViewById(R.id.gaveGiftButton)

        // 클릭된 버튼의 테두리 색상을 변경하는 함수
        fun setButtonStrokeColorSelected(button: Button) {
            button.isSelected = true
        }
        // 클릭되지 않은 버튼의 테두리 색상을 변경하는 함수
        fun setButtonStrokeColorUnselected(button: Button) {
            button.isSelected = false
        }

        // 클릭된 버튼의 글자 색상을 변경하는 함수
        fun setButtonTextColorSelected(button: Button) {
            button.setTextColor(resources.getColor(R.color.white))
        }
        // 클릭되지 않은 글자 버튼의 색상을 변경하는 함수
        fun setButtonTextColorUnselected(button: Button) {
            button.setTextColor(resources.getColor(R.color.black))
        }

        // 클릭된 버튼의 글자 스타일을 지정하는 함수
        fun setButtonTextStyleSelected(button: Button) {
            button.setTypeface(null, Typeface.BOLD)
        }

        // 클릭되지 않은 버튼의 스타일을 지정하는 함수
        fun setButtonTextStyleUnSelected(button: Button) {
            button.setTypeface(null, Typeface.NORMAL)
        }


        //받은 선물 버튼 클릭 시
        receivedGift_Button.setOnClickListener {
            //테두리 설정
            setButtonStrokeColorSelected(receivedGift_Button)
            setButtonStrokeColorUnselected(gift_Button)
            setButtonStrokeColorUnselected(gaveGift_Button)

            //글자 색상 설정
            setButtonTextColorSelected(receivedGift_Button)
            setButtonTextColorUnselected(gift_Button)
            setButtonTextColorUnselected(gaveGift_Button)

            //글자 스타일 설정
            setButtonTextStyleSelected(receivedGift_Button)
            setButtonTextStyleUnSelected(gift_Button)
            setButtonTextStyleUnSelected(gaveGift_Button)

            //받은 선물 리사이클러뷰만 보이도록 설정
            receivedGift_recyclerView.visibility = View.VISIBLE
            sendGift_recyclerView.visibility = View.GONE
            Gift_recyclerView.visibility =View.GONE
            giftToEmail.visibility = View.GONE
        }

        //보낸 선물 버튼 클릭 시
        gaveGift_Button.setOnClickListener {
            //테두리 설정
            setButtonStrokeColorSelected(gaveGift_Button)
            setButtonStrokeColorUnselected(gift_Button)
            setButtonStrokeColorUnselected(receivedGift_Button)

            //글자 색상 설정
            setButtonTextColorSelected(gaveGift_Button)
            setButtonTextColorUnselected(gift_Button)
            setButtonTextColorUnselected(receivedGift_Button)

            //글자 스타일 설정
            setButtonTextStyleSelected(gaveGift_Button)
            setButtonTextStyleUnSelected(gift_Button)
            setButtonTextStyleUnSelected(receivedGift_Button)

            //보낸 선물 리사이클러뷰만 보이도록 설정
            sendGift_recyclerView.visibility = View.VISIBLE
            receivedGift_recyclerView.visibility = View.GONE
            Gift_recyclerView.visibility = View.GONE
            giftToEmail.visibility = View.GONE
        }
        //선물하기 버튼 클릭 시
        gift_Button.setOnClickListener {
            //테두리 설정
            setButtonStrokeColorSelected(gift_Button)
            setButtonStrokeColorUnselected(receivedGift_Button)
            setButtonStrokeColorUnselected(gaveGift_Button)

            //글자 색상 설정
            setButtonTextColorSelected(gift_Button)
            setButtonTextColorUnselected(receivedGift_Button)
            setButtonTextColorUnselected(gaveGift_Button)

            //글자 스타일 설정
            setButtonTextStyleSelected(gift_Button)
            setButtonTextStyleUnSelected(receivedGift_Button)
            setButtonTextStyleUnSelected(gaveGift_Button)

            //선물하기 리사이클러뷰만 보이도록 설정
            Gift_recyclerView.visibility = View.VISIBLE
            receivedGift_recyclerView.visibility = View.GONE
            sendGift_recyclerView.visibility = View.GONE
            giftToEmail.visibility = View.VISIBLE

        }
        // 선물하기 버튼을 디폴트로 설정
        setButtonStrokeColorSelected(gift_Button)
        setButtonTextColorSelected(gift_Button)
        setButtonTextStyleSelected(gift_Button)
        receivedGift_recyclerView.visibility = View.GONE
        sendGift_recyclerView.visibility = View.GONE
    }
}
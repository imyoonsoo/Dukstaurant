package com.example.projecttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.projecttest.retrofit.RetrofitClient
import com.example.projecttest.retrofit.ReviewService
import kotlinx.android.synthetic.main.activity_review_write.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//가게 입장
class ReviewWriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_write)

        // 인텐트로 전달된 데이터를 받아옴
        val orderedStore  = intent.getStringExtra("orderedStore")
        val orderedMenu = intent.getStringExtra("orderedMenu")
        val storeImage = intent.getIntExtra("storeImage", R.id.storeImage)
        var orderId = intent.getIntExtra("orderId", 0)
        val orderStoreInt = res_NameToId(orderedStore)

        //Toast.makeText(this@ReviewWriteActivity, orderedStore, Toast.LENGTH_SHORT).show()

        // 받아온 데이터를 화면에 표시
        val orderedStoreTextView = findViewById<TextView>(R.id.orderedStore_writing)
        val orderedMenuTextView = findViewById<TextView>(R.id.orderedMenu_writing)
        val storeImageView = findViewById<ImageView>(R.id.storeImage_writing)

        // "주문 메뉴 : "를 찾아서 이후의 부분만 추출
        val startIndex = orderedMenu?.indexOf("주문 메뉴 :")
        if (startIndex != null && startIndex >= 0) {
            val menuWithoutPrefix = orderedMenu.substring(startIndex + 8) // 8은 "주문 메뉴 :"의 길이입니다.
            val formattedMenu = "$menuWithoutPrefix"
            orderedMenuTextView.text = formattedMenu
        } else {
            // "주문 메뉴 : "가 문자열에 없는 경우, 전체 문자열을 그대로 사용
            orderedMenuTextView.text = orderedMenu
        }
        orderedStoreTextView.text = orderedStore
        storeImageView.setImageResource(storeImage)

        val ReviewService = RetrofitClient.getClient()?.create(ReviewService::class.java)

        fun review_write(restaurant_num : Int, order_num : Int) {
            var content_input = editTextReview.text.toString()


            if(content_input == "") Toast.makeText(this@ReviewWriteActivity, "리뷰를 입력해주세요", Toast.LENGTH_SHORT).show()
            else if (true) {
                val call = ReviewService!!.postReview(LoginUser.email, content_input, restaurant_num, order_num)
                call.enqueue(object : Callback<JSONObject> {
                    override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                        if (!response.isSuccessful) {
                            return
                        }
                        //Toast.makeText(this@ReviewWriteActivity, "리뷰 등록 완료", Toast.LENGTH_SHORT).show()
                        val data = response.body()
                        finish()
                    }

                    override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                        Log.d("Debug", "onFailure 실행$t")
                    }
                })
            }
        }

        //리뷰작성완료 클릭 시
        val reviewWritingButton = findViewById<Button>(R.id.reviewDone)
        reviewWritingButton.setOnClickListener {
            Toast.makeText(this@ReviewWriteActivity, "리뷰 작성 완료", Toast.LENGTH_SHORT).show()
            review_write(orderStoreInt, orderId)

        }

        //뒤로가기 클릭 시
        val backButton = findViewById<ImageButton>(R.id.backButton_wirting)
        backButton.setOnClickListener {
            finish()
        }

    }
}
package com.example.projecttest

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.retrofit.RetrofitClient
import com.example.projecttest.retrofit.Review
import com.example.projecttest.retrofit.ReviewService
import kotlinx.android.synthetic.main.activity_review.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        // 리뷰 페이지 데이터 리스트
        val reviewDataList = mutableListOf<ReviewData>()
        reviewDataList.add(ReviewData(R.drawable.profile, "", ""))

        // 어댑터 생성 및 연결
        val recyclerView: RecyclerView = recyclerViewReview
        recyclerView.layoutManager = LinearLayoutManager(this@ReviewActivity)
        recyclerView.adapter = ReviewAdapter(reviewDataList)

        // 어댑터 인스턴스 생성
        val adapter = ReviewAdapter(reviewDataList)
        recyclerView.adapter = adapter

        val ReviewService = RetrofitClient.getClient()?.create(ReviewService::class.java)

        fun review_show(restaurant_num : Int) {
            lateinit var review_list: List<Review>  //Review 받을 곳

            val call = ReviewService!!.getReview(restaurant_num)
            call.enqueue(object : Callback<List<Review>> {
                override fun onResponse(
                    call: Call<List<Review>>,
                    response: Response<List<Review>>
                ) {
                    if (!response.isSuccessful) {
                        return
                    }
                    review_list = response.body()!!
                    for (review in review_list) {
                        reviewDataList.add(ReviewData(R.drawable.profile, "주문번호 " + review.order.toString(), review.content))
                    }
                    adapter.change()
                }

                override fun onFailure(call: Call<List<Review>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }
        //var restaurant_input = 1   //파스타 눌렀다고 가정 -> id = 1 이 파스타
        reviewDataList.clear()
        review_show(1)  //맨 처음 파스타 눌림 상태 -> 파스타 리뷰 조회


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

        // 6개의 메뉴 버튼들
        val omeA_Button : Button = findViewById(R.id.omeA)
        val omeB_Button : Button = findViewById(R.id.omeB)
        val pasta_Button : Button = findViewById(R.id.pasta_re)
        val mara_Button : Button = findViewById(R.id.mara_re)
        val gunsan_Button : Button = findViewById(R.id.gunsan_re)
        val maseong_Button : Button = findViewById(R.id.maseong_re)


        // 각 버튼 클릭에 따라 버튼 테두리 지정
        // 각 버튼 클릭에 따른 글자 색상 지정
        omeA_Button.setOnClickListener {
            setButtonStrokeColorSelected(omeA_Button)
            setButtonStrokeColorUnselected(omeB_Button)
            setButtonStrokeColorUnselected(pasta_Button)
            setButtonStrokeColorUnselected(mara_Button)
            setButtonStrokeColorUnselected(gunsan_Button)
            setButtonStrokeColorUnselected(maseong_Button)

            setButtonTextColorSelected(omeA_Button)
            setButtonTextColorUnselected(omeB_Button)
            setButtonTextColorUnselected(pasta_Button)
            setButtonTextColorUnselected(mara_Button)
            setButtonTextColorUnselected(gunsan_Button)
            setButtonTextColorUnselected(maseong_Button)

            setButtonTextStyleSelected(omeA_Button)
            setButtonTextStyleUnSelected(omeB_Button)
            setButtonTextStyleUnSelected(pasta_Button)
            setButtonTextStyleUnSelected(mara_Button)
            setButtonTextStyleUnSelected(gunsan_Button)
            setButtonTextStyleUnSelected(maseong_Button)
            reviewDataList.clear() //datalist 모두 지우기
            review_show(5)
        }

        omeB_Button.setOnClickListener {
            setButtonStrokeColorSelected(omeB_Button)
            setButtonStrokeColorUnselected(omeA_Button)
            setButtonStrokeColorUnselected(pasta_Button)
            setButtonStrokeColorUnselected(mara_Button)
            setButtonStrokeColorUnselected(gunsan_Button)
            setButtonStrokeColorUnselected(maseong_Button)

            setButtonTextColorSelected(omeB_Button)
            setButtonTextColorUnselected(omeA_Button)
            setButtonTextColorUnselected(pasta_Button)
            setButtonTextColorUnselected(mara_Button)
            setButtonTextColorUnselected(gunsan_Button)
            setButtonTextColorUnselected(maseong_Button)

            setButtonTextStyleSelected(omeB_Button)
            setButtonTextStyleUnSelected(omeA_Button)
            setButtonTextStyleUnSelected(pasta_Button)
            setButtonTextStyleUnSelected(mara_Button)
            setButtonTextStyleUnSelected(gunsan_Button)
            setButtonTextStyleUnSelected(maseong_Button)
            reviewDataList.clear() //datalist 모두 지우기
            review_show(6)
        }

        pasta_Button.setOnClickListener {
            setButtonStrokeColorSelected(pasta_Button)
            setButtonStrokeColorUnselected(omeA_Button)
            setButtonStrokeColorUnselected(omeB_Button)
            setButtonStrokeColorUnselected(mara_Button)
            setButtonStrokeColorUnselected(gunsan_Button)
            setButtonStrokeColorUnselected(maseong_Button)

            setButtonTextColorSelected(pasta_Button)
            setButtonTextColorUnselected(omeA_Button)
            setButtonTextColorUnselected(omeB_Button)
            setButtonTextColorUnselected(mara_Button)
            setButtonTextColorUnselected(gunsan_Button)
            setButtonTextColorUnselected(maseong_Button)

            setButtonTextStyleSelected(pasta_Button)
            setButtonTextStyleUnSelected(omeA_Button)
            setButtonTextStyleUnSelected(omeB_Button)
            setButtonTextStyleUnSelected(mara_Button)
            setButtonTextStyleUnSelected(gunsan_Button)
            setButtonTextStyleUnSelected(maseong_Button)
            reviewDataList.clear()
            review_show(1)  //파스타 id = 1
        }

        mara_Button.setOnClickListener {
            setButtonStrokeColorSelected(mara_Button)
            setButtonStrokeColorUnselected(omeA_Button)
            setButtonStrokeColorUnselected(omeB_Button)
            setButtonStrokeColorUnselected(pasta_Button)
            setButtonStrokeColorUnselected(gunsan_Button)
            setButtonStrokeColorUnselected(maseong_Button)

            setButtonTextColorSelected(mara_Button)
            setButtonTextColorUnselected(omeA_Button)
            setButtonTextColorUnselected(omeB_Button)
            setButtonTextColorUnselected(pasta_Button)
            setButtonTextColorUnselected(gunsan_Button)
            setButtonTextColorUnselected(maseong_Button)

            setButtonTextStyleSelected(mara_Button)
            setButtonTextStyleUnSelected(omeA_Button)
            setButtonTextStyleUnSelected(omeB_Button)
            setButtonTextStyleUnSelected(pasta_Button)
            setButtonTextStyleUnSelected(gunsan_Button)
            setButtonTextStyleUnSelected(maseong_Button)
            reviewDataList.clear()
            review_show(2)   //마라탕 id = 1
        }

        gunsan_Button.setOnClickListener {
            setButtonStrokeColorSelected(gunsan_Button)
            setButtonStrokeColorUnselected(omeA_Button)
            setButtonStrokeColorUnselected(omeB_Button)
            setButtonStrokeColorUnselected(pasta_Button)
            setButtonStrokeColorUnselected(mara_Button)
            setButtonStrokeColorUnselected(maseong_Button)

            setButtonTextColorSelected(gunsan_Button)
            setButtonTextColorUnselected(omeA_Button)
            setButtonTextColorUnselected(omeB_Button)
            setButtonTextColorUnselected(pasta_Button)
            setButtonTextColorUnselected(mara_Button)
            setButtonTextColorUnselected(maseong_Button)

            setButtonTextStyleSelected(gunsan_Button)
            setButtonTextStyleUnSelected(omeA_Button)
            setButtonTextStyleUnSelected(omeB_Button)
            setButtonTextStyleUnSelected(pasta_Button)
            setButtonTextStyleUnSelected(mara_Button)
            setButtonTextStyleUnSelected(maseong_Button)
            reviewDataList.clear()
            review_show(3)
        }

        maseong_Button.setOnClickListener {
            setButtonStrokeColorSelected(maseong_Button)
            setButtonStrokeColorUnselected(omeA_Button)
            setButtonStrokeColorUnselected(omeB_Button)
            setButtonStrokeColorUnselected(pasta_Button)
            setButtonStrokeColorUnselected(mara_Button)
            setButtonStrokeColorUnselected(gunsan_Button)

            setButtonTextColorSelected(maseong_Button)
            setButtonTextColorUnselected(omeA_Button)
            setButtonTextColorUnselected(omeB_Button)
            setButtonTextColorUnselected(pasta_Button)
            setButtonTextColorUnselected(mara_Button)
            setButtonTextColorUnselected(gunsan_Button)

            setButtonTextStyleSelected(maseong_Button)
            setButtonTextStyleUnSelected(omeA_Button)
            setButtonTextStyleUnSelected(omeB_Button)
            setButtonTextStyleUnSelected(pasta_Button)
            setButtonTextStyleUnSelected(mara_Button)
            setButtonTextStyleUnSelected(gunsan_Button)
            reviewDataList.clear()
            review_show(4)
        }

        // 오늘의 메뉴A 버튼을 디폴트 클릭으로 설정
        setButtonStrokeColorSelected(pasta_Button)
        setButtonTextColorSelected(pasta_Button)
        setButtonTextStyleSelected(pasta_Button)

        // 뒤로가기 버튼 클릭 시
        val backButton : ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }
}
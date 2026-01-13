package com.example.projecttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.LoginUser.Companion.restaurant_num
import com.example.projecttest.retrofit.RetrofitClient
import com.example.projecttest.retrofit.Review
import com.example.projecttest.retrofit.ReviewService
import kotlinx.android.synthetic.main.activity_review.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_res_review)

        // 리뷰 페이지 데이터 리스트
        val reviewDataList = mutableListOf<ReviewData>()
        reviewDataList.add(ReviewData(R.drawable.profile, "", ""))

        // 어댑터 생성 및 연결
        val recyclerView: RecyclerView = recyclerViewReview
        recyclerView.layoutManager = LinearLayoutManager(this@ResReviewActivity)
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
        reviewDataList.clear()
        review_show(restaurant_num)
        //Toast.makeText(this@ResReviewActivity, restaurant_num.toString(), Toast.LENGTH_SHORT).show()

        //뒤로가기 버튼 생성 및 클릭 시 기능
        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener{
            finish()
        }
    }
}
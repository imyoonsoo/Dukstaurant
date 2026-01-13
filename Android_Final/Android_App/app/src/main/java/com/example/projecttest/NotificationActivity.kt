package com.example.projecttest

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.projecttest.retrofit.NotificationService
import com.example.projecttest.retrofit.Notification
import com.example.projecttest.retrofit.RetrofitClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val NotificationService = RetrofitClient.getClient()?.create(NotificationService::class.java)

        fun notification_show() {
            lateinit var notification_list: List<Notification>  //Notification 받을 곳
            var rest = ""

            val call = NotificationService!!.getNotification
            call.enqueue(object : Callback<List<Notification>> {
                override fun onResponse(
                    call: Call<List<Notification>>,
                    response: Response<List<Notification>>
                ) {
                    if (!response.isSuccessful) {
                        return
                    }
                    notification_list = response.body()!!

                    // 공지사항 페이지 데이터 리스트
                    val noticeDataList = mutableListOf<NoticeData>()

                    for (notification in notification_list) {
                        var drawableId  = res_idToImage(notification.restaurant)
                        noticeDataList.add(NoticeData(drawableId, notification.title, notification.content, notification.date))
                    }
                    // 어댑터 생성 및 연결
                    val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
                    recyclerView.layoutManager = LinearLayoutManager(this@NotificationActivity)
                    recyclerView.adapter = NoticeAdapter(noticeDataList)

                    /*
                    // 어댑터 인스턴스 생성
                    val adapter = NoticeAdapter(noticeDataList)
                    recyclerView.adapter = adapter

                     */
                }

                override fun onFailure(call: Call<List<Notification>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }
        notification_show()

        //뒤로가기 버튼 생성 및 클릭 시 기능
        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener{
            finish()
        }

    }
}


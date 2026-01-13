package com.example.projecttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.projecttest.LoginUser.Companion.restaurant_num
import com.example.projecttest.retrofit.NotificationService
import com.example.projecttest.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_etc.*
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_notification_write.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class NotificationWriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_write)

        val NotificationService = RetrofitClient.getClient()?.create(NotificationService::class.java)

        fun notification_write(restaurant_num : Int) {   //공지사항 작성하기
            var title_input = editTitle_writing.text.toString()
            var content_input = editContent_writing.text.toString()
            val currentDate = LocalDate.now(ZoneId.of("Asia/Seoul")).toString()

            if(title_input == "") Toast.makeText(this@NotificationWriteActivity, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
            else if(content_input == "") Toast.makeText(this@NotificationWriteActivity, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
            else if (true) {
                val call = NotificationService!!.postNotification(title_input, content_input, currentDate, restaurant_num)
                call.enqueue(object : Callback<JSONObject> {
                    override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                        if (!response.isSuccessful) {
                            return
                        }
                        Toast.makeText(this@NotificationWriteActivity, "공지 등록 완료", Toast.LENGTH_SHORT).show()
                        val data = response.body()
                    }

                    override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                        Log.d("Debug", "onFailure 실행$t")
                    }

                })
            }
        }

        DoneButton_writing.setOnClickListener {
            notification_write(restaurant_num)
            editTitle_writing.text = null
            editContent_writing.text = null
        }

        backButton_writing.setOnClickListener {   //뒤로가기 버튼
            finish()
        }
    }
}
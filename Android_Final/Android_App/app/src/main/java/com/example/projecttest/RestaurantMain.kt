package com.example.projecttest

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.wheelview.WheelView
import kotlinx.android.synthetic.main.activity_restaurant_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RestaurantMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val restwheelView = findViewById<WheelView>(R.id.restMainwheelView)
        restwheelView.titles = listOf("가게 주문 내역", "공지 쓰기", "가게 리뷰 보기")

        restwheelView.selectListener = { // selected position
            //Toast.makeText(this,"selected on ${it}", Toast.LENGTH_SHORT).show()
            //Thread.sleep(2000)
            GlobalScope.launch {
                delay(1000) // 1초 동안 기다림
                if(it==0){
                    val intent = Intent(this@RestaurantMain, ROrderDetailActivity::class.java)  //가게 주문내역 버튼 클릭 했을 때
                    startActivity(intent)
                }
                else if(it==1){
                    val intent = Intent(this@RestaurantMain, NotificationWriteActivity::class.java)  //공지 쓰기 버튼 클릭 했을 때
                    startActivity(intent)
                }
                else if(it==2){
                    val intent = Intent(this@RestaurantMain, ResReviewActivity::class.java)  //우리가게 리뷰 보기 버튼 클릭 했을 때
                    startActivity(intent)
                }
            }

        }

        // 로그아웃 버튼 클릭 시
        val logoutButton: Button = findViewById(R.id.buttonLogout)
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
                val intent = Intent(this@RestaurantMain, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  //로그아웃 -> 스택에 들어있는 모든 액티비티 종료
                startActivity(intent)
                finish() // 현재 화면 종료
                alertDialog.dismiss() // 다이얼로그 닫기
            }
            alertDialog.setView(dialogView)
            alertDialog.show()
        }


    }
}
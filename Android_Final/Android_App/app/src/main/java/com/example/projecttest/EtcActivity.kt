package com.example.projecttest

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_etc.*
import kotlinx.android.synthetic.main.activity_restaurant_main.*

class EtcActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etc)



        button6.setOnClickListener {
            val intent = Intent(this@EtcActivity, NotificationActivity::class.java)  //공지 보기 버튼 클릭 했을 때
            startActivity(intent)
        }

        button7.setOnClickListener {
            val intent = Intent(this@EtcActivity, ReviewActivity::class.java)  //리뷰 보기 버튼 클릭 했을 때
            startActivity(intent)
        }

        button8.setOnClickListener {
            val intent = Intent(this@EtcActivity, ReviewSelect::class.java)  //리뷰 쓰기 버튼 클릭 했을 때
            startActivity(intent)
        }

        button15.setOnClickListener {
            val intent = Intent(this@EtcActivity, MyHealthActivity::class.java)  //리뷰 쓰기 버튼 클릭 했을 때
            startActivity(intent)
        }

        button16.setOnClickListener {
            val intent = Intent(this@EtcActivity, OrderDetailActivity::class.java)  //유저 주문내역 버튼 클릭 했을 때
            startActivity(intent)
        }

        button17.setOnClickListener {
            val intent = Intent(this@EtcActivity, MyInfoActivity::class.java)  //내정보 버튼 클릭 했을 때
            startActivity(intent)
        }
        //선물하기 버튼 클릭 시
        toGiftButton.setOnClickListener {
            val intent = Intent(this@EtcActivity, GiftActivity::class.java)
            startActivity(intent)
        }

        button_back3.setOnClickListener {    //뒤로가기
            finish()
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
                val intent = Intent(this@EtcActivity, MainActivity::class.java)
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
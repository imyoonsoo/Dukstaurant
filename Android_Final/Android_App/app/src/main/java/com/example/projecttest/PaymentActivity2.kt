package com.example.projecttest

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.projecttest.Cart.cartReset
import com.example.projecttest.R.id.paymentLoadingIcon
import com.example.projecttest.retrofit.Order
import com.example.projecttest.retrofit.OrderService
import com.example.projecttest.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_gift.*
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.activity_payment2.*
import kotlinx.android.synthetic.main.activity_r_order_detail.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment2)


        val loadingView = findViewById<ImageView>(R.id.paymentLoadingIcon2)
        loadingView.setImageResource(R.drawable.loading_icon2_24px)

        val layoutParams = loadingView.layoutParams

        val rotateAnimation = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotateAnimation.duration = 1500 // 2.5초 동안 회전
//        rotateAnimation.repeatCount = Animation.INFINITE // 무한 반복

        loadingView.startAnimation(rotateAnimation)

        val loadingText = findViewById<TextView>(R.id.loadingText2)
        loadingText.text = "결제 처리 중..."


        Handler().postDelayed({
            loadingView.setImageResource(R.drawable.gift)
            layoutParams.width = 300 // 가로폭 설정
            layoutParams.height = 300 // 세로높이 설정
            loadingView.layoutParams = layoutParams
            loadingText.text = "선물 보내기 완료!"
            loadingText.setTextSize(22f) // 24픽셀
        }, 1500)

        Handler().postDelayed({
            finish()
        }, 3000)


        //Toast.makeText(this@PaymentActivity2, "", Toast.LENGTH_SHORT).show()
    }

}
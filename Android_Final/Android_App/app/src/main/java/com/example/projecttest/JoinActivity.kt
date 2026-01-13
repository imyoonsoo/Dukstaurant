package com.example.projecttest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projecttest.retrofit.RetrofitClient
import com.example.projecttest.retrofit.UserService
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.projecttest.retrofit.*

class JoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        //회원가입 버튼 클릭시
        fun join(restaurant_on : Boolean, restarant_Click:Boolean) {
            var email_input = joinEmailAddress.text.toString()
            var password_input = joinPassword.text.toString()
            var password_input2 = joinPassword2.text.toString()
            var nickname_input = joinNickname.text.toString()


            if (nickname_input == "") Toast.makeText(this@JoinActivity, "닉네임 입력해주세요", Toast.LENGTH_SHORT).show()
            else if(email_input == "") Toast.makeText(this@JoinActivity, "이메일 입력해주세요", Toast.LENGTH_SHORT).show()
            else if (password_input == "") Toast.makeText(this@JoinActivity, "비밀번호 입력해주세요", Toast.LENGTH_SHORT).show()
            else if (password_input2 == "")  Toast.makeText(this@JoinActivity, "비밀번호 입력해주세요", Toast.LENGTH_SHORT).show()
            else if(password_input != password_input2) Toast.makeText(this@JoinActivity, "비밀번호가  일치하지않습니다", Toast.LENGTH_SHORT).show()
            else if (nickname_input == "") Toast.makeText(this@JoinActivity, "닉네임 입력해주세요", Toast.LENGTH_SHORT).show()
            else if (!restarant_Click) Toast.makeText(this@JoinActivity, "가게 or 손님 입력해주세요", Toast.LENGTH_SHORT).show()

            else if (true){
                //Toast.makeText(this@JoinActivity, "성공", Toast.LENGTH_SHORT).show()
                val UserService = RetrofitClient.getClient()?.create(UserService::class.java)

                val call = UserService!!.postUser(email_input, password_input, nickname_input, restaurant_on)
                call.enqueue(object : Callback<JSONObject> {
                    override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                        if (!response.isSuccessful) {
                            //textView.setText("Code: " + response.code())
                            return
                        }
                        val data = response.body()

                        //val UserService = RetrofitClient.getClient()?.create(UserService::class.java)
                        val call2 = UserService!!.getUserInf(email_input)
                        call2.enqueue(object: Callback<List<User>> {
                            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                                if (!response.isSuccessful) {
                                    return
                                }
                                lateinit var data_list: List<User>
                                data_list = response.body()!!
                                for (data in data_list) {}
                                for(data in data_list){
                                    LoginUser.id = data.id
                                    LoginUser.email = data.email
                                    LoginUser.password = data.password
                                    LoginUser.nickname = data.nickname
                                    LoginUser.restaurant_on = data.restaurant_on
                                }
                                //Toast.makeText(this@JoinActivity, LoginUser.id.toString() , Toast.LENGTH_SHORT).show()
                                //Toast.makeText(this@JoinActivity, LoginUser.email , Toast.LENGTH_SHORT).show()
                                //Toast.makeText(this@JoinActivity, LoginUser.password , Toast.LENGTH_SHORT).show()
                                //Toast.makeText(this@JoinActivity, LoginUser.nickname , Toast.LENGTH_SHORT).show()
                                //Toast.makeText(this@JoinActivity, LoginUser.restaurant_on.toString() , Toast.LENGTH_SHORT).show()

                                //회원가입 & 회원가입한 user 정보 얻기 성공시 페이지 이동
                                if( LoginUser.restaurant_on ){  //가게로 로그인 -> 가게 입장 페이지 이동
                                    LoginUser.set_restaurant_num()  //레스토랑 id -> LoginUser에 넣어주기
                                    val intent = Intent(this@JoinActivity, RestaurantMain::class.java)
                                    startActivity(intent)
                                    return
                                }
                                else{  //손님으로 로그인 -> 손님 입장 페이지 이동
                                    val intent = Intent(this@JoinActivity, OrderActivity::class.java)
                                    startActivity(intent)
                                    return
                                }
                            }
                            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                                Log.d("Debug", "onFailure 실행$t")
                            }

                        })
                    }

                    override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                        //textView.setText(t.message)
                        Log.d("Debug", "onFailure 실행$t")
                    }

                })
            }

        }
        var restaurant_on : Boolean = false
        var restarant_Click : Boolean = false
        radioGroup.setOnCheckedChangeListener { group, checkedid ->
            when(checkedid) {
                R.id.custButton -> restaurant_on = false
                R.id.sellerButton -> restaurant_on = true
            }
            restarant_Click = true
        }
        //회원가입 버튼 클릭
        finalJoinButton.setOnClickListener {
            join(restaurant_on, restarant_Click)
        }

        backButtonJoin.setOnClickListener {
            finish()
        }
    }
}
package com.example.projecttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.projecttest.retrofit.RetrofitClient
import com.example.projecttest.retrofit.User
import com.example.projecttest.retrofit.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.activity_order.view.*
//import kotlinx.android.synthetic.main.login.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val userService = RetrofitClient.getClient()?.create(UserService::class.java)

        //로그인 화면

        fun login(email_input: String, password_input: String) {
            lateinit var data_list : List<User>  //User받을 곳

            val call = userService!!.getUser
            call.enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    data_list = response.body()!!
                    for (data in data_list) {
                        if (email_input.equals(data.email)) {
                            if (password_input.equals(data.password)) {
                                //로그인한 유저의 정보 LoginUser에 저장하기
                                LoginUser.id = data.id        //수정
                                LoginUser.email = data.email
                                LoginUser.password = data.password
                                LoginUser.nickname = data.nickname
                                LoginUser.restaurant_on = data.restaurant_on

                                //Toast.makeText(this@MainActivity, "로그인 성공!!!", Toast.LENGTH_SHORT).show()


                                //로그인 성공시 화면 전환 -> 가게 또는 손님으로 로그인 했을 때 다른 화면 전환
                                if( LoginUser.restaurant_on ){  //가게로그인 시 -> 가게 입장 화면으로
                                    LoginUser.set_restaurant_num()  //레스토랑 id -> LoginUser에 넣어주기
                                    val intent = Intent(this@MainActivity, RestaurantMain::class.java)
                                    startActivity(intent)
                                    return
                                }
                                else{  //손님 로그인시 -> 손님 입장 화면으로
                                    val intent = Intent(this@MainActivity, OrderActivity::class.java)
                                    startActivity(intent)
                                    return
                                }
                            } else { Toast.makeText(this@MainActivity, "비밀번호 오류!!!", Toast.LENGTH_SHORT).show()
                                return
                            }
                        }
                    }
                    Toast.makeText(this@MainActivity, "아이디 오류!!!", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    //textView.setText(t.message)
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }

        //로그인 버튼 클릭시
        loginButton.setOnClickListener {
            var email_input = editTextEmailAddress.text.toString()
            var password_input = editTextPassword.text.toString()
            login(email_input, password_input)
        }

        //회원가입 화면 전환
        joinButton.setOnClickListener{
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
        
    }
}
package com.example.projecttest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.retrofit.*
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_my_health.*
import kotlinx.android.synthetic.main.activity_order.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONObject
import java.time.LocalDate
import java.time.ZoneId

class MyHealthActivity : AppCompatActivity(), CustomDialogCallback1, CustomDialogCallback2{
    //추천메뉴 목록
    private lateinit var recyclerView1: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recyclerView3: RecyclerView
    private var m1List = ArrayList<menuList>()
    private var m2List = ArrayList<menuList>()
    private var m3List = ArrayList<menuList>()
    private lateinit var recadapter1: recommendAdapter
    private lateinit var recadapter2: recommendAdapter
    private lateinit var recadapter3: recommendAdapter

    private var totalCalorie : Float = 0.0f
    private var Total_Car : Float = 0.0f
    private var Total_Pro : Float = 0.0f
    private var Total_Fat : Float = 0.0f
    private var Kcal : Float = 0.0f
    private var Car : Float = 0.0f
    private var Pro : Float = 0.0f
    private var Fat : Float = 0.0f

    lateinit var pieChart : PieChart
    var entries = ArrayList<PieEntry>()

    fun addPie() {
        //Toast.makeText(this@MyHealthActivity, "addpie실행" + Total_Car.toString() + Car.toString(), Toast.LENGTH_SHORT).show()
        //파이그래프 데이터 넣기
        entries.clear()  //전 데이터 지우기
        entries.add(PieEntry(Car, "탄"))  //먹은 탄
        entries.add(PieEntry(Total_Car-Car, "남은 탄"))  //먹을 탄
        entries.add(PieEntry(Pro, "단"))
        entries.add(PieEntry(Total_Pro-Pro, "남은 단"))
        entries.add(PieEntry(Fat, "지"))
        entries.add(PieEntry(Total_Fat, "남은 지"))

        val dataSet = PieDataSet(entries, "")
        //val valueTextColors = mutableListOf<Int>(255, 255, 255)
        //dataSet.setValueTextColors(valueTextColors)
        dataSet.colors = listOf(
            ContextCompat.getColor(this@MyHealthActivity, R.color.car),
            ContextCompat.getColor(this@MyHealthActivity, R.color.car2),
            ContextCompat.getColor(this@MyHealthActivity, R.color.pro),
            ContextCompat.getColor(this@MyHealthActivity, R.color.pro2),
            ContextCompat.getColor(this@MyHealthActivity, R.color.fat),
            ContextCompat.getColor(this@MyHealthActivity, R.color.fat2),
        )
        dataSet.valueTextSize = 16F
        val piedata = PieData(dataSet)
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setData(piedata)
        pieChart.invalidate()
    }

    val MyHealthService = RetrofitClient.getClient()?.create(MyHealthService::class.java)

    private fun myhealth_post_creation() {           //내가오늘먹은양 생성
        val currentDate = LocalDate.now(ZoneId.of("Asia/Seoul")).toString()

        val call = MyHealthService!!.postMyHealth_post(LoginUser.id, currentDate, 0.0f, 0.0f, 0.0f, 0.0f)
        call.enqueue(object : Callback<JSONObject> {
            override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                if (!response.isSuccessful) {
                    return
                }
                //Toast.makeText(this@MyHealthActivity, " 등록 완료", Toast.LENGTH_SHORT).show()
                val data = response.body()
                addPie()
            }

            override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                Log.d("Debug", "onFailure 실행$t")
            }
        })
    }

    private fun myhealth_pre_creation(kcal : Float) {         //권장섭취량 생성

        val call = MyHealthService!!.postMyHealth_pre(LoginUser.id, kcal)
        call.enqueue(object : Callback<JSONObject> {
            override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                if (!response.isSuccessful) {
                    return
                }
                Toast.makeText(this@MyHealthActivity, "권장 섭취량 등록", Toast.LENGTH_SHORT).show()
                val data = response.body()

                lateinit var myhealth_pre_list: List<Myhealth_pre>

                val call2 = MyHealthService!!.getMyHealth_pre(LoginUser.id)
                call2.enqueue(object : Callback<List<Myhealth_pre>> {
                    override fun onResponse(call: Call<List<Myhealth_pre>>, response: Response<List<Myhealth_pre>>) {
                        if (!response.isSuccessful) {
                            return
                        }
                        myhealth_pre_list = response.body()!!
                        for (myhealth_pre in myhealth_pre_list) {
                            totalCalorie =  myhealth_pre.kcal             //totalCalorie 조회로 받을지 판단
                            //Toast.makeText(this@MyHealthActivity, "받아온 칼로리" + totalCalorie.toString(), Toast.LENGTH_SHORT).show()
                            Total_Car = (myhealth_pre.kcal*0.5/4).toFloat()
                            Total_Pro = (myhealth_pre.kcal*0.3/4).toFloat()
                            Total_Fat = (myhealth_pre.kcal*0.2/9).toFloat()

                        }
                        lateinit var myhealth_post_list: List<Myhealth_post>
                        val currentDate = LocalDate.now(ZoneId.of("Asia/Seoul")).toString()

                        val call3 = MyHealthService!!.getMyHealth_post(LoginUser.id, currentDate)
                        call3.enqueue(object : Callback<List<Myhealth_post>> {
                            override fun onResponse(call: Call<List<Myhealth_post>>, response: Response<List<Myhealth_post>>) {
                                if (!response.isSuccessful) {
                                    return
                                }
                                myhealth_post_list = response.body()!!
                                if(myhealth_post_list.isEmpty()) {
                                    myhealth_post_creation()
                                }
                                else {
                                    for (myhealth_post in myhealth_post_list) {
                                        Kcal = myhealth_post.totalKcal
                                        Car = myhealth_post.total_car
                                        Pro = myhealth_post.total_pro
                                        Fat = myhealth_post.total_fat
                                        //Toast.makeText(this@MyHealthActivity, "받아온 Kcal" + Kcal.toString(), Toast.LENGTH_SHORT).show()
                                        //Toast.makeText(this@MyHealthActivity, "받아온 Car" + Car.toString(), Toast.LENGTH_SHORT).show()
                                        //Toast.makeText(this@MyHealthActivity, "받아온 Pro" + Pro.toString(), Toast.LENGTH_SHORT).show()
                                        //Toast.makeText(this@MyHealthActivity, "받아온 Fat" + Fat.toString(), Toast.LENGTH_SHORT).show()
                                    }
                                    addPie()
                                }
                            }
                            override fun onFailure(call: Call<List<Myhealth_post>>, t: Throwable) {
                                Log.d("Debug", "onFailure 실행$t")
                            }
                        })
                    }

                    override fun onFailure(call: Call<List<Myhealth_pre>>, t: Throwable) {
                        Log.d("Debug", "onFailure 실행$t")
                    }
                })
            }

            override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                Log.d("Debug", "onFailure 실행$t")
            }
        })
    }

    fun myhealth_post_add(kcal_input : Float, car_input : Float, pro_input : Float, fat_input : Float) { //내가오늘먹은양 추가
        lateinit var myhealth_post_list: List<Myhealth_post>
        val currentDate = LocalDate.now(ZoneId.of("Asia/Seoul")).toString()

        val call = MyHealthService!!.getMyHealth_post(LoginUser.id, currentDate)
        call.enqueue(object : Callback<List<Myhealth_post>> {
            override fun onResponse(call: Call<List<Myhealth_post>>, response: Response<List<Myhealth_post>>) {
                if (!response.isSuccessful) {
                    return
                }
                var kcal = 0.0f; var car = 0.0f; var pro = 0.0f; var fat = 0.0f; var id = 0 //더할 칼로기
                myhealth_post_list = response.body()!!
                for (myhealth_post in myhealth_post_list) {
                    kcal = myhealth_post.totalKcal + kcal_input
                    car = myhealth_post.total_car + car_input
                    pro = myhealth_post.total_pro + pro_input
                    fat = myhealth_post.total_fat + fat_input
                    id = myhealth_post.id
                }

                val call2 = MyHealthService!!.putMyHealth_post(id, LoginUser.id, currentDate, kcal, car, pro, fat)
                call2.enqueue(object : Callback<JSONObject> {
                    override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                        if (!response.isSuccessful) {
                            return
                        }
                        Toast.makeText(this@MyHealthActivity, "내가 먹은 영양성분 추가", Toast.LENGTH_SHORT).show()
                        val data = response.body()

                        lateinit var myhealth_pre_list: List<Myhealth_pre>

                        val call3 = MyHealthService!!.getMyHealth_pre(LoginUser.id)
                        call3.enqueue(object : Callback<List<Myhealth_pre>> {
                            override fun onResponse(call: Call<List<Myhealth_pre>>, response: Response<List<Myhealth_pre>>) {
                                if (!response.isSuccessful) {
                                    return
                                }
                                myhealth_pre_list = response.body()!!
                                for (myhealth_pre in myhealth_pre_list) {
                                    totalCalorie =  myhealth_pre.kcal             //totalCalorie 조회로 받을지 판단
                                    //Toast.makeText(this@MyHealthActivity, "받아온 칼로리" + totalCalorie.toString(), Toast.LENGTH_SHORT).show()
                                    Total_Car = (myhealth_pre.kcal*0.5/4).toFloat()
                                    Total_Pro = (myhealth_pre.kcal*0.3/4).toFloat()
                                    Total_Fat = (myhealth_pre.kcal*0.2/9).toFloat()
                                }

                                val call4 = MyHealthService!!.getMyHealth_post(LoginUser.id, currentDate)
                                call4.enqueue(object : Callback<List<Myhealth_post>> {
                                    override fun onResponse(call: Call<List<Myhealth_post>>, response: Response<List<Myhealth_post>>) {
                                        if (!response.isSuccessful) {
                                            return
                                        }
                                        myhealth_post_list = response.body()!!
                                        for (myhealth_post in myhealth_post_list) {
                                            Kcal = myhealth_post.totalKcal
                                            Car = myhealth_post.total_car
                                            Pro = myhealth_post.total_pro
                                            Fat = myhealth_post.total_fat
                                        }
                                        addPie()
                                        //pieChart.invalidate()
                                    }

                                    override fun onFailure(call: Call<List<Myhealth_post>>, t: Throwable) {
                                        Log.d("Debug", "onFailure 실행$t")
                                    }
                                })
                            }

                            override fun onFailure(call: Call<List<Myhealth_pre>>, t: Throwable) {
                                Log.d("Debug", "onFailure 실행$t")
                            }
                        })
                    }

                    override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                        Log.d("Debug", "onFailure 실행$t")
                    }
                })
            }

            override fun onFailure(call: Call<List<Myhealth_post>>, t: Throwable) {
                Log.d("Debug", "onFailure 실행$t")
            }
        })
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_health)

        pieChart = findViewById(R.id.country_pc_piechart)

        fun myhealth_show() {           //myhealth 띄우기
            lateinit var myhealth_pre_list: List<Myhealth_pre>

            //Toast.makeText(this@MyHealthActivity, "myheath_show", Toast.LENGTH_SHORT).show()
            val call = MyHealthService!!.getMyHealth_pre(LoginUser.id)
            call.enqueue(object : Callback<List<Myhealth_pre>> {
                override fun onResponse(call: Call<List<Myhealth_pre>>, response: Response<List<Myhealth_pre>>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    myhealth_pre_list = response.body()!!
                    if(myhealth_pre_list.isEmpty()) {
                        return
                    }
                    else {
                        //editTexttotalKcal.visibility = View.INVISIBLE
                        //button25.visibility = View.INVISIBLE
                    }
                    for (myhealth_pre in myhealth_pre_list) {
                        totalCalorie =  myhealth_pre.kcal             //totalCalorie 조회로 받을지 판단
                        Total_Car = (myhealth_pre.kcal*0.5/4).toFloat()
                        Total_Pro = (myhealth_pre.kcal*0.3/4).toFloat()
                        Total_Fat = (myhealth_pre.kcal*0.2/9).toFloat()

                    }
                    lateinit var myhealth_post_list: List<Myhealth_post>
                    val currentDate = LocalDate.now(ZoneId.of("Asia/Seoul")).toString()


                    val call2 = MyHealthService!!.getMyHealth_post(LoginUser.id, currentDate)
                    call2.enqueue(object : Callback<List<Myhealth_post>> {
                        override fun onResponse(call: Call<List<Myhealth_post>>, response: Response<List<Myhealth_post>>) {
                            if (!response.isSuccessful) {
                                return
                            }
                            myhealth_post_list = response.body()!!
                            if(myhealth_post_list.isEmpty()) {
                                myhealth_post_creation()
                            }
                            else {
                                for (myhealth_post in myhealth_post_list) {
                                    Kcal = myhealth_post.totalKcal
                                    Car = myhealth_post.total_car
                                    Pro = myhealth_post.total_pro
                                    Fat = myhealth_post.total_fat
                                }
                            }
                            addPie()
                        }

                        override fun onFailure(call: Call<List<Myhealth_post>>, t: Throwable) {
                            Log.d("Debug", "onFailure 실행$t")
                        }
                    })
                }

                override fun onFailure(call: Call<List<Myhealth_pre>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }
        myhealth_show()

        val RecommendService = RetrofitClient.getClient()?.create(RecommendService::class.java)

        //걸음 수 측정 버튼
        val goToWalk = findViewById<Button>(R.id.goToWalk)
        goToWalk.setOnClickListener{
            //만보기 측정 화면으로 이동
            val intent = Intent(this, StepActivity::class.java)
            startActivity(intent)
        }

        //추천메뉴 리사이클러뷰 세팅
        recyclerView1 = findViewById(R.id.recommendrecycle1)
        recyclerView2 = findViewById(R.id.recommendrecycle2)
        recyclerView3 = findViewById(R.id.recommendrecycle3)
        recyclerView1.setHasFixedSize(true)
        recyclerView2.setHasFixedSize(true)
        recyclerView3.setHasFixedSize(true)
        recyclerView1.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerView2.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerView3.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recadapter1 = recommendAdapter(m1List)
        recadapter2 = recommendAdapter(m2List)
        recadapter3 = recommendAdapter(m3List)
        recyclerView1.adapter = recadapter1
        recyclerView2.adapter = recadapter2
        recyclerView3.adapter = recadapter3

        fun todaypreference_show() {
            val call = RecommendService!!.getTodaypreference
            call.enqueue(object : Callback<Reccomend> {
                override fun onResponse(call: Call<Reccomend>, response: Response<Reccomend>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    val todaypreference_list = response.body()
                    val list = todaypreference_list?.most_ordered_menu_list
                    if (list != null) {
                        m3List.clear()
                        for (todaypreference in list) {
                            m3List.add(menuList(todaypreference.toString(), 3, res_idToImage(menuTores_id(todaypreference))))
                        }
                        recadapter3.notifyDataSetChanged()
                    }
                }
                override fun onFailure(call: Call<Reccomend>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }
        todaypreference_show()

        fun mypreference_show() {

            val call = RecommendService!!.getMypreference(LoginUser.email)
            call.enqueue(object : Callback<Reccomend> {
                override fun onResponse(call: Call<Reccomend>, response: Response<Reccomend>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    val mypreference_list = response.body()
                    val list = mypreference_list?.most_ordered_menu_list
                    if (list != null) {
                        m2List.clear()
                        for (mypreference in list) {
                            //자주 찾은 메뉴
                            m2List.add(menuList(mypreference.toString(), 3, res_idToImage(menuTores_id(mypreference))))
                        }
                        recadapter2.notifyDataSetChanged()
                    }
                }
                override fun onFailure(call: Call<Reccomend>, t: Throwable) {
                    //Toast.makeText(this@MyHealthActivity, "kcal ", Toast.LENGTH_SHORT).show()
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }
        mypreference_show()

        fun recommendedmenu_show() {
            val call = RecommendService!!.getRecommendedmenu(LoginUser.id)
            call.enqueue(object : Callback<Reccomend2> {
                override fun onResponse(call: Call<Reccomend2>, response: Response<Reccomend2>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    val recommendedmenu_list = response.body()
                    val list = recommendedmenu_list?.recommended_menus
                    if (list != null) {
                        m1List.clear()
                        for (recommendedmenu in list) {
                            m1List.add(menuList(recommendedmenu.toString(), 1, res_idToImage(menuTores_id(recommendedmenu))))  //res_idToImage(menuTores_id(recommendedmenu)))
                        }
                        recadapter1.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<Reccomend2>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }
        recommendedmenu_show()

        returnButton.setOnClickListener{
            finish()
            //메인화면으로 돌아가기
        }

        //영양 정보 입력 버튼
        val editButton : FloatingActionButton = findViewById(R.id.infoEditButton)
        editButton.setOnClickListener(View.OnClickListener {
            if(totalCalorie == 0.0f){
                CustomDialog1.showDialog(this@MyHealthActivity, this)
            }
            else {
                CustomDialog2.showDialog(this@MyHealthActivity, this)
            }
        })
    }

    override fun onTextEntered1(text: Float) {
        totalCalorie = text.toString().toFloat()
        //Toast.makeText(this, "하루 목표 칼로리 : ${text.toString()}", Toast.LENGTH_SHORT).show()
        if(totalCalorie == 0.0f){
            ///
        }else {
            myhealth_pre_creation(totalCalorie)  //하루 목표 칼로리 저장
        }
    }
    override fun onTextEntered2(text: ArrayList<Float>) {
        //Toast.makeText(this, "칼로리 : ${text[0].toString()}, 탄수화물 : ${text[1].toString()}, 단백질 : ${text[2].toString()}, 지방 : ${text[3].toString()}", Toast.LENGTH_SHORT).show()
        myhealth_post_add(text[0].toFloat(), text[1].toFloat(), text[2].toFloat(), text[3].toFloat())  //먹은 칼로리 추가
    }

    override fun onResume() {
        super.onResume()
        val StepService = RetrofitClient.getClient()?.create(StepService::class.java)

        fun todayStep_creation() {           //오늘 걸음수 생성
            val currentDate = LocalDate.now(ZoneId.of("Asia/Seoul")).toString()

            val call = StepService!!.postStep(LoginUser.id, currentDate, 0)
            call.enqueue(object : Callback<JSONObject> {
                override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    //Toast.makeText(this@MyHealthActivity, "step 등록 완료", Toast.LENGTH_SHORT).show()
                    val data = response.body()
                }

                override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }

        fun todayStep_show() {           //오늘 걸음수 보기
            lateinit var step_list: List<Step>
            val currentDate = LocalDate.now(ZoneId.of("Asia/Seoul")).toString()

            val call = StepService!!.getStep(LoginUser.id, currentDate)
            call.enqueue(object : Callback<List<Step>> {
                override fun onResponse(call: Call<List<Step>>, response: Response<List<Step>>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    step_list = response.body()!!
                    if(step_list.isEmpty()) {
                        todayStep_creation()
                        //textViewStep.text = "0칼로리 소비"
                        return
                    }
                    else {

                        for (Tstep in step_list) {
                            var Step = Tstep.step
                            var Kcal = (Step*0.04).toInt()
                            //var content = "${Kcal}칼로리 소비"
                            //textViewStep.text = content
                            todayRecord.text = Kcal.toString() + "Kcal"
                        }
                    }
                }
                override fun onFailure(call: Call<List<Step>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }
        todayStep_show()
    }
}
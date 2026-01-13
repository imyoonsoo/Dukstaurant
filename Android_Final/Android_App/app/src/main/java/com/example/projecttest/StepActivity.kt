package com.example.projecttest

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.projecttest.retrofit.*
import kotlinx.android.synthetic.main.activity_step.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.ZoneId

class StepActivity : AppCompatActivity(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var stepCountSensor: Sensor? = null

    // 현재 걸음 수
    private var currentSteps = 0

    @RequiresApi(api = Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step)

        // 활동 퍼미션 체크
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED
        ) { requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 0) }

        // 걸음 센서 연결
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        stepCountSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        // 디바이스에 걸음 센서의 존재 여부 체크
        if (stepCountSensor == null) {
            Toast.makeText(this, "No Step Sensor", Toast.LENGTH_SHORT).show()
        }

        val StepService = RetrofitClient.getClient()?.create(StepService::class.java)

        fun todayStep_add() {          //오늘 걸음수 추가
            lateinit var step_list: List<Step>
            val currentDate = LocalDate.now(ZoneId.of("Asia/Seoul")).toString()

            val call = StepService!!.getStep(LoginUser.id, currentDate)
            call.enqueue(object : Callback<List<Step>> {
                override fun onResponse(call: Call<List<Step>>, response: Response<List<Step>>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    var step = 0; var id = 0
                    step_list = response.body()!!
                    for (Tstep in step_list) {
                        step = Tstep.step + currentSteps
                        id = Tstep.id
                    }

                    val call2 = StepService!!.putStep(id, LoginUser.id, currentDate, step)
                    call2.enqueue(object : Callback<JSONObject> {
                        override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                            if (!response.isSuccessful) {
                                return
                            }
                            //Toast.makeText(this@StepActivity, "step 추가 완료", Toast.LENGTH_SHORT).show()
                            val data = response.body()
                        }

                        override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                            Log.d("Debug", "onFailure 실행$t")
                        }
                    })
                }

                override fun onFailure(call: Call<List<Step>>, t: Throwable) {
                    Log.d("Debug", "onFailure 실행$t")
                }
            })
        }

        finishedWalking.setOnClickListener { //운동 끝 버튼
            todayStep_add()
            Handler().postDelayed({
                finish()
            }, 1000)
        }
    }

    public override fun onStart() {
        super.onStart()
        if (stepCountSensor != null) {
            // 센서 속도 설정
            sensorManager!!.registerListener(this, stepCountSensor,
                SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        // 걸음 센서 이벤트 발생시
        if (event.sensor.type == Sensor.TYPE_STEP_DETECTOR) {
            if (event.values[0] == 1.0f) {
                // 센서 이벤트가 발생할때 마다 걸음수 증가
                currentSteps++
                var Step = currentSteps
                var Kcal = (currentSteps*0.04).toInt()
                var content = "${Step}걸음 ${Kcal}칼로리"
                steps.text =  Step.toString() + " 걸음"
                calSpent.text = Kcal.toString() + " Kcal"
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
}
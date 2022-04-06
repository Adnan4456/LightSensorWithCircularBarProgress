package com.example.circularbarchangewithlightsensorvalue

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.circularbarchangewithlightsensorvalue.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , SensorEventListener {

    private lateinit var binding: ActivityMainBinding


    // Individual light and proximity sensors.

    private var mSensorLight: Sensor? = null
    //SensorManager
    private var mSensorManager: SensorManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mSensorLight =
            mSensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

    }

    override fun onResume() {
        super.onResume()
        mSensorLight?.also {
            mSensorManager!!.registerListener(this , mSensorLight,
                SensorManager.SENSOR_DELAY_NORMAL)
        }


    }

    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }

    private fun brightness(bright: Float):String{
        return when(bright.toInt())
        {
            0 ->"Pitch black"
            in 1..10 -> "Dark"
            in 11..50 ->"Grey"
            in 51..5000-> "Normal"
            in 5001.. 25000 -> "Incredibly bright"
            else -> "This  light will blind you"
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
//        Both the light and proximity sensors only report one value, in values[0]
        var currentValue = event!!.values[0]

        var sensorType = event!!.sensor.type

        when(sensorType){
            // Event came from the light sensor.
            Sensor.TYPE_LIGHT ->
            {
                binding.tvText.text =  "Sensor: $currentValue\n ${brightness(currentValue)}"
                binding.circularbar.setProgressWithAnimation(currentValue)
            }

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

}
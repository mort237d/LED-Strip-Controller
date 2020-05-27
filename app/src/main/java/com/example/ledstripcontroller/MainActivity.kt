package com.example.ledstripcontroller

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManager
import java.io.IOException

/**
 * Skeleton of an Android Things activity.
 *
 * Android Things peripheral APIs are accessible through the PeripheralManager
 * For example, the snippet below will open a GPIO pin and set it to HIGH:
 *
 * val manager = PeripheralManager.getInstance()
 * val gpio = manager.openGpio("BCM6").apply {
 *     setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * }
 * gpio.value = true
 *
 * You can find additional examples on GitHub: https://github.com/androidthings
 */
class MainActivity : Activity() {
    private val TAG = "HomeActivity"

    private lateinit var redLedSwitch:Switch
    private lateinit var greenLedSwitch:Switch
    private lateinit var blueLedSwitch:Switch
    private lateinit var redGpio:Gpio
    private lateinit var greenGpio:Gpio
    private lateinit var blueGpio:Gpio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RGB()

        //blink()
    }

    private fun RGB(){
        redLedSwitch = findViewById(R.id.redLedSwitch)
        greenLedSwitch = findViewById(R.id.greenLedSwitch)
        blueLedSwitch = findViewById(R.id.blueLedSwitch)

        val manager = PeripheralManager.getInstance()

        redGpio = manager.openGpio("BCM17")
        greenGpio = manager.openGpio("BCM18")
        blueGpio = manager.openGpio("BCM27")

        redLedSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            redGpio.value = !isChecked
        }
        greenLedSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            greenGpio.value = !isChecked
        }
        blueLedSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            blueGpio.value = !isChecked
        }
    }

    private fun blink(){
        while (true){
            redGpio.value = !redGpio.value
            Thread.sleep(500)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (redGpio != null) {
            try { redGpio.close() }
            catch (e: IOException) { Log.e(TAG,"Error on PeripheralIO API", e) }
        }
        if (greenGpio != null) {
            try { redGpio.close() }
            catch (e: IOException) { Log.e(TAG,"Error on PeripheralIO API", e) }
        }
        if (blueGpio != null) {
            try { redGpio.close() }
            catch (e: IOException) { Log.e(TAG,"Error on PeripheralIO API", e) }
        }
    }
}

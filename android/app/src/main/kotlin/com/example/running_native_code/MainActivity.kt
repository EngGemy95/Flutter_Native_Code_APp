package com.example.running_native_code

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    //First #3
    private lateinit var channel:MethodChannel;
    private val BATTERY_CHANNEL = "course.flutter.dev/battery";

    //First #4
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        //First #5
        channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger , BATTERY_CHANNEL);
        //First #6
        channel.setMethodCallHandler { call, result ->
            //First #7
            //This name must same the name of 'invokeMethod' in flutter
            if (call.method == "getBatteryLevel"){
                val arguments = call.arguments() as Map<String,String>
                val name = arguments["name"]

                val batteryLevel = getBatteryLevel()
                result.success("$name says battery level : $batteryLevel")
            }
        }
    }

    private fun getBatteryLevel():Int {
        val batteryLevel : Int
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP){
            val batteryManager = getSystemService(Context.BATTERY_SERVICE)as BatteryManager
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        }else{
            val intent =  ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            batteryLevel = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL,-1)* 100 / intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        }
        return  batteryLevel
    }

}


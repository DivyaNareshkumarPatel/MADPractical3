package com.example.mad_practical3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.AlarmClock
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val camera: Button = findViewById(R.id.camerabtn)
        val gallery: Button = findViewById(R.id.gallerybtn)
        val alarm: Button = findViewById(R.id.alarmbtn)
        val callLog : Button = findViewById(R.id.callbtn)
        val login : Button = findViewById(R.id.loginbtn)
        camera.setOnClickListener{openAppOrActivity("camera")}
        gallery.setOnClickListener{openAppOrActivity("gallery")}
        alarm.setOnClickListener{openAppOrActivity("alarm")}
        callLog.setOnClickListener{openAppOrActivity("call_log")}
        login.setOnClickListener{openAppOrActivity("login")}
        val inputNumber : EditText = findViewById(R.id.phoneInput)
        val phone : Button = findViewById(R.id.phonebtn)
        phone.setOnClickListener{
            val phoneNumber = inputNumber.text.toString().trim()
            if (phoneNumber.isNotEmpty()) {
                makeCall(phoneNumber)
            }
            else {
                Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show()
            }
        }
        val urlInput : EditText = findViewById(R.id.urlInput)
        val browse : Button = findViewById(R.id.browse)
        browse.setOnClickListener{
            val url = urlInput.text.toString().trim()

            if (url.isNotEmpty()) {
                browseUrl(url)
            }
            else {
                Toast.makeText(this, "Please enter a URL", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun browseUrl(url : String){
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No application available to open the URL", Toast.LENGTH_SHORT).show()
        }
    }
    private fun makeCall(phoneNumber : String){
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if(intent.resolveActivity(packageManager)!=null){
            startActivity(intent)
        }
        else{
            Toast.makeText(this, "Call functionality is not available", Toast.LENGTH_SHORT).show()
        }
    }
    private fun openAppOrActivity(type:String){
        val intent : Intent? = when(type){
            "camera" -> Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
            "call_log" -> Intent(Intent.ACTION_VIEW).setType("vnd.android.cursor.dir/calls")
            "alarm" -> Intent(AlarmClock.ACTION_SHOW_ALARMS)
            "gallery" -> Intent(Intent.ACTION_VIEW).setType("image/*")
            "login" -> Intent(this, LoginActivity::class.java)
            else -> null
        }
        if(intent!=null && intent.resolveActivity(packageManager) != null){
            startActivity(intent)
        }
        else{
            Toast.makeText(this, "App not available", Toast.LENGTH_SHORT).show()
        }
    }
}
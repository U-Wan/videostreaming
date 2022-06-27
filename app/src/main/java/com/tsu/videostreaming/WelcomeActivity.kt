package com.tsu.videostreaming

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.tsu.videostreaming.agora.StreamingActivity

class WelcomeActivity : AppCompatActivity() {
    lateinit var btncall:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btncall=findViewById(R.id.btncall)

        btncall.setOnClickListener{
            val intent = Intent(this, StreamingActivity::class.java)
            startActivity(intent)
        }

    }


}
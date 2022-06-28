package com.tsu.videostreaming


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tsu.videostreaming.databinding.ActivityWelcomeBinding


class WelcomeActivity : AppCompatActivity() {
    lateinit var binding:ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Joinbnt.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

    }

}
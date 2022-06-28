package com.tsu.videostreaming

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tsu.videostreaming.agora.StreamingActivity
import com.tsu.videostreaming.databinding.ActivityWelcomeBinding


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var btncall:Button
    private lateinit var tv_welcome:TextView

    lateinit var fuser: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        btncall=findViewById(R.id.btncall)
        tv_welcome=findViewById(R.id.txt_welcome)

        fuser= FirebaseAuth.getInstance().currentUser!!
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val data = sharedPreferences.getString("name", "")
        var username=data.toString()
        val email=fuser.email
        tv_welcome.text="Welcome $username\n  $email"
        Toast.makeText(this, data, Toast.LENGTH_LONG).show()

        btncall.setOnClickListener{
            val intent = Intent(this, StreamingActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.go_to_Signin -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}
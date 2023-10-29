package com.br.amber.logins

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.br.amber.logins.activities.ListLoginsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonStart = findViewById<Button>(R.id.buttonStart)

        buttonStart.setOnClickListener {
            val intent = Intent(this, ListLoginsActivity::class.java)
            startActivity(intent)
        }
    }
}
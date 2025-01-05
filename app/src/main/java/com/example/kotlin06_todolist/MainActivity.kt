package com.example.kotlin06_todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin06_todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
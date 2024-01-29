package com.example.recepcioncda.view.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.recepcioncda.R
import com.example.recepcioncda.databinding.ActivityMainBinding
import java.util.logging.Handler

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var handler : Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.animationView.setAnimation(R.raw.caranimation)
        binding.animationView.playAnimation()
        
        handler = Handler(Looper.myLooper()!!)


    }
}
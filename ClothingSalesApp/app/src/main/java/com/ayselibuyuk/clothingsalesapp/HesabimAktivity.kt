package com.ayselibuyuk.clothingsalesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ayselibuyuk.clothingsalesapp.databinding.ActivityMainBinding

class HesabimAktivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}
package com.ayselibuyuk.clothingsalesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ayselibuyuk.clothingsalesapp.databinding.ActivityAnaSayfaBinding

class AnaSayfaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnaSayfaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAnaSayfaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchView.clearFocus()

    }




}
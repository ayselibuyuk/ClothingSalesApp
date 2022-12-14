package com.ayselibuyuk.clothingsalesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayselibuyuk.clothingsalesapp.databinding.ActivitySiparislerimiGosterBinding

class SiparislerimiGoster : AppCompatActivity() {
    private lateinit var binding:ActivitySiparislerimiGosterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySiparislerimiGosterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.rcsiparislerim.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=SiparislerimAdapter(siparislerArrayList)
        }
    }
}
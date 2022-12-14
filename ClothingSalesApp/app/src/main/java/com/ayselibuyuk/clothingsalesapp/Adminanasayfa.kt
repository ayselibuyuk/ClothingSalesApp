package com.ayselibuyuk.clothingsalesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ayselibuyuk.clothingsalesapp.databinding.ActivityAdminProfilBinding
import com.ayselibuyuk.clothingsalesapp.databinding.ActivityAdminanasayfaBinding

class Adminanasayfa : AppCompatActivity() {
    private lateinit var binding: ActivityAdminanasayfaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdminanasayfaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.adminadminkullanicilarigor.setOnClickListener{
            startActivity(Intent(this,AdminKullanicilarigor::class.java))
        }
        binding.adminprofil.setOnClickListener{
            startActivity(Intent(this,AdminProfil::class.java))
        }
        binding.adminurunekle.setOnClickListener{
            startActivity(Intent(this,AdminUrunEkle::class.java))
        }
        binding.adminurunlerigor.setOnClickListener{
            startActivity(Intent(this,AdminUrunleriGor::class.java))
        }
    }
}
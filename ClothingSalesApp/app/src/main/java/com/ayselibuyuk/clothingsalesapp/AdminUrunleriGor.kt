package com.ayselibuyuk.clothingsalesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.ayselibuyuk.clothingsalesapp.databinding.ActivityAdminUrunleriGorBinding

class AdminUrunleriGor : AppCompatActivity(),UrunClickListener,UrunGuncelle {
    private lateinit var binding: ActivityAdminUrunleriGorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdminUrunleriGorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.adminurunlerigorrc.apply {
            layoutManager= GridLayoutManager(context,2)
            adapter=AdminUrunleriGorAdapter(UrunlerList ,this@AdminUrunleriGor,this@AdminUrunleriGor)
        }
    }

    override fun onClick(urun: Urunler) {
        val intent = Intent(this, UrunDetaylarActivity::class.java)
        intent.putExtra(KİYAFET_ID_EXTRA, urun.id)
        startActivity(intent)
    }

    override fun onclick2(urun: Urunler) {
        val intent = Intent(this, UrunGuncelleme::class.java)
        intent.putExtra(KİYAFET_ID_EXTRA, urun.id)
        startActivity(intent)
    }
}
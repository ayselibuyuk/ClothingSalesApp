package com.ayselibuyuk.clothingsalesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayselibuyuk.clothingsalesapp.databinding.ActivityAdminKullanicilarigorBinding
class AdminKullanicilarigor : AppCompatActivity(),Kullanicisil, KullaniciDetaylar{
    private lateinit var binding : ActivityAdminKullanicilarigorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdminKullanicilarigorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.kullanicirc.apply {
            layoutManager = LinearLayoutManager(this@AdminKullanicilarigor, LinearLayoutManager.VERTICAL,false)
            adapter = KullaniciAdapter(KullanicilarArrayList,this@AdminKullanicilarigor)
            binding.kullanicirc.adapter = adapter
            binding.kullanicirc.layoutManager=layoutManager
        }
        binding.homebutton.setOnClickListener {
            startActivity(Intent(this,Adminanasayfa::class.java))
        }
    }
    override fun onclick() {
        onDestroy()
        Toast.makeText(getApplicationContext(),"Kullanıcı Silindi", Toast.LENGTH_LONG).show()
        startActivity(Intent(this,Adminanasayfa::class.java))
    }



    override fun onclick3(user: Kullanici) {
        val intent = Intent(this, KullaniciDetay::class.java)
        intent.putExtra("user", user.id)
        startActivity(intent)
    }
}
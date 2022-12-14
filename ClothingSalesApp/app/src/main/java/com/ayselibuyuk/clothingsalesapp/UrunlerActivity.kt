package com.ayselibuyuk.clothingsalesapp


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.ayselibuyuk.clothingsalesapp.databinding.ActivityUrunlerBinding

class UrunlerActivity : AppCompatActivity(),UrunClickListener {

    private lateinit var binding: ActivityUrunlerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUrunlerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.urunlerRecyclerView.apply {
            layoutManager= GridLayoutManager(context,2)
            adapter=UrunCardAdapter(seciliurunlerlist ,this@UrunlerActivity)
        }
        binding.geri.setOnClickListener {
            val intent= Intent(this@UrunlerActivity,Anasayfa::class.java)
            startActivity(intent)
        }
        if (timeTurkey!!.dayOfYear!!.toString()=="340")
        {
            binding.aralik23.setText("Efsane 23-24 Aralık indirimlerine son 17 gün!")
        }
        if(timeTurkey!!.dayOfYear!!.toString()=="341")
        {
            binding.aralik23.setText("Efsane 23-24 Aralık indirimlerine son 16 gün!")
        }
        if(timeTurkey!!.dayOfYear!!.toString()=="342")
        {
            binding.aralik23.setText("Efsane 23-24 Aralık indirimlerine son 15 gün!")
        }
        if(timeTurkey!!.dayOfYear!!.toString()=="343")
        {
            binding.aralik23.setText("Efsane 23-24 Aralık indirimlerine son 14 gün!")
        }
        if(timeTurkey!!.dayOfYear!!.toString()=="344")
        {
            binding.aralik23.setText("Efsane 23-24 Aralık indirimlerine son 13 gün!")
        }
        if(timeTurkey!!.dayOfYear!!.toString()=="345")
        {
            binding.aralik23.setText("Efsane 23-24 Aralık indirimlerine son 12 gün!")
        }

        if(timeTurkey!!.dayOfYear!!.toString()=="346")
        {
            binding.aralik23.setText("Efsane 23-24 Aralık indirimlerine son 11 gün!")
        }
    }

    override fun onClick(urun: Urunler) {
        val intent = Intent(this, UrunDetaylarActivity::class.java)
        intent.putExtra(KİYAFET_ID_EXTRA, urun.id)
        startActivity(intent)
    }

}
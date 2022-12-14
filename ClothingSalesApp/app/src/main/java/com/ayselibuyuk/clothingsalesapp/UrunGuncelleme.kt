package com.ayselibuyuk.clothingsalesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.ayselibuyuk.clothingsalesapp.databinding.ActivityUrunGuncelleBinding
import com.google.firebase.firestore.FirebaseFirestore

var db3: FirebaseFirestore = FirebaseFirestore.getInstance()
class UrunGuncelleme : AppCompatActivity() {
    private lateinit var binding: ActivityUrunGuncelleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUrunGuncelleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val urunID = intent.getStringExtra(KİYAFET_ID_EXTRA)
        val urun = urunFromID(urunID.toString())
        if(urun != null)
        {
            binding.guncelleurunadi.setText(urun.urunAd)
            binding.guncelleurunfiyati.setText(urun.fiyat.toString())
            binding.guncelleurunkategoriid.setText(urun.kategoriid)
            binding.guncelleurunkisaaciklama.setText(urun.kisaaciklama)
            binding.guncelleurunbedeni.setText(urun.beden)
            binding.guncelleurunstokbilgisi.setText(urun.stokbilgi.toString())
        }

        binding.btnurunguncelle.setOnClickListener {
            val UrunMap = hashMapOf<String, Any>()
            UrunMap.put("beden",binding.guncelleurunbedeni.text.toString())
            UrunMap.put("fiyat",binding.guncelleurunfiyati.text.toString())
            UrunMap.put("kategoriid",binding.guncelleurunkategoriid.text.toString())
            UrunMap.put("kisaaciklama",binding.guncelleurunkisaaciklama.text.toString())
            UrunMap.put("stokbilgi",binding.guncelleurunstokbilgisi.text.toString())
            UrunMap.put("urunAd",binding.guncelleurunadi.text.toString())

            Handler().postDelayed(
                {

                    db3.collection("Urunler").document(urun!!.id).update(UrunMap).
                    addOnCompleteListener{
                        if (it.isComplete && it.isSuccessful) {
                            //back
                            Toast.makeText(getApplicationContext(),"Ürün Güncellendi.", Toast.LENGTH_LONG).show();
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG)
                            .show()
                    }
                    startActivity(Intent(this,Adminanasayfa::class.java))
                },
                4000
            )
        }

    }

    private fun urunFromID(yemekID: String): Urunler?
    {
        for(urun in UrunlerList)
        {
            if(urun.id == yemekID)
                return urun
        }
        return null
    }


}
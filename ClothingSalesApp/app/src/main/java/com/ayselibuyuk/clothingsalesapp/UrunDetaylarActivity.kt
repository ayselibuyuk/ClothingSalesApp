package com.ayselibuyuk.clothingsalesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ayselibuyuk.clothingsalesapp.databinding.ActivityKategoriDetaylarBinding
import com.squareup.picasso.Picasso


class UrunDetaylarActivity : AppCompatActivity() {

    private lateinit var binding:ActivityKategoriDetaylarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityKategoriDetaylarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val urunID=intent.getStringExtra(KİYAFET_ID_EXTRA)
        val urun=UrunFromID(urunID.toString())
        if (urun!=null)
        {
            Picasso.get().load(urun.resim).into(binding.cover);
            binding.title.text=urun.urunAd
            binding.eskiFiyat.text=(urun.fiyat + 100).toString() + " TL"
            binding.yeniFiyat.text=urun.fiyat.toString()+" TL"
            binding.aciklama.text=urun.kisaaciklama
        }
        val items= listOf("32","34","36","38","40","42","44")
        val adapter=ArrayAdapter(this,R.layout.list_item_dropdown,items)
        binding.dropdownField.setAdapter(adapter)
        binding.sepeteekle.setOnClickListener {
            if (SepetimList.contains(urun)==true)
            {
                Toast.makeText(getApplicationContext(),urun!!.urunAd+" Zaten Sepetinizde Bulunuyor.",Toast.LENGTH_LONG).show();
            }
            else{
                if(binding.dropdownField.text==null)
                {
                    Toast.makeText(getApplicationContext(),"Lütfen Beden Seçiniz!",Toast.LENGTH_LONG).show();
                }
                else{
                    urun!!.beden=binding.dropdownField.text.toString()
                    SepetimList.add(urun!!)
                    Toast.makeText(getApplicationContext(),urun.urunAd+" Sepete Eklendi.",Toast.LENGTH_LONG).show();
                }
            }
        }
      /*  binding.geri.setOnClickListener {
            val intent=Intent(this,UrunlerActivity::class.java)
            startActivity(intent)
        }*/

    }

    private fun UrunFromID(urunId: String): Urunler?
    {
        for (kiyafet in UrunlerList)
        {
            if (kiyafet.id==urunId)
                return kiyafet
        }
        return null
    }

    private fun toastMesaji(str:String)
    {
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show()
    }
}
package com.ayselibuyuk.clothingsalesapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.ayselibuyuk.clothingsalesapp.databinding.ActivityKullaniciDetayBinding
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class KullaniciDetay : AppCompatActivity() {
    private lateinit var binding:ActivityKullaniciDetayBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityKullaniciDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val KullaniciFromID = intent.getStringExtra("user")
        val user = KullaniciFromID(KullaniciFromID.toString())
        if(user != null)
        {
            Picasso.get().load(user.ImageUrl).into(binding.imageViewProfil)
            binding.editTextMail.setText(user.email)
            binding.editTextAd.setText(user.isim)
            binding.editTextSifre.setText(user.sifre)
        }
        binding.buttonKullanYSil.setOnClickListener{
            removedatabase(user!!.id)
        }
        binding.buttonGuncelle.setOnClickListener {
            val Kullanicimap = hashMapOf<String, Any>()
            Kullanicimap.put("email",binding.editTextMail.text.toString())
            Kullanicimap.put("sifre",binding.editTextSifre.text.toString())
            Kullanicimap.put("isim",binding.editTextAd.text.toString())
            Handler().postDelayed(
                {
                    db5.collection("Kullanıcılar").document(user!!.id).update(Kullanicimap).
                    addOnCompleteListener{
                        if (it.isComplete && it.isSuccessful) {
                            //back
                            finish()
                            Toast.makeText(getApplicationContext(),"Kullanıcı Güncellendi.", Toast.LENGTH_LONG).show();
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG)
                            .show()
                    }
                    startActivity(Intent(this, Adminanasayfa::class.java))
                },
                8000
            )
        }
    }
    private fun KullaniciFromID(Kullanıcıid: String): Kullanici?
    {
        for(user in KullanicilarArrayList)
        {
            if(user.id == Kullanıcıid)
                return user
        }
        return null
    }
    private fun removedatabase(index:String)
    {
        db5.collection("Kullanıcılar").document(index)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(getApplicationContext(),"Kullanıcı Silindi.", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, Adminanasayfa::class.java))
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }
    }
}
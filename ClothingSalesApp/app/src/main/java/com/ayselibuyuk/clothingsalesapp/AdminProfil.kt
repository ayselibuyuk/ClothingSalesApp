package com.ayselibuyuk.clothingsalesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.test.core.app.ApplicationProvider
import com.ayselibuyuk.clothingsalesapp.databinding.ActivityAdminProfilBinding
import com.ayselibuyuk.clothingsalesapp.databinding.ActivityUrunlerBinding
import com.google.firebase.firestore.FirebaseFirestore



class AdminProfil : AppCompatActivity() {
    private  var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var binding: ActivityAdminProfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdminProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.Adminisim.setText(adminArrayList[0].isim)
        binding.Adminemail.setText(adminArrayList[0].email)
        binding.Adminsifre.setText(adminArrayList[0].sifre)

        binding.buttonGuncelle.setOnClickListener {
            kullaniciupdate()
        }
    }
    private fun kullaniciupdate() {
        val adminMap = hashMapOf<String, Any>()
        adminMap.put("email",binding.Adminemail.text.toString())
        adminMap.put("sifre",binding.Adminsifre.text.toString())
        adminMap.put("isim",binding.Adminisim.text.toString())
        Handler().postDelayed(
            {
                db.collection("Admin").document(adminArrayList[0].id).update(adminMap).
                addOnCompleteListener{
                    if (it.isComplete && it.isSuccessful) {
                        //back
                        Toast.makeText(
                            ApplicationProvider.getApplicationContext(),"Bilgilerin Güncellendi.",
                            Toast.LENGTH_LONG).show();

                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(ApplicationProvider.getApplicationContext(), exception.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }
                // startActivity(Intent(this,NewActivity::class.java))
            },


            8000


        )



    }
}
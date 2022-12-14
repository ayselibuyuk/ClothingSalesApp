package com.ayselibuyuk.clothingsalesapp

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

val siparislerArrayList: ArrayList<Siparislerim> = ArrayList()
class SplasScreen : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splas_screen)
        auth = Firebase.auth
        Handler().postDelayed(
            {
                getSiparislerim()
                if (auth.currentUser!= null)
                {
                    for (user in KullanicilarArrayList)
                    {
                        if (user.email==auth.currentUser!!.email.toString())
                        {
                            suankikullanicilist.add(user)
                        }
                    }
                    if (auth.currentUser!!.email.toString()=="ayse@gmail.com")
                    {
                        val intent = Intent(applicationContext, Adminanasayfa::class.java)
                        startActivity(intent)
                    }
                    else{
                        startActivity(Intent(this,MainActivity()::class.java))
                    }
                }
                else{
                    val builder1 = AlertDialog.Builder(this)
                    builder1.setMessage("Hesabınız Bulunamadı..")
                    builder1.setCancelable(true)
                    builder1.setNegativeButton(
                        "Kapat"
                    ) { dialog, id ->
                        dialog.cancel()
                        startActivity(Intent(this,girisyap::class.java))}
                    val alert11 = builder1.create()
                    alert11.show()
                }
            },
            5000
        )
    }

    fun getSiparislerim() {
        db3.collection("Siparisler").
                whereEqualTo("kimeait",auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    siparislerArrayList.add(
                        Siparislerim(
                            document.getId().toString(),
                            document.getString("resim").toString(),
                            document.getLong("fiyat")!!.toInt(),
                            document.getString("urunAd").toString(),
                            document.getString("kisaaciklamasi").toString(),
                            document.getString("kategoriid").toString(),
                            document.getString("beden").toString(),
                            document.getLong("stokbilgi")!!.toInt(),
                            document.getString("kimeait").toString()
                    )
                    )
                }

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }


    }




}
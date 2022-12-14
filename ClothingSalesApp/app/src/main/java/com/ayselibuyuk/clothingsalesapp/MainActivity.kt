package com.ayselibuyuk.clothingsalesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ayselibuyuk.clothingsalesapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private  var auth : FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(Anasayfa(supportFragmentManager))

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.anasayfa ->replaceFragment(Anasayfa(supportFragmentManager))
                R.id.favorilerim ->replaceFragment(Favorilerim(supportFragmentManager))
                R.id.sepetim ->replaceFragment(Sepetim(supportFragmentManager))
                R.id.ayarlar ->replaceFragment(Ayarlar(supportFragmentManager))
                R.id.cikisyap ->Cikisyap()

                else -> {
                }
            }
            true
        }
    }

     fun replaceFragment(fragment: Fragment){

        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

    fun Cikisyap()
    {
        Firebase.auth.updateCurrentUser(auth.currentUser!!)
        Firebase.auth.signOut()
        if(auth.currentUser==null)
        {
            startActivity(Intent(this,girisyap::class.java))
        }

    }
}
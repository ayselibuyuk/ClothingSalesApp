package com.ayselibuyuk.clothingsalesapp

import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.ayselibuyuk.clothingsalesapp.databinding.FragmentAyarlarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso


class Ayarlar(fragmentManager: FragmentManager) : Fragment(R.layout.fragment_ayarlar) {
    private var _binding: FragmentAyarlarBinding? = null
    private val binding get() = _binding!!
    private  var auth : FirebaseAuth = FirebaseAuth.getInstance()
    val db= FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAyarlarBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val builder1 = AlertDialog.Builder(context)
        builder1.setMessage("Hesabını Silmek istediğine emin misin?.")
        builder1.setCancelable(true)

        if (suankikullanicilist.isNotEmpty())
        {
            Picasso.get().load(suankikullanicilist[0].ImageUrl).into(binding.imageViewProfil)
            binding.profilimkullaniciemail.setText(auth.currentUser!!.email.toString())
            binding.profilimkullaniciIfre.setText(suankikullanicilist[0]!!.sifre)
            binding.profilimkullaniciisim.setText(suankikullanicilist[0].isim)
        }
        else
        {
            Picasso.get().load(yenikikullanicilist[0].ImageUrl).into(binding.imageViewProfil)
            binding.profilimkullaniciemail.setText(auth.currentUser!!.email.toString())
            binding.profilimkullaniciIfre.setText(yenikikullanicilist[0].sifre)
            binding.profilimkullaniciisim.setText(yenikikullanicilist[0].isim)
        }
        binding.buttonGuncelle.setOnClickListener {
            kullaniciupdate()
            replaceFragment(Anasayfa(fragmentManager))
        }
        binding.siparislerim.setOnClickListener {
            val intent= Intent(context,SiparislerimiGoster::class.java)
            startActivity(intent)
        }
        binding.buttonhesabimisil.setOnClickListener {
            builder1.setPositiveButton(
                "Evet"
            ) { dialog, id ->
                auth.currentUser!!.delete().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User account deleted.")
                        if (auth.currentUser==null)
                        {
                            replaceFragment(Anasayfa(fragmentManager))
                        }
                    }
                }
                 removedatabase( suankikullanicilist[0].id)
            }
            builder1.setNegativeButton(
                "İptal"
            ) { dialog, id -> dialog.cancel() }

            val alert11 = builder1.create()
            alert11.show()
        }
    }
    fun removedatabase(index:String) //hesabımı sil
    {
        db5.collection("Kullanıcılar").document(index)
            .delete()
            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully deleted!")

                auth.currentUser?.delete()
                activity?.let{
                    onDestroy()
                    val intent = Intent (it, girisyap::class.java)
                    it.startActivity(intent)
                }
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }
    }

    fun replaceFragment(fragment: Fragment){

        val fragmentManager=fragmentManager
        val fragmentTransaction=fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    private fun kullaniciupdate() {
        val adminMap = hashMapOf<String, Any>()
        adminMap.put("email",binding.profilimkullaniciemail.text.toString())
        adminMap.put("sifre",binding.profilimkullaniciIfre.text.toString())
        adminMap.put("isim",binding.profilimkullaniciisim.text.toString())

        Handler().postDelayed(
            {
                db.collection("Kullanıcılar").document(suankikullanicilist[0].id).update(adminMap).
                addOnCompleteListener{
                    if (it.isComplete && it.isSuccessful) {
                        //back
                        Toast.makeText(getApplicationContext(),"Bilgilerin Güncellendi.",Toast.LENGTH_LONG).show();

                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(getApplicationContext() , exception.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }
               // startActivity(Intent(this,NewActivity::class.java))
            },
            8000
        )
    }
}
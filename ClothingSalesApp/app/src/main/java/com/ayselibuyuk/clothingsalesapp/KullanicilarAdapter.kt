package com.ayselibuyuk.clothingsalesapp

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayselibuyuk.clothingsalesapp.databinding.KullanicilarigorBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

private  var db4 : FirebaseFirestore = FirebaseFirestore.getInstance()
class KullaniciAdapter
    (private val kullanicilar: ArrayList<Kullanici>,private val onclick: KullaniciDetaylar): RecyclerView.Adapter<KullaniciAdapter.KullaniciHolder>()
{
    class KullaniciHolder(val binding: KullanicilarigorBinding, private val onclick: KullaniciDetaylar): RecyclerView.ViewHolder(binding.root)
    {
        val kullaniciisim: TextView = binding.kullaniciisim
        val kullaniciemail: TextView = binding.kullaniciemail
        val kullanicisifre: TextView = binding.kullanicisifre
        val kullaniciImageView: ImageView = binding.kullanicifoto
        fun bindItems(item: Kullanici) {
            kullanicisifre.setText(item.sifre)
            kullaniciemail.setText(item.email)
            kullaniciisim.setText(item.isim)
            Picasso.get().load(item.ImageUrl).into(kullaniciImageView)
            binding.layoutkullanici.setOnClickListener {
                onclick.onclick3(item)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KullaniciHolder {
        val binding =KullanicilarigorBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return KullaniciAdapter.KullaniciHolder(binding,onclick)
    }
    override fun onBindViewHolder(holder: KullaniciHolder, position: Int) {
        holder.bindItems(kullanicilar[position])
    }
    override fun getItemCount(): Int {
        return  kullanicilar.size
    }
}

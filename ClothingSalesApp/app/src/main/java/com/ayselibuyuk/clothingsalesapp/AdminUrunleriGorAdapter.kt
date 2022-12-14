package com.ayselibuyuk.clothingsalesapp

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayselibuyuk.clothingsalesapp.databinding.ListItemAdminUrunlerigorBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
var db5 : FirebaseFirestore = FirebaseFirestore.getInstance()
class AdminUrunleriGorAdapter(
    private val urunler: ArrayList<Urunler>?,
    private val clickListener: UrunClickListener,
    private val clickListener2: UrunGuncelle

) : RecyclerView.Adapter<AdminUrunleriGorAdapter.UrunCardViewHolder>()

{
    class UrunCardViewHolder(
        private val cardCellBinding: ListItemAdminUrunlerigorBinding,
        private val clickListener: UrunClickListener,
        private val clickListener2: UrunGuncelle
    ) : RecyclerView.ViewHolder(cardCellBinding.root)
    {
        fun bindUrun(urun:Urunler)
        {
            cardCellBinding.tvEskiFiyat.text = (urun.fiyat + 145).toString() + " TL"
            Picasso.get().load(urun.resim).into(cardCellBinding.cover);
            cardCellBinding.tvBaslK.text = urun.urunAd
            cardCellBinding.tvKisaAciklama.text = urun.kisaaciklama
            cardCellBinding.tvFiyat.text = urun.fiyat.toString() + " TL"
            cardCellBinding.lvUrun.setOnClickListener {
                clickListener.onClick(urun)
            }
            cardCellBinding.Adminurunsilbutton.setOnClickListener {
                removedatabase(urun.id)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrunCardViewHolder
    {
        val from= LayoutInflater.from(parent.context)
        val binding=  ListItemAdminUrunlerigorBinding.inflate(from,parent,false)
        return UrunCardViewHolder(binding,clickListener,clickListener2)
    }

    override fun onBindViewHolder(holder: UrunCardViewHolder, position: Int)
    {
        holder.bindUrun(urunler!![position])
    }

    override fun getItemCount(): Int =urunler!!.size
}
private fun removedatabase(index:String)
{
    db5.collection("Urunler").document(index)
        .delete()
        .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully deleted!") }
        .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }
}
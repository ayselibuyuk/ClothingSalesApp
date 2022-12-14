package com.ayselibuyuk.clothingsalesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayselibuyuk.clothingsalesapp.databinding.ListItemFavorilerBinding
import com.ayselibuyuk.clothingsalesapp.databinding.ListItemUrunlerBinding
import com.squareup.picasso.Picasso

class FavorilerCardAdapter(
    private val favoriler: ArrayList<Urunler>?,
    private val clickListener: UrunClickListener
) : RecyclerView.Adapter<FavorilerCardAdapter.FavorilerCardViewHolder>()

{

    class FavorilerCardViewHolder(
        private val cardCellBinding: ListItemFavorilerBinding,
        private val clickListener: UrunClickListener
    ) : RecyclerView.ViewHolder(cardCellBinding.root) {
        fun bindUrun(urun:Urunler)
        {

            Picasso.get().load(urun.resim).into(cardCellBinding.cover);

            cardCellBinding.tvBaslK.text=urun.urunAd
            cardCellBinding.tvKisaAciklama.text=urun.kisaaciklama
               cardCellBinding.tvEskiFiyat.text= "Eski Fiyatı:"+(urun.fiyat+145).toString()+" TL"
            cardCellBinding.tvYeniFiyat.text= "Yeni Fiyatı:"+urun.fiyat.toString()+" TL"
            cardCellBinding.lvUrun.setOnClickListener {
                clickListener.onClick(urun)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavorilerCardViewHolder
    {
        val from= LayoutInflater.from(parent.context)
        val binding= ListItemFavorilerBinding.inflate(from,parent,false)
        return FavorilerCardViewHolder(binding,clickListener)
    }

    override fun onBindViewHolder(holder: FavorilerCardViewHolder, position: Int)
    {
        holder.bindUrun(favoriler!![position])
    }

    override fun getItemCount(): Int =favoriler!!.size
}
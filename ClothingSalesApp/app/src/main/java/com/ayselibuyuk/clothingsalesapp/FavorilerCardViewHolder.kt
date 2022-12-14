package com.ayselibuyuk.clothingsalesapp

import androidx.recyclerview.widget.RecyclerView
import com.ayselibuyuk.clothingsalesapp.databinding.ListItemFavorilerBinding
import com.ayselibuyuk.clothingsalesapp.databinding.ListItemUrunlerBinding

class FavorilerCardViewHolder(
    private val cardCellBinding: ListItemFavorilerBinding,
    private val clickListener: UrunClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root) {
    fun bindUrun(urun:Urunler)
    {

      //  cardCellBinding.cover.setImageResource(urun.resim)
        cardCellBinding.tvBaslK.text=urun.urunAd
        cardCellBinding.tvKisaAciklama.text=urun.kisaaciklama
     //   cardCellBinding.tvEskiFiyat.text= urun.eskiFiyat.toString()
        cardCellBinding.tvYeniFiyat.text= urun.fiyat.toString()
        cardCellBinding.lvUrun.setOnClickListener {
            clickListener.onClick(urun)
        }
    }
}
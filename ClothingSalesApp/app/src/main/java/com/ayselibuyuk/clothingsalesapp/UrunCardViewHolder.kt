package com.ayselibuyuk.clothingsalesapp

import androidx.recyclerview.widget.RecyclerView
import com.ayselibuyuk.clothingsalesapp.databinding.CardCellBinding
import com.ayselibuyuk.clothingsalesapp.databinding.ListItemUrunlerBinding

class UrunCardViewHolder(
    private val cardCellBinding: ListItemUrunlerBinding,
    private val clickListener: UrunClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root)
{
    fun bindUrun(urun:Urunler)
    {

      //  cardCellBinding.cover.setImageResource(urun.resim)
        cardCellBinding.tvBaslK.text=urun.urunAd
        cardCellBinding.tvKisaAciklama.text=urun.kisaaciklama
        cardCellBinding.tvFiyat.text= urun.fiyat.toString()
        cardCellBinding.lvUrun.setOnClickListener {
            clickListener.onClick(urun)
        }
    }
}
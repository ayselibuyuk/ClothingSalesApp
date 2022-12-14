package com.ayselibuyuk.clothingsalesapp

import androidx.recyclerview.widget.RecyclerView
import com.ayselibuyuk.clothingsalesapp.databinding.CardCellBinding
import com.squareup.picasso.Picasso

class CardViewHolder(
    private val cardCellBinding: CardCellBinding,
    private val clickListener: KiyafetClickListener
    ) : RecyclerView.ViewHolder(cardCellBinding.root)
{
    fun bindKiyafet(kiyafet: Kategoriler)
    {
        Picasso.get().load(kiyafet.imageUrl).into(cardCellBinding.cover);
        cardCellBinding.title.text=kiyafet.isim

        cardCellBinding.cardView.setOnClickListener {
            clickListener.onClick(kiyafet)
        }
    }
}
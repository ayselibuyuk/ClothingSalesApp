package com.ayselibuyuk.clothingsalesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayselibuyuk.clothingsalesapp.databinding.CardCellBinding
import com.squareup.picasso.Picasso


class CardAdapter(

    private val kiyafetler: ArrayList<Kategoriler>?,
    private val clickListener: KiyafetClickListener
    ) :RecyclerView.Adapter<CardAdapter.CardViewHolder>()
{

    class CardViewHolder(
        private val cardCellBinding: CardCellBinding,
        private val clickListener: KiyafetClickListener
    ) : RecyclerView.ViewHolder(cardCellBinding.root)
    {
        fun bindKiyafet(kiyafet: Kategoriler)
        {
            Picasso.get().load(kiyafet.imageUrl).into(cardCellBinding.cover)
            cardCellBinding.title.text=kiyafet.isim

            cardCellBinding.cardView.setOnClickListener {
                clickListener.onClick(kiyafet)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder
    {
        val from=LayoutInflater.from(parent.context)
        val binding=CardCellBinding.inflate(from,parent,false)
        return CardViewHolder(binding,clickListener)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int)
    {
      //  val cardCellBinding: CardCellBinding

        holder.bindKiyafet(kiyafetler!![position])

    }

    override fun getItemCount(): Int =kiyafetler!!.size
}
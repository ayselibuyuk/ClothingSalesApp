package com.ayselibuyuk.clothingsalesapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ayselibuyuk.clothingsalesapp.databinding.CardCellBinding
import com.ayselibuyuk.clothingsalesapp.databinding.ListItemUrunlerBinding
import com.squareup.picasso.Picasso

var sayac:Int=0
class UrunCardAdapter(
    private val urunler: ArrayList<Urunler>?,
    private val clickListener: UrunClickListener

) : RecyclerView.Adapter<UrunCardAdapter.UrunCardViewHolder>()
{
    class UrunCardViewHolder(
        private val cardCellBinding: ListItemUrunlerBinding,
        private val clickListener: UrunClickListener
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
                if (FavorilerList.contains(urun))
                {
                    cardCellBinding.favoriekle.setImageResource(R.drawable.ic_favoriteicidolu)
                }
                cardCellBinding.favoriekle.setOnClickListener {
                    if (FavorilerList.contains(urun)==false) {
                        FavorilerList.add(urun)

                        cardCellBinding.favoriekle.setImageResource(R.drawable.ic_favoriteicidolu)

                    } else {
                        FavorilerList.remove(urun)

                        cardCellBinding.favoriekle.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    }
                }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrunCardViewHolder
    {
        val from= LayoutInflater.from(parent.context)
        val binding= ListItemUrunlerBinding.inflate(from,parent,false)
        return UrunCardViewHolder(binding,clickListener)
    }

    override fun onBindViewHolder(holder: UrunCardViewHolder, position: Int)
    {
        holder.bindUrun(urunler!![position])
    }

    override fun getItemCount(): Int =urunler!!.size
}
package com.ayselibuyuk.clothingsalesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayselibuyuk.clothingsalesapp.databinding.ListItemSepetimBinding


import com.squareup.picasso.Picasso


var toplam:Int=0
class SepetimCardAdapter(
    private val sepetim: ArrayList<Urunler>?,
    private val clickListener: UrunClickListener,
    private  val sepetclik:SepetimClickListener
) : RecyclerView.Adapter<SepetimCardAdapter.SepetimCardViewHolder>()
{
    class SepetimCardViewHolder(

        private val cardCellBinding: ListItemSepetimBinding,
        private val clickListener: UrunClickListener,
        private  val sepetclik:SepetimClickListener

    ) : RecyclerView.ViewHolder(cardCellBinding.root) {
        fun bindUrun(urun:Urunler)
        {
            cardCellBinding.sepettencikart.visibility= View.VISIBLE
            Picasso.get().load(urun.resim).into(cardCellBinding.cover);
            cardCellBinding.tvbeden.setText("Beden: "+urun.beden)
            cardCellBinding.tvBaslK.text=urun.urunAd
            cardCellBinding.tvKisaAciklama.text=urun.kisaaciklama
            cardCellBinding.tvEskiFiyat.text= "Eski Fiyatı:"+(urun.fiyat+145).toString()+" TL"
            cardCellBinding.tvYeniFiyat.text= "Yeni Fiyatı:"+urun.fiyat.toString()+" TL"
            cardCellBinding.lvUrun.setOnClickListener {
                clickListener.onClick(urun)
            }
            cardCellBinding.sepettencikart.setOnClickListener {
                SepetimList.remove(urun)
                sepetclik.onClick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SepetimCardViewHolder
    {
        val from= LayoutInflater.from(parent.context)
        val binding= ListItemSepetimBinding.inflate(from,parent,false)
        return SepetimCardViewHolder(binding,clickListener,sepetclik)
    }

    override fun onBindViewHolder(holder: SepetimCardViewHolder, position: Int)
    {
        holder.bindUrun(sepetim!![position])
    }
    override fun getItemCount(): Int =sepetim!!.size
}
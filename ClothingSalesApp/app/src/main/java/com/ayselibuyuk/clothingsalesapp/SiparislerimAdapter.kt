package com.ayselibuyuk.clothingsalesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayselibuyuk.clothingsalesapp.databinding.ListItemSepetimBinding
import com.squareup.picasso.Picasso

class SiparislerimAdapter( private val siparislerim: ArrayList<Siparislerim>?): RecyclerView.Adapter<SiparislerimAdapter.SiparislerimCardViewHolder>()
{
    class SiparislerimCardViewHolder(
        private val cardCellBinding: ListItemSepetimBinding,
    ) : RecyclerView.ViewHolder(cardCellBinding.root) {
        fun bindUrun(urun:Siparislerim)
        {
            cardCellBinding.sepettencikart.visibility= View.GONE
            Picasso.get().load(urun.resim).into(cardCellBinding.cover);
            cardCellBinding.tvbeden.setText("Beden: "+urun.beden)
            cardCellBinding.tvBaslK.text=urun.urunAd
            cardCellBinding.tvKisaAciklama.text=urun.kisaaciklama
            cardCellBinding.tvEskiFiyat.text= "Eski Fiyatı:"+(urun.fiyat+145).toString()+" TL"
            cardCellBinding.tvYeniFiyat.text= "Yeni Fiyatı:"+urun.fiyat.toString()+" TL"
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SiparislerimAdapter.SiparislerimCardViewHolder {
        val from= LayoutInflater.from(parent.context)
        val binding= ListItemSepetimBinding.inflate(from,parent,false)
        return SiparislerimAdapter.SiparislerimCardViewHolder(binding)

    }

    override fun onBindViewHolder(holder: SiparislerimAdapter.SiparislerimCardViewHolder, position: Int) {
        holder.bindUrun(siparislerim!![position])
    }

    override fun getItemCount(): Int {
       return siparislerim!!.size
    }
}
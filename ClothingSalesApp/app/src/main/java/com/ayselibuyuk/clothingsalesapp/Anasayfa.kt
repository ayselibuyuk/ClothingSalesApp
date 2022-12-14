package com.ayselibuyuk.clothingsalesapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.ayselibuyuk.clothingsalesapp.databinding.FragmentAnasayfaBinding
import com.google.firebase.firestore.FirebaseFirestore

val seciliurunlerlist=ArrayList<Urunler>()
val aranankategorilist=ArrayList<Kategoriler>()
class Anasayfa(fragmentManager: FragmentManager?) : Fragment(R.layout.fragment_anasayfa),KiyafetClickListener {
    val db=FirebaseFirestore.getInstance().collection("Kategoriler")
    private var _binding: FragmentAnasayfaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAnasayfaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (timeTurkey!!.dayOfWeek!!.toString()=="2")
        {
            binding.indirimapi.setText("Çarşamba İndirimine son 1 gün! Fırsatları kaçırma!")
        }
        if(timeTurkey!!.dayOfWeek!!.toString()=="1")
        {
            binding.indirimapi.setText("Çarşamba İndirimine son 2 gün! Fırsatları kaçırma!")
        }
        if(timeTurkey!!.dayOfWeek!!.toString()=="3")
        {
            binding.indirimapi.setText("Çarşamba İndirimine Yakalandın! Fırsatları kaçırma!")
        }
        if(timeTurkey!!.dayOfWeek!!.toString()=="4")
        {
            binding.indirimapi.setText("Çarşamba İndirimine son 6 gün! Fırsatları kaçırma!")
        }
        if(timeTurkey!!.dayOfWeek!!.toString()=="5")
        {
            binding.indirimapi.setText("Çarşamba İndirimine son 5 gün! Fırsatları kaçırma!")
        }
        if(timeTurkey!!.dayOfWeek!!.toString()=="6")
        {
            binding.indirimapi.setText("Çarşamba İndirimine son 4 gün! Fırsatları kaçırma!")
        }

        if(timeTurkey!!.dayOfWeek!!.toString()=="7")
        {
            binding.indirimapi.setText("Çarşamba İndirimine son 3 gün! Fırsatları kaçırma!")
        }

      //  binding.indirimapi.setText(timeTurkey!!.dayOfWeek.toString())

        binding.searchView.clearFocus()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                aranankategorilist.clear()
                for(kategori in kategorilerlist)
                {
                    if (newText != null) {
                        if(kategori.isim.toLowerCase().contains(newText.toLowerCase()))
                        {
                            aranankategorilist.add(kategori)
                        }
                    }
                    else{
                        binding.recyclerview.apply {
                            layoutManager= GridLayoutManager(context,3)
                            adapter=CardAdapter(kategorilerlist ,this@Anasayfa)
                        }
                    }

                }
                binding.recyclerview.apply {
                    layoutManager= GridLayoutManager(context,3)
                    adapter=CardAdapter(aranankategorilist ,this@Anasayfa)
                }
                return true
            }
        })

        val anasayfa=this
        binding.recyclerview.apply {
            layoutManager= GridLayoutManager(context,3)
            adapter=CardAdapter(kategorilerlist ,anasayfa)
        }
    }

    //Kategoriye ait ürünleri getiriyor.
     override fun onClick(kiyafet: Kategoriler)
     {
         seciliurunlerlist.clear()
         for (urun in UrunlerList)
         {
             if(kiyafet.id==urun.kategoriid)
             {
                 seciliurunlerlist.add(urun)
             }
         }
         //println("URUNLER LİST " + seciliurunlerlist.size)

        val intent= Intent(context,UrunlerActivity::class.java)
        intent.putExtra(KİYAFET_ID_EXTRA,kiyafet.id)
        startActivity(intent)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }





}



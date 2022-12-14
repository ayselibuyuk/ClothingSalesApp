package com.ayselibuyuk.clothingsalesapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayselibuyuk.clothingsalesapp.databinding.FragmentSepetimBinding
import com.google.firebase.auth.FirebaseAuth

var sepettekilerlistfiyat=ArrayList<Urunler>()
class Sepetim(fragmentManager: FragmentManager) : Fragment(R.layout.fragment_sepetim),UrunClickListener,SepetimClickListener {
    private var _binding: FragmentSepetimBinding? = null
    private val binding get() = _binding!!
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSepetimBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val anasayfa=this
        binding.rvSepetim.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=SepetimCardAdapter(SepetimList ,anasayfa,this@Sepetim)
        }

        binding.buttonAlisveriseDevamEt.setOnClickListener {
            val intent = Intent(this@Sepetim.requireContext(), Anasayfa::class.java)
            startActivity(intent)
        }

        if (SepetimList.isEmpty())
        {
            binding.layouttoplamtutar.visibility=View.GONE
            binding.imageViewSepetim.visibility=View.VISIBLE
            binding.textViewNot.visibility=View.VISIBLE
            binding.buttonAlisveriseDevamEt.visibility=View.VISIBLE
           // binding.linearLayoutSiparisOnay.visibility=View.GONE
        }else{
            toplam=0
            for(sepetteki in SepetimList)
            {
                toplam=toplam+sepetteki.fiyat
            }

            binding.toplamtutar.setText(toplam.toString())
            binding.layouttoplamtutar.visibility=View.VISIBLE
            binding.imageViewSepetim.visibility=View.GONE
            binding.textViewNot.visibility=View.GONE
            binding.buttonAlisveriseDevamEt.visibility=View.GONE
        }


        binding.btnsepetionayla.setOnClickListener {
            val builder1 = AlertDialog.Builder(context)
            builder1.setMessage("Sepeti onaylıyor musunuz?")
            builder1.setTitle("Seçtiğiniz Ürünlerin Toplamı: " + toplam.toString())
            builder1.setCancelable(true)

            builder1.setPositiveButton(
                "Onaylıyorum"
            ) { dialog, id -> Toast.makeText(context,"Siparişiniz Onaylandı.",Toast.LENGTH_LONG).show()
                firebaseyukle()
            dialog.cancel()}
            builder1.setNegativeButton(
                "Kapat"
            ) { dialog, id -> dialog.cancel() }
            val alert11 = builder1.create()
            alert11.show()
        }

        binding.buttonAlisveriseDevamEt.setOnClickListener {
            replaceFragment(Anasayfa(requireFragmentManager()))
        }
    }
    private fun firebaseyukle() {

        for(sepetteki in SepetimList)
        {
            val UrunMap = hashMapOf<String,Any>()
            UrunMap.put("resim",sepetteki.resim)
            UrunMap.put("kisaaciklamasi", sepetteki.kisaaciklama)
            UrunMap.put("beden",sepetteki.beden.toString())
            UrunMap.put("fiyat",Integer.parseInt(sepetteki.fiyat.toString()))
            UrunMap.put("kategoriid",sepetteki.kategoriid.toString())
            UrunMap.put("stokbilgi",Integer.parseInt(sepetteki.stokbilgi.toString()))
            UrunMap.put("urunAd",sepetteki.urunAd.toString())
            UrunMap.put("kimeait",auth.currentUser!!.uid.toString())
            db5.collection( "Siparisler").add(UrunMap).addOnCompleteListener{ task ->
                if (task.isComplete && task.isSuccessful) {
                    //back
                    println("oldu")
                  //  Toast.makeText(context, " Eklendi. Kategoriler Kısmından Görebilirsin",Toast.LENGTH_LONG).show()
                    replaceFragment(Anasayfa(fragmentManager))
                }
            }.addOnFailureListener{exception ->
                Toast.makeText(context,exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun replaceFragment(fragment: Fragment){

        val fragmentManager=fragmentManager
        val fragmentTransaction=fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onClick(urun: Urunler) {
        val intent = Intent(context, UrunDetaylarActivity::class.java)
        intent.putExtra(KİYAFET_ID_EXTRA, urun.id)
        startActivity(intent)
    }

    override fun onClick() {
        binding.rvSepetim.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=SepetimCardAdapter(SepetimList ,this@Sepetim,this@Sepetim)
        }
    }


}
package com.ayselibuyuk.clothingsalesapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.ayselibuyuk.clothingsalesapp.databinding.FragmentFavorilerimBinding

class Favorilerim(fragmentManager: FragmentManager) : Fragment(R.layout.fragment_favorilerim),UrunClickListener {

    private var _binding: FragmentFavorilerimBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavorilerimBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favorilerRecyclerView.apply {
            layoutManager= GridLayoutManager(context,1)
            adapter=FavorilerCardAdapter(FavorilerList ,this@Favorilerim)
        }

        if (FavorilerList.isEmpty())
        {
            binding.imageViewSepetim.visibility=View.VISIBLE
            binding.textViewNot.visibility=View.VISIBLE
            binding.buttonAlisveriseDevamEt.visibility=View.VISIBLE
        }else{
            binding.imageViewSepetim.visibility=View.GONE
            binding.textViewNot.visibility=View.GONE
            binding.buttonAlisveriseDevamEt.visibility=View.GONE
        }
        binding.buttonAlisveriseDevamEt.setOnClickListener {
            replaceFragment(Anasayfa(requireFragmentManager()))
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

     override fun onClick(urunler: Urunler) {
        val intent = Intent(context,UrunDetaylarActivity::class.java)
        intent.putExtra(KİYAFET_ID_EXTRA, urunler.id)
        startActivity(intent)
    }

}
package com.ayselibuyuk.clothingsalesapp

interface KiyafetClickListener
{
    fun onClick(kiyafet : Kategoriler)
}

interface UrunClickListener
{
    fun onClick(urun:Urunler)
}
interface FavorilerClickListener
{
    fun onClick(favorilerim: Favorilerim)
}
interface SepetimClickListener
{
    fun onClick()
}

interface Kullanicisil {
    fun onclick()
}

interface UrunGuncelle{
    fun onclick2(urun:Urunler)
}

interface KullaniciDetaylar{
    fun onclick3(user:Kullanici)
}





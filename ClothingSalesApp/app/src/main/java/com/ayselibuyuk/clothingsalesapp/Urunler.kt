package com.ayselibuyuk.clothingsalesapp

var UrunlerList= ArrayList<Urunler>()
var FavorilerList= ArrayList<Urunler>()
var SepetimList= ArrayList<Urunler>()
class Urunler(
   var id:String,
   var resim:String,
   var fiyat:Int,
   var urunAd:String,
   var kisaaciklama:String,
   var kategoriid:String,
   var beden:String,
   var stokbilgi:Int
    )

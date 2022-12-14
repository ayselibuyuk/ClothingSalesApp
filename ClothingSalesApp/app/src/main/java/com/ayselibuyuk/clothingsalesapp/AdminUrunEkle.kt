package com.ayselibuyuk.clothingsalesapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ayselibuyuk.clothingsalesapp.databinding.ActivityAdminUrunEkleBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.IOException
import java.util.*

class AdminUrunEkle : AppCompatActivity() {
    var db6 : FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var binding : ActivityAdminUrunEkleBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    var selectedPicture : Uri? = null
    var selectedBitmap : Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdminUrunEkleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerLauncher()

        binding.btnurunekle.setOnClickListener {
            uploadClicked(binding.root)

        }
        binding.adminfotosec.setOnClickListener {
            imageViewClicked(binding.root)
        }
    }

    fun imageViewClicked(view : View)  {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(view, "Galeriye girme izni gerekli", Snackbar.LENGTH_INDEFINITE).setAction("İzin ver",
                    View.OnClickListener {
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }).show()
            } else {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)

        }

    }
    fun registerLauncher() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val intentFromResult = result.data
                if (intentFromResult != null) {
                    selectedPicture = intentFromResult.data
                    try {
                        if (Build.VERSION.SDK_INT >= 28) {
                            val source = ImageDecoder.createSource(
                                this@AdminUrunEkle.contentResolver,
                                selectedPicture!!
                            )
                            selectedBitmap = ImageDecoder.decodeBitmap(source)
                            binding.adminfotosec.setImageBitmap(selectedBitmap)
                        } else {
                            selectedBitmap = MediaStore.Images.Media.getBitmap(
                                this@AdminUrunEkle.contentResolver,
                                selectedPicture
                            )
                            binding.adminfotosec.setImageBitmap(selectedBitmap)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { result ->
            if (result) {
                //permission granted
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            } else {
                //permission denied
                Toast.makeText(this@AdminUrunEkle, "Permisson needed!", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun uploadClicked (view: View) {

        //UUID -> image name
        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"

        val storage = Firebase.storage
        val reference = storage.reference
        val imagesReference = reference.child("images").child(imageName)

        if (selectedPicture != null) {
            imagesReference.putFile(selectedPicture!!).addOnSuccessListener { taskSnapshot ->

                val uploadedPictureReference = storage.reference.child("images").child(imageName)
                uploadedPictureReference.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    println(downloadUrl)

                    val UrunMap = hashMapOf<String,Any>()
                    UrunMap.put("resim",downloadUrl)
                    UrunMap.put("kisaaciklamasi", binding.adminurunkisaaciklama.text.toString())
                    UrunMap.put("beden",binding.adminurunbedeni.text.toString())
                    UrunMap.put("fiyat",Integer.parseInt(binding.adminurunfiyati.text.toString()))
                    UrunMap.put("kategoriid",binding.adminurunkategoriid.text.toString())
                    UrunMap.put("stokbilgi",Integer.parseInt(binding.adminurunstokbilgisi.text.toString()))
                    UrunMap.put("urunAd",binding.adminurunadi.text.toString())
                    //  postMap.put("sifre",binding.uploadCommentText.text.toString())

                    db6.collection( "Urunler").add(UrunMap).addOnCompleteListener{ task ->

                        if (task.isComplete && task.isSuccessful) {
                            //back
                            println("oldu")
                            Toast.makeText(applicationContext,binding.adminurunadi.text.toString()+ " eklendi. Kategoriler Kısmından Görebilirsin",Toast.LENGTH_LONG).show()
                            startActivity(Intent(this,Adminanasayfa::class.java))
                        }
                    }.addOnFailureListener{exception ->
                        Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
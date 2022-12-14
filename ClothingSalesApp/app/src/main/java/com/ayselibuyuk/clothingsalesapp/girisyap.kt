package com.ayselibuyuk.clothingsalesapp

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ayselibuyuk.clothingsalesapp.Api.TimeApi
import com.ayselibuyuk.clothingsalesapp.Api.TimeTurkey
import com.ayselibuyuk.clothingsalesapp.databinding.ActivityGirisyapBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

import java.io.IOException
import java.util.*


val yenikikullanicilist : ArrayList<Kullanici> = ArrayList()
var retrofit: Retrofit? = null //İnternetten veri çekmek için kullandığımız paket
var timeApi: TimeApi? = null
val baseurl: String ="http://worldtimeapi.org/api/timezone/"
var timeTurkeycall: Call<TimeTurkey>? = null
var timeTurkey: TimeTurkey? = null
val adminArrayList: ArrayList<Admin> = ArrayList()
val KullanicilarArrayList: ArrayList<Kullanici> = ArrayList()
val kategorilerlist = ArrayList<Kategoriler>()
val suankikullanicilist: ArrayList<Kullanici> = ArrayList()
var time :String="yok"

class girisyap : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    var selectedPicture: Uri? = null
    var selectedBitmap: Bitmap? = null
    var db3: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityGirisyapBinding
    val db = FirebaseFirestore.getInstance().collection("Kategoriler")
    val db2 = FirebaseFirestore.getInstance().collection("Urunler")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGirisyapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofitsettings()
        registerLauncher()
        getkategori()
        geturunler()
        getkullaniciFromFirestore()
        getAdminFromFirestore()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("502258300323-4jnjco2ojak7ghl65b0j729hjvd7qfdd.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        //Google ile giriş butonu
        binding.googlegiris.setOnClickListener() {
            signIn()
        }

        binding.singUp.setOnClickListener {
            binding.singUp.background = resources.getDrawable(R.drawable.switch_trcks, null)
            binding.singUp.setTextColor(resources.getColor(R.color.black, null))
            binding.logIn.background = null
            binding.singUpLayout.visibility = View.VISIBLE
            binding.logInLayout.visibility = View.GONE
            binding.logIn.setTextColor(resources.getColor(R.color.white, null))
            binding.yada.visibility = View.GONE
            binding.girisyapbutton.text = "Kayıt Ol"
            binding.googlegiris.visibility = View.GONE
            binding.girisyapbutton.visibility = View.GONE
            binding.kayitolbutton.visibility = View.VISIBLE
        }
        binding.logIn.setOnClickListener {

            binding.logIn.setTextColor(resources.getColor(R.color.white, null))
            binding.singUp.background = null
            binding.singUp.setTextColor(resources.getColor(R.color.white, null))
            binding.logIn.background = resources.getDrawable(R.drawable.switch_trcks, null)
            binding.singUpLayout.visibility = View.GONE
            binding.logInLayout.visibility = View.VISIBLE
            binding.logIn.setTextColor(resources.getColor(R.color.black, null))
            binding.girisyapbutton.text = "Giriş Yap"
            binding.yada.visibility = View.VISIBLE
            binding.googlegiris.visibility = View.VISIBLE
            binding.girisyapbutton.visibility = View.VISIBLE
            binding.kayitolbutton.visibility = View.GONE
        }
        binding.girisyapbutton.setOnClickListener {
            signInClicked(binding.root)
            if (binding.girisyapemail.text.toString()
                    .isNotEmpty() && binding.girisyapsifre.text.toString().isNotEmpty()
            ) {
                startActivity(Intent(this@girisyap, SplasScreen::class.java))
            } else {
                val builder1 = AlertDialog.Builder(this)
                builder1.setMessage("Lütfen şifre veya e-maili giriniz.")
                builder1.setCancelable(true)

                builder1.setNegativeButton(
                    "Kapat"
                ) { dialog, id -> dialog.cancel() }

                val alert11 = builder1.create()
                alert11.show()
            }

        }

        //Kayıt Ol butonu tıklanınca
        binding.kayitolbutton.setOnClickListener() {
            uploadClicked(binding.root)
            signUpClicked(binding.root)
            val intent = Intent(applicationContext, SplasScreen::class.java)
            startActivityForResult(intent, 1234)
        }
        //Fotoğraf Seç butonu
        binding.fotosec.setOnClickListener() {
            imageViewClicked(binding.root)
            binding.fotografseciniz.visibility = View.GONE
        }
    }
            //Time Api
    private fun retrofitsettings() {
        retrofit=Retrofit.Builder().baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        timeApi= retrofit?.create<TimeApi>()
        timeTurkeycall=timeApi?.getTime()
        timeTurkeycall!!.enqueue(object : Callback<TimeTurkey?> {
            override fun onResponse(call: Call<TimeTurkey?>,response: Response<TimeTurkey?>) {
                if (response.isSuccessful) {
                    timeTurkey = response.body()
                }
            }
            override fun onFailure(call: Call<TimeTurkey?>, t: Throwable) {
                println(t.toString())
            }
        })


    }

            //GOOGLE İLE GİRİŞ
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    } //Hazır kodlar

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra(EXTRA_NAME, user.displayName)
            startActivity(intent)
        }
    }
    companion object{
        const val RC_SIGN_IN = 1001
        const val EXTRA_NAME = "EXTRA_NAME"
    }
    //FIREBASE'DEN KATEGORİLERİ ÇEKME İŞLEMİ
    fun getkategori() {
        db
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    kategorilerlist.add(
                        Kategoriler(
                            document.getString("imageUrl").toString(),
                            document.getString("isim").toString(),
                            document.getString("kategoriid").toString()
                        )
                    )
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    //FIREBASE'DEN URUNLERİ ÇEKME İŞLEMİ
    fun geturunler() {
        db2
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    UrunlerList.add(
                        Urunler(
                            document.getId().toString(),
                            document.getString("resim").toString(),
                            document.getLong("fiyat")!!.toInt(),
                            document.getString("urunAd").toString(),
                            document.getString("kisaaciklama").toString(),
                            document.getString("kategoriid").toString(),
                            document.getString("beden").toString(),
                            document.getLong("stokbilgi")!!.toInt()
                        )
                    )
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    //Galeri izin işlemleri
    fun imageViewClicked(view: View) {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Give Permission",
                        View.OnClickListener {
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }).show()
            } else {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            val intentToGallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)

        }

    }

    fun uploadClicked(view: View) {

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
                    val kullaniciMap = hashMapOf<String, Any>()
                    kullaniciMap.put("ImageUrl", downloadUrl)
                    kullaniciMap.put("email", auth.currentUser!!.email.toString())
                    kullaniciMap.put("isim", binding.kayitolisim.text.toString())
                    kullaniciMap.put("sifre", binding.kayitolsifre.text.toString())
                    db3.collection("Kullanıcılar").document(auth.currentUser!!.uid.toString()).set(kullaniciMap).addOnCompleteListener { task ->

                        if (task.isComplete && task.isSuccessful) {
                            //back
                        }

                    }.addOnFailureListener { exception ->
                        Toast.makeText(
                            applicationContext,
                            exception.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    } //Seçili Fotoğrafın Storage'e yüklenmesi ve User oluşturması

    private fun registerLauncher() {
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
                                this@girisyap.contentResolver,
                                selectedPicture!!
                            )
                            selectedBitmap = ImageDecoder.decodeBitmap(source)
                            binding.fotosec.setImageBitmap(selectedBitmap)
                        } else {
                            selectedBitmap = MediaStore.Images.Media.getBitmap(
                                this@girisyap.contentResolver,
                                selectedPicture
                            )
                            binding.fotosec.setImageBitmap(selectedBitmap)
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
                val intentToGallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            } else {
                //permission denied
                Toast.makeText(this@girisyap, "Permisson needed!", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signInClicked(view: View) {
        val userEmail = binding.girisyapemail.text.toString()
        val password = binding.girisyapsifre.text.toString()

        if (userEmail.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(userEmail, password).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Welcome: ${auth.currentUser?.email.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                    // println(userEmail.toString())
                    //println(password.toString())
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG)
                    .show()
            }
        }

    } //Giriş Yap

    fun signUpClicked(view: View) {

        val userEmail = binding.kayitolemail.text.toString()
        val password = binding.kayitolsifre.text.toString()
        val isim = binding.kayitolisim.text.toString()
        if (userEmail.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(userEmail, password).addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    yenikikullanicilist.add(Kullanici("1",userEmail,password,isim,selectedPicture.toString()))
                }

            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG)
                    .show()

            }
        }

    } //Kayıt ol

    fun getkullaniciFromFirestore() {
        db3.collection("Kullanıcılar")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

                    KullanicilarArrayList.add(
                        Kullanici(
                            document.getId().toString(),
                            document.getString("email").toString(),
                            document.getString("sifre").toString(),
                            document.getString("isim").toString(),
                            document.getString("ImageUrl").toString(),
                        )
                    )
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)

            }


    } //Kullanıcıları Firebase'den getirme

    fun getAdminFromFirestore() {
        db3.collection("Admin")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    adminArrayList.add(
                        Admin(
                            document.getId().toString(),
                            document.getString("email").toString(),
                            document.getString("sifre").toString(),
                            document.getString("adsoyad").toString()
                        )
                    )
                }

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }


        } // Admin'i Firebase'den getirme



}
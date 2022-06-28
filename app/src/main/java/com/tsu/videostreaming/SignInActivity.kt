package com.tsu.videostreaming

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.tsu.videostreaming.databinding.ActivitySignInBinding


class SignInActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySignInBinding
    private lateinit var googleSignInClient:GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        //sign in with email
        binding.signinbtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, ProfileActivity2::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
        val googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("somethinghere")
             //   .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()


        binding.signInGoogleButton.setOnClickListener {
            Log.d(TAG, "onCreat:begin Google SignIn")
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)

        }
    }


    private fun checkUser() {
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser!=null){
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Result returne from launching the intent from googlesign..
        if(requestCode== RC_SIGN_IN){
            Log.d(TAG,"onActivityResult: Google SignIn intent result")
            val accountTask=GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                Log.d(TAG,"onActivityResult: try")

                val account=accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            }catch (e:Exception){
                Log.d(TAG,"onActivityResult: cath")
                Log.d(TAG,"onActivityResult: ${e.message}")
            }

        }

    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(TAG,"firebaseAuthWithGoogleAccount:begin firebase auth with google account")


        val credential=GoogleAuthProvider.getCredential(account!!.idToken,null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {authResult->

                Log.d(TAG,"firebaseAuthWithGoogleAccount:LoggedID")
                //get loggedIn user
                val firebaseUser=firebaseAuth.currentUser

                //get ufer info
                val uid=firebaseUser!!.uid
                val email=firebaseUser.email

                Log.d(TAG,"firebaseAuthWithGoogleAccount:Uid:$uid")
                Log.d(TAG,"firebaseAuthWithGoogleAccount:Uid:$uid")


                if(authResult.additionalUserInfo!!.isNewUser){
                    Log.d(TAG,"firebaseAuthWithGoogleAccount:Account Created.. \n$email")
                    Toast.makeText(this@SignInActivity,"Account created...\n$email",Toast.LENGTH_SHORT).show()

                }
                else{
                    Log.d(TAG,"firebaseAuthWithGoogleAccount:Existing user... \n$email")

                    Toast.makeText(this@SignInActivity,"Loged in    ..\n$email",Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e->
                Log.d(TAG,"firebaseAuthWithGoogleAccount:Loggin Failed due to ${e.message}")
                Toast.makeText(this@SignInActivity,"Login Failed due to ...\n${e.message}",Toast.LENGTH_SHORT).show()

            }
    }

    override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, ProfileActivity2::class.java)
            startActivity(intent)
        }
    }

    companion object {
        private const val RC_SIGN_IN=2
        private const val TAG="GOOGLE_SIGN_IN_TAG"
    }
}

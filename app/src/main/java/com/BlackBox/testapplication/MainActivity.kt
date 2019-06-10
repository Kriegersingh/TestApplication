package com.BlackBox.testapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

//import androidx.test.orchestrator.junit.BundleJUnitUtils.getResult



class MainActivity : AppCompatActivity() {

    val RC_SIGN_IN: Int =1

    private lateinit var googleSignInOptions :GoogleSignInOptions
    private lateinit var googleSignInClient :GoogleSignInClient
    private lateinit var firebaseAuth :FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        FirebaseApp.initializeApp(this)
        firebaseAuth = FirebaseAuth.getInstance()




        login.setOnClickListener {

            label1.text = email.text.toString()
            label2.text = password.text.toString()
        }

        configureGoogleSignIn()
        stepUpUi()

    }

    fun stepUpUi(){
        gmail.setOnClickListener {
            signIn()
        }
    }

    fun signIn() {
        val signInIntent :Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent ,RC_SIGN_IN)
    }

    private fun configureGoogleSignIn(){

        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(toString())
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)

    }

    override fun onActivityResult(requestCode :Int ,resultCode : Int ,data :Intent?){

        super.onActivityResult(requestCode ,resultCode, data)
        if(requestCode==RC_SIGN_IN){
            val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            hasndleResult(task)
//            try {
//                 val account =task.getResult(ApiException::class.java)
//                    firebaseAuthWithGoogle(account)
//
//                Toast.makeText(this,"Done succesfully",Toast.LENGTH_SHORT).show()
//
//            }catch (e :ApiException){
//                    Toast.makeText(this,"some error occured", Toast.LENGTH_SHORT).show()
//            }
        }
    }

//    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?){
//        val credential = GoogleAuthProvider.getCredential(acct?.idToken,null)
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
//            if(it.isSuccessful){
//                Toast.makeText(this,"completed",Toast.LENGTH_SHORT).show()
//
//            }else{
//                Toast.makeText(this,"error occured",Toast.LENGTH_SHORT).show()
//            }
//        }
//
//
//    }
    private fun hasndleResult(completedTask: Task<GoogleSignInAccount>){

        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            Toast.makeText(this,"done",Toast.LENGTH_SHORT).show()

            //updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(, "signInResult:failed code=" + e.statusCode)
            //updateUI(null)

            label1.text=e.statusCode.toString()
            Toast.makeText(this,"error",Toast.LENGTH_SHORT).show()
        }


    }
}

package com.astronout.googlesignin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.astronout.googlesignin.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions
    private val RC_SIGN_IN: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.main = viewModel

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        checkLogoutButton()

        signInGoogleBtn.setOnClickListener {
            if (isSignedIn()) {
                Toast.makeText(this, "You already Logged In!", Toast.LENGTH_SHORT).show()
            } else {
                signInGoogle()
            }
        }

    }

    private fun checkLogoutButton(){
        if (isSignedIn()){
            signOutBtn.setOnClickListener {
                mGoogleSignInClient.signOut().addOnCompleteListener{
                    Toast.makeText(this, "Logged Out!", Toast.LENGTH_SHORT).show()
                    signOutBtn.visibility = View.INVISIBLE
                }
            }
        } else {
            signOutBtn.visibility = View.INVISIBLE
        }
    }

    private fun signInGoogle(){
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)!!
            updateUI(account)
        } catch (e: ApiException) {
            d("handleRequest", e.toString())
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val userDisplayName = account.displayName
        val userEmail = account.email
        val userAvatarUrl = account.photoUrl
        val userFistName = account.givenName
        val userLastName = account.familyName
        val token = account.idToken
        val userId = account.id

        signOutBtn.visibility = View.VISIBLE
        checkLogoutButton()

        Toast.makeText(this, "You are logged in", Toast.LENGTH_SHORT).show()

        d("userProfile", "id: $userId, displayName: $userDisplayName, firstName: $userFistName, lastName: $userLastName, userAvatar: $userAvatarUrl, token: $token")
    }

    private fun isSignedIn(): Boolean {
        return GoogleSignIn.getLastSignedInAccount(this) != null
    }

}

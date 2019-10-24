package com.astronout.googlesignin

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainViewModel(application: Application): AndroidViewModel(application) {

    var logoApp: ObservableField<Int> = ObservableField()

    init {
        logoApp.set(R.drawable.logotugu)
    }
}
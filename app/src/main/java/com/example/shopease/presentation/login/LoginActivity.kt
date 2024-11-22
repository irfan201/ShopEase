package com.example.shopease.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.example.shopease.R
import com.example.shopease.databinding.ActivityLoginBinding
import com.example.shopease.domain.model.UserState
import com.example.shopease.presentation.MainActivity
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (viewModel.getLogin()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvLoginGoogle.setOnClickListener {
            initiateGoogleSignIn()
        }
    }

    private fun initiateGoogleSignIn() {
        val credentialManager = CredentialManager.create(this)
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(getString(R.string.default_web_client_id))
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                val result: GetCredentialResponse =
                    credentialManager.getCredential(this@LoginActivity, request)
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                Toast.makeText(
                    this@LoginActivity,
                    "Google Sign-In failed: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("LoginActivity", "Google Sign-In failed", e)
            }
        }
    }

    private fun handleSignIn(response: GetCredentialResponse) {
        viewModel.signInWithGoogle(response)
        observeLoginState()
            lifecycleScope.launch {
                viewModel.saveLogin(true)
            }

    }


    private fun observeLoginState() {
        lifecycleScope.launch {
            viewModel.userState.collect { value ->
                when (value) {
                    is UserState.Loading -> {
                    }

                    is UserState.Error -> {
                        Log.e("LoginActivity", value.message)
                    }

                    is UserState.Success -> {
                        Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT)
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}

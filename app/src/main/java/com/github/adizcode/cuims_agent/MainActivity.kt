package com.github.adizcode.cuims_agent

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.adizcode.cuims_agent.databinding.ActivityMainBinding

const val CUIMS_URL = "https://uims.cuchd.in/uims/"
const val USER_CREDS = "USER_CREDENTIALS"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val userCredentials = getUserCredentials()
            val webViewIntent = Intent(this, WebViewActivity::class.java).apply {
                putExtra(
                    USER_CREDS,
                    userCredentials
                )
            }
            startActivity(webViewIntent)
        }
    }

    private fun getUserCredentials(): UserCredentials {
        val uid = binding.uidTextInputEditText.text.toString()
        val pass = binding.passTextInputEditText.text.toString()
        return UserCredentials(uid, pass)
    }
}
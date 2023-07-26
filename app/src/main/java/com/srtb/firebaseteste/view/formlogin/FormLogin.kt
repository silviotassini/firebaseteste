package com.srtb.firebaseteste.view.formlogin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.srtb.firebaseteste.databinding.ActivityFormLoginBinding
import com.srtb.firebaseteste.view.formcadastro.FormCadastro
import com.srtb.firebaseteste.view.main.MainActivity

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.naotemcadastro.setOnClickListener {
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener { view->
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()
            if(email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view, "Preencha todos os campos", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint( Color.RED)
                snackbar.show()
            } else {
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener {result->
                    if (result.isSuccessful){
                        navegaTelaPrincipal()
                    }
                }.addOnFailureListener {
                    val snackbar = Snackbar.make(view, "Erro ao fazer login", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint( Color.RED)
                    snackbar.show()
                }
            }
        }
    }
    private fun navegaTelaPrincipal(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
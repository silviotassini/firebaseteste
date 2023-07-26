package com.srtb.firebaseteste.view.formcadastro

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.srtb.firebaseteste.databinding.ActivityFormCadastroBinding


class FormCadastro : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCadastro.setOnClickListener { view->
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()
            if(email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view, "Preencha todos os campos",Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint( Color.RED)
                snackbar.show()
            } else {
                auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener {
                    if(it.isSuccessful){
                        val snackbar = Snackbar.make(view, "Cadastro com sucesso!",Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint( Color.BLUE)
                        snackbar.show()
                        binding.editEmail.setText("")
                        binding.editSenha.setText("")
                    }
                }.addOnFailureListener {exception ->
                    val msgErro =  when(exception) {
                        is FirebaseAuthWeakPasswordException -> "Senha precisa ter 6 digitos ou mais"
                        is FirebaseAuthInvalidCredentialsException -> "Digite um e-mail válido!"
                        is FirebaseAuthUserCollisionException -> "Usuário já cadastrado!"
                        is FirebaseNetworkException -> "Sem conexão com Internet"
                        else -> "Erro ao cadastrar usuário!"
                    }
                    val snackbar = Snackbar.make(view, msgErro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint( Color.RED)
                    snackbar.show()

                }
            }

        }
    }
}
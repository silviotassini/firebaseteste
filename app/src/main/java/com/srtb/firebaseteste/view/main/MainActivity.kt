package com.srtb.firebaseteste.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.srtb.firebaseteste.databinding.ActivityMainBinding
import com.srtb.firebaseteste.view.formlogin.FormLogin

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                binding = ActivityMainBinding.inflate(layoutInflater)
                setContentView(binding.root)
        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val telaLogin = Intent(this, FormLogin::class.java)
            startActivity(telaLogin)
            finish()
        }

        binding.btnGravar.setOnClickListener {
            val userMap = hashMapOf(
                "nome" to "silvio2",
                "pace" to 4,
                "midname" to "Rogerio2"
            )
            db.collection("usuarios").document("silvio2")
                .set(userMap).addOnCompleteListener {
                    Log.d("db","Sucesso ao salvar usuario")
                }
        }

        binding.btnLerdados.setOnClickListener {
            db.collection("usuarios").document("silvio2")
                .addSnapshotListener { value, error ->
                    if (value != null){
                        binding.textDados.text = value.getString("nome") +
                                "  - " + value.getString("midname")+
                                "  = " + value.getLong("pace").toString()
                    }
                }
        }

        binding.btnAtualizar.setOnClickListener {
            db.collection("usuarios").document("silvio1")
                .update("sobrenome","Tassini Borges","idade",47)
                .addOnCompleteListener {
                    Log.d("db","Sucesso ao atualizar usuario")
                }
        }

        binding.btnApagar.setOnClickListener {
            db.collection("usuarios").document("silvio1")
                .delete()
                .addOnCompleteListener {
                    Log.d("db","Sucesso ao apagar usuario")
                }
        }
    }
}
package com.example.beerstopmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beerstopmobile.BDBeerStop;

public class AdicionarComandaActivity extends AppCompatActivity {
    private EditText editTextNomeComanda;
    private BDBeerStop db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_comanda);

        editTextNomeComanda = findViewById(R.id.editTextNomeComanda);
        Button btnAdicionarComanda = findViewById(R.id.button);
        Button btnAdicionarProdutoComanda = findViewById(R.id.btnAdicionarProdutoComanda);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        db = new BDBeerStop(this);

        btnAdicionarComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeComanda = editTextNomeComanda.getText().toString();
                String descricaoProduto = editTextNomeComanda.getText().toString();
                int quantidadeProduto = Integer.parseInt(editTextNomeComanda.getText().toString());
                if (!nomeComanda.isEmpty()) {
                    db.inserirComanda(nomeComanda, descricaoProduto, quantidadeProduto);
                    editTextNomeComanda.setText("");
                    // Adicione qualquer outra lógica desejada após adicionar a comanda
                }
            }
        });

        btnAdicionarProdutoComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeComanda = editTextNomeComanda.getText().toString();
                if (!nomeComanda.isEmpty()) {
                    Intent intent = new Intent(AdicionarComandaActivity.this, AdicionarProdutoComandaActivity.class);
                    intent.putExtra("nomeComanda", nomeComanda);
                    startActivity(intent);
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

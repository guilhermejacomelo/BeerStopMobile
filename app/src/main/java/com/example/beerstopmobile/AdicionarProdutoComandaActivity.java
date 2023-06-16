package com.example.beerstopmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AdicionarProdutoComandaActivity extends AppCompatActivity {
        private EditText editTextDescricaoProduto;
        private EditText editTextQuantidadeProduto;
        private BDBeerStop db;
        private String nomeComanda;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_adicionar_produto_comanda);

            editTextDescricaoProduto = findViewById(R.id.editTextDescricaoProdutoComanda);
            editTextQuantidadeProduto = findViewById(R.id.editTextQuantidadeProduto);
            Button btnAdicionarProduto = findViewById(R.id.btnAdicionarProduto);
            Button btnVoltar = findViewById(R.id.btnVoltar);

            db = new BDBeerStop(this);

            Intent intent = getIntent();
            nomeComanda = intent.getStringExtra("nomeComanda");

            btnAdicionarProduto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String descricaoProduto = editTextDescricaoProduto.getText().toString();
                    String quantidadeProduto = editTextQuantidadeProduto.getText().toString();

                    if (!descricaoProduto.isEmpty() && !quantidadeProduto.isEmpty()) {
                        int quantidade = Integer.parseInt(quantidadeProduto);
                        db.inserirProduto(descricaoProduto, quantidade);
                        editTextDescricaoProduto.setText("");
                        editTextQuantidadeProduto.setText("");

                        Intent intent = new Intent(AdicionarProdutoComandaActivity.this, AdicionarComandaFinalActivity.class);
                        intent.putExtra("nomeCliente", nomeComanda);
                        intent.putExtra("descricaoProduto", descricaoProduto);
                        intent.putExtra("quantidadeProduto", quantidade);
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

package com.example.beerstopmobile;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.beerstopmobile.AdicionarProdutoComandaActivity;

public class AdicionarComandaFinalActivity extends AppCompatActivity {
    private TextView textViewNomeCliente;
    private TextView textViewDescricaoProduto;

    private TextView textViewQuantidadeProduto;
    private BDBeerStop db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_comanda_final);

        textViewNomeCliente = findViewById(R.id.textViewNomeComanda);
        textViewDescricaoProduto = findViewById(R.id.textViewDescricaoProduto);
        textViewQuantidadeProduto = findViewById(R.id.textViewQuantidadeProduto);

        db = new BDBeerStop(this);

        Intent intent = getIntent();
        String nomeCliente = intent.getStringExtra("nomeCliente");
        String descricaoProduto = intent.getStringExtra("descricaoProduto");
        int quantidadeProduto = intent.getIntExtra("quantidadeProduto", 0);

        textViewNomeCliente.setText(nomeCliente);
        textViewDescricaoProduto.setText(descricaoProduto);
        textViewQuantidadeProduto.setText(String.valueOf(quantidadeProduto));

        Button btnConfirmar = findViewById(R.id.btnConfirmarComandaFinal);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Salvar os dados da comanda
                String nomeCliente = textViewNomeCliente.getText().toString();
                String descricaoProduto = textViewDescricaoProduto.getText().toString();

                salvarDadosComanda(nomeCliente, descricaoProduto, Integer.valueOf(quantidadeProduto));

                // Voltar para a MainActivity
                Intent intent = new Intent(AdicionarComandaFinalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Retorna à atividade anterior
            }
        });


    }

    private void salvarDadosComanda(String nomeCliente, String descricaoProduto, Integer quantidadeProduto) {
        db.inserirComanda(nomeCliente, descricaoProduto, quantidadeProduto);
    }
}

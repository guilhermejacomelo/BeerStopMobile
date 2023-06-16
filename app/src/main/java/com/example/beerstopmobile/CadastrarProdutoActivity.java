package com.example.beerstopmobile;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beerstopmobile.BDBeerStop;
import com.example.beerstopmobile.R;

public class CadastrarProdutoActivity extends AppCompatActivity {

    private EditText editTextDescricao;
    private EditText editTextQuantidade;

    private BDBeerStop databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_produto);

        editTextDescricao = findViewById(R.id.editTextDescricao);
        editTextQuantidade = findViewById(R.id.editTextQuantidade);

        databaseHelper = new BDBeerStop(getApplicationContext());

        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnCadastrarProduto = findViewById(R.id.btnCadastrarProduto);
        btnCadastrarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarProduto();
            }
        });
    }

    private void salvarProduto() {
        String descricao = editTextDescricao.getText().toString();
        String quantidade = editTextQuantidade.getText().toString();

        if (descricao.isEmpty() || quantidade.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantidadeInt;
        try {
            quantidadeInt = Integer.parseInt(quantidade);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Quantidade inválida", Toast.LENGTH_SHORT).show();
            return;
        }

        if (quantidadeInt <= 0) {
            Toast.makeText(this, "A quantidade deve ser maior que zero", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("descricao", descricao);
        values.put("quantidade", quantidadeInt);

        long produtoId = db.insert("produtos", null, values);

        if (produtoId != -1) {
            Toast.makeText(this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            limparCampos();
        } else {
            Toast.makeText(this, "Erro ao cadastrar o produto", Toast.LENGTH_SHORT).show();
        }
    }


    private void limparCampos() {
        editTextDescricao.setText("");
        editTextQuantidade.setText("");
    }
}

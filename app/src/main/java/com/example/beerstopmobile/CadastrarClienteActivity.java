package com.example.beerstopmobile;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.beerstopmobile.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beerstopmobile.R;

public class CadastrarClienteActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextCPF;
    private EditText editTextTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_cliente);

        editTextNome = findViewById(R.id.editTextNome);
        editTextCPF = findViewById(R.id.editTextCPF);
        editTextTelefone = findViewById(R.id.editTextTelefone);

        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnCadastrarCliente = findViewById(R.id.btnCadastrarCliente);
        btnCadastrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarCliente();
            }
        });
    }

    private void salvarCliente() {
        String nome = editTextNome.getText().toString();
        String cpf = editTextCPF.getText().toString();
        String telefone = editTextTelefone.getText().toString();

        if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica se o CPF já existe no banco de dados
        boolean cpfExists = checkCpfExists(cpf);
        if (cpfExists) {
            Toast.makeText(this, "CPF já cadastrado", Toast.LENGTH_SHORT).show();
            return;
        }

        BDBeerStop databaseHelper = new BDBeerStop(getApplicationContext());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("cpf", cpf);
        values.put("telefone", telefone);

        long clienteId = db.insert("clientes", null, values);

        if (clienteId != -1) {
            Toast.makeText(this, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            limparCampos();

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("mensagem", "Usuário cadastrado com sucesso!");
            startActivity(intent);
        } else {
            Toast.makeText(this, "Erro ao cadastrar o cliente", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    private void limparCampos() {
        editTextNome.setText("");
        editTextCPF.setText("");
        editTextTelefone.setText("");
    }

    private boolean checkCpfExists(String cpf) {
        BDBeerStop databaseHelper = new BDBeerStop(getApplicationContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {"cpf"};
        String selection = "cpf = ?";
        String[] selectionArgs = {cpf};

        Cursor cursor = db.query("clientes", projection, selection, selectionArgs, null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();

        return exists;
    }
}

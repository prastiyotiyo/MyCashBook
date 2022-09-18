package com.example.mycashbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity {

    Button buttonGantiPassword, buttonKembali;
    EditText oldPass, newPass;
    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        database = new DatabaseHelper(this);
        buttonGantiPassword = (Button)findViewById(R.id.btnChangePass);
        buttonKembali = (Button)findViewById(R.id.btnBackToMenu);
        oldPass = (EditText)findViewById(R.id.formPasswordLama);
        newPass = (EditText)findViewById(R.id.formPasswordBaru);

        // back to menu
        buttonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        buttonGantiPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldP = oldPass.getText().toString();
                String newP = newPass.getText().toString();

                if(oldP != "" && newP != ""){
                    Boolean checkSession = database.checkPass(oldP);
                    if(checkSession == true){
                        Boolean updateP = database.updatePassword(newP,1);
                        if(updateP == true){
                            Toast.makeText(getApplicationContext(), "Sukses Ganti Password!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Setting.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Password Lama Salah!",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Form tidak bisa kosong!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
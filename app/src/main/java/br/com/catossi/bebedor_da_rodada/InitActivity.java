package br.com.catossi.bebedor_da_rodada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import br.com.catossi.bebedor_da_rodada.handler.DatabaseHandler;
import br.com.catossi.bebedor_da_rodada.model.User;

public class InitActivity extends AppCompatActivity {

    Button btnDontHaveAccess;
    Button btnHaveAccess;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        overridePendingTransition(R.anim.trans_top_in, R.anim.trans_top_out);

        btnDontHaveAccess = findViewById(R.id.btn_dont_have_access);
        btnHaveAccess = findViewById(R.id.btn_have_access);

        btnHaveAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InitActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        btnDontHaveAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InitActivity.this, NameActivity.class);
                startActivity(i);
            }
        });

         db = new DatabaseHandler(this);
         List<User> listUser = db.getAllUsers();

         if(listUser.size() == 0) {
             db.addUser(new User("", "", ""));
         } else {
             for(User user : listUser) {

                 if(!user.getEmail().equals("")) {
                     if(!user.getNickname().equals("")) {
                         if(!user.getName().equals("")) {
                             Intent i = new Intent(InitActivity.this, MainActivity.class);
                             startActivity(i);
                             finish();
                         }
                     }
                 }
             }
         }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_bottom_in, R.anim.trans_bottom_out);
    }
}

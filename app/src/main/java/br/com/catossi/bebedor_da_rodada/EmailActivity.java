package br.com.catossi.bebedor_da_rodada;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import br.com.catossi.bebedor_da_rodada.handler.DatabaseHandler;
import br.com.catossi.bebedor_da_rodada.model.User;

public class EmailActivity extends AppCompatActivity {

    Button btnNext;
    EditText etEmail;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        btnNext = findViewById(R.id.btn_next);
        etEmail = findViewById(R.id.et_email);

        db = new DatabaseHandler(this);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etEmail.getText().toString().equals("")) {
                    List<User> listUser = db.getAllUsers();

                    if (listUser.size() > 0) {
                        for (User user : listUser) {
                            user.setEmail("" + etEmail.getText());
                            db.updateUser(user);
                        }
                    }

                    Intent i = new Intent(EmailActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    new AlertDialog.Builder(EmailActivity.this)
                            .setTitle(R.string.title_message_insert_error)
                            .setMessage(R.string.message_insert_error)
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Whatever...
                                }
                            }).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}

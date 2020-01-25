package br.com.catossi.bebedor_da_rodada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NicknameActivity extends AppCompatActivity {

    Button btnNext;
    EditText etNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        btnNext = findViewById(R.id.btn_next);
        etNickname = findViewById(R.id.et_nickname);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NicknameActivity.this, EmailActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}

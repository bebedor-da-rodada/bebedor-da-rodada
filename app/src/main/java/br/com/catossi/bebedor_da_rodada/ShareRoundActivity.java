package br.com.catossi.bebedor_da_rodada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ShareRoundActivity extends AppCompatActivity {

    TextView tvCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_round);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvCode = findViewById(R.id.tv_code);

        String roundCode;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                roundCode= null;
            } else {
                roundCode= extras.getString("ROUND_CODE");
            }
        } else {
            roundCode= (String) savedInstanceState.getSerializable("ROUND_CODE");
        }

        tvCode.setText("" + roundCode);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(ShareRoundActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}

package br.com.catossi.bebedor_da_rodada;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import br.com.catossi.bebedor_da_rodada.handler.DatabaseHandler;
import br.com.catossi.bebedor_da_rodada.model.User;
import br.com.catossi.bebedor_da_rodada.model.UserRequest;
import br.com.catossi.bebedor_da_rodada.service.APIClient;
import br.com.catossi.bebedor_da_rodada.service.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InitActivity extends AppCompatActivity {

    private Button btnDontHaveAccess;
    private Button btnHaveAccess;
    private DatabaseHandler db;

    private APIInterface apiService;
    private Call<UserRequest> callBalance;
    private ProgressDialog progress;

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
                Intent i = new Intent(InitActivity.this, EmailActivity.class);
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

                             progress = ProgressDialog.show(InitActivity.this, "Carregando", "" + R.string.waiting, true);

                             String email = user.getEmail();

                             apiService = APIClient.getService().create(APIInterface.class);

                             callBalance = apiService.getUser(email);

                             Log.e("INIT REQUEST CONSULT", "" + email);


                             UserRequest userRequest = new UserRequest();

                             callBalance.enqueue(new Callback<UserRequest>() {
                                 @Override
                                 public void onResponse(Call<UserRequest> call, Response<UserRequest> response) {
                                     if (response.raw().code() == 200) {

                                         UserRequest payloadResponse = response.body();

                                         Log.e("RESULT - STATUS", payloadResponse.getStatus());
                                         Log.e("RESULT - MESSAGE", payloadResponse.getMessage());

                                         progress.dismiss();

                                         Intent i = new Intent(InitActivity.this, MainActivity.class);
                                         startActivity(i);
                                         finish();

                                     }

                                     Log.e("RESPONSE BODY", "" + response.body());
                                     progress.dismiss();
                                 }

                                 @Override
                                 public void onFailure(Call<UserRequest> call, Throwable t) {
                                     Log.e("ERROR ", t.toString());
                                     progress.dismiss();
                                 }
                             });
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

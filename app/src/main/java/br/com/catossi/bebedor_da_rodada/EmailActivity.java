package br.com.catossi.bebedor_da_rodada;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import br.com.catossi.bebedor_da_rodada.handler.DatabaseHandler;
import br.com.catossi.bebedor_da_rodada.model.User;
import br.com.catossi.bebedor_da_rodada.model.UserInsert;
import br.com.catossi.bebedor_da_rodada.model.UserRequest;
import br.com.catossi.bebedor_da_rodada.model.UserResponse;
import br.com.catossi.bebedor_da_rodada.service.APIClient;
import br.com.catossi.bebedor_da_rodada.service.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailActivity extends AppCompatActivity {
    private Button btnNext;
    private EditText etEmail;
    private DatabaseHandler db;
    private APIInterface apiService;
    private Call<UserResponse> callBalance;
    private Call<UserRequest> callEmail;
    private ProgressDialog progress;

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
                Log.e("EMAIL ", "" + etEmail.getText().toString());

                if (!etEmail.getText().toString().equals("")) {

                    List<User> listUser = db.getAllUsers();

                    User user = new User();

                    Log.e("LIST USER SIZE", "" + listUser.size());

                    if (listUser.size() > 0) {
                        if (!listUser.get(0).getName().equals("")) {
                            user = listUser.get(0);
                            user.setEmail("" + etEmail.getText());
                            db.updateUser(user);

                            progress = ProgressDialog.show(EmailActivity.this, "Carregando", "" + getResources().getString(R.string.waiting), true);
                            apiService = APIClient.getService().create(APIInterface.class);
                            callBalance = apiService.insertUser(user.getName(), user.getEmail(), user.getNickname());

                            Log.e("INIT REQUEST INSERT", "" + user.getEmail());

                            UserResponse userResponse = new UserResponse();

                            callBalance.enqueue(new Callback<UserResponse>() {
                                @Override
                                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                                    if (response.raw().code() == 200) {

                                        UserResponse payloadResponse = response.body();

                                        Log.e("RESULT - STATUS", payloadResponse.getStatus());
                                        Log.e("RESULT - MESSAGE ", payloadResponse.getMessage());
                                        Log.e("RESULT - ID USUARIO", "" + payloadResponse.getData().getIdUsuario());

                                        progress.dismiss();

                                        Intent i = new Intent(EmailActivity.this, MainActivity.class);
                                        startActivity(i);
                                        finish();

                                    } else {
                                        Log.e("STATUS ERRO", "" + response.raw().code());
                                        Log.e("RESPONSE BODY", "" + response.body());
                                    }

                                    Log.e("RESPONSE BODY", "" + response.body());
                                    progress.dismiss();
                                }

                                @Override
                                public void onFailure(Call<UserResponse> call, Throwable t) {
                                    Log.e("ERROR ", t.toString());
                                    progress.dismiss();
                                }
                            });
                        } else {
                            progress = ProgressDialog.show(EmailActivity.this, "Carregando", "" + getResources().getString(R.string.waiting), true);

                            String email = etEmail.getText().toString();

                            apiService = APIClient.getService().create(APIInterface.class);

                            callEmail = apiService.getUser("" + etEmail.getText().toString());

                            Log.e("INIT REQUEST CONSULT", "" + email);


                            UserRequest userRequest = new UserRequest();

                            callEmail.enqueue(new Callback<UserRequest>() {
                                @Override
                                public void onResponse(Call<UserRequest> call, Response<UserRequest> response) {
                                    if (response.raw().code() == 200) {

                                        UserRequest payloadResponse = response.body();

                                        Log.e("RESULT - STATUS", payloadResponse.getStatus());
                                        Log.e("RESULT - MESSAGE", payloadResponse.getMessage());

                                        Log.e("RESULT - EMAIL", payloadResponse.getData().getUsuario().getEmail());
                                        Log.e("RESULT - NAME", payloadResponse.getData().getUsuario().getNome());
                                        Log.e("RESULT - NICKNAME", payloadResponse.getData().getUsuario().getApelido());

                                        User user = new User();
                                        user.setId(1);
                                        user.setEmail(payloadResponse.getData().getUsuario().getEmail());
                                        user.setName(payloadResponse.getData().getUsuario().getNome());
                                        user.setNickname(payloadResponse.getData().getUsuario().getApelido());

                                        db.updateUser(user);

                                        progress.dismiss();

                                        Intent i = new Intent(EmailActivity.this, MainActivity.class);
                                        startActivity(i);
                                        finish();

                                    } else {
                                        goToHome();
                                    }

                                    Log.e("RESPONSE BODY", "" + response.body());
                                    progress.dismiss();
                                }

                                @Override
                                public void onFailure(Call<UserRequest> call, Throwable t) {
                                    Log.e("ERROR ", t.toString());
                                    progress.dismiss();
                                    goToHome();
                                }
                            });
                        }
                    }
                } else {
                    new AlertDialog.Builder(EmailActivity.this)
                            .setTitle(R.string.title_message_insert_error)
                            .setMessage(R.string.message_insert_error)
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                }
            }
        });
    }

    private void goToHome() {
        new AlertDialog.Builder(EmailActivity.this)
                .setTitle(R.string.title_message_email_error)
                .setMessage(R.string.message_email_error)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(EmailActivity.this, InitActivity.class);
                        startActivity(i);
                        finish();
                    }
                }).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}

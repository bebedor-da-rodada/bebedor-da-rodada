package br.com.catossi.bebedor_da_rodada.ui.create;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.catossi.bebedor_da_rodada.EmailActivity;
import br.com.catossi.bebedor_da_rodada.InitActivity;
import br.com.catossi.bebedor_da_rodada.MainActivity;
import br.com.catossi.bebedor_da_rodada.R;
import br.com.catossi.bebedor_da_rodada.adapter.DrinkCustomAdapter;
import br.com.catossi.bebedor_da_rodada.handler.DatabaseHandler;
import br.com.catossi.bebedor_da_rodada.model.DrinkRequest;
import br.com.catossi.bebedor_da_rodada.model.DrinkResponse;
import br.com.catossi.bebedor_da_rodada.model.Round;
import br.com.catossi.bebedor_da_rodada.model.User;
import br.com.catossi.bebedor_da_rodada.model.UserRequest;
import br.com.catossi.bebedor_da_rodada.service.APIClient;
import br.com.catossi.bebedor_da_rodada.service.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFragment extends Fragment {

    private CreateViewModel createViewModel;
    private APIInterface apiService;
    private Call<DrinkResponse> callDrinks;
    private ProgressDialog progress;
    private DrinkResponse drinkResponse;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private DrinkCustomAdapter drinkCustomAdapter;
    private EditText etTitle;
    private EditText etDescription;
    private Button btnCreateRound;
    private Call<UserRequest> callEmail;
    private DatabaseHandler db;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        createViewModel =
                ViewModelProviders.of(this).get(CreateViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_create, container, false);

        etTitle = root.findViewById(R.id.et_title);
        etDescription = root.findViewById(R.id.et_description);
        btnCreateRound = root.findViewById(R.id.btn_create_round);

        drinkResponse = new DrinkResponse();
        getDrinks(root);
        db = new DatabaseHandler(root.getContext());

        btnCreateRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRound(root);
            }
        });

        return root;
    }

    private void getDrinks(final View root) {
        progress = ProgressDialog.show(root.getContext(), "Carregando", "" + getResources().getString(R.string.waiting), true);

        apiService = APIClient.getService().create(APIInterface.class);

        callDrinks = apiService.getDrinks();

        Log.e("INIT REQUEST CONSULT", " DRINKS");

        callDrinks.enqueue(new Callback<DrinkResponse>() {
            @Override
            public void onResponse(Call<DrinkResponse> call, Response<DrinkResponse> response) {
                if (response.raw().code() == 200) {

                    DrinkResponse _drinkResponse = response.body();

                    Log.e("RESULT - STATUS", _drinkResponse.getStatus());
                    Log.e("RESULT - MESSAGE", _drinkResponse.getMessage());

                    progress.dismiss();

                    drinkResponse = _drinkResponse;
                    setListDrinks(root);
                }

                Log.e("RESPONSE BODY", "" + response.body());
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<DrinkResponse> call, Throwable t) {
                Log.e("ERROR ", t.toString());
                progress.dismiss();
            }
        });
    }

    private void setListDrinks(View root) {
        Log.d("LOG MESSAGE ", "" + drinkResponse.getMessage());
        Log.d("LOG STATUS ", "" + drinkResponse.getStatus());
        Log.d("LOG DATA ", "" + drinkResponse.getData());

        for (DrinkRequest drink : drinkResponse.getData().getBebidas()) {
            Log.d("LOG BEBIDA ", "" + drink.getId());
            Log.d("LOG TITULO ", "" + drink.getTitulo());
            Log.d("LOG DESCRICAO ", "" + drink.getDescricao());
            Log.d("LOG FOTO ", "" + drink.getFoto());
            Log.d("LOG PONTUACAO ", "" + drink.getPontuacao());
            Log.d("LOG TEOR ", "" + drink.getTeor());
        }

        recyclerView = (RecyclerView) root.findViewById(R.id.drinkList);
        mLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(mLayoutManager);


        drinkCustomAdapter = new DrinkCustomAdapter(root.getContext(), drinkResponse.getData().getBebidas());

        recyclerView.setAdapter(drinkCustomAdapter);
    }

    private void createRound(View root) {
        ArrayList<Integer> listSelectedDrinks = drinkCustomAdapter.getSelectedDrinks();
        for(Integer drinkID : listSelectedDrinks) {
            Log.d("LOG DRINKID SELECTED ", "" + drinkID);
        }

        String roundTitle = etTitle.getText().toString();
        String roundDescription = etDescription.getText().toString();

        Log.d("LOG ROUNDTITLE ", "" + roundTitle);
        Log.d("LOG ROUNDDESCRIPTION ", "" + roundDescription);

        // CONSULTAR EMAIL PARA PEGAR ID DO USUARIO
        // INSERIR RODADA E PEGAR CODIGO
        // ABRIR TELA COM CODIGO DA RODADA PARA COMPARTILHAR

        String email = "";
        List<User> userList = db.getAllUsers();
        if(userList.size() > 0) {
            email = userList.get(0).getEmail();
        }

        if(!email.equals("")) {
            Round round = new Round();
            round.setBebidas(listSelectedDrinks);
            round.setDescricao(roundDescription);
            round.setTitulo(roundTitle);

            findUserAndCreateRound(email, root, round);
        }
    }

    private void createErro(final View root) {
        new AlertDialog.Builder(root.getContext())
                .setTitle("Ops, falhou.")
                .setMessage("Algo não funcionou. Tente novamente.")
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    private void createRoundRequest(View root, Round round) {
        Log.d("LOG ROUND ", "ROUNDDDD");
        Log.d("LOG DESCRICAO ", "" + round.getDescricao());
        Log.d("LOG ID USUARIO ", "" + round.getIdUsuarioCriador());
        Log.d("LOG TITULO ", "" + round.getTitulo());

        for (Integer drink : round.getBebidas()) {
            Log.d("LOG BEBIDA ", "" + drink);
        }



    }

    private void findUserAndCreateRound(String email,final View root, final Round round) {
        progress = ProgressDialog.show(root.getContext(), "Carregando", "" + getResources().getString(R.string.waiting), true);

        apiService = APIClient.getService().create(APIInterface.class);

        callEmail = apiService.getUser("" + email);

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

                    progress.dismiss();

                    round.setIdUsuarioCriador("" + payloadResponse.getData().getUsuario().getId());

                    createRoundRequest(root, round);

                } else {
                    createErro(root);
                }

                Log.e("RESPONSE BODY", "" + response.body());
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<UserRequest> call, Throwable t) {
                Log.e("ERROR ", t.toString());
                progress.dismiss();
                createErro(root);
            }
        });
    }
}
package br.com.catossi.bebedor_da_rodada.ui.create;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.catossi.bebedor_da_rodada.InitActivity;
import br.com.catossi.bebedor_da_rodada.MainActivity;
import br.com.catossi.bebedor_da_rodada.R;
import br.com.catossi.bebedor_da_rodada.adapter.DrinkCustomAdapter;
import br.com.catossi.bebedor_da_rodada.model.DrinkRequest;
import br.com.catossi.bebedor_da_rodada.model.DrinkResponse;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        createViewModel =
                ViewModelProviders.of(this).get(CreateViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_create, container, false);


        drinkResponse = new DrinkResponse();
        getDrinks(root);

        return root;
    }

    private void getDrinks(final View root) {
        progress = ProgressDialog.show(root.getContext(), "Carregando", "" + R.string.waiting, true);

        apiService = APIClient.getService().create(APIInterface.class);

        callDrinks = apiService.getDrinks();

        Log.e("INIT REQUEST CONSULT", " DRINKS" );

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
        Log.d("LOG MESSAGE " ,  "" + drinkResponse.getMessage());
        Log.d("LOG STATUS " ,  "" + drinkResponse.getStatus());
        Log.d("LOG DATA " ,  "" + drinkResponse.getData());

        for(DrinkRequest drink : drinkResponse.getData().getBebidas()) {
            Log.d("LOG BEBIDA " ,  "" + drink.getId());
            Log.d("LOG TITULO " ,  "" + drink.getTitulo());
            Log.d("LOG DESCRICAO " ,  "" + drink.getDescricao());
            Log.d("LOG FOTO " ,  "" + drink.getFoto());
            Log.d("LOG PONTUACAO " ,  "" + drink.getPontuacao());
            Log.d("LOG TEOR " ,  "" + drink.getTeor());
        }

        recyclerView = (RecyclerView) root.findViewById(R.id.drinkList);
        mLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        DrinkCustomAdapter drinkCustomAdapter;
        drinkCustomAdapter = new DrinkCustomAdapter(root.getContext(), drinkResponse.getData().getBebidas());

        recyclerView.setAdapter(drinkCustomAdapter);
    }
}
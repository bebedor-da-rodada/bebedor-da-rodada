package br.com.catossi.bebedor_da_rodada.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import br.com.catossi.bebedor_da_rodada.R;
import br.com.catossi.bebedor_da_rodada.handler.DatabaseHandler;
import br.com.catossi.bebedor_da_rodada.model.User;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private DatabaseHandler db;
    private TextView tvName, tvEmail, tvNickname;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        tvEmail = root.findViewById(R.id.tv_email);
        tvName = root.findViewById(R.id.tv_name);
        tvNickname = root.findViewById(R.id.tv_nickname);



        db = new DatabaseHandler(this.getContext());
        List<User> listUser = db.getAllUsers();

        if(listUser.size() > 0) {
            User user = listUser.get(0);

            tvEmail.setText(user.getEmail());
            tvName.setText(user.getName());
            tvNickname.setText(user.getNickname());

        }

        return root;
    }
}
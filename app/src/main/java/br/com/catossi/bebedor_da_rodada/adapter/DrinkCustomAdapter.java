package br.com.catossi.bebedor_da_rodada.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.catossi.bebedor_da_rodada.R;
import br.com.catossi.bebedor_da_rodada.model.DrinkRequest;

public class DrinkCustomAdapter extends RecyclerView.Adapter<DrinkCustomAdapter.MyViewHolder> {

    private Context mContext;
    private List<DrinkRequest> drinkList;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv_title, tv_score, tv_content;
        ImageView iv_icon;
        Button btn_add;
        LinearLayout ll_line;

        public MyViewHolder(View view) {
            super(view);

            tv_title =  view.findViewById(R.id.tv_title);
            tv_score =  view.findViewById(R.id.tv_score);
            tv_content =  view.findViewById(R.id.tv_content);
            iv_icon =  view.findViewById(R.id.iv_icon);
            btn_add =  view.findViewById(R.id.btn_add);
            ll_line =  view.findViewById(R.id.ll_line);

        }
    }

    public DrinkCustomAdapter(Context mContext, List<DrinkRequest> drinkList) {
        this.mContext = mContext;
        this.drinkList = drinkList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_drink, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final DrinkRequest drinkRequest = drinkList.get(position);
        holder.tv_title.setText("" + drinkRequest.getTitulo());
        holder.tv_content.setText("álc. " + drinkRequest.getTeor() + "% vol.");
        holder.tv_score.setText("" + drinkRequest.getPontuacao());

        if(position % 2 == 0) {
            holder.ll_line.setBackgroundResource(R.color.sectionSelected);
        }

        Glide.with(mContext).load(drinkRequest.getFoto()).into(holder.iv_icon);

//        if(offerProduct.isInterested()) {
//            holder.iv_star.setVisibility(View.VISIBLE);
//        }
//
//        holder.ll_line.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, InfoOfferActivity.class);
//                intent.putExtra("id", "" + offerProduct.get_id());
//                mContext.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
}

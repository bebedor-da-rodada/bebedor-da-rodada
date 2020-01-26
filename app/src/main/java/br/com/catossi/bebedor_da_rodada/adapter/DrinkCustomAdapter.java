package br.com.catossi.bebedor_da_rodada.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.catossi.bebedor_da_rodada.R;
import br.com.catossi.bebedor_da_rodada.model.DrinkRequest;

public class DrinkCustomAdapter extends RecyclerView.Adapter<DrinkCustomAdapter.MyViewHolder> {

    private Context mContext;
    private List<DrinkRequest> offerList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView tv_price_unit;
//        TextView tv_name_company;
//        //        TextView tv_name_product;
//        TextView tv_distance;
//        TextView tv_qtd_offers_company;
//        TextView tv_type_company;
//        ImageView iv_icon;
//        ImageView iv_star;
//        LinearLayout ll_line;

        public MyViewHolder(View view) {
            super(view);

//            tv_price_unit = (TextView) view.findViewById(R.id.tv_price_unit);
//            tv_name_company = (TextView) view.findViewById(R.id.tv_name_company);
////            tv_name_product = (TextView) view.findViewById(R.id.tv_name_product);
//            tv_distance = (TextView) view.findViewById(R.id.tv_distance);
//            tv_qtd_offers_company = (TextView) view.findViewById(R.id.tv_qtd_offers_company);
//            tv_type_company = (TextView) view.findViewById(R.id.tv_type_company);
//
//            iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
//            iv_star = (ImageView) view.findViewById(R.id.iv_star);
//            ll_line = (LinearLayout) view.findViewById(R.id.ll_line);
        }
    }

    public DrinkCustomAdapter(Context mContext, List<DrinkRequest> offerList) {
        this.mContext = mContext;
        this.offerList = offerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_drink, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

//        final OfferProduct offerProduct = offerList.get(position);
//        holder.tv_price_unit.setText("Preço (Unidade): " + String.valueOf(offerProduct.getPrice_unit()));
//        holder.tv_name_company.setText("Nome da Empresa: " + String.valueOf(offerProduct.getName_company()));
////        holder.tv_name_product.setText("Name Product/Service: " + String.valueOf(offerProduct.getProduct()));
//        holder.tv_distance.setText("" + offerProduct.getDistance() + "km");
//        holder.tv_qtd_offers_company.setText("Quantidade de ofertas feitas pela companhia: " + String.valueOf(offerProduct.getQtd_offers_company()));
//        holder.tv_type_company.setText("Tipo da companhia (porte): " + offerProduct.getType_company());
//
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
        return offerList.size();
    }
}

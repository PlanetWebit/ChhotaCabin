package planet.com.chhotacabin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import planet.com.chhotacabin.CataProList;
import planet.com.chhotacabin.R;
import planet.com.chhotacabin.pojo.CatagoryPojo;


public class Categories_Custom_Adapter extends RecyclerView.Adapter<Categories_Custom_Adapter.MyViewHolder> {

    private ArrayList<CatagoryPojo> dataset;
    private Context context;
    private ArrayList<CatagoryPojo> mFilteredList = new ArrayList<CatagoryPojo>();
    private ArrayList<String> m1 = new ArrayList<String>();
    OnClickCata clickcata;

    public Categories_Custom_Adapter(Context context, ArrayList<String> m1, OnClickCata clickcata) {
        this.context = context;
        this.m1 = m1;
        mFilteredList = dataset;
        this.clickcata = clickcata;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_name_adapter, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

//        final CatagoryPojo categoriesDataModel = dataset.get(position);

    /*    holder.textView.setText(categoriesDataModel.getName());

        Picasso.with(this.context)
                .load(categoriesDataModel.getImage())
                .into(holder.imageView);*/
        holder.linearCity.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                Intent in = new Intent(context, CataProList.class);
                in.putExtra("catId", "id");
                in.putExtra("catName", "name");
                context.startActivity(in);
                    //  clickcata.cataItemClick(dataset.get(position).getId(),dataset.get(position).getName());
        }
        });

    }

    @Override
    public int getItemCount() {
        return m1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView, textViewid;
        ImageView cityImg;
        LinearLayout linearCity;

        public MyViewHolder(View itemView) {

            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.cityName);
            cityImg = (ImageView) itemView.findViewById(R.id.cityImg);
            linearCity = (LinearLayout) itemView.findViewById(R.id.linearCity);

            // textViewid = (TextView)itemView.findViewById(R.id.cataId);

        }
    }

    public interface OnClickCata {
        public void cataItemClick(String id, String name);
    }

}

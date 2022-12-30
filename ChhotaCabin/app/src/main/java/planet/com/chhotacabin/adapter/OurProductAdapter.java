package planet.com.chhotacabin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import planet.com.chhotacabin.R;
import planet.com.chhotacabin.pojo.ProductListPojo;

public class OurProductAdapter extends RecyclerView.Adapter<OurProductAdapter.MyViewHolder> {

    Context context;
    ArrayList<ProductListPojo> data1 = new ArrayList<>();
   // ArrayList<String> data11 = new ArrayList<>();
    OnClickPro prodata1;

    public OurProductAdapter(Context context, ArrayList<ProductListPojo> data1, OnClickPro prodata1) {
        this.context = context;
        this.data1 = data1;
        this.prodata1 = prodata1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.our_product_adapter, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.location.setText(data1.get(position).getDiscount());
        holder.name.setText(data1.get(position).getName());

        if (data1.get(position).getPropertyType().equalsIgnoreCase("Cabin")){
            holder.person_limit.setText("Cabin - "  +data1.get(position).getPerson_limit());
            holder.mrpPri.setText("2 Hours - "+"\u20b9 "+data1.get(position).getPrice());
        }else  if (data1.get(position).getPropertyType().equalsIgnoreCase("Event")){
            holder.person_limit.setText("Event -  "  +data1.get(position).getPerson_limit());
            holder.mrpPri.setText("Full Day - "+"\u20b9 "+data1.get(position).getPrice());
        }else  if (data1.get(position).getPropertyType().equalsIgnoreCase("Training")){
            holder.person_limit.setText("Training - "  +data1.get(position).getPerson_limit());
            holder.mrpPri.setText("2 Hours - "+"\u20b9 "+data1.get(position).getPrice());
        }else {
            holder.person_limit.setText("Conference Limit - "  +data1.get(position).getPerson_limit());
            holder.mrpPri.setText("2 Hours - "+"\u20b9 "+data1.get(position).getPrice());
        }

       // holder.person_limit.setText("Capacity "  +data1.get(position).getPerson_limit());
       if (data1.get(position).getImage().equalsIgnoreCase("")){

       }else {
           Picasso.with(context)
                   .load(data1.get(position).getImage())
                   .into(holder.img);
       }

        holder.cardPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prodata1.productClick(data1.get(position).getId(),data1.get(position).getName()
                ,data1.get(position).getPropertyType(),data1.get(position).getDiscount()
                ,data1.get(position).getPerson_limit());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardPro;
        TextView discount, name, mrpPri,location,person_limit;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardPro = itemView.findViewById(R.id.cardPro);
            discount = itemView.findViewById(R.id.discount);
            name = itemView.findViewById(R.id.name);
            mrpPri = itemView.findViewById(R.id.mrpPri);
            location = itemView.findViewById(R.id.location);
            img = itemView.findViewById(R.id.img);
            person_limit = itemView.findViewById(R.id.person_limit);
        }
    }

    public interface OnClickPro {
        public void productClick(String id, String name,String propertyType,String pro_assress,String pers_limit);
    }
}

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

import planet.com.chhotacabin.CityActivity;
import planet.com.chhotacabin.R;
import planet.com.chhotacabin.pojo.City_Module;

public class City_Adapter extends RecyclerView.Adapter<City_Adapter.MyViewHolder> {
    Context context;
    ArrayList<City_Module> arrayList;
    cityClick cityClick;
    public City_Adapter(CityActivity cityActivity, ArrayList<City_Module> arrayList, cityClick cityClick) {

        this.context = cityActivity;
        this.arrayList = arrayList;
        this.cityClick = cityClick;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_custom, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.city_location.setText(arrayList.get(position).getProperty_address());
        holder.city_name.setText(arrayList.get(position).getProperty_name());

        if (arrayList.get(position).getProperty_type().equalsIgnoreCase("Cabin")){
            holder.city_person_limit.setText("Cabin - "  +arrayList.get(position).getPerson_limit());
            holder.city_Price.setText("2 Hours - "+"\u20b9 "+arrayList.get(position).getPrice());
        }else  if (arrayList.get(position).getProperty_type().equalsIgnoreCase("Event")){
            holder.city_person_limit.setText("Event -  "  +arrayList.get(position).getPerson_limit());
            holder.city_Price.setText("Full Day - "+"\u20b9 "+arrayList.get(position).getPrice());
        }else  if (arrayList.get(position).getProperty_type().equalsIgnoreCase("Training")){
            holder.city_person_limit.setText("Training - "  +arrayList.get(position).getPerson_limit());
            holder.city_Price.setText("2 Hours - "+"\u20b9 "+arrayList.get(position).getPrice());
        }else {
            holder.city_person_limit.setText("Conference Limit - "  +arrayList.get(position).getPerson_limit());
            holder.city_Price.setText("2 Hours - "+"\u20b9 "+arrayList.get(position).getPrice());
        }

         holder.city_person_limit.setText("Capacity "  +arrayList.get(position).getPerson_limit());
        if (arrayList.get(position).getUser_image().equalsIgnoreCase("")){
            holder.city_img.setImageResource(R.drawable.logo);
        }else {
            Picasso.with(context)
                    .load(arrayList.get(position).getUser_image())
                    .into(holder.city_img);
        }



        holder.city_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cityClick.cityClick(arrayList.get(position).getId(), arrayList.get(position).getProperty_name()
                        , arrayList.get(position).getProperty_type(), arrayList.get(position).getProperty_address()
                        , arrayList.get(position).getPerson_limit());

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView city_l;
        TextView city_discount, city_name, city_Price, city_location, city_person_limit;
        ImageView city_img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            city_l = itemView.findViewById(R.id.city_l);
            city_discount = itemView.findViewById(R.id.city_discount);
            city_name = itemView.findViewById(R.id.city_name);
            city_Price = itemView.findViewById(R.id.city_Price);
            city_location = itemView.findViewById(R.id.city_location);
            city_img = itemView.findViewById(R.id.city_img);
            city_person_limit = itemView.findViewById(R.id.city_person_limit);
        }
    }
    public interface cityClick{
        public void  cityClick(String id, String name, String propertyType, String pro_assress, String person_limit);
    }
}

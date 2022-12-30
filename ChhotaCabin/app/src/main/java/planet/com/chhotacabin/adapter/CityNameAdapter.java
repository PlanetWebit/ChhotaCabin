package planet.com.chhotacabin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import planet.com.chhotacabin.R;
import planet.com.chhotacabin.pojo.CityData;

public class CityNameAdapter extends RecyclerView.Adapter<CityNameAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CityData> cityList = new ArrayList<>();
    itemClick OnClickName;

    public CityNameAdapter(Context context, ArrayList<CityData> cityList, itemClick OnClickName) {
        this.context = context;
        this.cityList = cityList;
        this.OnClickName = OnClickName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_name_adapter, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView cityImg;
        TextView cityName;
        LinearLayout linearCity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cityImg = (CircleImageView) itemView.findViewById(R.id.cityImg);
            cityName = (TextView) itemView.findViewById(R.id.cityName);
            linearCity = (LinearLayout) itemView.findViewById(R.id.linearCity);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.cityName.setText(cityList.get(position).getCityName());

        if (cityList.get(position).getCityName().equalsIgnoreCase("Near me")){
            Picasso.with(context)
                    .load(R.mipmap.near)
                    .into(holder.cityImg);
        }else {
            if (cityList.get(position).getCityImg().equalsIgnoreCase("")) {
                Picasso.with(context)
                        .load(R.drawable.profile_icon)
                        .into(holder.cityImg);
            } else {
                Picasso.with(context)
                        .load(cityList.get(position).getCityImg())
                        .into(holder.cityImg);
            }
        }

        holder.linearCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     OnClickName.clickCity(cityList.get(position).getCityName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public interface itemClick {
        public void clickCity(String cityName);
    }
}

package planet.com.chhotacabin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

import planet.com.chhotacabin.R;
import planet.com.chhotacabin.pojo.CityData;

public class SearchCityAdapter extends RecyclerView.Adapter<SearchCityAdapter.MyViewHolder> implements Filterable {
    Context context;
    ArrayList<CityData> data = new ArrayList<>();
    private ArrayList<CityData> mFilteredList = new ArrayList<CityData>();
    onClickCity clickcity;

    public SearchCityAdapter(Context context, ArrayList<CityData> data,onClickCity clickcity) {
        this.context = context;
        this.data = data;
        mFilteredList=data;
        this.clickcity=clickcity;
    }

    public void filterList(ArrayList<CityData> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        data = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_city_adapter, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.clickCityText.setText(data.get(position).getCityName());
        holder.clickCityText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent in = new Intent(context, SelectTypeforUser.class);
                context.startActivity(in);*/

                clickcity.clickCommanCity(data.get(position).getCityName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView clickCityText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            clickCityText = itemView.findViewById(R.id.cityText);
        }
    }
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    data = mFilteredList;
                } else {

                    ArrayList<CityData> filteredList = new ArrayList<CityData>();

                    for (CityData save : mFilteredList) {

                        if (save.getCityName().toLowerCase().contains(charString) || save.getCityName().toLowerCase().contains(charString)) {

                            filteredList.add(save);
                        }
                    }

                    data = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data = (ArrayList<CityData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public  interface onClickCity{
        public void clickCommanCity(String name);
    }
}



package planet.com.chhotacabin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import planet.com.chhotacabin.R;
import planet.com.chhotacabin.pojo.ReferListPojo;

public class ReferUserListAdapter extends RecyclerView.Adapter<ReferUserListAdapter.MyViewHolder> {
    Context context;
    ArrayList<ReferListPojo> data = new ArrayList<>();

    public ReferUserListAdapter(Context context, ArrayList<ReferListPojo> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_refer_user_list_adapter, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, point, mobile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            point = itemView.findViewById(R.id.point);
            mobile = itemView.findViewById(R.id.mobile);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.date.setText(data.get(position).getDate());
        holder.point.setText("Point "+data.get(position).getPoint());
        holder.mobile.setText(data.get(position).getMobile());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}

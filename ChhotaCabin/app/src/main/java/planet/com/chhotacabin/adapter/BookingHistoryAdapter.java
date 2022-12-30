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
import planet.com.chhotacabin.pojo.BookingHistroyPojo;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.MyViewHolder> {

    Context context;
    ArrayList<BookingHistroyPojo> data = new ArrayList<>();
    OnClickMap clickMap;

    public BookingHistoryAdapter(Context context, ArrayList<BookingHistroyPojo> data, OnClickMap clickMap) {
        this.context = context;
        this.data = data;
        this.clickMap = clickMap;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_history_adapter, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.Bookdate.setText("Date/Time:-" + data.get(position).getDate() + " | " + data.get(position).getSelectTime());
        holder.Proname.setText(data.get(position).getProname());
        holder.qunti.setText("Person:- " + data.get(position).getQunti());
        holder.mrpPri.setText("\u20b9 " + data.get(position).getProPrice());
        holder.proAdd.setText("Add :- " + data.get(position).getProAdd());
        // holder.selectTime.setText();
        holder.txnId.setText("Txn Id :- " + data.get(position).getTxnId());
        if (data.get(position).getProimg().equalsIgnoreCase("")) {

        } else {
            Picasso.with(context)
                    .load(data.get(position).getProimg())
                    .into(holder.Proimg);
        }
        holder.map_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMap.onClickMap(data, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView callImg, Proimg, map_redirect;
        CardView cardView;
        TextView Bookdate, Proname, qunti, mrpPri, proAdd, selectTime, txnId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            Proimg = itemView.findViewById(R.id.Proimg);
            Bookdate = itemView.findViewById(R.id.Bookdate);
            Proname = itemView.findViewById(R.id.Proname);
            qunti = itemView.findViewById(R.id.qunti);
            mrpPri = itemView.findViewById(R.id.mrpPri);
            proAdd = itemView.findViewById(R.id.proAdd);
            selectTime = itemView.findViewById(R.id.selectTime);
            txnId = itemView.findViewById(R.id.txnId);
            map_redirect = itemView.findViewById(R.id.map_redirect);


        }
    }

    public interface OnClickMap {
        public void onClickMap(ArrayList<BookingHistroyPojo> data, int position);
    }
}

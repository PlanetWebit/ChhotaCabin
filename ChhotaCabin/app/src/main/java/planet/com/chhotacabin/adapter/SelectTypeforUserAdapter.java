package planet.com.chhotacabin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import planet.com.chhotacabin.R;
import planet.com.chhotacabin.pojo.SelectTypeUserPojo;

public class SelectTypeforUserAdapter extends RecyclerView.Adapter<SelectTypeforUserAdapter.MyViewHolder> {
    Context context;
    ArrayList<SelectTypeUserPojo> data = new ArrayList<>();
    OnClickChosse chooseClick;

    public SelectTypeforUserAdapter(Context context, ArrayList<SelectTypeUserPojo> data, OnClickChosse chooseClick) {
        this.context = context;
        this.data = data;
        this.chooseClick = chooseClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_type_for_user_adapter, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView classicRadio;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            classicRadio = itemView.findViewById(R.id.classicRadio);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.classicRadio.setText(data.get(position).getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseClick.OnClickCabin(data, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnClickChosse {
        public void OnClickCabin(ArrayList<SelectTypeUserPojo> id, int name);
    }
}

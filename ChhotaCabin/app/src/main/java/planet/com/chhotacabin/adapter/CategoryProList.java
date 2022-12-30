package planet.com.chhotacabin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

;

import java.util.ArrayList;

import planet.com.chhotacabin.ProductDetail;
import planet.com.chhotacabin.R;
import planet.com.chhotacabin.pojo.ProductListPojo;

public class CategoryProList extends RecyclerView.Adapter<CategoryProList.MyViewHolder> {

    Context context;
    ArrayList<ProductListPojo> data = new ArrayList<>();
    ArrayList<String> data1 = new ArrayList<>();

    public CategoryProList(Context context, ArrayList<String> data1) {
        this.context = context;
        this.data1 = data1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buttom_button_group_adapter,parent,false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

      /*  holder.discount.setText(data.get(position).getDiscount());
                holder.name.setText(data.get(position).getName());
                holder.mrpPri.setText(data.get(position).getPrice());
                Picasso.with(context)
                        .load(data.get(position).getImage())
                        .into(holder.img);*/
                holder.cardPro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(context, ProductDetail.class);
                        in.putExtra("proId", "id");
                        in.putExtra("proName", "name");
                        in.putExtra("proCart","proDe");
                        context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardPro;
        TextView discount,name,mrpPri;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardPro=itemView.findViewById(R.id.cardPro);
            discount=itemView.findViewById(R.id.discount);
            name=itemView.findViewById(R.id.name);
            mrpPri=itemView.findViewById(R.id.mrpPri);
            img=itemView.findViewById(R.id.img);
        }
    }
}

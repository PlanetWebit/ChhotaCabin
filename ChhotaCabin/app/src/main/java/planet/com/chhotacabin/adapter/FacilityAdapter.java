package planet.com.chhotacabin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import planet.com.chhotacabin.ProductDetail;
import planet.com.chhotacabin.R;

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.ViewHoder> {
    Context context;
    List<String> list;

    public FacilityAdapter(ProductDetail productDetail, List<String> list) {

        this.context = productDetail;
        this.list = list;
    }

    @NonNull
    @Override
    public FacilityAdapter.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facilitycustom, parent, false);
        FacilityAdapter.ViewHoder hoder = new FacilityAdapter.ViewHoder(view);
        return hoder;
    }

    @Override
    public void onBindViewHolder(@NonNull FacilityAdapter.ViewHoder holder, int position) {
        holder.valueFacility.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView valueFacility;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);

            valueFacility = itemView.findViewById(R.id.valueFacility);
        }
    }
}

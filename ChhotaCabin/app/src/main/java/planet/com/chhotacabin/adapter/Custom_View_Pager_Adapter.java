package planet.com.chhotacabin.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import planet.com.chhotacabin.R;
import planet.com.chhotacabin.pojo.ViewPagerData;


public class Custom_View_Pager_Adapter extends PagerAdapter {
    Context context;
    // int images[];
    LayoutInflater layoutInflater;
    ArrayList<ViewPagerData> images = new ArrayList<ViewPagerData>();
    itemClick callBackitemClick;


    public Custom_View_Pager_Adapter(Context context, ArrayList<ViewPagerData> images, itemClick callBackitemClick) {
        this.context = context;
        this.images = images;
        this.callBackitemClick=callBackitemClick;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        Log.e("Image Array size", ">>>" + images.size());
        return images.size();

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.custom_view_pager_adapter, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.viewimg);
        LinearLayout llImageClick=(LinearLayout)itemView.findViewById(R.id.llImageClick);

        final ViewPagerData imageShow = images.get(position);
        Log.e("Get Image", ">>>" + imageShow.getImageSl());


        Picasso.with(context)
                .load(imageShow.getImageSl())
                .into(imageView);
        container.addView(itemView);

        //listening to image click
        llImageClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent in = new Intent(context, ProductDetails.class);
                context.startActivity(in);*/
              //  callBackitemClick.itemData(imageShow.getSlidId());
               // Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
    public interface itemClick {

    public void itemData(String imageId);


}
}
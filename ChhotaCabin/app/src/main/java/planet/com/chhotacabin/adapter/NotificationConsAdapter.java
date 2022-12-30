package planet.com.chhotacabin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

import planet.com.chhotacabin.R;
import planet.com.chhotacabin.pojo.VideoListTopPojo;

public class NotificationConsAdapter extends RecyclerView.Adapter<NotificationConsAdapter.MyViewHolder> {

    Context context;
    ArrayList<VideoListTopPojo> data = new ArrayList<>();
    OnQuizClick OnclickQuiz;
    OnWinnerList onWinnerList;

    public NotificationConsAdapter(Context context, ArrayList<VideoListTopPojo> data, OnQuizClick OnclickQuiz, OnWinnerList onWinnerList) {
        this.context = context;
        this.data = data;
        this.OnclickQuiz = OnclickQuiz;
        this.onWinnerList = onWinnerList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contest_list_adapter, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView quizImg;
        TextView quizName, quizQutiTime, starQuizTest, quizQutiDate, Result, personlimit,date;
        TextView title_notification,body_notification;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            quizImg = itemView.findViewById(R.id.quizImg);
            quizName = itemView.findViewById(R.id.quizName);
            personlimit = itemView.findViewById(R.id.personlimit);
            quizQutiTime = itemView.findViewById(R.id.quizQutiTime);
            starQuizTest = itemView.findViewById(R.id.starQuizTest);
            quizQutiDate = itemView.findViewById(R.id.quizQutiDate);
            Result = itemView.findViewById(R.id.Result);
            date = itemView.findViewById(R.id.date);
            title_notification = itemView.findViewById(R.id.title_notification);
            body_notification = itemView.findViewById(R.id.body_notification);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title_notification.setText(data.get(position).getTitle());
        holder.body_notification.setText(data.get(position).getBody());
       /* holder.quizName.setText(data.get(position).getName());
        holder.date.setText(data.get(position).getDate() );
        holder.personlimit.setText("Limit " + data.get(position).getPersonlimit() + ",");
        *//*Picasso.with(context).load(data.get(position).getImg())
                .into(holder.quizImg);*//*
        holder.quizImg.setImageResource(R.mipmap.chhota_circle_logo);
        holder.starQuizTest.setVisibility(View.GONE);
        holder.Result.setVisibility(View.GONE);
       *//* if (data.get(position).getCheckPlay().equalsIgnoreCase("play")) {
            holder.starQuizTest.setVisibility(View.VISIBLE);
            holder.Result.setVisibility(View.GONE);
        } else {
            holder.starQuizTest.setVisibility(View.GONE);
            holder.Result.setVisibility(View.VISIBLE);
        }*//*
        holder.quizQutiTime.setText("Ques : " + data.get(position).getTotal_question() + " , " + data.get(position).getQuiz_time() + " , " + " Points" + data.get(position).getPrice());
        holder.starQuizTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnclickQuiz.DailyQuizClick(data.get(position).getId(),
                        data.get(position).getName(), data.get(position).getTotal_question()
                        , data.get(position).getCatagory_id());
            }
        });
        holder.Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWinnerList.checkWinnerList(data.get(position).getId(),
                        data.get(position).getName(), data.get(position).getTotal_question()
                        , data.get(position).getCatagory_id());
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnQuizClick {
        public void DailyQuizClick(String id, String quizname, String totlQuetion, String cataId);
    }

    public interface OnWinnerList {
        public void checkWinnerList(String id, String quizname, String totlQuetion, String cataId);
    }
}



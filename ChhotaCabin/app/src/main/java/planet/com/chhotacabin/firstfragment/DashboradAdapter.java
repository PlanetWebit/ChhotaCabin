package planet.com.chhotacabin.firstfragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import planet.com.chhotacabin.R;
import planet.com.chhotacabin.firstfragment.pojo.DashboradData;

public class DashboradAdapter extends RecyclerView.Adapter<DashboradAdapter.MyViewHolder> {
    private Context context;
    String conut = "";
    String imgid = "";
    private ProgressDialog bar;
    private String path = "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
    private MediaController ctlr;

    private ArrayList<DashboradData> listData = new ArrayList<>();
    private ArrayList<String> listData1 = new ArrayList<>();
    likeClickItem onlikeClick;
    vdolikeClick onVdoLikeClick;
    private MediaPlayer mediaPlayer;
    imgcomment onimgcnt;
    vdocomment onVdocnt;
    shareimg onshareimg;
    sharevideo onvideoshare;
    folloItem OnFollowClick;
    folloItemList OnFollowClickList;
    thousShare OnThousShare;
    ProgressDialog progressDialog;

    public DashboradAdapter(Context context, ArrayList<String> listData1, likeClickItem onlikeClick, vdolikeClick onVdoLikeClick, imgcomment onimgcnt, vdocomment onVdocnt, shareimg onshareimg, sharevideo onvideoshare, folloItem OnFollowClick, folloItemList OnFollowClickList, thousShare OnThousShare) {
        this.context = context;
        this.listData1 = listData1;
        this.onlikeClick = onlikeClick;
        this.onVdoLikeClick = onVdoLikeClick;
        this.onimgcnt = onimgcnt;
        this.onshareimg = onshareimg;
        this.onvideoshare = onvideoshare;
        this.onVdocnt = onVdocnt;
        this.OnFollowClick = OnFollowClick;
        this.OnFollowClickList = OnFollowClickList;
        this.OnThousShare = OnThousShare;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.dashborad_adapter, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        progressDialog = new ProgressDialog(context);

        return myViewHolder;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView imgDescText, imgLikeCunt, ImgcmntCunt, ImageUpName,
                thouths, thosName, thoutDate, ImageDate, videoDate,
                followThos, folloImg, folloVideo, folloListImg;
        ImageView image, Imgcmnt, imgLike, share, ThsLike, Thscmnt, Thsshare;

        CardView imgeCard, videoCard, thosCard;

        TextView videoDesc, videoLikeCunt, videoCmntCunt,
                videoUpName, ThscmntCunt, ThsLikeCunt, folloListVideo, followThosList;
        //  VideoView video;
        ImageView videoLike, videoCmnt, sharevideoimg;

        CircleImageView imageProfile, videoProfile, thosProfile;
        WebView web;
        private VideoView videoView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgDescText = (TextView) itemView.findViewById(R.id.imgDescText);
            imgLikeCunt = (TextView) itemView.findViewById(R.id.imgLikeCunt);
            ImgcmntCunt = (TextView) itemView.findViewById(R.id.ImgcmntCunt);
            ImageUpName = (TextView) itemView.findViewById(R.id.ImageUpName);
            ImageDate = (TextView) itemView.findViewById(R.id.ImageDate);
            videoUpName = (TextView) itemView.findViewById(R.id.videoUpName);
            thouths = (TextView) itemView.findViewById(R.id.thouths);
            thosName = (TextView) itemView.findViewById(R.id.thosName);
            thoutDate = (TextView) itemView.findViewById(R.id.thoutDate);
            followThos = (TextView) itemView.findViewById(R.id.followThos);
            folloImg = (TextView) itemView.findViewById(R.id.folloImg);
            ThsLikeCunt = (TextView) itemView.findViewById(R.id.ThsLikeCunt);
            folloListImg = (TextView) itemView.findViewById(R.id.folloListImg);
            folloListVideo = (TextView) itemView.findViewById(R.id.folloListVideo);

            videoDesc = (TextView) itemView.findViewById(R.id.videoDesc);
            videoLikeCunt = (TextView) itemView.findViewById(R.id.videoLikeCunt);
            videoCmntCunt = (TextView) itemView.findViewById(R.id.videoCmntCunt);
            videoDate = (TextView) itemView.findViewById(R.id.videoDate);
            folloVideo = (TextView) itemView.findViewById(R.id.folloVideo);
            ThscmntCunt = (TextView) itemView.findViewById(R.id.ThscmntCunt);
            followThosList = (TextView) itemView.findViewById(R.id.followThosList);

            image = (ImageView) itemView.findViewById(R.id.image);
            Imgcmnt = (ImageView) itemView.findViewById(R.id.Imgcmnt);
            imgLike = (ImageView) itemView.findViewById(R.id.imgLike);
            sharevideoimg = (ImageView) itemView.findViewById(R.id.sharevideo);
            ThsLike = (ImageView) itemView.findViewById(R.id.ThsLike);
            Thscmnt = (ImageView) itemView.findViewById(R.id.Thscmnt);
            Thsshare = (ImageView) itemView.findViewById(R.id.Thsshare);


            imageProfile = (CircleImageView) itemView.findViewById(R.id.imageProfile);
            videoProfile = (CircleImageView) itemView.findViewById(R.id.videoProfile);
            thosProfile = (CircleImageView) itemView.findViewById(R.id.thosProfile);


            videoLike = (ImageView) itemView.findViewById(R.id.videoLike);
            videoCmnt = (ImageView) itemView.findViewById(R.id.videoCmnt);
            share = (ImageView) itemView.findViewById(R.id.share);

            videoView = (VideoView) itemView.findViewById(R.id.video);
            web = itemView.findViewById(R.id.web);
            imgeCard = (CardView) itemView.findViewById(R.id.imgeCard);
            videoCard = (CardView) itemView.findViewById(R.id.videoCard);
            thosCard = (CardView) itemView.findViewById(R.id.thosCard);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
      // holder.web.loadUrl(path);
   MediaController mediaController= new MediaController(context);
        mediaController.setAnchorView(holder.videoView);
        Uri uri=Uri.parse("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");
        holder.videoView.setMediaController(mediaController);
        holder.videoView.setVideoURI(uri);
        holder.videoView.requestFocus();
    //    holder.videoView.start();
/*
        if (listData.get(position).getImage().equalsIgnoreCase("") && listData.get(position).getVideo().equalsIgnoreCase("")) {
            holder.imgeCard.setVisibility(View.GONE);
            holder.videoCard.setVisibility(View.GONE);
            holder.thosCard.setVisibility(View.VISIBLE);
            holder.thouths.setText(listData.get(position).getImgDesc());
            holder.thoutDate.setText(listData.get(position).getThoutDate());

            //  holder.folloListImg.setText(listData.get(position).getFollowThos());
            if (listData.get(position).getProfileImge().equalsIgnoreCase("")) {
                Picasso.with(context)
                        .load(R.drawable.profile_icon)
                        .into(holder.thosProfile);
            } else {
                Picasso.with(context)
                        .load(listData.get(position).getProfileImge())
                        .into(holder.thosProfile);
            }

                holder.followThosList.setText(listData.get(position).getFollowTotal());
                holder.followThos.setText(listData.get(position).getFollowThos());

            holder.followThos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnFollowClick.followClick(listData.get(position).getCountId());
                }
            });
            holder.followThosList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnFollowClickList.followClickList(listData.get(position).getCountId());
                }
            });
            holder.thosName.setText(listData.get(position).getUserName() + " " + listData.get(position).getUserNameLast());
            holder.ThsLikeCunt.setText(listData.get(position).getTotal_like());
            holder.ThscmntCunt.setText(listData.get(position).getTotal_comment());
            holder.followThos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    OnFollowClick.followClick(listData.get(position).getCountId());
                }
            });
          */
/*  holder.folloListImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.followThos.setVisibility(View.GONE);
                    OnFollowClickList.followClickList(listData.get(position).getCountId());
                }
            });*//*

            holder.ThsLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // listData.get(position).getImgLikeCunt();
                    onlikeClick.likeCunt(listData.get(position).getCountId());


                    // Toast.makeText(context,conut, Toast.LENGTH_SHORT).show();

                }
            });
            holder.Thscmnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onimgcnt.comment(listData.get(position).getCountId());
                    */
/*Toast.makeText(context, "aa gya ", Toast.LENGTH_SHORT).show();
                    Intent in=new Intent(context, MainActivity.class);
                    in.putExtra("imgid",listData.get(position).getCountId());
                    context.startActivity(in);*//*

                }
            });

            holder.Thsshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     OnThousShare.thousShareData(listData.get(position).getImgDesc());
                }
            });
        } else {
            if (listData.get(position).getImage().equalsIgnoreCase("")) {
                holder.imgeCard.setVisibility(View.GONE);
            } else {
                holder.imgeCard.setVisibility(View.VISIBLE);
                Picasso.with(context)
                        .load(listData.get(position).getImage())
                        .into(holder.image);

                holder.imgDescText.setText(listData.get(position).getImgDesc());
                holder.imgLikeCunt.setText(listData.get(position).getTotal_like());
                holder.ImgcmntCunt.setText(listData.get(position).getTotal_comment());
                holder.videoCmntCunt.setText(listData.get(position).getTotal_comment());
                holder.videoLikeCunt.setText(listData.get(position).getTotal_like());
                holder.ImageUpName.setText(listData.get(position).getUserName() + " " + listData.get(position).getUserNameLast());
                holder.ImageDate.setText(listData.get(position).getThoutDate());
                if (listData.get(position).getProfileImge().equalsIgnoreCase("")) {
                    Picasso.with(context)
                            .load(R.drawable.profile_icon)
                            .into(holder.image);
                } else {
                    Picasso.with(context)
                            .load(listData.get(position).getProfileImge())
                            .into(holder.imageProfile);
                }
                holder.imgLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // listData.get(position).getImgLikeCunt();
                        onlikeClick.likeCunt(listData.get(position).getCountId());


                        // Toast.makeText(context,conut, Toast.LENGTH_SHORT).show();

                    }
                });


                    holder.folloListImg.setText(listData.get(position).getFollowTotal());
                    holder.folloImg.setText(listData.get(position).getFollowThos());

                holder.Imgcmnt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        onimgcnt.comment(listData.get(position).getCountId());
                    */
/*Toast.makeText(context, "aa gya ", Toast.LENGTH_SHORT).show();
                    Intent in=new Intent(context, MainActivity.class);
                    in.putExtra("imgid",listData.get(position).getCountId());
                    context.startActivity(in);*//*

                    }
                });

                holder.share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onshareimg.share(listData.get(position).getImage());
                    }
                });
                holder.folloImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnFollowClick.followClick(listData.get(position).getCountId());
                    }
                });
                holder.folloListImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnFollowClickList.followClickList(listData.get(position).getCountId());
                    }
                });

            }

            if (listData.get(position).getVideo().equalsIgnoreCase("")) {
                holder.videoCard.setVisibility(View.GONE);
            } else {
                holder.videoCard.setVisibility(View.VISIBLE);
                MediaController mediaController = new MediaController(context);
                mediaController.setAnchorView(holder.video);
                //specify the location of media file
                //   Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");
                Uri uri = Uri.parse(listData.get(position).getVideo());
                //Setting MediaController and URI, then starting the videoView
                holder.video.setMediaController(mediaController);
                holder.video.setVideoURI(uri);
                 holder.video.requestFocus();
                //  holder.video.start();

                holder.videoDesc.setText(listData.get(position).getImgDesc());
                holder.videoLikeCunt.setText(listData.get(position).getTotal_like());
                holder.videoCmntCunt.setText(listData.get(position).getTotal_comment());
                holder.videoUpName.setText(listData.get(position).getUserName() + " " + listData.get(position).getUserNameLast());
                holder.videoDate.setText(listData.get(position).getThoutDate());
                if (listData.get(position).getProfileImge().equalsIgnoreCase("")) {
                    Picasso.with(context)
                            .load(R.drawable.profile_icon)
                            .into(holder.image);
                } else {
                    Picasso.with(context)
                            .load(listData.get(position).getProfileImge())
                            .into(holder.videoProfile);
                }

                    holder.folloListVideo.setText(listData.get(position).getFollowTotal());
                    holder.folloVideo.setText(listData.get(position).getFollowThos());

                holder.folloVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnFollowClick.followClick(listData.get(position).getCountId());
                    }
                });
                holder.folloListVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnFollowClickList.followClickList(listData.get(position).getCountId());
                    }
                });
                holder.videoCmnt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        onVdocnt.commentvdo(listData.get(position).getCountId());
                    */
/*Toast.makeText(context, "aa gya ", Toast.LENGTH_SHORT).show();
                    Intent in=new Intent(context, MainActivity.class);
                    in.putExtra("imgid",listData.get(position).getCountId());
                    context.startActivity(in);*//*

                    }
                });

                holder.videoLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // listData.get(position).getImgLikeCunt();
                        onVdoLikeClick.vdoLike(listData.get(position).getCountId());

                    }
                });
                holder.sharevideoimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onvideoshare.video(listData.get(position).getVideo());
                    }
                });
            }

        }
        holder.folloVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnFollowClick.followClick(listData.get(position).getCountId());
            }
        });


*/
    }


    @Override
    public int getItemCount() {
        return listData1.size();
    }

    public interface likeClickItem {
        public void likeCunt(String countId);
    }

    public interface vdolikeClick {
        public void vdoLike(String countVdoId);
    }

    public interface imgcomment {

        public void comment(String id);
    }

    public interface vdocomment {

        public void commentvdo(String id);
    }

    public interface shareimg {
        public void share(String img);
    }

    public interface sharevideo {
        public void video(String video);
    }

    public interface folloItem {
        public void followClick(String followId);
    }

    public interface folloItemList {
        public void followClickList(String followListId);
    }

    public interface thousShare {
        public void thousShareData(String thousDec);
    }
}

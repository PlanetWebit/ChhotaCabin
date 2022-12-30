package planet.com.chhotacabin.firstfragment;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import planet.com.chhotacabin.R;
import planet.com.chhotacabin.firstfragment.pojo.DashboradData;
import planet.com.chhotacabin.utils.MyPreferences;

public class AllDataFragment extends Fragment implements DashboradAdapter.likeClickItem, DashboradAdapter.vdolikeClick, DashboradAdapter.imgcomment, DashboradAdapter.shareimg,
        DashboradAdapter.sharevideo, DashboradAdapter.vdocomment, DashboradAdapter.folloItem, DashboradAdapter.folloItemList, DashboradAdapter.thousShare {

    private RecyclerView dashRecy;
    private RecyclerView.Adapter dashAdapter;
    private ArrayList<DashboradData> list = new ArrayList<>();
    private ArrayList<DashboradData> listimgLike = new ArrayList<>();
    private static final int PERMISSION_REQUEST_CODE = 200;
    ProgressDialog progressDialog;
    String type = "";
    String imgLike = "";
    String follIDSend = "";
    ArrayList<String> list1 = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_data_fragment, container, false);

        dashRecy = (RecyclerView) view.findViewById(R.id.dashRecy);
        dashRecy.setHasFixedSize(true);
        dashRecy.setLayoutManager(new LinearLayoutManager(getActivity()));
        dashRecy.setItemAnimator(new DefaultItemAnimator());

        progressDialog = new ProgressDialog(getActivity());
//        getAllData();
        if (checkPermission()) {
            requestPermissionAndContinue();
        }

        for (int i = 0; i < 20; i++) {
            list1.add("Dheeraj");
        }
        dashAdapter = new DashboradAdapter(getActivity(), list1, AllDataFragment.this, AllDataFragment.this, AllDataFragment.this, AllDataFragment.this, AllDataFragment.this, AllDataFragment.this, AllDataFragment.this, AllDataFragment.this, AllDataFragment.this);
        dashRecy.setAdapter(dashAdapter);
        return view;
    }

    public void getAllData() {

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://money21.co/api/get_data",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hide();

                        if (response != null) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                Log.e("respall", "" + response);

                                if (obj.getString("status").equalsIgnoreCase("success")) {

                                    JSONArray array = obj.getJSONArray("data");
                                    list.clear();
                                    for (int i = 0; i < array.length(); i++) {

                                        JSONObject object = array.getJSONObject(i);
                                        DashboradData pojo = new DashboradData();

                                        String date = object.getString("created_date");

                                        pojo.setImage(object.getString("image"));
                                        pojo.setImgDesc(object.getString("info"));
                                        pojo.setVideo(object.getString("video"));
                                        pojo.setCountId(object.getString("id"));
                                        pojo.setTotal_like(object.getString("total_like"));
                                        pojo.setTotal_comment(object.getString("total_comment"));
                                        pojo.setUserName(object.getString("first_name"));
                                        pojo.setUserNameLast(object.getString("last_name"));
                                        pojo.setProfileImge(object.getString("user_image"));
                                        pojo.setThoutDate(date.substring(0, 10));
                                        pojo.setFollowThos(object.getString("status"));
                                        pojo.setFollowTotal(object.getString("total_followers"));

                                        list.add(pojo);
                                    }

                                   /* dashAdapter = new DashboradAdapter(getActivity(), list, AllDataFragment.this,AllDataFragment.this, AllDataFragment.this, AllDataFragment.this, AllDataFragment.this, AllDataFragment.this, AllDataFragment.this, AllDataFragment.this,AllDataFragment.this);
                                    dashRecy.setAdapter(dashAdapter);*/
                                } else {
                                    Toast.makeText(getActivity(), "Sever issue please try again!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void likeCunt(String countId) {
        imgLike = countId;
        getimgLike();
    }


    @Override
    public void vdoLike(String countVdoId) {
        imgLike = countVdoId;
        // Toast.makeText(getActivity(), "hii"+imgLike, Toast.LENGTH_SHORT).show();
        getimgLike();

    }

    public void getimgLike() {
        progressDialog.show();
        //Toast.makeText(context,"1", Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://money21.co/api/like_images",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hide();

                        if (response != null) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                Log.e("resp1", "" + response);
                                if (obj.getString("status").equalsIgnoreCase("success")) {
                                    getAllData();

                                } else {
                                    //Toast.makeText(getActivity(), "Sever issue please try again!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", MyPreferences.getActiveInstance(getActivity()).getUserId());
                params.put("image_id", imgLike);
                Log.e("para", ">>>" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void commentvdo(String id) {
       /* Intent in = new Intent(getActivity(), CommentActivity.class);
        //in.putExtra("imgid",id);
        MyPreferences.getActiveInstance(getActivity()).setimgid(id);
        startActivity(in);*/

    }

    @Override
    public void comment(String id) {


        /*//Toast.makeText(getActivity(), "aa gya ", Toast.LENGTH_SHORT).show();
        Intent in = new Intent(getActivity(), CommentActivity.class);
        //in.putExtra("imgid",id);
        MyPreferences.getActiveInstance(getActivity()).setimgid(id);
        startActivity(in);*/


    }

    @Override
    public void share(String img) {

        //  new DataShare(getActivity(), "http://money21.co//api/share_link").ShareAndLoadImage(img);
    }


    @Override
    public void video(String video) {

        shareWhatsapp(getActivity(), "http://money21.co//api/share_link" + "\n" + "\n" + "\n" + "\n", video);
    }

    public static void shareWhatsapp(Activity activity, String text, String url) {
        PackageManager pm = activity.getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");

            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text + " " + url);
            activity.startActivity(Intent
                    .createChooser(waIntent, "Money21"));

        } catch (Exception e) {
            Toast.makeText(activity, "Money21",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ;
    }

    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Give Permission");
                alertBuilder.setMessage("");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            openActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    openActivity();
                } else {
                    getActivity().finish();
                }

            } else {
                getActivity().finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openActivity() {
        //add your further process after giving permission or to download images from remote server.
    }


    @Override
    public void followClick(String followId) {

        follIDSend = followId;
        sendFollowRequest();
    }

    public void sendFollowRequest() {
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://money21.co/api/follow",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hide();

                        if (response != null) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                Log.e("resp1", "" + response);
                                if (obj.getString("status").equalsIgnoreCase("Success")) {
                                    getAllData();

                                } else {
                                    //Toast.makeText(getActivity(), "Sever issue please try again!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", MyPreferences.getActiveInstance(getActivity()).getUserId());
                params.put("video_id", follIDSend);

                Log.e("kyagyaFollow", ">>>" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void followClickList(String followListId) {
        /*Intent in = new Intent(getActivity(), FollowListFragment.class);
        in.putExtra("followId",followListId);
        startActivity(in);*/
    }

    @Override
    public void thousShareData(String thousDec) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, thousDec);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}

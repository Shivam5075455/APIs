package com.example.api;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class DogFragment extends Fragment {
    ImageView imgDog;
    TextView tvFact;
    SwipeRefreshLayout dogSwiperefreshlayout;
    String urldog = "https://dog.ceo/api/breeds/image/random";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dog, container, false);
        tvFact =  view.findViewById(R.id.fact);
        imgDog =  view.findViewById(R.id.imgDog);
        dogSwiperefreshlayout =  view.findViewById(R.id.dogSwiperefreshlayout);
        dog();

        dogSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dog();
            }
        });
        return view;
    }

    public void dog() {
        dogSwiperefreshlayout.setRefreshing(true);
        Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.urldog, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String imgUrl = jsonObject.getString("message");
                    // Create ImageRequest and enqueue it
                    ImageRequest imageRequest = new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response2) {
                            imgDog.setImageBitmap(response2);
                        }
                    }, 0, 0, ImageView.ScaleType.CENTER_INSIDE, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DogFragment.this.getContext(), "Error loading image", Toast.LENGTH_SHORT).show();

                        }
                    });
                    // Enqueue the ImageRequest
                    Volley.newRequestQueue(getContext()).add(imageRequest);
                    dogSwiperefreshlayout.setRefreshing(false);
                } catch (Exception e) {
                    Toast.makeText(DogFragment.this.getContext(), "Data not found", Toast.LENGTH_SHORT).show();
                    dogSwiperefreshlayout.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Toast.makeText(DogFragment.this.getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                dogSwiperefreshlayout.setRefreshing(false);
            }
        });
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}

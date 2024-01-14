package com.example.api;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class CatFragment extends Fragment {
    Button btnApis;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tvFact;
    ImageView imgCat;
    String urlcat = "https://catfact.ninja/fact";
    String urlCatImage = "https://cataas.com/cat";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cat, container, false);
        tvFact = view.findViewById(R.id.fact);
        btnApis = view.findViewById(R.id.btnApis);
        swipeRefreshLayout = view.findViewById(R.id.fmSwipeRefresh);
        imgCat = view.findViewById(R.id.imgCat);

        cat();
        fetchCatImage();
        BtnApis();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Handle refresh action
            cat();
            fetchCatImage();
        });

        return view;
    }

    public void BtnApis() {
        btnApis.setOnClickListener(view -> {
            startActivity(new Intent(requireContext(), GettingAPIs.class));
        });
    }

    public void cat() {
        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, this.urlcat, null,
                response -> {
                    try {
                        String fact = response.getString("fact");
                        tvFact.setText(fact);
                        swipeRefreshLayout.setRefreshing(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                },
                error -> {
                    // Handle error response if needed
                    swipeRefreshLayout.setRefreshing(false);
                });

        Volley.newRequestQueue(requireContext()).add(request1);
    }//cat()

    private void fetchCatImage() {
        ImageRequest imageRequest = new ImageRequest(urlCatImage, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                // Set the fetched image to the ImageView
                imgCat.setImageBitmap(response);

                // Hide refreshing indicator
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_INSIDE, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response if needed
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

        Volley.newRequestQueue(requireContext()).add(imageRequest);

    }
}

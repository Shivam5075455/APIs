package com.example.api;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GettingAPIs extends AppCompatActivity {
    ApiAdapter apiAdapter;
    List<ApiModalClass> list;
    RecyclerView recyclerviewApi;
    SwipeRefreshLayout swipeRefresh;
    String url = "https://api.publicapis.org/entries";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_apis);
        recyclerviewApi = findViewById(R.id.recyclerviewApi);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        Window window = getWindow();
        window.setStatusBarColor(getColor(R.color.dark_orange));

        Toolbar customToolbar = findViewById(R.id.customToolbarApi);
        setSupportActionBar(customToolbar);

        jsonParseApiName();
        refresh();
    }

    void refresh() {
        swipeRefresh.setOnRefreshListener(() -> jsonParseApiName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtered(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void jsonParseApiName() {
        list = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("entries");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String api = object.getString("API");
                            String link = object.getString("Link");
                            String category = object.getString("Category");
                            ApiModalClass apiModalClass = new ApiModalClass((i + 1) + ". " + api, category, link);
                            list.add(apiModalClass);
                        }
                        apiAdapter = new ApiAdapter(this, list);
                        recyclerviewApi.setAdapter(apiAdapter);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                        recyclerviewApi.setLayoutManager(linearLayoutManager);
                        if (swipeRefresh.isRefreshing()) {
                            swipeRefresh.setRefreshing(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(GettingAPIs.this, error.toString(), Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(this).add(request);
    }

    public void filtered(String text) {
        ArrayList<ApiModalClass> arraySearchList = new ArrayList<>();
        for (ApiModalClass apiModalClass : list) {
            if (apiModalClass.getApiCategory().toLowerCase().contains(text.toLowerCase()) ||
                    apiModalClass.getApiName().toLowerCase().contains(text.toLowerCase()) ||
                    apiModalClass.getApiLink().toLowerCase().contains(text.toLowerCase())) {
                arraySearchList.add(apiModalClass);
            }
        }
        if (arraySearchList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            apiAdapter.setFilteredList(arraySearchList);
        }
    }
}

package com.example.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class ApiAdapter extends RecyclerView.Adapter<ApiAdapter.viewHolder> {
    Context context;
    List<ApiModalClass> list;

    public void setFilteredList(ArrayList<ApiModalClass> apiModalClassList) {
        this.list = apiModalClassList;
        notifyDataSetChanged();
    }

    public ApiAdapter(Context context, List<ApiModalClass> list) {
        this.context = context;
        this.list = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.api_custom_listview, viewGroup, false);
        return new viewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(viewHolder holder, int position) {
        ApiModalClass apiModalClass = this.list.get(position);
        holder.tvApiName.setText(apiModalClass.getApiName());
        holder.tvCategory.setText(apiModalClass.getApiCategory());
        holder.tvLink.setText(apiModalClass.getApiLink());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    /* loaded from: classes5.dex */
    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tvApiName;
        TextView tvCategory;
        TextView tvLink;

        public viewHolder(View view) {
            super(view);
            this.tvApiName = (TextView) view.findViewById(R.id.tvApiName);
            this.tvLink = (TextView) view.findViewById(R.id.tvLink);
            this.tvCategory = (TextView) view.findViewById(R.id.tvCategory);
        }
    }
}

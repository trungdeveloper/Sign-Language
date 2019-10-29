package com.example.signlanguage.Fragment.BasicTab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.signlanguage.R;
import com.example.signlanguage.VolleyApi;
import com.example.signlanguage.model.Tab;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BasicAdapter extends RecyclerView.Adapter<BasicAdapter.BasicViewHolder> {
    public List<Tab> TabsItem = new ArrayList<>();
    private OnItemClicked onClick;

    public BasicAdapter(VolleyApi.OnTabResponse onTabResponse, List<Tab> tabs) {
        this.TabsItem = tabs;
    }


    public interface OnItemClicked {
        void onClickDetailTab(int position);

    }

    @NonNull
    @Override
    public BasicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_tab, parent, false);
        return new BasicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasicViewHolder holder, final int position) {
        holder.tvView.setText(TabsItem.get(position).getName());
        holder.ImageItemTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClickDetailTab(position);

            }
        });



    }

    @Override
    public int getItemCount() {
        if(TabsItem == null){
            return 0;
        }else {
            return TabsItem.size();
        }

    }

    class BasicViewHolder extends RecyclerView.ViewHolder {
        TextView tvView;
        ImageView ImageItemTab;


        public BasicViewHolder(@NonNull View itemView) {
            super(itemView);

            tvView = itemView.findViewById(R.id.textView);
            ImageItemTab = itemView.findViewById(R.id.ImageItemTab);

        }
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }


}
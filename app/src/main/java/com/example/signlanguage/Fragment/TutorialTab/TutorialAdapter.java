package com.example.signlanguage.Fragment.TutorialTab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signlanguage.R;
import com.example.signlanguage.VolleyApi;
import com.example.signlanguage.model.Tab;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.TutorialViewHolder> {
    public List<Tab> TabsItem = new ArrayList<>();
    private OnItemClicked onClick;

    public TutorialAdapter(VolleyApi.OnTabResponse onTabResponse, List<Tab> tabs) {
        this.TabsItem = tabs;
    }


    public interface OnItemClicked {
        void onClickDetailTab(int position);

    }

    @NonNull
    @Override
    public TutorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_tab, parent, false);
        return new TutorialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorialViewHolder holder, final int position) {
        holder.tvView.setText(TabsItem.get(position).getName());
        Picasso.get().load(TabsItem.get(position).getImage()).into(holder.ImageItemTab);
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

    class TutorialViewHolder extends RecyclerView.ViewHolder {
        TextView tvView;
        ImageView ImageItemTab;


        public TutorialViewHolder(@NonNull View itemView) {
            super(itemView);

            tvView = itemView.findViewById(R.id.textView);
            ImageItemTab = itemView.findViewById(R.id.ImageItemTab);

        }
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }


}

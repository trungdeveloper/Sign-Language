package com.example.signlanguage.Screens.TabDetail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signlanguage.Fragment.BasicTab.BasicAdapter;
import com.example.signlanguage.R;

import com.example.signlanguage.VolleyApi;
import com.example.signlanguage.model.Subcategory;
import com.example.signlanguage.model.Tab;

import java.util.ArrayList;
import java.util.List;

public class TabDetailAdapter extends RecyclerView.Adapter<TabDetailAdapter.BasicTabDetailViewHolder> {
    public List<Subcategory> SubCategoryItem = new ArrayList<>();
    private OnItemClicked onClick;


    public TabDetailAdapter(VolleyApi.OnSubCategoryResponse onSubResponse,List<Subcategory> subCategoryItem) {
        SubCategoryItem = subCategoryItem;
    }

    public interface OnItemClicked {
        void onClickDetailTab(int position);

    }

    @NonNull
    @Override
    public BasicTabDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_tab_detail_item, parent, false);
        return new BasicTabDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasicTabDetailViewHolder holder, final int position) {
        holder.nameItem.setText(SubCategoryItem.get(position).getKeyword());

        holder.nameItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClickDetailTab(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        if(SubCategoryItem == null){
            return 0;
        }else {
            return SubCategoryItem.size();
        }

    }

    class BasicTabDetailViewHolder extends RecyclerView.ViewHolder {
        TextView nameItem;
        public BasicTabDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            nameItem = itemView.findViewById(R.id.nameTabBasicDetail);

        }
    }

    public void setOnClick(TabDetailAdapter.OnItemClicked onClick) {
        this.onClick = onClick;
    }
}
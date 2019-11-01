package com.example.signlanguage.Fragment.DictionaryTab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signlanguage.R;
import com.example.signlanguage.VolleyApi;
import com.example.signlanguage.model.Subcategory;


import java.util.ArrayList;
import java.util.List;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder> {
    public List<Subcategory> TabsItem = new ArrayList<>();
    private OnItemClicked onClick;

    public DictionaryAdapter(VolleyApi.OnSubCategoryResponse onSubCategoryResponse, List<Subcategory> subcategories) {
        this.TabsItem = subcategories;
    }


    public interface OnItemClicked {
        void onClickResultItem(int position);
    }

    @NonNull
    @Override
    public DictionaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_dictionary_item_tab, parent, false);
        return new DictionaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DictionaryViewHolder holder, final int position) {
        holder.tvView.setText(TabsItem.get(position).getKeyword());
        holder.tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClickResultItem(position);

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

    class DictionaryViewHolder extends RecyclerView.ViewHolder {
        TextView tvView;

        public DictionaryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvView = itemView.findViewById(R.id.textView);


        }
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }


}
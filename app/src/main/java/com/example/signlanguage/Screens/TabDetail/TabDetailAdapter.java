package com.example.signlanguage.Screens.TabDetail;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signlanguage.R;

import com.example.signlanguage.VolleyApi;
import com.example.signlanguage.model.Subcategory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TabDetailAdapter extends RecyclerView.Adapter<TabDetailAdapter.BasicTabDetailViewHolder> {
    public List<Subcategory> SubCategoryItem = new ArrayList<>();
    private OnItemClicked onClick;
    Context mContext;


    public TabDetailAdapter(VolleyApi.OnSubCategoryResponse onSubResponse,List<Subcategory> subCategoryItem, Context mContext) {
        SubCategoryItem = subCategoryItem;
        this.mContext = mContext;

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
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));
        if (SubCategoryItem.get(position).getImage() == null|| SubCategoryItem.get(position).getImage().isEmpty()){
            holder.image.setImageResource(R.drawable.windows);
            holder.nameItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }else {
            Picasso.get().load(SubCategoryItem.get(position).getImage()).fit().into(holder.image);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
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
        ImageView image;
        CardView cardView;
        public BasicTabDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            nameItem = itemView.findViewById(R.id.nameTabBasicDetail);
            image = itemView.findViewById(R.id.imageDetail);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

    public void setOnClick(TabDetailAdapter.OnItemClicked onClick) {
        this.onClick = onClick;
    }
}
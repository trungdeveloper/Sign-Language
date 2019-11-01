package com.example.signlanguage.Screens.TabDetail.Favorites;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signlanguage.Database.Favorites;
import com.example.signlanguage.R;
import com.example.signlanguage.model.Subcategory;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    List<Favorites> TabsItem;
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onClickResultItem(int position);
        void OnclickDeleteFavorite(int position);
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_favorites, parent, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, final int position) {
        holder.tvView.setText(TabsItem.get(position).getKeyword());
        holder.cardViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClickResultItem(position);

            }
        });
        holder.btn_deleteFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.OnclickDeleteFavorite(position);
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

    class FavoritesViewHolder extends RecyclerView.ViewHolder {
        TextView tvView;
        CardView cardViewFavorite;
        ImageView btn_deleteFavorite;

        @SuppressLint("WrongViewCast")
        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvView = itemView.findViewById(R.id.textViewName);
            cardViewFavorite = itemView.findViewById(R.id.cardItemFavorite);
            btn_deleteFavorite =  itemView.findViewById(R.id.btn_delete);


        }
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }


}
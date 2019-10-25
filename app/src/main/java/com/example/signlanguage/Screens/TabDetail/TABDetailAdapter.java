package com.example.signlanguage.Screens.TabDetail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signlanguage.Fragment.BasicTab.BasicAdapter;
import com.example.signlanguage.R;

import com.example.signlanguage.model.Tab;

import java.util.ArrayList;
import java.util.List;

public class TABDetailAdapter extends RecyclerView.Adapter<TABDetailAdapter.BasicTabDetailViewHolder> {
    public List<Tab> TabsItem = new ArrayList<>();
    private BasicAdapter.OnItemClicked onClick;

    public TABDetailAdapter(Tab_Detail_Activity basic_tab_activity) {
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
    public void onBindViewHolder(@NonNull BasicTabDetailViewHolder holder, int position) {
        holder.nameItem.setText("A");
    }




    @Override
    public int getItemCount() {
            return 10;
    }

    class BasicTabDetailViewHolder extends RecyclerView.ViewHolder {
        TextView nameItem;
        public BasicTabDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            nameItem = itemView.findViewById(R.id.nameTabBasicDetail);

        }
    }

    public void setOnClick(BasicAdapter.OnItemClicked onClick) {
        this.onClick = onClick;
    }
}
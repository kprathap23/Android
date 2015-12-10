package com.pratap.sectionrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pratap.sectionrecyclerview.models.DataModel;
import com.pratap.sectionrecyclerview.utils.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewSectionAdapter2 extends SectionedRecyclerViewAdapter<RecyclerViewSectionAdapter2.DataViewHolder> {


    private List<DataModel> allData;


    public RecyclerViewSectionAdapter2(List<DataModel> data) {


        this.allData = data;
    }


    @Override
    public int getSectionCount() {
        return allData.size();
    }

    @Override
    public int getItemCount(int section) {

        return allData.get(section).getAllItemsInSection().size();

    }

    @Override
    public void onBindHeaderViewHolder(DataViewHolder holder, int section) {

        String sectionName = allData.get(section).getHeaderTitle();

        holder.sectionTitle.setText(sectionName);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int section, int relativePosition, int absolutePosition) {

        ArrayList<String> itemsInSection = allData.get(section).getAllItemsInSection();

        String itemName = itemsInSection.get(relativePosition);

        holder.itemTitle.setText(itemName);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, boolean header) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(header ? R.layout.list_item_section : R.layout.list_item, parent, false);

        return new DataViewHolder(v);
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        final TextView itemTitle;
        final TextView sectionTitle;

        public DataViewHolder(View itemView) {
            super(itemView);

            itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
            sectionTitle = (TextView) itemView.findViewById(R.id.sectionTitle);


        }
    }
}
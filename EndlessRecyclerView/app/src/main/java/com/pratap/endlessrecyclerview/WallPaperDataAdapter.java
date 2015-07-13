package com.pratap.endlessrecyclerview;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pratap.endlessrecyclerview.models.WallPaper;

import java.util.List;

public class WallPaperDataAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private static List<WallPaper> imagesList;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;


    public WallPaperDataAdapter(List<WallPaper> imagesList1, RecyclerView recyclerView) {
        imagesList = imagesList1;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return imagesList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.wallpaper_row_item, parent, false);

            vh = new WallPaperViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof WallPaperViewHolder) {

            final WallPaper singleWallPaper = (WallPaper) imagesList.get(position);

            ((WallPaperViewHolder) holder).tvDownloads.setText(singleWallPaper.getDownloads()+"");

            ((WallPaperViewHolder) holder).tvFav.setText(singleWallPaper.getFav()+"");

            ((WallPaperViewHolder) holder).postion=position;

            ((WallPaperViewHolder) holder).tvFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int count=Integer.parseInt(singleWallPaper.getFav());
                    ++count;

                    singleWallPaper.setFav(String.valueOf(count));

                    notifyItemChanged(position);


                }
            });
            Glide.with(((WallPaperViewHolder) holder).thumbIcon.getContext())
                    .load(singleWallPaper.getThumbUrl())
                    .centerCrop()
                    .placeholder(R.drawable.bg)
                    .crossFade()
                    .into(((WallPaperViewHolder) holder).thumbIcon);


        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    //
    public static class WallPaperViewHolder extends RecyclerView.ViewHolder {


        public ImageView thumbIcon;

        public TextView tvDownloads;

        public TextView tvFav;

        public int postion;


        public WallPaperViewHolder(View v) {
            super(v);


            thumbIcon = (ImageView) v.findViewById(R.id.thumbIcon);
            tvDownloads = (TextView) v.findViewById(R.id.tvDownloads);
            tvFav = (TextView) v.findViewById(R.id.tvFav);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent newIntent= new Intent(v.getContext(),WallPaperDetailActivity.class);

                    newIntent.putExtra("WallPaper",imagesList.get(postion));


                    v.getContext().startActivity(newIntent);

                }
            });



        }
    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }
}
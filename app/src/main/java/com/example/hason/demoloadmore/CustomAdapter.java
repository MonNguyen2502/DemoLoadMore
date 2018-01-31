package com.example.hason.demoloadmore;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hason on 1/29/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter {

    Context context;
    List<String> dataList;
    int[] id_images;
    LinearLayoutManager layoutManager;

    int totalItem;
    int postionLast;
    boolean isLoading = false;

    public static final int VIEW_TYPE_PROGRESS = 0;
    public static final int VIEW_TYPE_ITEM = 1;

    public CustomAdapter(final Context context, List<String> dataList, int[] id_images, RecyclerView recyclerView) {
        this.context = context;
        this.dataList = dataList;
        this.id_images = id_images;

        layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItem = layoutManager.getItemCount();
                postionLast = layoutManager.findLastCompletelyVisibleItemPosition();

                if (isLoading == false && totalItem <= (postionLast + 5)) {
                    ((ILoadMore) context).loadMore();
                }
            }
        });
    }

    public void setLoading() {
        if (isLoading) {
            isLoading = false;
        } else {
            isLoading = true;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_PROGRESS) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.progress, parent, false);
            return new ProgressViewHolder(itemView);
        } else if (viewType == VIEW_TYPE_ITEM) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.item, parent, false);
            return new ItemViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProgressViewHolder) {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        } else if (holder instanceof ItemViewHolder) {
            if (position >= id_images.length) {
                ((ItemViewHolder) holder).imageView.setImageResource(R.drawable.ic_launcher_background);
            } else {
                ((ItemViewHolder) holder).imageView.setImageResource(id_images[position]);
            }

            ((ItemViewHolder) holder).textView.setText(dataList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position) == null) {
            return VIEW_TYPE_PROGRESS;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}

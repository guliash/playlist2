package ru.yandex.yamblz.playlist2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SingersAdapter extends RecyclerView.Adapter<SingersAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.cover);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }

    private List<Singer> mSingers;

    public SingersAdapter(@Nullable List<Singer> singers) {
        mSingers = singers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.singer_card, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Singer singer = mSingers.get(position);
        holder.name.setText(singer.getName());
        Picasso.with(holder.cover.getContext()).load(singer.getCover().getSmall()).fit().into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return mSingers != null ? mSingers.size() : 0;
    }
}

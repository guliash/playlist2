package ru.yandex.yamblz.playlist2.ui.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.yandex.yamblz.playlist2.R;
import ru.yandex.yamblz.playlist2.structures.Singer;

public class SingersAdapter extends RecyclerView.Adapter<SingersAdapter.ViewHolder> {

    public interface Callbacks {
        void onSingerChosen(Singer singer);
    }

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
    private Callbacks mCallbacks;

    public SingersAdapter(@Nullable Callbacks callbacks, @Nullable List<Singer> singers) {
        mCallbacks = callbacks;
        mSingers = singers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ViewHolder viewHolder = new ViewHolder(inflater.inflate(R.layout.singer_card, parent, false));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = viewHolder.getAdapterPosition();
                if(adapterPos != RecyclerView.NO_POSITION) {
                    mCallbacks.onSingerChosen(mSingers.get(adapterPos));
                }
            }
        });
        return viewHolder;
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

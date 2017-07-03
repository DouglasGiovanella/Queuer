package com.douglasgiovanella.queuer.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.douglasgiovanella.queuer.R;
import com.douglasgiovanella.queuer.model.QueueItem;

import java.util.List;

/**
 * Created by Douglas Giovanella on 27/06/2017.
 */

public class QueueListAdapter extends RecyclerView.Adapter<QueueListAdapter.ViewHolderQueue> {

    private List<QueueItem> mList;

    public QueueListAdapter(List<QueueItem> objects) {
        mList = objects;
    }

    @Override
    public ViewHolderQueue onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.queue_view_holder, parent, false);
        return new ViewHolderQueue(layoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolderQueue holder, int position) {
        if (mList.get(position).getValue() != null) {
            holder.value.setText(mList.get(position).getValue().toString());
        } else {
            holder.value.setText("null");
        }

        if (mList.get(position).isHead() && mList.get(position).isTail()) {
            holder.valueType.setText("HEAD/TAIL");
        } else if (mList.get(position).isHead()) {
            holder.valueType.setText("HEAD");
        } else if (mList.get(position).isTail()) {
            holder.valueType.setText("TAIL");
        } else {
            holder.valueType.setText("");
        }

    }

    public void swap(List<QueueItem> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolderQueue extends RecyclerView.ViewHolder {

        TextView value, valueType;

        ViewHolderQueue(View itemView) {
            super(itemView);
            value = itemView.findViewById(R.id.value_view_holder);
            valueType = itemView.findViewById(R.id.value_type_holder);
        }
    }

}

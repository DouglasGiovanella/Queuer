package com.douglasgiovanella.queuer.view.adapter;

import android.support.annotation.NonNull;
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

    @NonNull
    @Override
    public ViewHolderQueue onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.queue_view_holder, parent, false);
        return new ViewHolderQueue(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderQueue holder, int position) {
        holder.bind(mList.get(position));
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

        void bind(QueueItem item) {
            if (item.getValue() != null) {
                value.setText(item.getValue().toString());
            } else {
                value.setText("null");
            }

            if (item.isHead() && item.isTail()) {
                valueType.setText("HEAD/TAIL");
            } else if (item.isHead()) {
                valueType.setText("HEAD");
            } else if (item.isTail()) {
                valueType.setText("TAIL");
            } else {
                valueType.setText("");
            }
        }
    }

}

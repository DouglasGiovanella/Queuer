package com.douglasgiovanella.queuer;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Douglas Giovanella on 27/06/2017.
 */

public class QueueListAdapter extends RecyclerView.Adapter<QueueListAdapter.ViewHolderQueue> {

    private List<Object> mList;
    private Context mContext;
    private Type type;
    private int currentHeadPosition, currentTailPosition;

    public QueueListAdapter(Context mContext, List<Object> objects, Type type) {
        this.mContext = mContext;
        mList = objects;
        this.type = type;
    }

    @Override
    public ViewHolderQueue onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.queue_view_holder, parent, false);
        return new ViewHolderQueue(layoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolderQueue holder, int position) {
        if (mList.get(position) != null) {
            holder.value.setText(mList.get(position).toString());
        } else {
            if (type.equals(Character.TYPE)) {
                holder.value.setText(" ");
            } else {
                holder.value.setText("0");
            }
        }

        if(currentHeadPosition == currentTailPosition && currentHeadPosition == position){

        }

        if (position == currentHeadPosition) {
            holder.valueType.setText("HEAD");
        }else{
            if(position == currentTailPosition){
                holder.valueType.setText("TAIL");
            }
        }
    }

    public void swap(List<Object> list) {
        if (mList != null) {
            mList.clear();
            mList.addAll(list);
        } else {
            mList = list;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolderQueue extends RecyclerView.ViewHolder {

        public final TextView value, valueType;
        public final CardView mCardView;

        ViewHolderQueue(View itemView) {
            super(itemView);
            value = itemView.findViewById(R.id.value_view_holder);
            valueType = itemView.findViewById(R.id.value_type_holder);
            mCardView = itemView.findViewById(R.id.card_view);
        }
    }

}

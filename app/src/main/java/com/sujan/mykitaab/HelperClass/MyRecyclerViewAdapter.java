package com.sujan.mykitaab.HelperClass;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sujan.mykitaab.R;

/**
 * Created by macbookpro on 4/18/17.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

   int[] list_of_icons={android.R.drawable.ic_btn_speak_now,
            android.R.drawable.ic_delete,
            android.R.drawable.ic_dialog_email,
            android.R.drawable.ic_input_add,
            android.R.drawable.ic_input_add,
            android.R.drawable.ic_lock_idle_lock,
            android.R.drawable.ic_lock_idle_low_battery,
            android.R.drawable.ic_lock_lock,
            android.R.drawable.ic_popup_reminder

    };
    Context context;

    public MyRecyclerViewAdapter(Context context) {
        this.context=context;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewlayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context).load(list_of_icons[position]).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list_of_icons.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView textView;
        ImageView imageView;


        public MyViewHolder(View itemView) {
            super(itemView);
            cardView= (CardView) itemView.findViewById(R.id.card_view);
            textView= (TextView) itemView.findViewById(R.id.info_text);
            imageView= (ImageView) itemView.findViewById(R.id.imageView3);
        }
    }
}

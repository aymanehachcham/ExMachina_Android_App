package com.android.ex_machina;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.ex_machina.Models.ItemHome;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeItemsAdapter extends RecyclerView.Adapter<HomeItemsAdapter.MyViewHolder> {


    private List<ItemHome> articles;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public HomeItemsAdapter(List<ItemHome> articles, Context context) {

        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_home, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {

        final MyViewHolder holder = holders;
        ItemHome model = articles.get(position);

        holder.title.setText(model.getTitle());
        holder.title.setTypeface(null, Typeface.BOLD);

        holder.desc.setText(model.getDescription());
        holder.profilePic.setImageResource(model.getProfile());
        holder.cardBackground.setCardBackgroundColor(model.getBackground());

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        TextView title, desc;
        ImageView profilePic;
        CardView cardBackground;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {

            super(itemView);

            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.Title);
            desc = itemView.findViewById(R.id.Description);
            profilePic = itemView.findViewById(R.id.avatar_image);
            cardBackground = itemView.findViewById(R.id.cardLayout);

            this.onItemClickListener = onItemClickListener;

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}

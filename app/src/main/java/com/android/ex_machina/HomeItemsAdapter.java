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

public class HomeItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;

    private List<ItemHome> articles;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public HomeItemsAdapter(List<ItemHome> articles, Context context) {

        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(context).inflate(R.layout.card_home, parent, false);
        //return new MyViewHolder(view, onItemClickListener);

        Context context = parent.getContext();

        if (viewType == TYPE_ITEM) {

            final View view = LayoutInflater.from(context).inflate(R.layout.card_home, parent, false);
            return new MyViewHolder(view, onItemClickListener);

        } else if (viewType == TYPE_HEADER) {

            final View view = LayoutInflater.from(context).inflate(R.layout.card_header, parent, false);
            return new RecyclerHeaderViewHolder(view);
        }

        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types    correctly");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holders, int position) {

        if(!isPositionHeader(position)){

            final MyViewHolder holder = (MyViewHolder) holders;
            ItemHome model = articles.get(position - 1);

            holder.title.setText(model.getTitle());
            holder.title.setTypeface(null, Typeface.BOLD);

            holder.desc.setText(model.getDescription());
            holder.profilePic.setImageResource(model.getProfile());
            holder.cardBackground.setCardBackgroundColor(model.getBackground());
        }

    }

    //our old getItemCount()
    public int getBasicItemCount() {
        return articles == null ? 0 : articles.size();
    }

    @Override
    public int getItemCount() {

        return getBasicItemCount() + 1;
    }

    //added a method to check if given position is a header
    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    //added a method that returns viewType for a given position
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
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

    public class RecyclerHeaderViewHolder extends RecyclerView.ViewHolder {
        public RecyclerHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}

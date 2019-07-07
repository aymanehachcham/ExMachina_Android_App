package com.android.ex_machina.Adapters;

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

import com.android.ex_machina.Models.ItemCourses;
import com.android.ex_machina.Models.ItemHome;
import com.android.ex_machina.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;

    private List<ItemCourses> courses;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public CourseAdapter(List<ItemCourses> articles, Context context) {

        this.courses = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(context).inflate(R.layout.card_home, parent, false);
        //return new MyViewHolder(view, onItemClickListener);

        Context context = parent.getContext();

        if (viewType == TYPE_ITEM) {

            final View view = LayoutInflater.from(context).inflate(R.layout.card_course, parent, false);
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
            ItemCourses model = courses.get(position - 1);

            holder.fid.setText(model.getFid());
            holder.fid.setTypeface(null, Typeface.BOLD);

            holder.titre.setText(model.getTitre());
            holder.cardBackground.setCardBackgroundColor(model.getBackground());
        }

    }

    //our old getItemCount()
    public int getBasicItemCount() {
        return courses == null ? 0 : courses.size();
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

        TextView fid, titre;
        CardView cardBackground;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {

            super(itemView);

            itemView.setOnClickListener(this);
            fid = itemView.findViewById(R.id.itemID);
            titre = itemView.findViewById(R.id.itemTitle);
            cardBackground = itemView.findViewById(R.id.cardCourseBackground);

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
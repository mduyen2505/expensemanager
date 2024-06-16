package com.example.expensemanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HomeViewHolder> {
    private List<Item> list;
    private ItemListener itemListener;

    public RecycleViewAdapter() {

        list = new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {

        this.itemListener = itemListener;
    }

    public void setList(List<Item> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Item getItem(int position) {

        return list.get(position);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Item item = list.get(position);
        holder.title.setText(item.getTitle());
        holder.category.setText(item.getCategory());
        holder.price.setText(item.getPrice());
        holder.date.setText(item.getDate());
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, category, price, date;

        public HomeViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.Title);
            category = view.findViewById(R.id.Category);
            price = view.findViewById(R.id.Price);
            date = view.findViewById(R.id.Date);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemListener != null) {
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemListener {
        void onItemClick(View view, int position);
    }

    // Assuming you have a custom Item class
    public static class Item implements Serializable {
        private String title;
        private String category;
        private String price;
        private String date;

        public Item(int id, String title, String category, String price, String date) {
            this.title = title;
            this.category = category;
            this.price = price;
            this.date = date;
        }
        public String getTitle() {
            return title;
        }

        public String getCategory() {
            return category;
        }

        public String getPrice() {
            return price;
        }

        public String getDate() {
            return date;
        }

        public int getId() {
            return 1;
        }


    }
}

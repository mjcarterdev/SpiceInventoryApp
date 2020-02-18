package com.example.spicesinventory.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spice_sqlite_test.R;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
    private List<ShoppingItem> shopping_Lists;

    public ShoppingListAdapter(@NonNull List<ShoppingItem> shopping_List) {
        this.shopping_Lists = shopping_List;
    }

    @NonNull
    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterview_shopping, parent, false);
        return new ShoppingListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListAdapter.ViewHolder holder, int position) {
        holder.spiceName.setText(shopping_Lists.get(position).getItem_name());
        holder.amount.setText(shopping_Lists.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return shopping_Lists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView spiceName, amount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            spiceName = itemView.findViewById(R.id.RVShoppingAdaptorName);
            amount = itemView.findViewById(R.id.RVShoppingAdaptorAmount);
        }
    }
}

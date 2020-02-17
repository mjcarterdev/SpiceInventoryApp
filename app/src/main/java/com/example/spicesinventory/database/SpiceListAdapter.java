package com.example.spicesinventory.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spice_sqlite_test.R;

import java.util.List;


public class SpiceListAdapter extends RecyclerView.Adapter<SpiceListAdapter.ViewHolder> {

    private List<Spice> spices;

    public SpiceListAdapter(List<Spice> users) {
        this.spices = users;
    }

    @NonNull
    @Override
    public SpiceListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpiceListAdapter.ViewHolder holder, int position) {
        holder.barcode.setText(spices.get(position).getBarcodeID());
        holder.spiceName.setText(spices.get(position).getSpice_name());
        holder.stock.setText(spices.get(position).getStock());
        holder.containerType.setText(spices.get(position).getContainerType());
        holder.brand.setText(spices.get(position).getBrand());

    }

    @Override
    public int getItemCount() {
        return spices.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView barcode, spiceName, stock, containerType, brand;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            barcode = itemView.findViewById(R.id.RVBarcode);
            spiceName = itemView.findViewById(R.id.RVSpiceName);
            stock = itemView.findViewById(R.id.RVStock);
            containerType = itemView.findViewById(R.id.RVContainerType);
            brand = itemView.findViewById(R.id.RVBrand);

        }
    }
}

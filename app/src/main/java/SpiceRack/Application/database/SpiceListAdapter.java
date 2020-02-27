package SpiceRack.Application.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

import SpiceRack.R;


public class SpiceListAdapter extends RecyclerView.Adapter<SpiceListAdapter.ViewHolder> {

    private List<Spice> spices;

    public SpiceListAdapter(List<Spice> spices) {
        this.spices = spices;
    }

    @NonNull
    @Override
    public SpiceListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpiceListAdapter.ViewHolder holder, int position) {
        holder.barcode.setText(spices.get(position).getBarcode());
        holder.spiceName.setText(spices.get(position).getSpiceName());
        holder.containerType.setText(spices.get(position).getContainerType());
        holder.brand.setText(spices.get(position).getBrand());
        holder.stock.setText(spices.get(position).getStock());
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
            brand = itemView.findViewById(R.id.RVBrand);
            containerType = itemView.findViewById(R.id.RVContainerType);
            stock = itemView.findViewById(R.id.RVStock);
        }
    }
}

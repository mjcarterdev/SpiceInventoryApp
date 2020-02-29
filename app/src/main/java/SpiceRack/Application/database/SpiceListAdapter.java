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
    private SpiceOnClickListener mySpiceListener;

    public SpiceListAdapter(List<Spice> spices, SpiceOnClickListener spiceOnClickListener) {
        this.spices = spices;
        this.mySpiceListener = spiceOnClickListener;
    }

    @NonNull
    @Override
    public SpiceListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterview, parent, false);
        return new ViewHolder(view, mySpiceListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SpiceListAdapter.ViewHolder holder, int position) {
        holder.spiceName.setText(spices.get(position).getSpiceName());
        holder.containerType.setText(spices.get(position).getContainerType());
        holder.brand.setText(spices.get(position).getBrand());
        holder.stock.setText(String.valueOf(spices.get(position).getStock()));
    }

    @Override
    public int getItemCount() {
        return spices.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView spiceName, stock, containerType, brand;
        SpiceOnClickListener spiceOnClickListener;

        ViewHolder(@NonNull View itemView, SpiceOnClickListener spiceOnClickListener) {
            super(itemView);
            this.spiceOnClickListener = spiceOnClickListener;
            spiceName = itemView.findViewById(R.id.RVSpiceName);
            brand = itemView.findViewById(R.id.RVBrand);
            containerType = itemView.findViewById(R.id.RVContainerType);
            stock = itemView.findViewById(R.id.RVStock);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            spiceOnClickListener.spiceOnClick(getAdapterPosition());
        }
    }

    public interface SpiceOnClickListener{
        void spiceOnClick(int position);
    }
}
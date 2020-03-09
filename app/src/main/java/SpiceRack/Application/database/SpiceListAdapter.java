package SpiceRack.Application.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import SpiceRack.R;

/**
 *
 * @author Michael
 * @author Astrid
 * @version 1.0
 * @since 05.03.2020
 */

public class SpiceListAdapter extends RecyclerView.Adapter<SpiceListAdapter.ViewHolder> {

    private List<Spice> spices;
    private SpiceOnClickListener mySpiceListener;

    /**
     * <p>
     *     This is the constructor for the ShoppingListAdaptor. It takes a list of Shopping Items and
     *     a onClickListener as parameters.
     * </p>
     * @param spices list of Spices.
     * @param spiceOnClickListener the ItemClickListener for the recyclerview.
     */
    public SpiceListAdapter(List<Spice> spices, SpiceOnClickListener spiceOnClickListener) {
        this.spices = spices;
        this.mySpiceListener = spiceOnClickListener;
    }

    /**
     *<p>
     *     creates and inflates the layout for a Spice object within the list.
     *</p>
     * @param parent ViewGroup
     * @param viewType the view type of the object
     * @return new viewHolder for a spice.
     */
    @NonNull
    @Override
    public SpiceListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterview_spice, parent, false);
        return new ViewHolder(view, mySpiceListener);
    }

    /**
     *<p>
     *     Is internally called method to update the viewHolder with the correct data. The method
     *     checks the holder for which version it is. Then will set the contents of the holder based
     *     on the information required for that layout view.
     *</p>
     * @param holder the viewHolder created depending on object.
     * @param position The position of the view in the adapter.
     */
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

    /**
     *<p>
     *     viewHolder class extends the Recycler.ViewHolder and implements an onClickListener.
     *     This class is used for the layout used on Spices from the spice inventory.
     *</p>
     */
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
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
 *<p>
 *
 *</p>
 * @author Michael
 * @author Astrid
 * @version 1.0
 * @since 05.03.2020
 */

public class ShoppingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SPICE_NORMAL = 1;
    private static final int SPICE_STRIKE = 2;
    private static final int SHOPPING_NORMAL = 3;
    private static final int SHOPPING_STRIKE = 4;

    private List<ShoppingItem> shoppingLists;

    private OnItemClickListener onItemClickListener;

    /**
     * <p>
     *     This is the constructor for the ShoppingListAdaptor. It takes a list of Shopping Items and
     *     a onClickListener as parameters.
     * </p>
     * @param shoppingLists list of shopping items.
     * @param onItemClickListener the ItemClickListener for the recyclerview.
     */
    public ShoppingListAdapter(List<ShoppingItem> shoppingLists, OnItemClickListener onItemClickListener) {
        this.shoppingLists = shoppingLists;
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * <p>
     *     gets the size of the list to be displayed on the recyclerview.
     * </p>
     * @return the number of items within the list.
     */
    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    /**
     *<p>
     *     retrieves the viewType from the object at the position clicked within the list on the recycler
     *     view.
     *</p>
     * @param position The position of the view in the adapter.
     * @return the objects view type.
     */
    @Override
    public int getItemViewType(int position) {
        ShoppingItem item = shoppingLists.get(position);
        switch (item.getViewType()) {
            case SPICE_NORMAL:
                return SPICE_NORMAL;
            case SPICE_STRIKE:
                return SPICE_STRIKE;
            case SHOPPING_NORMAL:
                return SHOPPING_NORMAL;
            case SHOPPING_STRIKE:
                return SHOPPING_STRIKE;
            default:
                return -1;
        }
    }

    /**
     *<p>
     *     When a new viewHolder is created for a object within the list, a check on the items view type
     *     is carried out. The view layout will change depending on the viewType.
     *</p>
     * @param parent ViewGroup
     * @param viewType the view type of the object
     * @return new viewHolder depending on viewType.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case SPICE_NORMAL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterview_spice, parent, false);
                return new ViewHolderSpice(view, onItemClickListener);
            case SPICE_STRIKE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptorview_spice_strikethrough, parent, false);
                return new ViewHolderSpice(view, onItemClickListener);
            case SHOPPING_NORMAL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterview_shopping, parent, false);
                return new ViewHolderShopping(view, onItemClickListener);
            case SHOPPING_STRIKE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterview_shopping_strikethrough, parent, false);
                return new ViewHolderShopping(view, onItemClickListener);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_activity, parent, false);
                return new ViewHolderSpice(view, onItemClickListener);
        }
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof  ViewHolderSpice){
            final ViewHolderSpice viewHolderSpice = (ViewHolderSpice) holder;
            viewHolderSpice.spiceName.setText(shoppingLists.get(position).getItemName());
            viewHolderSpice.containerType.setText(shoppingLists.get(position).getContainerType());
            viewHolderSpice.brand.setText(shoppingLists.get(position).getBrand());
            viewHolderSpice.stock.setText(String.valueOf(shoppingLists.get(position).getAmount()));

        }else{
            final ViewHolderShopping viewHolderShopping = (ViewHolderShopping) holder;
            viewHolderShopping.shoppingName.setText(shoppingLists.get(position).getItemName());
            viewHolderShopping.amount.setText(String.valueOf(shoppingLists.get(position).getAmount()));
        }
    }

    /**
     *<p>
     *     viewHolderSpice class extends the Recycler.ViewHolder and implements an onClickListener.
     *     This class is used for the layout used on Spices from the spice inventory. It has more
     *     information to display thus has more variables to set.
     *</p>
     */

    static class ViewHolderSpice extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView spiceName, stock, containerType, brand;
        OnItemClickListener onItemClickListener;

        /**
         *<p>
         *     sets the layout of the object ready for data to be assigned.
         *</p>
         * @param itemView view.
         * @param onItemClickListener onItemClickListener.
         */
        ViewHolderSpice(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            spiceName = itemView.findViewById(R.id.RVSpiceName);
            brand = itemView.findViewById(R.id.RVBrand);
            containerType = itemView.findViewById(R.id.RVContainerType);
            stock = itemView.findViewById(R.id.RVStock);
            itemView.setOnClickListener(this);
        }

        /**
         *<p>
         *     gets the position of the object clicked within the recycler view list.
         *</p>
         * @param v view that was clicked.
         */
        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }

    }

    /**
     *
     */
    static class ViewHolderShopping extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView shoppingName, amount;
        OnItemClickListener onItemClickListener;

        /**
         *<p>
         *     sets the layout of the object ready for data to be assigned.
         *</p>
         * @param itemView view.
         * @param onItemClickListener onItemClickListener.
         */
        ViewHolderShopping(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            shoppingName = itemView.findViewById(R.id.rvShoppingName);
            amount = itemView.findViewById(R.id.rvShoppingAmount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    /**
     * <p>
     *     Creates methods for onItemClick() with position that is implemented into the viewHolders
     *     so that a code block can be ran.
     * </p>
     */
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}

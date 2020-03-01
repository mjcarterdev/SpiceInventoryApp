package SpiceRack.Application.database;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import SpiceRack.R;


public class ShoppingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SPICE_NORMAL = 1;
    private static final int SPICE_STRIKE = 2;
    private static final int SHOPPING_NORMAL = 3;
    private static final int SHOPPING_STRIKE = 4;
    private String TAG = "DEBUGGING";

    private List<ShoppingItem> shoppingLists;
    private Context mContext;
    private OnItemClickListener onItemClickListener;


    public ShoppingListAdapter(Context context, List<ShoppingItem> shoppingLists, OnItemClickListener onItemClickListener) {
        this.shoppingLists = shoppingLists;
        this.mContext= context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        ShoppingItem item = shoppingLists.get(position);
        switch (item.getViewType()) {
            case SPICE_NORMAL:
                Log.d(TAG, "spice normal");
                return SPICE_NORMAL;
            case SPICE_STRIKE:
                Log.d(TAG, "spice strike");
                return SPICE_STRIKE;
            case SHOPPING_NORMAL:
                Log.d(TAG, "shopping normal");
                return SHOPPING_NORMAL;
            case SHOPPING_STRIKE:
                Log.d(TAG, "spice strike");
                return SHOPPING_STRIKE;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case SPICE_NORMAL:
                Log.d(TAG, "spice normal viewholderSpice");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterview_spice, parent, false);
                return new ViewHolderSpice(view, onItemClickListener);
            case SPICE_STRIKE:
                Log.d(TAG, "spice strike viewholderSpice");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptorview_spice_strikethrough, parent, false);
                return new ViewHolderSpice(view, onItemClickListener);
            case SHOPPING_NORMAL:
                Log.d(TAG, "shopping normal viewholderhopping");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterview_shopping, parent, false);
                return new ViewHolderShopping(view, onItemClickListener);
            case SHOPPING_STRIKE:
                Log.d(TAG, "shopping strike viewholderhopping");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterview_shopping_strikethrough, parent, false);
                return new ViewHolderShopping(view, onItemClickListener);
            default:
                Log.d(TAG, "default should be seeing this!");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_activity, parent, false);
                return new ViewHolderSpice(view, onItemClickListener);
        }

    }

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


    // Static inner class to initialize the views of rows
    static class ViewHolderSpice extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView spiceName, stock, containerType, brand;
        OnItemClickListener onItemClickListener;

        ViewHolderSpice(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            spiceName = itemView.findViewById(R.id.RVSpiceName);
            brand = itemView.findViewById(R.id.RVBrand);
            containerType = itemView.findViewById(R.id.RVContainerType);
            stock = itemView.findViewById(R.id.RVStock);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }

    }

    static class ViewHolderShopping extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView shoppingName, amount;
        OnItemClickListener onItemClickListener;

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
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}

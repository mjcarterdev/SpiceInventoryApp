package SpiceRack.Application.database;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import SpiceRack.R;


public class ShoppingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ShoppingItem> shoppingLists;
    private Context mContext;

    public ShoppingListAdapter(List<ShoppingItem> shoppingLists, Context context) {
        this.shoppingLists = shoppingLists;
        this.mContext= context;
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        ShoppingItem item = shoppingLists.get(position);
        if (item.getViewType() == 1) {
            return 1;
        } else if (item.getViewType() == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterview, parent, false);
            return new ViewHolderSpice(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterview_shopping, parent, false);
            return new ViewHolderShopping(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 1:
                initLayoutSpice((ViewHolderSpice) holder, position);
                break;
            case 0:
                initLayoutShopping((ViewHolderShopping) holder, position);
                break;
            default:
                break;

        }

    }

    private void initLayoutSpice(ViewHolderSpice holder, int position) {
        holder.spiceName.setText(shoppingLists.get(position).getItemName());
        holder.containerType.setText(shoppingLists.get(position).getContainerType());
        holder.brand.setText(shoppingLists.get(position).getBrand());
        holder.stock.setText(String.valueOf(shoppingLists.get(position).getAmount()));
    }

    private void initLayoutShopping(ViewHolderShopping holder, int pos) {
        holder.shoppingName.setText(shoppingLists.get(pos).getItemName());
        holder.amount.setText(String.valueOf(shoppingLists.get(pos).getAmount()));
    }

    // Static inner class to initialize the views of rows
    static class ViewHolderSpice extends RecyclerView.ViewHolder {
        TextView spiceName, stock, containerType, brand;

        ViewHolderSpice(View itemView) {
            super(itemView);

            spiceName = itemView.findViewById(R.id.RVSpiceName);
            brand = itemView.findViewById(R.id.RVBrand);
            containerType = itemView.findViewById(R.id.RVContainerType);
            stock = itemView.findViewById(R.id.RVStock);

        }

    }

    static class ViewHolderShopping extends RecyclerView.ViewHolder  {
        TextView shoppingName, amount;

        ViewHolderShopping(View itemView) {
            super(itemView);
            shoppingName = itemView.findViewById(R.id.rvShoppingName);
            amount = itemView.findViewById(R.id.rvShoppingAmount);

        }
    }

}

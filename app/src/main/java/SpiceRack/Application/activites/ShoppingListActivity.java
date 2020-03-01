package SpiceRack.Application.activites;


import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import SpiceRack.Application.database.ShoppingDao;
import SpiceRack.Application.database.ShoppingItem;
import SpiceRack.Application.database.ShoppingListAdapter;
import SpiceRack.Application.database.Spice;
import SpiceRack.Application.database.SpiceDatabase;
import SpiceRack.Application.utilities.Navigation;
import SpiceRack.R;

import java.util.List;

public class ShoppingListActivity extends AppCompatActivity implements ShoppingListAdapter.OnItemClickListener {

    private static final int SPICE_NORMAL = 1;
    private static final int SPICE_STRIKE = 2;
    private static final int SHOPPING_NORMAL = 3;
    private static final int SHOPPING_STRIKE = 4;
    private GestureDetectorCompat myGesture;
    int amount;
    private View.OnClickListener myClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.btnAddItem) {
                addItemToShopping();
            } else {
                clearList();
            }
            updateUI();
        }
    };

    EditText editName, editAmount;
    Button addItem, clearList;
    String name;
    SpiceDatabase mySpiceRackDb;
    ShoppingDao myShoppingDao;
    List<Spice> spiceList;
    List<ShoppingItem> shoppingItem;
    ShoppingItem Item;
    RecyclerView listShopping;
    ShoppingListAdapter adapterShopping;
    Navigation nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppinglist_activity);

        editName = findViewById(R.id.editName);
        editAmount = findViewById(R.id.editAmount);

        addItem = findViewById(R.id.btnAddItem);
        clearList = findViewById(R.id.btnClearList);
        listShopping = findViewById(R.id.rvShoppingList);
        addItem.setOnClickListener(myClick);
        clearList.setOnClickListener(myClick);

        mySpiceRackDb = SpiceDatabase.getINSTANCE(this);
        myShoppingDao = mySpiceRackDb.getShoppingDao();
        spiceList = mySpiceRackDb.getSpiceDao().getSpiceByStock(0);

        nav = new Navigation(this);
        myGesture = new GestureDetectorCompat(this, new MyGestureListener());

        new ItemTouchHelper(itemTouchCallback).attachToRecyclerView(listShopping);

        addZeroStockSpices(spiceList);
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listShopping.setLayoutManager(layoutManager);
        shoppingItem = myShoppingDao.getAllShoppingItems();
        adapterShopping = new ShoppingListAdapter(shoppingItem, this);
        listShopping.setAdapter(adapterShopping);
    }

    private void updateUI() {
        shoppingItem = myShoppingDao.getAllShoppingItems();
        adapterShopping = new ShoppingListAdapter(shoppingItem, this);
        listShopping.setAdapter(adapterShopping);
    }

    private void addZeroStockSpices(List<Spice> spiceList) {
        for (Spice element : spiceList) {
            String name = element.getSpiceName();
            String containerType = element.getContainerType();
            String brand = element.getBrand();
            Item = new ShoppingItem(name, 1, containerType, brand, SPICE_NORMAL, true);
            myShoppingDao.insertItem(Item);
        }
    }

    public void addItemToShopping() {
        name = editName.getText().toString();
        amount = Integer.parseInt(editAmount.getText().toString());
        Item = new ShoppingItem(name, amount, "N/A", "N/A", SHOPPING_NORMAL, false);
        myShoppingDao.insertItem(Item);
    }

    public void clearList() {
        List<ShoppingItem> list = myShoppingDao.getAllShoppingItems();
        for (ShoppingItem element : list) {
            if (element.getViewType() == 2 || element.getViewType() == 4) {
                myShoppingDao.deleteItem(element);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.myGesture.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onItemClick(int position) {
        ShoppingItem item = shoppingItem.get(position);
        switch (item.getViewType()) {
            case SPICE_NORMAL:
                item.setViewType(SPICE_STRIKE);
                break;
            case SPICE_STRIKE:
                item.setViewType(SPICE_NORMAL);
                break;
            case SHOPPING_NORMAL:
                item.setViewType(SHOPPING_STRIKE);
                break;
            case SHOPPING_STRIKE:
                item.setViewType(SHOPPING_NORMAL);
                break;
        }
        myShoppingDao.upDate(shoppingItem.get(position));
        updateUI();
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
            float diffY = moveEvent.getY() - downEvent.getY();
            float diffX = moveEvent.getX() - downEvent.getX();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                //right or left swipe
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        nav.homePage();
                    }
                }
            }
            return true;
        }
    }

    ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            ShoppingItem item = shoppingItem.get(viewHolder.getAdapterPosition());
            if (direction == ItemTouchHelper.RIGHT) {
                myShoppingDao.deleteItem(item);
                updateUI();
                Toast.makeText(ShoppingListActivity.this, item.getItemName() + " is Deleted", Toast.LENGTH_SHORT).show();
            } else {
                String name = editName.getText().toString();
                String amount = editAmount.getText().toString();
                if (name.isEmpty() || amount.isEmpty()) {
                    Toast.makeText(ShoppingListActivity.this, "Error - name or amount is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    item.setItemName(name);
                    item.setAmount(Integer.parseInt(amount));
                    myShoppingDao.upDate(item);
                    Toast.makeText(ShoppingListActivity.this, item.getItemName() + " has been updated", Toast.LENGTH_SHORT).show();
                }
                updateUI();
            }
        }
    };
}
package SpiceRack.Application.activites;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Objects;
import SpiceRack.Application.database.ShoppingDao;
import SpiceRack.Application.database.ShoppingItem;
import SpiceRack.Application.database.ShoppingListAdapter;
import SpiceRack.Application.database.Spice;
import SpiceRack.Application.database.SpiceDatabase;
import SpiceRack.Application.utilities.Navigation;
import SpiceRack.R;
import SpiceRack.databinding.ShoppinglistActivityBinding;
import static android.view.View.VISIBLE;

/**
 *<p>
 *      ShoppingListActivity class creates and displays a recyclerview shopping list. This list is
 *      created from zero stock spices in the inventory and manually added items. The recyclerview
 *      allows for onTouch items to strike through the item on the list. Any items with this strike
 *      through are toggled to be removed when the clear list button is pressed. The items on the
 *      list can also be swiped left or right, either removing the item form the list or updating it
 *      with information typed in the name and amount fields of the page.
 *</p>
 *
 *  @author Michael
 *  @author Astrid
 *  @version 1.0
 *  @since 05.03.2020
 */
public class ShoppingListActivity extends AppCompatActivity implements ShoppingListAdapter.OnItemClickListener {
    /**
     * <p>
     *      Static parameters used to identify the viewType for each item. This decides on which
     *      layout is created in the recyclerView.
     * </p>
     */
    private static final int SPICE_NORMAL = 1;
    private static final int SPICE_STRIKE = 2;
    private static final int SHOPPING_NORMAL = 3;
    private static final int SHOPPING_STRIKE = 4;
    private GestureDetectorCompat myGesture;
    private Parcelable recyclerViewState;
    private View.OnClickListener myClick = new View.OnClickListener() {
        /**
         *<p>
         *     onCLick is called when a view is clicked. the id of the clicked view will dictate the
         *     code block to be ran.
         *</p>
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.btnAddItem) {
                addItemToShopping();
            } else if(v.getId() ==R.id.ibInformation){
                isVisible();
            }else {
                clearList();
            }
            updateUI();
        }
    };

    private ShoppingDao myShoppingDao;
    private List<ShoppingItem> shoppingItem;
    private ShoppingItem Item;
    private RecyclerView listShopping;
    private ShoppingListAdapter adapterShopping;
    private Navigation nav;
    ShoppinglistActivityBinding shoppingLayout;

    /**
     * <p>
     *     onCreate() sets the listeners to the ShoppingList activity and instantiates the database.
     *     it then creates a Spice list based on the stock levels of the inventory being 0. New
     *     navigation and gesture objects are created along with a ItemTouchListener is attached to
     *     the recyclerview.
     * </p>
     * @param savedInstanceState saved instance state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shoppingLayout = ShoppinglistActivityBinding.inflate(LayoutInflater.from(this));
        setContentView(shoppingLayout.getRoot());

        shoppingLayout.ibInformation.setOnClickListener(myClick);
        listShopping = shoppingLayout.rvShoppingList;
        shoppingLayout.btnAddItem.setOnClickListener(myClick);
        shoppingLayout.btnClearList.setOnClickListener(myClick);

        SpiceDatabase mySpiceRackDb = SpiceDatabase.getINSTANCE(this);
        myShoppingDao = mySpiceRackDb.getShoppingDao();
        List<Spice> spiceList = mySpiceRackDb.getSpiceDao().getSpiceByStock(0);
        nav = new Navigation(this);
        myGesture = new GestureDetectorCompat(this, new MyGestureListener());

        new ItemTouchHelper(itemTouchCallback).attachToRecyclerView(listShopping);

        addZeroStockSpices(spiceList);
        initRecyclerView();
    }

    /**
     * <p>
     *     initRecyclerView handlers the initial creation of the shopping list recyclerview and sets
     *     the adaptor.
     * </p>
     */

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listShopping.setLayoutManager(layoutManager);
        shoppingItem = myShoppingDao.getAllShoppingItems();
        adapterShopping = new ShoppingListAdapter(shoppingItem, this);
        listShopping.setAdapter(adapterShopping);
    }

    /**
     * <p>
     *     updateUI() updates the recyclerview after a change of data is carried out on the
     *     shopping list. The adaptor is reset.
     * </p>
     */

    private void updateUI() {
        shoppingItem = myShoppingDao.getAllShoppingItems();
        adapterShopping = new ShoppingListAdapter(shoppingItem, this);
        listShopping.setAdapter(adapterShopping);
        Objects.requireNonNull(listShopping.getLayoutManager()).onRestoreInstanceState(recyclerViewState);
    }

    /**
     *<p>
     *      adZeroStockSpices() takes a list of zero quantity spice and adds them to the shopping
     *      list table within the database.
     *</p>
     * @param spiceList this is a list of spices from the inventory that have zero stock associated.
     */

    private void addZeroStockSpices(List<Spice> spiceList) {
        for (Spice element : spiceList) {
            String name = element.getSpiceName();
            String containerType = element.getContainerType();
            String brand = element.getBrand();
            Item = new ShoppingItem(name, 1, containerType, brand, SPICE_NORMAL, true);
            myShoppingDao.insertItem(Item);
        }
    }

    /**
     *<p>
     *     addItemToShopping() takes the values from the name and amount editText fields and saves
     *     them as a new object within the shopping list table.
     *</p>
     */

    public void addItemToShopping() {
        String name = shoppingLayout.editName.getText().toString();
        String amount = shoppingLayout.editAmount.getText().toString();

        if(name.isEmpty() || amount.isEmpty()){
            Toast.makeText(this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
        }else {
            if(Integer.parseInt(amount) >=0 && Integer.parseInt(amount) <= 99) {
                ShoppingItem item = myShoppingDao.getItemName(name);
                if(item == null) {
                    Item = new ShoppingItem(name, Integer.parseInt(amount), "N/A", "N/A", SHOPPING_NORMAL, false);
                    myShoppingDao.insertItem(Item);
                } else{
                    Toast.makeText(this, "This item already exists.", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Amount has to be between 0 and 99.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * <p>
     *     clearList() takes the list of shopping items and checks the viewType of each item. 2 and 4
     *     symbolise the view with a strike through therefore is selected for deletion.
     * </p>
     */

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

    /**
     *<p>
     *     when the onItemClick is called it checks the position within the shopping list and saves
     *     that object. The object is then queried for its viewType, depending on this value, the
     *     number will be changed and updated to toggle the strike through on or off. The location
     *     of the item clicked is saved so that when the recyclerview is refreshed the scroll
     *     location is in the same place.
     *</p>
     * @param position is the location within the list of items displayed on the recyclerview.
     */

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

        recyclerViewState = Objects.requireNonNull(listShopping.getLayoutManager()).onSaveInstanceState();
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

    /**
     *<p>
     *     new ItemTouchHelper is created to handle the actions to be done after an item in the
     *     shopping list is swiped left or right. If the object is swiped right the item is deleted.
     *     If swiped left the values from name and amount fields is used to update the item object.
     *</p>
     */
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
                String name = shoppingLayout.editName.getText().toString();
                String amount = shoppingLayout.editAmount.getText().toString();
                if (name.isEmpty() || amount.isEmpty()) {
                    Toast.makeText(ShoppingListActivity.this, "Error - name or amount is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    ShoppingItem tempItem = myShoppingDao.getItemName(name);
                    if(item.getItemName().equals(tempItem.getItemName())){
                        item.setItemName(name);
                        item.setAmount(Integer.parseInt(amount));
                        myShoppingDao.upDate(item);
                        Toast.makeText(ShoppingListActivity.this, item.getItemName() + " has been updated", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ShoppingListActivity.this, "Item already exists modify that item!", Toast.LENGTH_SHORT).show();
                    }
                }
                updateUI();
            }
        }
    };

    public void isVisible(){

        if(shoppingLayout.tvHomeInstruction.getVisibility() == VISIBLE){
            shoppingLayout.tvHomeInstruction.setVisibility(View.INVISIBLE);

        }else{
            shoppingLayout.tvHomeInstruction.setVisibility(VISIBLE);
        }
    }
}
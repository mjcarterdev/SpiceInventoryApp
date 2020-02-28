package SpiceRack.Application.activites;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
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

public class ShoppingListActivity extends AppCompatActivity {

    EditText editName, editAmount;
    Button addItem, clearList;
    String name, amount;
    SpiceDatabase mySpiceRackDb;
    ShoppingDao myShoppingDao;
    List<Spice> spiceList;
    ShoppingItem Item;
    RecyclerView listShopping;
    RecyclerView.Adapter adapterShopping;
    Navigation nav;
    private GestureDetectorCompat myGesture;

    private View.OnClickListener myClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.btnAddItem){
                addSpiceToShopping();
                //Toast.makeText(ShoppingListActivity.this, "BLA", Toast.LENGTH_SHORT).show();
            } else {
                clearList();
            }

            updateUI();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppinglist_activity);

        listShopping = findViewById(R.id.rvShoppingList);

        editName = findViewById(R.id.editName);
        editAmount = findViewById(R.id.editAmount);

        addItem = findViewById(R.id.btnAddItem);
        clearList = findViewById(R.id.btnClearList);

        addItem.setOnClickListener(myClick);
        clearList.setOnClickListener(myClick);

        mySpiceRackDb = SpiceDatabase.getINSTANCE(this);
        myShoppingDao = mySpiceRackDb.getShoppingDao();
        spiceList = mySpiceRackDb.getSpiceDao().getSpiceByStock(0);

        nav = new Navigation(this);
        myGesture = new GestureDetectorCompat(this, new MyGestureListener());

        for (Spice element:spiceList) {
            String name = element.getSpiceName();
            Item = new ShoppingItem(name, "1");
            myShoppingDao.insertItem(Item);
        }

        listShopping.setLayoutManager(new LinearLayoutManager(this));
        updateUI();
    }

    public void addSpiceToShopping() {
        name = editName.getText().toString();
        amount = editAmount.getText().toString();

        Item = new ShoppingItem(name, amount);

        myShoppingDao.insertItem(Item);
    }

    public void clearList(){
        myShoppingDao.deleteList();
    }

    public void updateUI() {
        List<ShoppingItem> shoppingItem = myShoppingDao.getAllShoppingItems();
        adapterShopping = new ShoppingListAdapter(shoppingItem);
        listShopping.setAdapter(adapterShopping);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.myGesture.onTouchEvent(event);
        return super.onTouchEvent(event);
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
}
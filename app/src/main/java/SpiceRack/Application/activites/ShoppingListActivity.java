package SpiceRack.Application.activites;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import SpiceRack.Application.database.ShoppingDao;
import SpiceRack.Application.database.ShoppingItem;
import SpiceRack.Application.database.ShoppingListAdapter;
import SpiceRack.Application.database.SpiceDatabase;
import SpiceRack.R;

import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    EditText editName, editAmount;
    Button addItem, clearList;
    String name, amount;
    SpiceDatabase mySpiceRackDb;
    ShoppingDao myShoppingDao;
    ShoppingItem Item;
    RecyclerView listShopping;
    RecyclerView.Adapter adapterShopping;

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

    }

    public void updateUI() {
        List<ShoppingItem> shoppingItem = myShoppingDao.getAllShoppingItems();
        adapterShopping = new ShoppingListAdapter(shoppingItem);
        listShopping.setAdapter(adapterShopping);
    }

}
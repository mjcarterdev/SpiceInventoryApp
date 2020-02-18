package com.example.spicesinventory.activites;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spice_sqlite_test.R;
import com.example.spicesinventory.database.ShoppingDao;
import com.example.spicesinventory.database.ShoppingItem;
import com.example.spicesinventory.database.ShoppingListAdapter;
import com.example.spicesinventory.database.Spice_Database;

import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    EditText editName, editAmount;
    Button addItem, clearList;
    String name, amount;
    Spice_Database mySpiceRackDb;
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

        mySpiceRackDb = Spice_Database.getINSTANCE(this);
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
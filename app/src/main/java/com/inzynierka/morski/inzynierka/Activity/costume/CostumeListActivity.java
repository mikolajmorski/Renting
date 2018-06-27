package com.inzynierka.morski.inzynierka.Activity.costume;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.inzynierka.morski.inzynierka.DataBase.Costume.Costume;
import com.inzynierka.morski.inzynierka.DataBase.Costume.CostumeDataSource;
import com.inzynierka.morski.inzynierka.DataBase.rentedItems.RentedItems;
import com.inzynierka.morski.inzynierka.DataBase.rentedItems.RentedItemsDataSource;
import com.inzynierka.morski.inzynierka.MySimpleArrayAdapter;
import com.inzynierka.morski.inzynierka.R;

import java.util.ArrayList;
import java.util.List;

public class CostumeListActivity extends AppCompatActivity {
    private CostumeDataSource dataSource;
    private RentedItemsDataSource rentedItemsDataSource;
    private ListView listCostumes;
    private MySimpleArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costume_list);

        dataSource = new CostumeDataSource(this);
        dataSource.open();
        rentedItemsDataSource = new RentedItemsDataSource(this);
        rentedItemsDataSource.open();

        listCostumes = (ListView) findViewById(R.id.listViewAllCostumes);
        List<String> values = new ArrayList<>();
        final List<Long> costumeIds = new ArrayList<>();
        for(Costume kostium: dataSource.getAllCostumes()){
            Long costumeId = kostium.getId();
            ArrayList<RentedItems> rentedItems = rentedItemsDataSource.getRentedItemsOfCostume(costumeId);
            boolean status = checkIsRent(rentedItems);

            //RentedItems rentedItem = rentedItemsDataSource.getRentedItem(costumeId);
            //rentedItemsDataSource.getRentedItemsOfOneCostume(costumeId);
            String opis="";
            if(status){
                opis="WYPOŻYCZONY";
            }
            else{
                opis="DOSTĘPNY";
            }
            values.add(kostium.getName() + " " + opis);
            costumeIds.add(costumeId);
        }
        adapter = new MySimpleArrayAdapter(this, values);
        listCostumes.setAdapter(adapter);

        listCostumes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                Long toPut = costumeIds.get((pos));
                //RentedItems rentedItem = rentedItemsDataSource.getRentedItem(toPut);
                ArrayList<RentedItems> rentedItems = rentedItemsDataSource.getRentedItemsOfCostume(toPut);
                boolean status = checkIsRent(rentedItems);
                if (status) {
                    Snackbar snackbar = Snackbar.make(arg1, "KOSTIUM WYPOZYCZONY NIE MOZNA EDYTOWAC", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    Intent myIntent;
                    myIntent = new Intent(CostumeListActivity.this, CostumeEditActivity.class);
                    myIntent.putExtra("clientId", toPut);
                    CostumeListActivity.this.startActivity(myIntent);

                }

                return true;
            }
        });
    }

    private boolean checkIsRent(ArrayList<RentedItems> rentedItems){
        for(RentedItems item : rentedItems){
            if(item.getPosition()==1)
                return true;
        }
        return false;
    }



}

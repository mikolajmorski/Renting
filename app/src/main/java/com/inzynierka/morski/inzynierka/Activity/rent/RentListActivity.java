package com.inzynierka.morski.inzynierka.Activity.rent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.inzynierka.morski.inzynierka.DataBase.Client.Client;
import com.inzynierka.morski.inzynierka.DataBase.Client.ClientDataSource;
import com.inzynierka.morski.inzynierka.DataBase.rent.Rent;
import com.inzynierka.morski.inzynierka.DataBase.rent.RentDataSource;
import com.inzynierka.morski.inzynierka.R;

import java.util.ArrayList;
import java.util.List;

public class RentListActivity extends AppCompatActivity {
    private ListView listRents;
    private ArrayAdapter<String> adapter;
    private RentDataSource rentDataSource;
    private ClientDataSource clientDataSource;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_list);

        rentDataSource = new RentDataSource(this);
        rentDataSource.open();
        clientDataSource = new ClientDataSource(this);
        clientDataSource.open();

        listRents = (ListView) findViewById(R.id.listViewRentList);
        List<String> values = new ArrayList<>();
        final List<Long> rentIds = new ArrayList<>();
        for(Rent rent: rentDataSource.getAllRents()){
            Client client = clientDataSource.getClient(rent.getClientId());
            values.add(client.getName() + " " + client.getLastName() + " " + rent.getDateRent());
            rentIds.add(rent.getId());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        listRents.setAdapter(adapter);

        listRents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                Intent myIntent;
                myIntent = new Intent(com.inzynierka.morski.inzynierka.Activity.rent.RentListActivity.this, RentDetailsActivity.class);
                Long toPut = rentIds.get((pos));
                myIntent.putExtra("rentId", toPut);
                com.inzynierka.morski.inzynierka.Activity.rent.RentListActivity.this.startActivity(myIntent);
                return true;
            }
        });
    }
}

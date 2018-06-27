package com.inzynierka.morski.inzynierka.Activity.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.inzynierka.morski.inzynierka.DataBase.Client.Client;
import com.inzynierka.morski.inzynierka.DataBase.Client.ClientDataSource;
import com.inzynierka.morski.inzynierka.R;

import java.util.ArrayList;
import java.util.List;

public class ClientListActivity extends AppCompatActivity {

    private ListView listClients;
    private ArrayAdapter<String> adapter;
    private ClientDataSource dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        dataSource = new ClientDataSource(this);
        dataSource.open();


        listClients = (ListView) findViewById(R.id.listViewClients);
        List<String> values = new ArrayList<>();
        final List<Long> clientIds = new ArrayList<>();
        for(Client klient: dataSource.getAllClients()){
            values.add(klient.getName() + " " + klient.getLastName());
            clientIds.add(klient.getId());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        listClients.setAdapter(adapter);

        listClients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                Intent myIntent;
                myIntent = new Intent(ClientListActivity.this, ClientEditActivity.class);
                Long toPut = clientIds.get((pos));
                myIntent.putExtra("clientId", toPut);
                ClientListActivity.this.startActivity(myIntent);

                return true;
            }
        });

    }



}

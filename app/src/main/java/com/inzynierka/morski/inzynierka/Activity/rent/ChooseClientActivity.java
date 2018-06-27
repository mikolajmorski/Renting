package com.inzynierka.morski.inzynierka.Activity.rent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.inzynierka.morski.inzynierka.DataBase.Client.Client;
import com.inzynierka.morski.inzynierka.DataBase.Client.ClientDataSource;
import com.inzynierka.morski.inzynierka.R;

import java.util.ArrayList;
import java.util.List;

public class ChooseClientActivity extends AppCompatActivity {

    private ClientDataSource dataSource;
    private ListView listClients;
    private ArrayList<Long> clientsId;
    private ArrayAdapter<String> adapter;
    private String choosenClient;
    private Snackbar snackbar;
    private Long chooseClientId;

    private  EditText edChoosenclient;

    ArrayList<String> labelSelectedCostumes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_client);

        labelSelectedCostumes = getIntent().getExtras().getStringArrayList("labelSelectedCostumes");


        dataSource = new ClientDataSource(this);
        dataSource.open();

        clientsId = new ArrayList<>();
        listClients = (ListView) findViewById(R.id.listViewClientList);
        List<String> values = new ArrayList<>();
        for(Client klient: dataSource.getAllClients()){
            values.add(klient.getName() + " " + klient.getLastName());
            clientsId.add(klient.getId());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        listClients.setAdapter(adapter);

        edChoosenclient = (EditText)findViewById(R.id.editTextChoosenClient);
        listClients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {

                choosenClient = adapter.getItem(pos);
                chooseClientId = clientsId.get(pos);
                edChoosenclient.setText(choosenClient);
                return true;
            }
        });
    }

    public void onClickButtonChooseClient (View view){
        Intent myIntent;
                if(edChoosenclient.getText().toString().matches("")){
                    snackbar = Snackbar.make(view, "NIE WYBRANO KLIENTA", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else{
                    myIntent = new Intent(com.inzynierka.morski.inzynierka.Activity.rent.ChooseClientActivity.this, RentSummaryActivity.class);
                    myIntent.putExtra("choosenClient", choosenClient);
                    myIntent.putExtra("choosenClientId", chooseClientId);
                    myIntent.putExtra("labelSelectedCostumes", labelSelectedCostumes);
                    com.inzynierka.morski.inzynierka.Activity.rent.ChooseClientActivity.this.startActivity(myIntent);
                }
    }



}

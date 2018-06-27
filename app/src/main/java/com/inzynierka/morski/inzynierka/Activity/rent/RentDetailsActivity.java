package com.inzynierka.morski.inzynierka.Activity.rent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.inzynierka.morski.inzynierka.DataBase.Client.Client;
import com.inzynierka.morski.inzynierka.DataBase.Client.ClientDataSource;
import com.inzynierka.morski.inzynierka.DataBase.Costume.Costume;
import com.inzynierka.morski.inzynierka.DataBase.Costume.CostumeDataSource;
import com.inzynierka.morski.inzynierka.DataBase.rent.Rent;
import com.inzynierka.morski.inzynierka.DataBase.rent.RentDataSource;
import com.inzynierka.morski.inzynierka.DataBase.rentedItems.RentedItems;
import com.inzynierka.morski.inzynierka.DataBase.rentedItems.RentedItemsDataSource;
import com.inzynierka.morski.inzynierka.Exeptions.NoCostumeInDataBaseException;
import com.inzynierka.morski.inzynierka.R;

import java.util.ArrayList;

public class RentDetailsActivity extends AppCompatActivity {
    TextView editTextName;
    TextView editTextRentDate;
    TextView editTextReturnDate;
    TextView editTextPrice;
    TextView editTextStatus;
    ListView listViewCostumes;

    private RentDataSource rentDataSource;
    private ClientDataSource clientDataSource;
    private RentedItemsDataSource rentedItemsDataSource;
    private CostumeDataSource costumeDataSource;

    private Rent rent;
    private Client client;

    private Long rentId;
    private ArrayAdapter<String> adapter;
    private ArrayList<Costume> listCostumes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_details);

        ascribeFields();
        rentId = getIntent().getExtras().getLong("rentId");
        initiateDataBase();

        rent = rentDataSource.getRent(rentId);
        client = clientDataSource.getClient(rent.getClientId());
        setDataToFields(rent, client);


        listViewCostumes = (ListView) findViewById(R.id.listViewRentDetailCostumes);
        fillList();
        ArrayList<String> values = new ArrayList();
        for(Costume costume: listCostumes){
            values.add(costume.getName());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        listViewCostumes.setAdapter(adapter);

    }

    private void initiateDataBase(){
        rentDataSource = new RentDataSource(this);
        rentDataSource.open();
        clientDataSource = new ClientDataSource(this);
        clientDataSource.open();
        rentedItemsDataSource = new RentedItemsDataSource(this);
        rentedItemsDataSource.open();
        costumeDataSource = new CostumeDataSource(this);
        costumeDataSource.open();
    }

    private void ascribeFields(){
        editTextName = (TextView)findViewById(R.id.textViewRentDetailNameLastName);
        editTextRentDate = (TextView)findViewById(R.id.textViewRentDetailRentDate);
        editTextReturnDate = (TextView)findViewById(R.id.textViewRentDetailReturnDate);
        editTextPrice = (TextView)findViewById(R.id.textViewRentDetailPrice);
        editTextStatus = (TextView)findViewById(R.id.textViewRentDetailStatus);
    }

    private void setDataToFields(Rent rent, Client client){
        editTextName.setText(client.getName() + " " + client.getLastName());
        editTextRentDate.setText("Wypożyczono: " + rent.getDateRent());
        editTextReturnDate.setText("Zwrot: " + rent.getDateReturn());
        editTextPrice.setText("Cena: " + rent.getPrice());
        editTextStatus.setText("Status: " + rent.getStatus());
    }

    private void fillList(){
        listCostumes = new ArrayList<>();
        for(RentedItems rentedItem: rentedItemsDataSource.getAllRentedItems()){
            if(rentedItem.getRentId() == rent.getId()){
                try {
                    listCostumes.add(costumeDataSource.getCostume(rentedItem.getCostumeId()) );
                } catch (NoCostumeInDataBaseException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}

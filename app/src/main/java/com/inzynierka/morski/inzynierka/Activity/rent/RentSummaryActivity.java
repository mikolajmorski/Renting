package com.inzynierka.morski.inzynierka.Activity.rent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.inzynierka.morski.inzynierka.Activity.MainActivity;
import com.inzynierka.morski.inzynierka.DataBase.Client.Client;
import com.inzynierka.morski.inzynierka.DataBase.Client.ClientDataSource;
import com.inzynierka.morski.inzynierka.DataBase.Costume.Costume;
import com.inzynierka.morski.inzynierka.DataBase.Costume.CostumeDataSource;
import com.inzynierka.morski.inzynierka.DataBase.rent.Rent;
import com.inzynierka.morski.inzynierka.DataBase.rent.RentDataSource;
import com.inzynierka.morski.inzynierka.DataBase.rentedItems.RentedItemsDataSource;
import com.inzynierka.morski.inzynierka.Exeptions.NoCostumeInDataBaseException;
import com.inzynierka.morski.inzynierka.R;
import com.inzynierka.morski.inzynierka.RentStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RentSummaryActivity extends AppCompatActivity {

    private ClientDataSource clientDataSource;
    private RentDataSource  rentDataSource;
    private RentedItemsDataSource rentedItemsDataSource;
    private CostumeDataSource costumeDataSource;

    private ListView costumeList;
    private ArrayAdapter<String> adapter;

    Client client = null;

    String choosenClient;
    Long choosenClientId;
    Snackbar snackbar;
    private ArrayList<String> labelSelectedCostumes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_summary);

        choosenClient = getIntent().getExtras().getString("choosenClient");
        choosenClientId = getIntent().getExtras().getLong("choosenClientId");
        labelSelectedCostumes = getIntent().getExtras().getStringArrayList("labelSelectedCostumes");



        clientDataSource = new ClientDataSource(this);
        clientDataSource.open();

        rentDataSource= new RentDataSource(this);
        rentDataSource.open();

        rentedItemsDataSource = new RentedItemsDataSource(this);
        rentedItemsDataSource.open();

        costumeDataSource = new CostumeDataSource(this);
        costumeDataSource.open();

        setRentDate();
        setRentClient(choosenClient);

        costumeList = (ListView) findViewById(R.id.listViewRentSummaryCostumeList);
        List<Costume> costumes = new ArrayList<>();
        for(String label: labelSelectedCostumes){
            try {
                costumes.add(costumeDataSource.getCostume(label));
            } catch (NoCostumeInDataBaseException e) {
                e.printStackTrace();
            }
        }

        List<String> costumeNames = new ArrayList<>();
        for(Costume costume: costumes){
            costumeNames.add(costume.getName());
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, costumeNames);
        costumeList.setAdapter(adapter);

        generateClient();
    }

    private void setRentDate(){
        EditText editTextRentDate = (EditText)findViewById(R.id.editTextRentDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(new Date());
        editTextRentDate.setText(currentDateandTime);
    }

    private void setRentClient(String choosenClient){
        EditText editTextBorrower = (EditText)findViewById(R.id.editTextBorrower);
        editTextBorrower.setText(choosenClient);
    }

    public void onClickButtonPerformRent (View view){
        //TODO Wyswietlac komunikat o zakonczonym procesie i przejsce do menu glownego
        if(!returnDateIsEmpty()){
            if(!priceIsEmpty()){
                Wrapper wrapper = new Wrapper();
                Rent rent = rentDataSource.createRent(client.getId(), wrapper.getRentDate(), wrapper.getReturnDate(), null, wrapper.getPrice(), RentStatus.AKTYWNE.wartosc);


                for(int i=0; i<labelSelectedCostumes.size(); i++){
                    try {
                        Costume costume = costumeDataSource.getCostume(labelSelectedCostumes.get(i));
                        rentedItemsDataSource.createRentedItem(rent.getId(), costume.getId(), 1);

                    } catch (NoCostumeInDataBaseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Intent myIntent;
        myIntent = new Intent(com.inzynierka.morski.inzynierka.Activity.rent.RentSummaryActivity.this, MainActivity.class);
        com.inzynierka.morski.inzynierka.Activity.rent.RentSummaryActivity.this.startActivity(myIntent);
    }

    private class Wrapper{

       private String rentDate;
       private String returnDate;
       private int price;

        public Wrapper(){
            generateRntDate();
            generateReturnDate();
            generatePrice();
        }

        private void generateRntDate(){
            EditText editTextRentDate = (EditText)findViewById(R.id.editTextRentDate);
            rentDate =  editTextRentDate.getText().toString();
        }

        private void generateReturnDate(){
            EditText editTextReturnDate = (EditText)findViewById(R.id.editTextReturnDate);
            returnDate = editTextReturnDate.getText().toString();
        }

        private void generatePrice(){
            EditText editTextPrice = (EditText)findViewById(R.id.editTextPrice);
            price = Integer.parseInt(editTextPrice.getText().toString());
        }


        public String getRentDate() {
            return rentDate;
        }

        public String getReturnDate() {
            return returnDate;
        }

        public int getPrice() {
            return price;
        }

    }

    private boolean returnDateIsEmpty(){
        EditText editTextReturnDate = (EditText)findViewById(R.id.editTextReturnDate);
        String returnDate = editTextReturnDate.getText().toString();
        if(TextUtils.isEmpty(returnDate)) {
            editTextReturnDate.setError("PROSZE PODAC DATE ZWROTU");
            return true;
        }
        return false;
    }

    private boolean priceIsEmpty(){
        EditText editTextPrice = (EditText)findViewById(R.id.editTextPrice);
        String price = editTextPrice.getText().toString();
        if(TextUtils.isEmpty(price)) {
            editTextPrice.setError("PROSZE PODAC CENĘ WYPOŻYCZENIA");
            return true;
        }
        return false;
    }

    private void generateClient(){
        for(Client klient : clientDataSource.getAllClients()){
            if(klient.getId()==choosenClientId){
                client = klient;
                return;
            }
        }
    }

}

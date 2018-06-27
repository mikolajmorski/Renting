package com.inzynierka.morski.inzynierka.Activity.returnCostume;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.inzynierka.morski.inzynierka.Activity.MainActivity;
import com.inzynierka.morski.inzynierka.Activity.rent.ScanNFCActivity;
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
import com.inzynierka.morski.inzynierka.RentStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReturnActivity extends AppCompatActivity {

    private CostumeDataSource costumeDataSource;
    private RentedItemsDataSource rentedItemsDataSource;
    private RentDataSource rentDataSource;
    private ClientDataSource clientDataSource;

    private Rent rent;
    private RentedItems activRentedItem;

    private ListView listReturn;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        costumeDataSource = new CostumeDataSource(this);
        costumeDataSource.open();
        rentedItemsDataSource = new RentedItemsDataSource(this);
        rentedItemsDataSource.open();
        rentDataSource = new RentDataSource(this);
        rentDataSource.open();
        clientDataSource = new ClientDataSource(this);
        clientDataSource.open();

        listReturn = (ListView) findViewById(R.id.listViewReturnCostumes);
    }

    public void onClickButtonReturnMain (View view){
    Intent myIntent;
        switch (view.getId()){
            case R.id.btnReturnScanCode:
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
                break;

            case R.id.btnReturnScanNFC:
                Intent nfcIntent = new Intent(ReturnActivity.this, ScanNFCActivity.class);
                startActivityForResult(nfcIntent, 1);
                break;

            case R.id.btnPerformReturn:
                performReturn(rent);
                myIntent = new Intent(ReturnActivity.this, MainActivity.class);
                ReturnActivity.this.startActivity(myIntent);
                break;
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == 0 || requestCode == 1){
            handleScannCode(intent);
        }
    }

    private void handleScannCode(Intent intent){
        String wynikSkanowania = intent.getStringExtra("SCAN_RESULT");
        Costume costume = getCostume(wynikSkanowania);
        if(costume!=null){
            ArrayList<RentedItems> rentedItems = rentedItemsDataSource.getRentedItemsOfCostume(costume.getId());
            boolean status = checkIsRent(rentedItems);
            if(status){
                fillTheList(costume);
            }
            else{
                Toast.makeText(getBaseContext(), "KOSTIUM JUŻ ZWRÓCONY", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(getBaseContext(), "KOSTIUM NIE ISTNIEJE", Toast.LENGTH_LONG).show();
        }
    }

    private Costume getCostume(String label){
        Costume costume;
        try {
            costume = costumeDataSource.getCostume(label);
        } catch (NoCostumeInDataBaseException e) {
            e.printStackTrace();
            return null;
        }
        return costume;
    }



    private void fillTheList(Costume costume){
        rent = null;
        List<String> values = new ArrayList<>();
        //TODO sprawdzic czy to nie bedzie wywalac bledow
        Long rentId = activRentedItem.getRentId();
        Rent rent = rentDataSource.getRent(rentId);
        Client client = clientDataSource.getClient(rent.getClientId());

        values.add(client.getName() + " " + client.getLastName());
        values.add("Wypozyczono: " + rent.getDateRent());
        values.add("Zwrot: " + rent.getDateReturn());
        for(RentedItems item: rentedItemsDataSource.getAllRentedItems()){
            if(item.getRentId()==rentId){
                Costume returnedCostume = null;
                try {
                    returnedCostume = costumeDataSource.getCostume(item.getCostumeId());
                } catch (NoCostumeInDataBaseException e) {
                    e.printStackTrace();
                }
                values.add(returnedCostume.getName());
            }
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        listReturn.setAdapter(adapter);
        findViewById(R.id.btnPerformReturn).setEnabled(true);
        this.rent = rent;
    }

    private void performReturn(Rent rent){
        rentDataSource.updateRent(mapFields(),rent.getId());
        Long rentId = rent.getId();
        for(RentedItems rentedItem: rentedItemsDataSource.getAllRentedItems()){
            if(rentedItem.getRentId()==rentId){
                rentedItemsDataSource.updateRentedItem(0, rentedItem.getId());
            }
        }
    }

    private Map<String, String> mapFields(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(new Date());
        Map<String, String> rentData = new HashMap<>();
        rentData.put("clientId", String.valueOf(rent.getClientId()) );
        rentData.put("dateRent", rent.getDateRent());
        rentData.put("dateReturn", rent.getDateReturn());
        rentData.put("dateTrueReturn", currentDateandTime); //ustawiamy date
        rentData.put("price", String.valueOf(rent.getPrice()));
        rentData.put("status", RentStatus.ZAKONCZONE.wartosc);
        return rentData;
    }

    private boolean checkIsRent(ArrayList<RentedItems> rentedItems){
        for(RentedItems item : rentedItems){
            if(item.getPosition()==1){
                activRentedItem = item;
                return true;
            }

        }
        return false;
    }

}

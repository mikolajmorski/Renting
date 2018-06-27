package com.inzynierka.morski.inzynierka.Activity.rent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.inzynierka.morski.inzynierka.DataBase.Costume.Costume;
import com.inzynierka.morski.inzynierka.DataBase.Costume.CostumeDataSource;
import com.inzynierka.morski.inzynierka.DataBase.rentedItems.RentedItems;
import com.inzynierka.morski.inzynierka.DataBase.rentedItems.RentedItemsDataSource;
import com.inzynierka.morski.inzynierka.Exeptions.NoCostumeInDataBaseException;
import com.inzynierka.morski.inzynierka.R;

import java.util.ArrayList;
import java.util.List;

public class RentMainActivity extends AppCompatActivity {

    private CostumeDataSource dataSource;
    private RentedItemsDataSource rentedItemsDataSource;
    private ListView listCostumes;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> labelSelectedCostumes = new ArrayList<String>();

    Snackbar snackbar;
    Snackbar snackbarTheSameCostume;
    Snackbar snackbarCostumeAlreadyRent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_main);


        dataSource = new CostumeDataSource(this);
        dataSource.open();
        rentedItemsDataSource = new RentedItemsDataSource(this);
        rentedItemsDataSource.open();

        //TODO Lista kostiumow z mozliwoscia usuniecia kostiumu, miniatura kostiumu
        /**/
        listCostumes = (ListView) findViewById(R.id.listViewCostumesToRent);
        List<String> values = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        listCostumes.setAdapter(adapter);

    }

    public void onClickButtonRentMain (View view){
        Intent myIntent;
        switch (view.getId()){
            case R.id.btnScanCode:

                //TODO dodac zabezpieczenie ze nie ma BarcodeScannera od Zixing
                snackbar = Snackbar.make(view, "NIE MA TAKIEGO KOSTIUMU W BAZIE", Snackbar.LENGTH_LONG);
                snackbarTheSameCostume = Snackbar.make(view, "TEN KOSTIUM ZOSTAL JUZ DODANY", Snackbar.LENGTH_LONG);
                snackbarCostumeAlreadyRent = Snackbar.make(view, "TEN KOSTIUM JEST AKTUALNIE WYPOŻYCZONY", Snackbar.LENGTH_LONG);

                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
                break;

            case R.id.btnScanNFC:
                myIntent = new Intent(com.inzynierka.morski.inzynierka.Activity.rent.RentMainActivity.this, ScanNFCActivity.class);
                com.inzynierka.morski.inzynierka.Activity.rent.RentMainActivity.this.startActivity(myIntent);
                break;

            case R.id.btnEndScan:
                myIntent = new Intent(com.inzynierka.morski.inzynierka.Activity.rent.RentMainActivity.this, ChooseClientActivity.class);
                if(!labelSelectedCostumes.isEmpty()){
                    myIntent.putExtra("labelSelectedCostumes", labelSelectedCostumes);
                    com.inzynierka.morski.inzynierka.Activity.rent.RentMainActivity.this.startActivity(myIntent);
                }
                else{
                    snackbar = Snackbar.make(view, "NIE WYBRANO ZADNEGO KOSTIUMU", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

                break;

        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                String wynikSkanowania = intent.getStringExtra("SCAN_RESULT");
                Log.i("xZing", "contents: " + wynikSkanowania ); // Handle successful scan

                Costume costume = null;
                try {
                    costume = dataSource.getCostume(wynikSkanowania);
                    RentedItems rentedItem = rentedItemsDataSource.getRentedItem(costume.getId());

                    if(checkIsRent(rentedItem));
                    else if(labelSelectedCostumes.contains(costume.getLabel()+"")){
                        snackbarTheSameCostume.show();
                    }
                    else{
                        adapter.add(costume.getName());
                        adapter.notifyDataSetChanged();
                        labelSelectedCostumes.add(costume.getLabel()+"");
                    }

                } catch (NoCostumeInDataBaseException e) {
                    e.printStackTrace();
                    snackbar.show();
                    return;
                }
            }
            else if(resultCode == RESULT_CANCELED){ // Handle cancel
                Log.i("xZing", "Cancelled");
            }
        }
    }
    private boolean checkIsRent(RentedItems rentedItem){
        if(rentedItem!=null){
            if(rentedItem.getPosition()==1){
                snackbarCostumeAlreadyRent.show();
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

}

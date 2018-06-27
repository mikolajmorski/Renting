package com.inzynierka.morski.inzynierka.Activity.costume;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

//import com.inzynierka.morski.inzynierka.Activity.Rent.ScanNFCActivity;
import  com.inzynierka.morski.inzynierka.Activity.rent.ScanNFCActivity;
import com.inzynierka.morski.inzynierka.DataBase.Costume.Costume;
import com.inzynierka.morski.inzynierka.DataBase.Costume.CostumeDataSource;
import com.inzynierka.morski.inzynierka.DataBase.rentedItems.RentedItemsDataSource;
import com.inzynierka.morski.inzynierka.Exeptions.NoCostumeInDataBaseException;
import com.inzynierka.morski.inzynierka.R;

import java.util.HashMap;
import java.util.Map;

public class CostumeEditActivity extends AppCompatActivity {

    EditText editTextCostumeName;
    EditText editTextCostumeLabel;

    private Long costumeId;
    private CostumeDataSource dataSource;
    private RentedItemsDataSource rentedItemsDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costume_edit);

        ascribeFields();
        costumeId = getIntent().getExtras().getLong("clientId");
        dataSource = new CostumeDataSource(this);
        dataSource.open();
        rentedItemsDataSource = new RentedItemsDataSource(this);
        rentedItemsDataSource.open();

        fillFields();

    }

    private void ascribeFields(){
        editTextCostumeName = (EditText)findViewById(R.id.editTextEditCostumeName);
        editTextCostumeLabel = (EditText)findViewById(R.id.editTextEditCostumeLabel);
    }

    private void setDataToFields(Costume costume) {
        editTextCostumeName.setText(costume.getName(), TextView.BufferType.EDITABLE);
        editTextCostumeLabel.setText(costume.getLabel(), TextView.BufferType.EDITABLE);
    }

    private void fillFields(){
        for(Costume costume: dataSource.getAllCostumes()){
            if(costume.getId()==costumeId){
                setDataToFields(costume);
                break;
            }
        }
    }

    public void onClickButtonCostumeEdit (View view) {
        Costume costume = null;
        try {
            costume = dataSource.getCostume(costumeId);
        } catch (NoCostumeInDataBaseException e) {
            e.printStackTrace();
        }
        switch (view.getId()) {
            case R.id.btnEditScan:
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
                break;

            case R.id.btnEditNFCScan:
                Intent nfcIntent = new Intent(CostumeEditActivity.this, ScanNFCActivity.class);
                startActivityForResult(nfcIntent, 1);
                break;

            case R.id.btnEditRemoveCostume:
                    dataSource.deleteCostume(costume);
                    Intent myIntent;
                    myIntent = new Intent(CostumeEditActivity.this, CostumeListActivity.class);
                    CostumeEditActivity.this.startActivity(myIntent);
                break;

            case R.id.btnEditEnd:
                dataSource.updateCostume(mapFields(), costumeId);
                myIntent = new Intent(CostumeEditActivity.this, CostumeListActivity.class);
                CostumeEditActivity.this.startActivity(myIntent);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                handleCode(intent);
            }
            else if(resultCode == RESULT_CANCELED){ // Handle cancel
                Log.i("xZing", "Cancelled");
            }
        }
        else if(requestCode == 1){
            handleCode(intent);
        }
    }

    private Map<String, String> mapFields(){
        Map<String, String> costumeData = new HashMap<>();
        costumeData.put("label", editTextCostumeLabel.getText().toString());
        costumeData.put("name", editTextCostumeName.getText().toString());
        return costumeData;
    }

    private void handleCode(Intent intent){
        String wynikSkanowania = intent.getStringExtra("SCAN_RESULT");

        Costume costume;
        try {
            costume = dataSource.getCostume(wynikSkanowania);
            editTextCostumeLabel.setText(costume.getLabel());
        } catch (NoCostumeInDataBaseException e) {
            e.printStackTrace();
        }
    }

}

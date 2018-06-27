package com.inzynierka.morski.inzynierka.Activity.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.inzynierka.morski.inzynierka.DataBase.Client.Client;
import com.inzynierka.morski.inzynierka.DataBase.Client.ClientDataSource;
import com.inzynierka.morski.inzynierka.R;

import java.util.HashMap;
import java.util.Map;

public class ClientEditActivity extends AppCompatActivity {

    EditText editTextPersonName;
    EditText editTextPersonLastName;
    EditText editTextStreet;
    EditText editTextHouseNumber;
    EditText editTextPostalCity;
    EditText editTextCity;
    EditText editTextPhone;
    EditText editTextMail;

    private ClientDataSource dataSource;
    Long clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_edit);

        ascribeFields();
        clientId = getIntent().getExtras().getLong("clientId");
        dataSource = new ClientDataSource(this);
        dataSource.open();
        fillFields();

    }

    private void ascribeFields(){
        editTextPersonName = (EditText)findViewById(R.id.editTextEditPersonName);
        editTextPersonLastName = (EditText)findViewById(R.id.editTextEditPersonLastname);
        editTextStreet = (EditText)findViewById(R.id.editTextEditStreet);
        editTextHouseNumber = (EditText)findViewById(R.id.editTextEditHouseNumber);
        editTextCity = (EditText)findViewById(R.id.editTextEditCity);
        editTextPostalCity = (EditText)findViewById(R.id.editTextEditPostalCode);
        editTextPhone = (EditText)findViewById(R.id.editTextEditPhoneNumber);
        editTextMail = (EditText)findViewById(R.id.editTextEditMail);
    }

    private void setDataToFields(Client client){
        editTextPersonName.setText(client.getName(), TextView.BufferType.EDITABLE);
        editTextPersonLastName.setText(client.getLastName(), TextView.BufferType.EDITABLE);
        editTextStreet.setText(client.getAddress().split(" ")[0], TextView.BufferType.EDITABLE);
        editTextHouseNumber.setText(client.getAddress().split(" ")[1], TextView.BufferType.EDITABLE);
        editTextCity.setText(client.getCity(), TextView.BufferType.EDITABLE);
        String postalCode = Integer.toString(client.getPostalCode());
        editTextPostalCity.setText(postalCode, TextView.BufferType.EDITABLE);
        String phoneNumber = Integer.toString(client.getPhoneNumber());
        editTextPhone.setText(phoneNumber, TextView.BufferType.EDITABLE);
        editTextMail.setText(client.getMail(), TextView.BufferType.EDITABLE);
    }

    private void fillFields(){
        for(Client klient: dataSource.getAllClients()){
            if(klient.getId()==clientId){
                setDataToFields(klient);
                break;
            }
        }
    }

    public void onClickButtonEditClient (View view){
        Intent myIntent;
        switch (view.getId()) {
            case R.id.btnEditClient:
                if (!fieldsEmpty()) {
                    dataSource.updateClient(mapFields(), clientId);
                    myIntent = new Intent(ClientEditActivity.this, ClientListActivity.class);
                    ClientEditActivity.this.startActivity(myIntent);
                }
                break;
            case R.id.btnEditRemoveClient:
                break;
        }
    }

    private boolean fieldsEmpty(){
        if(TextUtils.isEmpty(editTextPersonName.getText().toString())) {
            editTextPersonName.setError("PROSZE PODAC IMIĘ");
            return true;
        }
        else if(TextUtils.isEmpty(editTextPersonLastName.getText().toString())) {
            editTextPersonLastName.setError("PROSZE PODAC NAZWISKO");
            return true;
        }
        else if(TextUtils.isEmpty(editTextStreet.getText().toString())) {
            editTextStreet.setError("PROSZE PODAC ULICĘ");
            return true;
        }

        else if(TextUtils.isEmpty(editTextHouseNumber.getText().toString())) {
            editTextHouseNumber.setError("PROSZE PODAC NR DOMU I MIESZKANIA");
            return true;
        }

        else if(TextUtils.isEmpty(editTextPostalCity.getText().toString())) {
            editTextPostalCity.setError("PROSZE PODAC KOD POCZTOWY");
            return true;
        }

        else if(TextUtils.isEmpty(editTextCity.getText().toString())) {
            editTextCity.setError("PROSZE PODAC MIASTO");
            return true;
        }

        else if(TextUtils.isEmpty(editTextPhone.getText().toString())) {
            editTextPhone.setError("PROSZE PODAC NR TELEFONU");
            return true;
        }

        else if(TextUtils.isEmpty(editTextMail.getText().toString())) {
            editTextMail.setError("PROSZE PODAC MAIL");
            return true;
        }

        return false;
    }

    private Map<String,String> mapFields(){
        Map<String, String> personData = new HashMap<>();
        personData.put("name", editTextPersonName.getText().toString());
        personData.put("lastName", editTextPersonLastName.getText().toString());
        personData.put("address", editTextStreet.getText().toString() + " " + editTextHouseNumber.getText().toString());
        personData.put("postalCode", editTextPostalCity.getText().toString());
        personData.put("city", editTextCity.getText().toString());
        personData.put("phone", editTextPhone.getText().toString());
        personData.put("mail", editTextMail.getText().toString());
        return personData;
    }

}

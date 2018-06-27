package com.inzynierka.morski.inzynierka.Activity.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.inzynierka.morski.inzynierka.DataBase.Client.Client;
import com.inzynierka.morski.inzynierka.DataBase.Client.ClientDataSource;
import com.inzynierka.morski.inzynierka.R;

public class ClientAddActivity extends AppCompatActivity {

    private ClientDataSource dataSource;

    Toast toast;

    EditText editTextPersonName;
    EditText editTextPersonLastName;
    EditText editTextStreet;
    EditText editTextHouseNumber;
    EditText editTextPostalCity;
    EditText editTextCity;
    EditText editTextPhone;
    EditText editTextMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add);

        dataSource = new ClientDataSource(this);
        dataSource.open();

        editTextPersonName = (EditText)findViewById(R.id.editTextName);
        editTextPersonLastName = (EditText)findViewById(R.id.editTextLastName);
        editTextStreet = (EditText)findViewById(R.id.editTextStreet);
        editTextHouseNumber = (EditText)findViewById(R.id.editTextHouseNumber);
        editTextCity = (EditText)findViewById(R.id.editTextCity);
        editTextPostalCity = (EditText)findViewById(R.id.editTextPostalCode);
        editTextPhone = (EditText)findViewById(R.id.editTextPhoneNumber);
        editTextMail = (EditText)findViewById(R.id.editTextMail);

    }
    public void onClickButtonClientAdd (View view){
        Intent myIntent = null;
        switch (view.getId()){
            case R.id.btnAddPerson:
                if(!fieldsEmpty()){
                    setFieldsEnabled(false);
                    Wrapper wrapper = new Wrapper();
                    Client client = dataSource.createClient(wrapper.getName(), wrapper.getLastName(),
                            wrapper.getStreet() + " " + wrapper.getHouseNumber(), wrapper.getPostalCode(),
                            wrapper.getCity(), wrapper.getPhone(), wrapper.getMail());
                    toast = Toast.makeText(ClientAddActivity.this, "KLIENT POMYŚLNIE DODANY", Toast.LENGTH_LONG);
                    toast.show();
                    clearFields();
                    setFieldsEnabled(true);
                    break;
                }

                break;
        }

    }

    private class Wrapper{
        private String name;
        private String lastName;
        private String street;
        private int houseNumber;
        private int postalCode;
        private String city;
        private int phone;
        private String mail;


        public Wrapper(){
            generateName();
            generateLastName();
            generateStreet();
            generateHouseNumber();
            generateCity();
            generatePostalCode();
            generatePhone();
            generateMail();

        }

        private void generateName(){
            name = editTextPersonName.getText().toString();
        }

        private void generateLastName(){

            lastName = editTextPersonLastName.getText().toString();
        }

        private void generateStreet(){

            street = editTextStreet.getText().toString();
        }

        private void generateHouseNumber(){

            houseNumber = Integer.parseInt(editTextHouseNumber.getText().toString());
        }

        private void generateCity(){

            city = editTextCity.getText().toString();
        }
        private void generatePostalCode(){

            postalCode = Integer.parseInt(editTextPostalCity.getText().toString());
        }

        private void generatePhone(){

            phone  = Integer.parseInt(editTextPhone.getText().toString());
        }

        private void generateMail(){

            mail = editTextMail.getText().toString();
        }


        public String getName() {
            return name;
        }

        public String getLastName() {
            return lastName;
        }

        public String getStreet() {
            return street;
        }

        public int getHouseNumber(){
            return houseNumber;
        }

        public String getCity(){
            return city;
        }

        public int getPostalCode() {
            return postalCode;
        }

        public int getPhone() {
            return phone;
        }

        public String getMail() {
            return mail;
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

    private void setFieldsEnabled(boolean enabled){
        editTextPersonName.setEnabled(enabled);
        editTextPersonLastName.setEnabled(enabled);
        editTextStreet.setEnabled(enabled);
        editTextHouseNumber.setEnabled(enabled);
        editTextCity.setEnabled(enabled);
        editTextPostalCity.setEnabled(enabled);
        editTextPhone.setEnabled(enabled);
        editTextMail.setEnabled(enabled);
    }

    private void clearFields(){
        editTextPersonName.setText("");
        editTextPersonLastName.setText("");
        editTextStreet.setText("");
        editTextHouseNumber.setText("");
        editTextCity.setText("");
        editTextPostalCity.setText("");
        editTextPhone.setText("");
        editTextMail.setText("");
    }

}

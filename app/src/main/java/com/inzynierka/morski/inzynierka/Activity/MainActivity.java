package com.inzynierka.morski.inzynierka.Activity;

import android.content.Intent;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import  com.inzynierka.morski.inzynierka.Activity.rent.RentListActivity;
import com.inzynierka.morski.inzynierka.Activity.client.ClientAddActivity;
import com.inzynierka.morski.inzynierka.Activity.client.ClientListActivity;
import com.inzynierka.morski.inzynierka.Activity.costume.CostumeAddActivity;
import com.inzynierka.morski.inzynierka.Activity.costume.CostumeListActivity;
import com.inzynierka.morski.inzynierka.Activity.exportImport.ExportImportDB;
import com.inzynierka.morski.inzynierka.Activity.returnCostume.ReturnActivity;
import com.inzynierka.morski.inzynierka.R;
import com.inzynierka.morski.inzynierka.Activity.rent.RentMainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClickButtonMainMenu (View view){
        Intent myIntent = null;
        switch (view.getId()){
            case R.id.btnRent:
                myIntent = new Intent(MainActivity.this, RentMainActivity.class);
                break;

            case R.id.btnReturn:
                myIntent = new Intent(MainActivity.this, ReturnActivity.class);
                break;

            case R.id.btnAddCostume:
                myIntent = new Intent(MainActivity.this, CostumeAddActivity.class);
                break;

            case R.id.btnAddClient:
                myIntent = new Intent(MainActivity.this, ClientAddActivity.class);
                break;

            case R.id.btnCostumeList:
                myIntent = new Intent(MainActivity.this, CostumeListActivity.class);
                break;

            case R.id.btnClientList:
                myIntent = new Intent(MainActivity.this, ClientListActivity.class);
                break;

            case R.id.btnRentList:
                myIntent = new Intent(MainActivity.this, RentListActivity.class);
                break;

            case R.id.btnExportImport:
                myIntent = new Intent(MainActivity.this, ExportImportDB.class);
                break;
        }
        MainActivity.this.startActivity(myIntent);
    }

}

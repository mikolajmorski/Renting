package com.inzynierka.morski.inzynierka.Activity.rent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.inzynierka.morski.inzynierka.DataBase.Client.Client;
import com.inzynierka.morski.inzynierka.DataBase.Client.ClientDataSource;
import com.inzynierka.morski.inzynierka.DataBase.rent.Rent;
import com.inzynierka.morski.inzynierka.DataBase.rent.RentDataSource;
import com.inzynierka.morski.inzynierka.PagerAdapter;
import com.inzynierka.morski.inzynierka.R;
import com.inzynierka.morski.inzynierka.Tab1;
import com.inzynierka.morski.inzynierka.Tab2;
import com.inzynierka.morski.inzynierka.Tab3;

import java.util.ArrayList;
import java.util.List;

public class RentListActivity extends AppCompatActivity implements Tab1.OnFragmentInteractionListener, Tab2.OnFragmentInteractionListener, Tab3.OnFragmentInteractionListener{
    private ListView listRents;
    private ArrayAdapter<String> adapter;
    private RentDataSource rentDataSource;
    private ClientDataSource clientDataSource;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_list);

        /*rentDataSource = new RentDataSource(this);
        rentDataSource.open();
        clientDataSource = new ClientDataSource(this);
        clientDataSource.open();

        listRents = (ListView) findViewById(R.id.listViewRentList);
        List<String> values = new ArrayList<>();
        final List<Long> rentIds = new ArrayList<>();
        for(Rent rent: rentDataSource.getAllRents()){
            Client client = clientDataSource.getClient(rent.getClientId());
            values.add(client.getName() + " " + client.getLastName() + " " + rent.getDateRent());
            rentIds.add(rent.getId());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        listRents.setAdapter(adapter);

        listRents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                Intent myIntent;
                myIntent = new Intent(com.inzynierka.morski.inzynierka.Activity.rent.RentListActivity.this, RentDetailsActivity.class);
                Long toPut = rentIds.get((pos));
                myIntent.putExtra("rentId", toPut);
                com.inzynierka.morski.inzynierka.Activity.rent.RentListActivity.this.startActivity(myIntent);
                return true;
            }
        });*/

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

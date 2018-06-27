package com.inzynierka.morski.inzynierka.Activity.client;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.inzynierka.morski.inzynierka.R;

/**
 * Created by Asdf on 2015-12-29.
 */
public class ClientRowAdapter extends ArrayAdapter<ClientRow> {

    Context context;
    int layoutResourceId;
    ClientRow data[] = null;

    public ClientRowAdapter(Context context, int layoutResourceId, ClientRow[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ClientRowHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ClientRowHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.checkBox = (CheckBox)row.findViewById(R.id.checkBox);
            holder.txtTitle = (TextView)row.findViewById(R.id.textTitle);

            row.setTag(holder);
        }
        else
        {
            holder = (ClientRowHolder)row.getTag();
        }

        ClientRow clientRow = data[position];
        holder.txtTitle.setText(clientRow.title);
        holder.imgIcon.setImageResource(clientRow.icon);

        return row;
    }

    static class ClientRowHolder
    {

        ImageView imgIcon;
        CheckBox checkBox;
        TextView txtTitle;
    }
}

package com.inzynierka.morski.inzynierka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> values;

    public MySimpleArrayAdapter(Context context, List<String> values) {
        super(context, R.layout.rowlayout , values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        String[] arrayValues = values.toArray(new String[values.size()]);
        textView.setText(arrayValues[position]);
        // Change the icon for Windows and iPhone
        String s = arrayValues[position];
        if (s.endsWith("aktywne") || s.endsWith("WYPOŻYCZONY")) {
            textView.setTextColor(0xFFFF0000); //imageView.setImageResource(R.drawable.no);
        } else if(s.endsWith("czesciowo zakonczone")) {
            textView.setTextColor(0xFFFFFF00);
        }
        else if(s.endsWith("zakonczone") || s.endsWith("DOSTĘPNY")){
            textView.setTextColor(0xFF00FF00);
        }

        return rowView;
    }
}

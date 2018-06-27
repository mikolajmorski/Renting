package com.inzynierka.morski.inzynierka.Activity.exportImport;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.inzynierka.morski.inzynierka.Activity.MainActivity;
import com.inzynierka.morski.inzynierka.R;
import com.inzynierka.morski.inzynierka.Server.ServerMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class ExportImportDB extends AppCompatActivity {
//TODO zabezpieczyc przed wczytywaniem nie chcianych plikow, podawac ścieżkę do importu i eksportu
    File direct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_import_db);
//creating a new folder for the database to be backuped to

        direct = new File(Environment.getExternalStorageDirectory() + "/Rent Backup");

        if(!direct.exists())
        {
            if(direct.mkdir())
            {
                //directory is created;
            }

        }


    }

    public void onClickButtonExportImport (View view){
        switch (view.getId()) {
            case R.id.btnImport:
                importDB();
                break;
            case R.id.btnExport:
                exportDB();
                break;
            case R.id.btnSendMail:
                ServerMethods server = new ServerMethods();
                server.sendMail("m.morski@gmail.com", "Własny temat ziom", "Oddawaj wszystkie stroje kutasiarzu.");
                break;
        }
        Intent myIntent;
        myIntent = new Intent(ExportImportDB.this, MainActivity.class);
        ExportImportDB.this.startActivity(myIntent);
    }
    //importing database
    private void importDB() {
        try {
            File data  = Environment.getDataDirectory();

            if (direct.canWrite()) {
                String  currentDBPath= "/data/" + "com.inzynierka.morski.inzynierka" + "/databases/" + "database.id";
                String backupDBPath  = "backupFile.id";
                File  backupDB= new File(data, currentDBPath);
                File currentDB  = new File(direct, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }
    //exporting database
    private void exportDB() {
        try {
            File data = Environment.getDataDirectory();

            if (direct.canWrite()) {
                String  currentDBPath= "/data/" + "com.inzynierka.morski.inzynierka" + "/databases/" + "database.id";
                String backupDBPath  = "backupFile.id";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(direct, backupDBPath);
                backupDB.createNewFile();

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }

}

package com.example.internshipappjxl;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.widget.Toast;


import java.io.InputStream;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.TEXTDISP);
    }

    public void btnClick(android.view.View view){
        try {
            AssetManager am = getAssets();
            InputStream is  = am.open("Sample.xls");
            Workbook wb = Workbook.getWorkbook(is);
            Sheet s = wb.getSheet(0);

            int row = s.getRows();
            int col = s.getColumns();

            String data ="";

            for(int i=0;i<row;i++){
                for(int j=0;j<col;j++){
                   Cell c = s.getCell(j,i);
                   data = data + " " + c.getContents().toString();
                }
                data = data + "\n";
            }//end of outer for
            tv.setText(data);

            Toast.makeText(this,"Access to Excel file Successful",Toast.LENGTH_LONG).show();
        }
        catch (Exception e){

        }//catch


//
//        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setData(Uri.parse("tel:9930414241"));
//
//        if (ActivityCompat.checkSelfPermission(MainActivity.this,
//                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            startActivity(callIntent);
//        }
//        else{
//            Toast.makeText(this,"Access denial", Toast.LENGTH_LONG);
//        }


    }//method
}

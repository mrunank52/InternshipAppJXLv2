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
    int CurrentRowNo; //To keep a track of the current being accessed


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.TEXTDISP);
        Toast.makeText(this,"OnCreate",Toast.LENGTH_SHORT).show();


        try{

            //Displaying the contents of the first entry
            AssetManager am = getAssets();
            InputStream is  = am.open("Sample.xls");
            Workbook wb = Workbook.getWorkbook(is);
            Sheet s = wb.getSheet(0);


            Toast.makeText(this,"Making access",Toast.LENGTH_SHORT).show();
            CurrentRowNo = 0;
            Cell nameCell = s.getCell(0,0);
            Cell phonenoCell =s.getCell(1,0);

            String tobedisplayed = "Name:"+nameCell.getContents() +"\nPhoneno:"+phonenoCell.getContents();

            tv.setText(tobedisplayed);


            wb.close();
            is.close();
        }
        catch (Exception e){

        }


    }//end of Oncreate

    public void MakeCall(android.view.View view){
        String callString ="tel:";

        try{

            //Displaying the contents of the first entry
            AssetManager am = getAssets();
            InputStream is  = am.open("Sample.xls");
            Workbook wb = Workbook.getWorkbook(is);
            Sheet s = wb.getSheet(0);


            Cell nameCell = s.getCell(0,CurrentRowNo);
            Cell phonenoCell =s.getCell(1,CurrentRowNo);

            callString = callString + phonenoCell.getContents();

            String tobedisplayed = "Name:"+nameCell.getContents() +"\nPhoneno:"+phonenoCell.getContents();

            tv.setText(tobedisplayed);


            wb.close();
            is.close();
        }
        catch (Exception e){

        }




//        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setData(Uri.parse(callString));
//        startActivity(callIntent);

    }

    public void btnClickLeft(android.view.View view){
        try {
            Toast.makeText(this,"Inside btnClik",Toast.LENGTH_SHORT).show();
            AssetManager am = getAssets();
            InputStream is  = am.open("Sample.xls");
            Workbook wb = Workbook.getWorkbook(is);
            Sheet s = wb.getSheet(0);

            int MaxEntries = s.getRows();

            CurrentRowNo = (CurrentRowNo-1+MaxEntries)%MaxEntries; //To be Modified

            Cell nameCell = s.getCell(0,CurrentRowNo);
            Cell phonenoCell =s.getCell(1,CurrentRowNo);

            String tobedisplayed = "Name:"+nameCell.getContents() +"\nPhoneno:"+phonenoCell.getContents();

            tv.setText(tobedisplayed);

            wb.close();
            is.close();
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


    }//method btnClick





    public void btnClickRight(android.view.View view) {
        try {
            AssetManager am = getAssets();
            InputStream is = am.open("Sample.xls");
            Workbook wb = Workbook.getWorkbook(is);
            Sheet s = wb.getSheet(0);

            int MaxEntries = s.getRows();

            CurrentRowNo = (CurrentRowNo + 1 ) % MaxEntries; //To be Modified

            Cell nameCell = s.getCell(0, CurrentRowNo);
            Cell phonenoCell = s.getCell(1, CurrentRowNo);

            String tobedisplayed = "Name:" + nameCell.getContents() + "\nPhoneno:" + phonenoCell.getContents();

            tv.setText(tobedisplayed);

            wb.close();
            is.close();

        } catch (Exception e) {

        }//catch

    }//btnClickRight


}

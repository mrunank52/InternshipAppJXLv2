package com.example.internshipappjxl;

import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.widget.Toast;
import android.support.v4.content.ContextCompat;
import android.support.annotation.NonNull;
import java.io.InputStream;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import android.content.Context;
import android.media.AudioManager;



public class MainActivity extends AppCompatActivity {
    TextView tv;
    int CurrentRowNo; //To keep a track of the current being accessed
    int REQUEST_CALL =1;
    String callString="1";

    MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Method that runs once the app is created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.TEXTDISP);
       // Toast.makeText(this,"OnCreate",Toast.LENGTH_SHORT).show();


        try{

            //Opening The excel file stored in the assets folder
            AssetManager am = getAssets();
            InputStream is  = am.open("Sample.xls");
            Workbook wb = Workbook.getWorkbook(is);
            Sheet s = wb.getSheet(0);

            //Logging to check file has been accessed
            Toast.makeText(this,"Made access to Excel File",Toast.LENGTH_SHORT).show();

            //Displaying the contents of the first entry
            CurrentRowNo = 0; //Initial value of CurrentRowNo
            Cell nameCell = s.getCell(0,CurrentRowNo);
            Cell phonenoCell =s.getCell(1,CurrentRowNo);

            String tobedisplayed = "Name:"+nameCell.getContents() +"\nPhoneno:"+phonenoCell.getContents();

            tv.setText(tobedisplayed); //Display the details in the Textview

            wb.close();
            is.close();
        }
        catch (Exception e){

        }


    }//end of Oncreate function

    public void Close(android.view.View view){
        finish();
    }//CloseButton

    public void MakeCall(android.view.View view){
        callString ="tel:";

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


        //This is the call to actual function making the phone call
        MakeActualPhoneCall(callString);
        callString = "";


    }//End of makecall




    void MakeActualPhoneCall(String callString) {
        //The method makes the actual Phone call that is required

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {



            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(callString)));

            boolean isSoundPlayed=false;

            while(!isSoundPlayed) {
                if(isCallActive(this)) {
                    isSoundPlayed=true;
                    play();
                }
            }//end of while

        }//end of else

    }//end of the func MakeActualPhoneCall()



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Overided method included in the android library for requesting permission for call access
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                MakeActualPhoneCall(callString);
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }//end of OnRequestPermissionsResult


    public void btnClickLeft(android.view.View view){
        //Traverse the excel file list in a circular manner to left
        try {
            //Toast.makeText(this,"Inside btnClik",Toast.LENGTH_SHORT).show();
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
            is.close();     //FIle closed
        }
        catch (Exception e){

        }//catch



    }//method btnClickLeft





    public void btnClickRight(android.view.View view) {
        try {
            //Traverse the excel file list in a circular manner to left
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
            is.close();         //File Closed

        } catch (Exception e) {

        }//catch

    }//end of btnClickRight



    //For mediaplayer - Sound file funcs

    public void play() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.song);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }

        player.start();
    }

    public void pause() {
        if (player != null) {
            player.pause();
        }
    }

    public void stop() {
        stopPlayer();
    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }


    public static boolean isCallActive(Context context){
        AudioManager manager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        if(manager.getMode()==AudioManager.MODE_IN_CALL){
            return true;
        }
        else{
            return false;
        }
    }



}//End of Class

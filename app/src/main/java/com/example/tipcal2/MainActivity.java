/*  Name: Anh Nguyen
    Date: Feb 24th, 2021
    Purpose: This is a simple Tip Calculator application. The user would enter Check Amount, Number
    of People, and Tip Percent (15% is the default). If "TIP" button is pressed, the app
    would calculate 4 numbers: Total Bill, Total Tip, Bill per Person, and Tip per Person. If "WEB"
    is pressed, the app opens a new Activity for web search. If "DIAL" button is pressed, automatically
    dial 781-891-2000. If "MAP" is pressed, it opens the built-in GoogleMaps application which will
    automatically display part (or all) of the Bentley campus.
*/

package com.example.tipcal2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private EditText CheckAmt; //Check amount
    private EditText NumPeople; //Number of people
    private EditText TipPer; //Tip percentage

    private TextView TotalBill; //Total amount of the Bill
    private TextView BillPeople; //Total amount paid by each person
    private TextView TotalTip; // Total amount of Tip
    private TextView TipPeople; //Amount of Tip paid by each person

    private Button Calculate; //Pressing this button will calculate the needed information
    public static final int requestCode_48 = 48;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckAmt = (EditText) findViewById(R.id.CheckAmt);
        NumPeople = (EditText) findViewById(R.id.NumPeople);
        TipPer = (EditText) findViewById(R.id.TipPer);
        TipPer.setHint("Default: 15%");

        TotalBill = (TextView) findViewById(R.id.TotalBill);
        BillPeople = (TextView) findViewById(R.id.BillPeople);
        TotalTip = (TextView) findViewById(R.id.TotalTip);
        TipPeople = (TextView) findViewById(R.id.TipPeople);

        //Change text font for calculated values
        Typeface typeface = getResources().getFont(R.font.droid_serif_bold);
        TotalBill.setTypeface(typeface);
        BillPeople.setTypeface(typeface);
        TotalTip.setTypeface(typeface);
        TipPeople.setTypeface(typeface);

        //Change text font for the button
        Calculate = (Button) findViewById(R.id.Calculate);
        Typeface typeface2 = getResources().getFont(R.font.alfa_slab_one);
        Calculate.setTypeface(typeface2);

        Button webButton = (Button) findViewById(R.id.webButton);
        webButton.setOnClickListener(this);

        Button dialButton = (Button) findViewById(R.id.dialButton);
        dialButton.setOnClickListener(this);

        Button mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(this);

        Calculate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String S_CheckAmt = CheckAmt.getText().toString();
                String S_NumPeople = NumPeople.getText().toString();
                String S_TipPer = TipPer.getText().toString();
                boolean check = true; //Only calculate the 4 values if this variable is true

                //Check for potential invalid data

                //if user doesn't put any information down for Check Amount, display an error
                // warning message
                if (S_CheckAmt.equals("") | S_CheckAmt == null) {
                    CheckAmt.setText("");
                    Toast.makeText(MainActivity.this, "Check Amount should be " +
                            "greater than 0", Toast.LENGTH_LONG).show();
                    check = false;

                }

                //if user doesn't put any information down for Number of People, display an error
                // warning message
                if (S_NumPeople.equals("") | S_NumPeople == null | S_NumPeople.equals("0")) {
                    Toast.makeText(MainActivity.this,"Number of people should " +
                            "be greater than 0", Toast.LENGTH_LONG).show();
                    NumPeople.setText("");
                    check = false;
                }

                //if user doesn't put any information down for Tip Percentage,  use the 15% default tip
                if (S_TipPer.equals("") | S_TipPer == null) {
                    TipPer.setText("15");
                }

                if (check) {
                    Double D_CheckAmt = Double.parseDouble(CheckAmt.getText().toString());
                    int I_NumPeople = Integer.parseInt(NumPeople.getText().toString());
                    Double D_TipPer = Double.parseDouble(TipPer.getText().toString());

                    Double D_TotalBill = D_CheckAmt * (1 + D_TipPer/100); //Calculate the total amount of the bill
                    Double D_BillPeople = D_TotalBill / I_NumPeople; // Calculate the total amount paid by each person
                    Double D_TotalTip = D_CheckAmt * (D_TipPer/100); //Calculate the total amount of tip
                    Double D_TipPeople = D_TotalTip / I_NumPeople; //Calculate the total amount of tip paid by each person

                    TotalBill.setText("$" + String.format("%.2f", D_TotalBill));
                    BillPeople.setText("$" + String.format("%.2f", D_BillPeople));
                    TotalTip.setText("$" + String.format("%.2f", D_TotalTip));
                    TipPeople.setText("$" + String.format("%.2f", D_TipPeople));
                }

            }
        });

    }


    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.webButton:
                Intent i1 = new Intent(this, WebLookUp.class);
                startActivity(i1); //Open WebLookUp Activity
                break;

            case R.id.dialButton:
                Uri uri2 = Uri.parse("tel:7818912000");
                Intent i2 = new Intent(Intent.ACTION_DIAL,uri2);
                startActivity(i2);
                break;

            case R.id.mapButton:
                Uri uri3 = Uri.parse("geo:0,0?q=175+forest+street+waltham+ma");
                Intent i3 = new Intent(Intent.ACTION_VIEW, uri3);
                if (i3.resolveActivity(getPackageManager()) != null) {
                    startActivity(i3);
                }
                break;
        }
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case (requestCode_48): {

                if (resultCode == Activity.RESULT_OK)
                    Toast.makeText(this, "Result OK", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, "Result NOT OK", Toast.LENGTH_LONG).show();
                break;
            }

            default : Toast.makeText(this, "Not my problem", Toast.LENGTH_LONG).show();
        }//switch

    }// onActivityResult





}
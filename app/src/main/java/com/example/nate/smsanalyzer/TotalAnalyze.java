package com.example.nate.smsanalyzer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.StringTokenizer;


/**
 * Created by Nate on 2015-02-15.
 */
public class TotalAnalyze extends Activity implements View.OnClickListener {

    ImageButton btn;
    ImageButton cBtn;

    //create the URI for inbox messages
    Uri inboxUri = Uri.parse("content://sms/inbox");
    Uri outboxUri = Uri.parse("content://sms/sent");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analyze_total);

        //assisgn buttons and link listeners
        btn = (ImageButton)findViewById(R.id.homeButton);
        cBtn = (ImageButton)findViewById(R.id.contactButton);
        btn.setOnClickListener(this);
        cBtn.setOnClickListener(this);

        //asssign text views and load them with data
        TextView txtRatio = (TextView)findViewById(R.id.totalMessageRatio);
        TextView txtAvgLen = (TextView)findViewById(R.id.totalAverageLength);
        txtRatio.setText(this.getTextRatio());
        txtAvgLen.setText(Integer.toString(this.getWordCount()/this.getTotalMessages()));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.homeButton:
                Intent intentHome = new Intent(v.getContext(), HomeActivity.class);
                startActivity(intentHome);
                this.finish();
            break;
            case R.id.contactButton:
                Intent intentContact = new Intent(v.getContext(), ContactAnalyze.class);
                startActivity(intentContact);
                this.finish();
            break;
        }
    }

    public String getTextRatio() {
        String textRatio = "";

        //set the required columns for the URI
        String[] totalTexts = new String[] {"DISTINCT _id"};

        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(inboxUri, totalTexts , null, null, null);
        Cursor d = cr.query(outboxUri, totalTexts, null, null, null);

        textRatio = Integer.toString(d.getCount()) + " / " + Integer.toString(c.getCount());

        //close the cursors
        c.close();
        d.close();

        return textRatio;
    }

    public int getWordCount() {
        int wordCount=0;
        String message="";
        String[] totalTexts = new String[] {"DISTINCT _id", "body"};
        int counter = 0;

        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(inboxUri, totalTexts, null, null, null);

        if(c != null) {
            while(c.moveToNext()) {
                message = c.getString(c.getColumnIndexOrThrow("body"));

                StringTokenizer token = new StringTokenizer(message);

                counter += token.countTokens();
            }
            wordCount = counter;
        }
        return wordCount;
    }

    public int getTotalMessages() {
       int inTotal = 0;

        String[] totalTexts = new String[] {"DISTINCT _id"};

        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(inboxUri, totalTexts , null, null, null);

        inTotal = c.getCount();

        //close the cursor
        c.close();

        return inTotal;
    }

}

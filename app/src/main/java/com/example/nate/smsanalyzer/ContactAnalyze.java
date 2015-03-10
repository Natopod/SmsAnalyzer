package com.example.nate.smsanalyzer;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Nate on 2015-02-16.
 */
public class ContactAnalyze extends Activity {

    TextView txt;

    //create the URI for inbox messages
    Uri inboxUri = Uri.parse("content://sms/inbox");
    Uri outboxUri = Uri.parse("content://sms/sent");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analyze_contact);

        txt = (TextView)findViewById(R.id.testText);
        this.getContactAnalysis();
   //     Intent intent = getIntent();
     //   Log.e("Cursor", intent.getStringExtra("this"));
      //  txt.setText(this.getWordCount());



    }

    public void getContactAnalysis() {
        int wordCount=0;
        String message="";
        int counter = 0;
        int vCounter = 0;
        int lCounter = 0;
        int hCounter = 0;

        StringBuffer stringBuffer = new StringBuffer();
        String[] uniqueSenders = new String[] {"DISTINCT address"};
        String[] totalTexts = new String[] {"DISTINCT _id"};
        String[] help = new String[] {"help", "need", "want", "helping", "favour", "rely"};
        String[] like = new String[] {"like", "love", "adore", "miss", "date", "kiss"};
        String[] vulgar = new String[] {"fuck", "shit", "ass", "damn", "asshole", "bitch"};
        Cursor c = getContentResolver().query(inboxUri,uniqueSenders,null,null,null);

        if(c!=null) {
            while(c.moveToNext()) {
                String number = c.getString(c.getColumnIndexOrThrow("address")).toString();

                stringBuffer.append("\n\n"+number+"\n--------------------------------------------------------");

                Cursor d = getContentResolver().query(inboxUri, new String[]{"address", "body"}, "address = '" + number + "'", null, null);

                while(d.moveToNext()) {
                    message = d.getString(d.getColumnIndexOrThrow("body"));
                    counter += 1;

                    StringTokenizer token = new StringTokenizer(message);
                    ArrayList textBody = new ArrayList();
                    wordCount += token.countTokens();


                    StringTokenizer token2 = new StringTokenizer(message);
                    while(token2.hasMoreTokens()) {
                        textBody.add(token2.nextToken());
                    }

                    for(int i = 0;i<textBody.size();i++) {
                        for(int j = 0;j<vulgar.length;j++) {
                            if(vulgar[j].equalsIgnoreCase((String) textBody.get(i))){
                                vCounter +=1;
                            }
                            if(help[j].equalsIgnoreCase((String) textBody.get(i))) {
                                hCounter += 1;
                            }
                            if(like[j].equalsIgnoreCase((String) textBody.get(i))) {
                                lCounter += 1;
                            }
                        }
                    }



                }
                stringBuffer.append("\nWord Count : " + Integer.toString(wordCount));
                stringBuffer.append("\nMessage Count : " + Integer.toString(counter));
                stringBuffer.append("\nAverage Word Count : " + Integer.toString(wordCount/counter));
                stringBuffer.append("\nProfanity Rating : " + Integer.toString(vCounter*10));
                stringBuffer.append("\nLove Rating : " + Integer.toString(lCounter*10));
                stringBuffer.append("\nHelp Rating : " + Integer.toString(hCounter*10));
                wordCount = 0;
                counter = 0;
                vCounter = 0;
                lCounter = 0;
                hCounter = 0;

                stringBuffer.append("\n--------------------------------------------------------");
            }
        }

        txt.setText(stringBuffer);
    }

  //  public int getWordCount() {
   //     int wordCount=0;
   //     String message="";
   //     String[] totalTexts = new String[] {"_id", "body", "address"};
   //     int counter = 0;

    //    ContentResolver cr = getContentResolver();

       // Cursor c = cr.query(inboxUri, totalTexts, "_id = '" + intent.getStringExtra("this") +"'", null, null);
       // String thisContact = c.getString(c.getColumnIndexOrThrow("_id"));

       // String[] userMessages = new String[] {"body","address"};
     //   Cursor d = cr.query(inboxUri,userMessages,"_id = '"+thisContact+"'",null,null);

       // if(d != null) {
         //   while(d.moveToNext()) {
           //     message = d.getString(d.getColumnIndexOrThrow("body"));

             //   StringTokenizer token = new StringTokenizer(message);

//                counter += token.countTokens();
  //          }
    //        wordCount = counter;
      //  }
    //  return wordCount;
  //  }
}

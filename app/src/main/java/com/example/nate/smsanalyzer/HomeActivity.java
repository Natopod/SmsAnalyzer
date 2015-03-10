package com.example.nate.smsanalyzer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomeActivity extends Activity implements OnClickListener {

    ImageButton btn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //link class variables to their xml id
        TextView txtUnique = (TextView) findViewById(R.id.uniqueSenders);
        TextView txtTotal = (TextView) findViewById(R.id.totalTexts);
        btn = (ImageButton) findViewById(R.id.analyzeButton);

        //initiate onClickListener for the button
        btn.setOnClickListener(this);

        //create the URI for inbox messages
        Uri inboxUri = Uri.parse("content://sms/inbox");

        //set the required columns for the URI
        String[] reqCols = new String[] {"_id", "address", "body", "date"};
        String[] uniqueSenders = new String[] {"DISTINCT address"}; //will only return results with a unique address (every sender)
        String[] totalTexts = new String[] {"DISTINCT _id"};    //returns all the messages in the inbox

        //set up the contentresolver to interact with sms database
        ContentResolver cr = getContentResolver();

        //specify the query being sent to the db
        Cursor c = cr.query(inboxUri, uniqueSenders, null, null, null);

        //test purposes
        //Log.v("TEST uniqueSenders", Integer.toString(c.getCount()));

        //assign the query results to the associated textview for uniqueSenders
        final String strUnique = Integer.toString(c.getCount());
        txtUnique.setText(strUnique);

        //assign query results to the textview for totalTexts
        c = cr.query(inboxUri, totalTexts , null, null, null);
        txtTotal.setText(Integer.toString(c.getCount()));
    }

    //override the onClick method to close this activity and open the next when the button is clicked
    @Override
    public void onClick(View v) {
        Intent intentAnalyze = new Intent(this,TotalAnalyze.class);
        startActivity(intentAnalyze);
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

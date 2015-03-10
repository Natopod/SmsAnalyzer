package com.example.nate.smsanalyzer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Nate on 2015-02-16.
 */
public class ContactsList extends Activity implements AdapterView.OnItemClickListener {

    Cursor c;
    ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_contacts);

        Uri inboxUri = Uri.parse("content://sms/inbox");
        lv = (ListView) findViewById(R.id.list);
        ContentResolver cr = getContentResolver();
        String[] uniqueSenders = new String[] {"DISTINCT address", "_id"}; //will only return results with a unique address (every sender)

        c = cr.query(inboxUri,uniqueSenders,null,null,null);



        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, R.layout.contact_list_items,c,new String[] {"address"},new int[] {R.id.text1});


        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Log.e("TEST","test");
        Intent intent = new Intent(v.getContext(), ContactAnalyze.class);
        Cursor extra = (Cursor) lv.getItemAtPosition(position);
        Log.e("Click",(String) lv.getItemAtPosition(position));
        String extraString = extra.getString(1);
        intent.putExtra("this",extraString);
        startActivity(intent);
    }


}

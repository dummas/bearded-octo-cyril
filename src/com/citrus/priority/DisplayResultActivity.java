package com.citrus.priority;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayResultActivity extends Activity {
	
	private static final String TAG = "ResultActivity";
	ArrayAdapter<String> adapter;
	ArrayList<String> input;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);
        
        Intent intent = getIntent();
        
        Bundle bundle = intent.getExtras();
		
		/* Get all the priorities */
        input = bundle.getStringArrayList("PRIORITIES");
		
		for (String d:input) {
			Log.d(TAG, d);
		}
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		
		adapter = new ArrayAdapter<String>(this, R.layout.text_view_1, input);
		
		listView.setAdapter(adapter);
        
        
    }
    
}

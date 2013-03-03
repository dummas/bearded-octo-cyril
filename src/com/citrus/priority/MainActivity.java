package com.citrus.priority;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

//	public final static String EXTRA_MESSAGE = "com.citrus.priority.MESSAGE";
//	public final static String EXTRA_MESSAGE1 = "com.citrus.priority.MESSAGE1";
//	public final static String EXTRA_MESSAGE2 = "com.citrus.priority.MESSAGE2";
	public static final String ARRAYS_COUNT = "com.citrus.ARRAYS_COUNT";
	public static final String ARRAYS_INDEX = "com.citrus.ARRAYS_INDEX";
	public static final String TAG = "MainActivity";
	
	/* Send button */
	Button send_button;
	/* Add more text fields button */
	Button add_button;
	/* Edit text index */
	int index;
	/* Layout of edit text */
	LinearLayout layout;
	/* Array to work on */
	ArrayList<String> priorities = new ArrayList<String>(); 

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		send_button = (Button) findViewById(R.id.button1);
		add_button = (Button) findViewById(R.id.button2);
		
		send_button.setOnClickListener(new SendButtonClick());
		add_button.setOnClickListener(new AddButtonClick());
		
		// Catch the layout
		layout = (LinearLayout) findViewById(R.id.linearlayout2);

		// Add three edit texts to layout2
		for ( index = 0; index < 3; ++index ) {
			Log.d(TAG, "Adding more text fields");
			
			EditText ed = new EditText(this);
			ed.setTag(index);
			ed.setText("p_" + index);
			layout.addView(ed);
			
		}
		
	}
	
	class AddButtonClick implements Button.OnClickListener {

		public void onClick(View v) {
			EditText ed = new EditText(v.getContext());
			ed.setTag(++index);
			layout.addView(ed);
		}
		
	}
	
	class SendButtonClick implements Button.OnClickListener {
		public void onClick(View v) {
			
			// Managing intent for new activity
			Intent intent = new Intent(v.getContext(), DisplayMessageActivity.class);
			
			// New Bundle to save all the text edit files
			Bundle bundle = new Bundle();
			
			// Catch all the edit text fields
			for ( int i = 0; i < layout.getChildCount(); i++ ) {
				if( layout.getChildAt( i ) instanceof EditText ) {
					EditText ed = (EditText) layout.getChildAt( i );
					priorities.add(ed.getText().toString());
				}
			}
			
			bundle.putStringArrayList("PRIORITIES", priorities);
			
			intent.putExtras(bundle);
			
			// Launch new activity
			startActivity(intent);
			
		}
	}

//	public void sendMessage(View view) {
//		
//		// Managing intents for new activity
////		Intent intent = new Intent(this, DisplayMessageActivity.class);
//		
//		// New Bundle
////		Bundle bundle = new Bundle();
//		
//		
////		EditText editText = (EditText) findViewById(R.id.edit_message);
////		String message = editText.getText().toString();
////
////		EditText editText1 = (EditText) findViewById(R.id.edit_message1);
////		String message1 = editText1.getText().toString();
////
////		EditText editText2 = (EditText) findViewById(R.id.edit_message2);
////		String message2 = editText2.getText().toString();
////		
//		
//
////		intent.putExtra(EXTRA_MESSAGE, message);
////		intent.putExtra(EXTRA_MESSAGE1, message1);
////		intent.putExtra(EXTRA_MESSAGE2, message2);
////		startActivity(intent);
//	}
}

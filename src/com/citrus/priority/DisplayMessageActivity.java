package com.citrus.priority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DisplayMessageActivity extends Activity {
	
	public final static String TAG = "DisplayMessageActivity";
	
	Button button_one;
	Button button_two;
	
	ArrayList<String> input = new ArrayList<String>();
	ArrayList<String> output = new ArrayList<String>();
	ArrayList<Priority> priorities = new ArrayList<Priority>();
	
	int number_of_participants = 0;
	int tour_counter = 0;
	int choices_counter = 0;
	int number_of_tours = 0;
	int current_priority = 0;
	int first_choice = 0;
	int second_choice = 0;
	int [][] history;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		/* Get all the priorities */
		input = bundle.getStringArrayList("PRIORITIES");
		
		/* Read the length */
		number_of_participants = input.size();
		
		history = new int[number_of_participants][number_of_participants];
		
		choices_counter = 1;
		
		/* Shuffle all the stuff */
		Collections.shuffle(input);
		
		/* Assign received priorities to Priority class */
		for (int i = 0; i < input.size(); i++) {
			
			/* The name */
			priorities.add(new Priority(input.get(i)));
			/* The id -- don't know why I do this -- see further -- maybe delete this */
			priorities.get(i).id = i;
		}
		
		button_one = (Button) findViewById(R.id.button1);
		button_two = (Button) findViewById(R.id.button2);
		
		/* Populate first choices */
		populate();
		
		button_one.setOnClickListener(new ButtonClicked());
		button_two.setOnClickListener(new ButtonClicked());
		
	}
	
	public void populate() {
		
		first_choice = 0;
		second_choice = 0;
		
		Log.d(TAG, "Choices counter: " + choices_counter);
		
		Log.d(TAG, "Participants: " + number_of_participants);
		
		int marked = 0;
		
		int i = 0;
		
		while ( i < priorities.size() ) {
			
			Log.d(TAG, "Current priority: " + current_priority);
			
			if ( priorities.get(i).rank == current_priority && marked == 1 ) {
				
				if ( history[first_choice][i] == 0 ) {
					Log.d(TAG, "Setting button one tag: " + i );
					marked = 2;
					second_choice = i;
				} else {
					priorities.get(i).addRank();
				}
				
				
			}
			
			// Search for priorities, with same rank
			if ( priorities.get(i).rank == current_priority && marked == 0 ) {
				Log.d(TAG, "Setting button two tag: " + i );
				marked = 1;
				first_choice = i;
			}
			
			
			i = i + 1;
		}
		
		for (Priority d:priorities) {
			System.out.println(d);
		}
		
		if ( marked != 2 ) {
			
			if (current_priority != number_of_participants) {
				
				current_priority = current_priority + 1;
				populate();
				
			} else {
				Log.d(TAG, "We have a winner");
				
				/* Sort according to rank */
				Collections.sort(priorities, new CustomComparator());
				
				/* Everything is ranked -- proceed to form the output ArrayList */
				Bundle bundle = new Bundle();
				
				Intent intent = new Intent(this, DisplayResultActivity.class);
				
				/* Populate output */
				for ( int j = 0; j < priorities.size(); j++ ) {
					output.add(priorities.get(j).name);
				}
				
				for (String d:output) {
					Log.d(TAG, d);
				}
				
				/* Bundle Array */
				bundle.putStringArrayList("PRIORITIES", output);
				
				/* Set extra */
				intent.putExtras(bundle);
				
				/* Start activity */
				startActivity(intent);
				
			}
		} else {
			button_one.setTag(second_choice);
			button_one.setText(priorities.get(second_choice).name);
			button_two.setTag(first_choice);
			button_two.setText(priorities.get(first_choice).name);
		}
		
	}
	
	class ButtonClicked implements Button.OnClickListener {

		public void onClick(View view) {
			
			/* Read the clicked tag */
			int tag = (Integer) view.getTag();
			
			/* Make higher rank */
			priorities.get(tag).addRank();
			
			Log.d(TAG, "Chosen: " + tag);
			
			Log.d(TAG, "First choice: " + first_choice);
			
			Log.d(TAG, "Second choice: " + second_choice);
			
			history[first_choice][second_choice] = tag;
			
			/* Populate */
			populate();

		}
	}
	
	public class CustomComparator implements Comparator<Priority> {

		public int compare(Priority o1, Priority o2) {
			
			if ( o1.getRank() > o2.getRank() ) {
				return -1;
			} else if (o1.getRank() < o2.getRank()) {
				return 1;
			} else {
				return 0;
			}
			
			
		}
		
	}
	
	/**
	 * Super nasty class for the priority stuff
	 * @author maksim
	 *
	 */
	class Priority {
		
		// Name of priority
		public String name;
		
		// Rank of priority
		public int rank;
		
		public int tour;
		
		// Id of priority
		public int id;
		
		@Override
		public String toString() {
			return "Name " + this.name + " rank: " + this.rank + " tour: " + this.tour;
		}

		Priority(String name) {
			this.name = name;
			this.rank = 0;
			this.tour = 0;
		}
		
		public void addTour() {
			this.tour = this.tour + 1;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}

		public int getRank() {
			return rank;
		}

		public void setRank(int rank) {
			this.rank = rank;
		}
		
		public void addRank() {
			this.rank = this.rank + 1;
		}
	}
}

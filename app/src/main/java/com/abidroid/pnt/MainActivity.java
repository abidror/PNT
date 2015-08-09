package com.abidroid.pnt;

import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	private Button btnToday, btnMonthDay, btnAbout;
	
	private TextView txtMonthDay, txtSehriEnd, txtSubheSadiq, txtFajrEnd, txtSunrise, txtIshraqStart, txtIshraqEnd, txtMamnuStart, txtMamnuEnd;
	private TextView txtZuhrEnd, txtAsreHanfi, txtAsrEnd, txtSunset, txtIshaStart, txtMidnight, txtDayTimeHrs;
	
	Typeface tf1, tf2, tf3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnToday = (Button)findViewById(R.id.btnToday);
		btnMonthDay = (Button)findViewById(R.id.btnSearch);
		btnAbout = (Button)findViewById(R.id.btnAbout);
		
		txtMonthDay = (TextView)findViewById(R.id.txtMonthDay);
		txtSehriEnd = (TextView)findViewById(R.id.txtSehriEnd);
		txtSubheSadiq = (TextView)findViewById(R.id.txtSubheSadiq);
		txtFajrEnd = (TextView)findViewById(R.id.txtFajrEnd);
		txtSunrise = (TextView)findViewById(R.id.txtSunrise);
		txtIshraqStart = (TextView)findViewById(R.id.txtIshraqStart);
		txtIshraqEnd = (TextView)findViewById(R.id.txtIshraqEnd);
		txtMamnuStart = (TextView)findViewById(R.id.txtMamnuStart);
		txtMamnuEnd = (TextView)findViewById(R.id.txtMamnuEnd);
		txtZuhrEnd = (TextView)findViewById(R.id.txtZuhrEnd);
		txtAsreHanfi = (TextView)findViewById(R.id.txtAsreHanfi);
		txtAsrEnd = (TextView)findViewById(R.id.txtAsrEnd);
		txtSunset = (TextView)findViewById(R.id.txtSunset);
		txtIshaStart = (TextView)findViewById(R.id.txtIshaStart);
		txtMidnight = (TextView)findViewById(R.id.txtMidnight);
		txtDayTimeHrs = (TextView)findViewById(R.id.txtDayTimeHours);
		
		tf1 = Typeface.createFromAsset(getAssets(), "fonts/Montague.ttf");
		tf2 = Typeface.createFromAsset(getAssets(), "fonts/BTTF.ttf");
		tf3 = Typeface.createFromAsset(getAssets(), "fonts/Marlboro.ttf");
		
		txtSehriEnd.setTypeface(tf1);
		txtSubheSadiq.setTypeface(tf1);
		txtFajrEnd.setTypeface(tf1);
		txtSunrise.setTypeface(tf1);
		txtIshraqStart.setTypeface(tf1);
		txtIshraqEnd.setTypeface(tf1);
		txtMamnuStart.setTypeface(tf1);
		txtMamnuEnd.setTypeface(tf1);
		txtZuhrEnd.setTypeface(tf1);
		txtAsreHanfi.setTypeface(tf1);
		txtAsrEnd.setTypeface(tf1);
		txtSunset.setTypeface(tf1);
		txtIshaStart.setTypeface(tf1);
		txtMidnight.setTypeface(tf1);
		txtDayTimeHrs.setTypeface(tf1);
		
		Calendar calendar = Calendar.getInstance();
		
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		getAndDisplayTiming(Integer.toString((month+1)), Integer.toString(day));
		
		//getAndDisplayTiming("10", "3");
		
		btnAbout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				final Dialog dialog = new Dialog(MainActivity.this);
				dialog.setTitle("About this App");
				dialog.setContentView(R.layout.custom_dialog);
				Button btnOkay = (Button)dialog.findViewById(R.id.btnOkay);
				
				dialog.show();
				
				btnOkay.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
					
						dialog.dismiss();
					}
				});
			}
		});
		btnToday.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Calendar calendar = Calendar.getInstance();
				
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				getAndDisplayTiming(Integer.toString((month+1)), Integer.toString(day));
			}
		});
		btnMonthDay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Calendar cal = Calendar.getInstance();
			
			int m = cal.get(Calendar.MONTH);
			int d = cal.get(Calendar.DAY_OF_MONTH);
			int y = cal.get(Calendar.YEAR);
			
		
			
			DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, dateListener, y, m, d);
			dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Search", dpd);
			dpd.show();
				//new DatePickerDialog(MainActivity.this, dateListener, y, m, d).show();
			}
		});				
	}
	
	DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
						
			getAndDisplayTiming(Integer.toString(monthOfYear + 1), Integer.toString(dayOfMonth));
		}
	};
	
	
	private void getAndDisplayTiming( String monthNum, String dayNum)
	{
		DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(MainActivity.this);
		Cursor cursor = null;
		try {
			// check if database exists in app path, if not copy it from assets
			dbHelper.create();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			// open the database
			dbHelper.open();
			dbHelper.getReadableDatabase();
		} catch (SQLException sqle) {
			throw sqle;
		}


		
		try {
			cursor = dbHelper.getTiming(monthNum, dayNum);
			
			if( cursor != null && cursor.getCount() > 0 )
			{
				String month = cursor.getString(1);
				
				txtMonthDay.setText( getMonthName(month) +  " " +  cursor.getString(2));
				txtSehriEnd.setText(cursor.getString(3));
				txtSubheSadiq.setText(cursor.getString(4));
				txtFajrEnd.setText(cursor.getString(5));
				txtSunrise.setText(cursor.getString(6));
				txtIshraqStart.setText(cursor.getString(7));
				txtIshraqEnd.setText(cursor.getString(8));
				txtMamnuStart.setText(cursor.getString(9));
				txtMamnuEnd.setText(cursor.getString(10));
				txtZuhrEnd.setText(cursor.getString(11));
				txtAsreHanfi.setText(cursor.getString(12));
				txtAsrEnd.setText(cursor.getString(13));
				txtSunset.setText(cursor.getString(14));
				txtIshaStart.setText(cursor.getString(15));
				txtMidnight.setText(cursor.getString(16));
				txtDayTimeHrs.setText(cursor.getString(17));
				
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Sorry, no data found", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		finally
		{
			cursor.close();
			dbHelper.close();
		}
	}
	
	private String getMonthName( String monthNumber )
	{
		int m = Integer.parseInt(monthNumber);
		String name ="";
		switch( m)
		{
		case 1:
			name = "January";
			break;
		case 2:
			name = "February";
			break;
		case 3:
			name = "March";
			break;
		case 4:
			name = "April";
			break;
		case 5:
			name = "May";
			break;
		case 6:
			name = "June";
			break;
		case 7:
			name = "July";
			break;
		case 8:
			name = "August";
			break;
		case 9:
			name = "September";
			break;
		case 10:
			name = "October";
			break;
		case 11:
			name = "November";
			break;
		case 12:
			name = "December";
			break;
		}
		
		return name;
	}
}

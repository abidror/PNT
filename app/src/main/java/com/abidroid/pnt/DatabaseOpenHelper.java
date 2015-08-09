package com.abidroid.pnt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "db_pnt";
	private static final int DATABASE_VERSION = 1;

	private static final String TABLE_NAME = "tbl_timings";

	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_MONTH_NUM = "month_num";
	private static final String COLUMN_DAY_NUM = "day_num";
	private static final String COLUMN_SEHRI_END = "sehri_end";
	private static final String COLUMN_SUBHE_SADIQ = "subhe_sadiq";
	private static final String COLUMN_FAJR_END = "fajr_end";
	private static final String COLUMN_SUNRISE = "sunrise";
	private static final String COLUMN_ISHRAQ_START = "ishraq_start";
	private static final String COLUMN_ISHRAQ_END = "ishraq_end";
	private static final String COLUMN_MAMNU_START = "mamnu_start";
	private static final String COLUMN_MAMNU_END = "mamnu_end";
	private static final String COLUMN_ZUHR_END = "zuhr_end";
	private static final String COLUMN_ASRE_HANFI = "asre_hanfi";
	private static final String COLUMN_ASR_END = "asr_end";
	private static final String COLUMN_SUNSET = "sunset";
	private static final String COLUMN_ISHA_START = "isha_start";
	private static final String COLUMN_MIDNIGHT = "midnight";
	private static final String COLUMN_DAY_TIME_HRS = "day_time_hrs";

	private SQLiteDatabase database;
	private final Context context;
	private static String DATABASE_PATH;

	public DatabaseOpenHelper(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub

		this.context = ctx;
		DATABASE_PATH = context.getFilesDir().getParentFile().getPath() + "/databases/";
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void create() throws IOException {
		boolean check = checkDataBase();

		SQLiteDatabase db_Read = null;

		// Creates empty database default system path
		db_Read = this.getWritableDatabase();
		db_Read.close();
		try {
			if (!check) {
				
				System.out.println("check executed");
				copyDataBase();
			}
		} catch (IOException e) {
			throw new Error("Error copying database");
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DATABASE_PATH + DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
		} catch (SQLiteException e) {
			// database does't exist yet.
		}

		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = context.getAssets().open(DATABASE_NAME);

		// Path to the just created empty db
		String outFileName = DATABASE_PATH + DATABASE_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	 /** open the database */
	 public void open() throws SQLException {
	  String myPath = DATABASE_PATH + DATABASE_NAME;
	  database = SQLiteDatabase.openDatabase(myPath, null,
	    SQLiteDatabase.OPEN_READWRITE);
	  
	  //System.out.println( "PATH: " + myPath);
	 }

	 /** close the database */
	 @Override
	 public synchronized void close() {
	  if (database != null)
	   database.close();
	  super.close();
	 }
	/* 
	 * select timings on the basis of month and day
	 */
	public Cursor getTiming( String monthNum, String dayNum )
	{
		Cursor c = null;
		
		try
		{
			
			//System.out.println("VERSION : " + database.getVersion() + database.getPath() );
			c = database.rawQuery("SELECT * from " + TABLE_NAME + " where month_num = \'" + monthNum + "\' AND day_num = \'" + dayNum + "\'", null );
		
			if(c != null && c.getCount() > 0  )
				c.moveToFirst();
				
		}
		catch( Exception e)
		{
			e.printStackTrace();
			//System.out.println(">>>>>>>>ERROR<<<<<");
		}
		return c;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}

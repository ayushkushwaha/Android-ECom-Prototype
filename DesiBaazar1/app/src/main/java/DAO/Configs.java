package DAO;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Configs extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 6;
	public static final String DATABASE_NAME = "Desibaazar.db";
	public static final String TABLE_NAME = "users";

	public static final String ID = "_id";
	public static final String NAME = "NAME";
	public static final String PASSWORD = "PASSWORD";
	public static final String EMAIL = "EMAIL";
	public static final String GENDER = "GENDER";
	public static final String COUNTRY = "COUNTRY";
	public static final String ADDRESS = "ADDRESS";

	private static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY" + ", " + NAME
			+ " VARCHAR(200), " + EMAIL + " VARCHAR(100), " + PASSWORD
			+ " VARCHAR(100), " + GENDER + " VARCHAR(100), " + COUNTRY
			+ " COUNTRY VARCHAR(100), " + ADDRESS + " VARCHAR(1000)" + ");";
	private static final String DROP_TABLE = "DROP TABLE IF EXISTS"
			+ TABLE_NAME + "";
	
	public static final String USER_ID = "USER_ID";
	public static final String PID = "PID";
	public static final String QTY = "QTY";

	private static final String TABLE_CART = "CREATE TABLE IF NOT EXISTS CART ( _id INTEGER PRIMARY KEY, "
			+ USER_ID + " INTEGER, " + PID + " INTEGER," + QTY + " INTEGER);";
	
	private static final String TABLE_ORDERS = "CREATE TABLE IF NOT EXISTS ORDERS ( _id INTEGER PRIMARY KEY, "
			+ USER_ID + " INTEGER, " + PID + " INTEGER," + QTY + " INTEGER);";
	
	

	Context context;

	public Configs(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(TABLE_CREATE);
			db.execSQL(TABLE_CART);
			db.execSQL(TABLE_ORDERS);

		} catch (SQLException ex) {
			Toast.makeText(this.context, "Error " + ex, Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		try {
			//db.execSQL(DROP_TABLE);
			//onCreate(db);
			
			db.execSQL(TABLE_CREATE);
			db.execSQL(TABLE_CART);
			db.execSQL(TABLE_ORDERS);
			
			Toast.makeText(this.context, "Onupgrade", Toast.LENGTH_LONG).show();
		} catch (SQLException ex) {
			Toast.makeText(this.context, "Error " + ex, Toast.LENGTH_LONG)
					.show();
		}
	}
}
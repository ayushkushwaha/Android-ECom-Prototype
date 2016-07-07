package in.sli.desibaazar;

import DAO.DB_operations;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class Profile extends Activity {
	TextView name, email, country, gender, address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);

		SharedPreferences pref = getApplicationContext().getSharedPreferences(
				"USER_PREF", MODE_PRIVATE);
		Editor editor = pref.edit();
		int uid = pref.getInt("UID", 0);

		name = (TextView) findViewById(R.id.name);
		email = (TextView) findViewById(R.id.email1);
		country = (TextView) findViewById(R.id.country1);
		gender = (TextView) findViewById(R.id.gender1);
		address = (TextView) findViewById(R.id.address1);

		DB_operations op = new DB_operations();

		Cursor cursor = op.Get_User_Profile(Profile.this, uid);

		if (cursor.moveToFirst() && cursor != null) {
			name.setText(cursor.getString(cursor.getColumnIndex("NAME")));
			email.setText(cursor.getString(cursor.getColumnIndex("EMAIL")));
			country.setText(cursor.getString(cursor.getColumnIndex("COUNTRY")));
			gender.setText(cursor.getString(cursor.getColumnIndex("GENDER")));
			//address.setText(cursor.getString(cursor.getColumnIndex("ADDRESS")));
		}
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater menuinf = getMenuInflater();
		menuinf.inflate(R.menu.common_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == R.id.logout) {
			DB_operations DB_obj = new DB_operations();
			DB_obj.Clear_cart(getApplicationContext());

			SharedPreferences pref = getApplicationContext()
					.getSharedPreferences("USER_PREF", MODE_PRIVATE);
			Editor editor = pref.edit();
			editor.clear();
			editor.commit();

			Intent i = new Intent(getApplicationContext(), Login.class);
			startActivity(i);

		} else if (id == R.id.exit) {
			DB_operations DB_obj = new DB_operations();
			DB_obj.Clear_cart(getApplicationContext());

			SharedPreferences pref = getApplicationContext()
					.getSharedPreferences("USER_PREF", MODE_PRIVATE);
			Editor editor = pref.edit();
			editor.clear();
			editor.commit();
			this.finish();
		} else if (id == R.id.cart) {
			Intent i = new Intent(getApplicationContext(), Cart.class);
			startActivity(i);
		}

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}

package in.sli.desibaazar;

import DAO.DB_operations;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Order extends Activity {

	LinearLayout linear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);

		overridePendingTransition(R.anim.fadein, R.anim.fadeout);

		ActionBar bar = getActionBar();

		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3f51b5")));
		getActionBar().setTitle(
				Html.fromHtml("<font color=\"#FFFFFF\">"
						+ getString(R.string.title_activity_order) + "</font>"));

		linear = (LinearLayout) findViewById(R.id.LinearLayout);
		// start

		DB_operations obj = new DB_operations();
		// Cursor cursor =
		SharedPreferences pref = getApplicationContext().getSharedPreferences(
				"USER_PREF", MODE_PRIVATE);
		Editor editor = pref.edit();
		final int uid = pref.getInt("UID", 0);
		final Cursor cursor = obj.Get_Cart_Data(Order.this, uid);

		int countofpro = 0;
		int total_price = 0;

		Data data = new Data();

		if (cursor.moveToFirst()) {

			while (cursor.isAfterLast() == false) {

				int PID = cursor.getInt(cursor.getColumnIndex("PID"));

				TextView name1 = new TextView(this);
				name1.setText(data.Get_Product_Name(PID));
				name1.setLines(3);

				TextView price = new TextView(this);
				price.setText(data.Get_Product_Price(PID));
				price.setTextColor(getResources().getColor(R.color.red));// "#F11"
				price.setLines(2);

				LinearLayout sub = new LinearLayout(this);
				sub.setOrientation(LinearLayout.HORIZONTAL);

				TextView size = new TextView(this);
				size.setText("Size: " + data.Get_Product_Size(PID)
						+ "    |   Qty: ");

				size.setGravity(Gravity.START);

				TextView qty = new TextView(this);
				qty.setText(data.Get_Product_Qty(PID) + "");

				qty.setGravity(Gravity.END);

				sub.addView(size);
				sub.addView(qty);

				linear.addView(name1);
				linear.addView(price);
				linear.addView(sub);

				Log.i("DATA of CART",
						cursor.getString(cursor.getColumnIndex("USER_ID")));
				cursor.moveToNext();
				countofpro++;

				total_price = total_price
						+ Integer.parseInt(data.Get_Product_Price(PID));
			}

			LinearLayout ll = (LinearLayout) findViewById(R.id.LinearLayout2);
			TextView summary = (TextView) findViewById(R.id.summry);
			Button pay = (Button) findViewById(R.id.ButtonforPayment);
			summary.setText("Your purchase summary is: \nTotal payble amount:  "
					+ total_price
					+ " \nTotal number of products: "
					+ countofpro);

			pay.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(Order.this, "Thank you! for purchase",
							Toast.LENGTH_LONG).show();

					if (cursor.moveToFirst()) {

						while (cursor.isAfterLast() == false) {

							int PID = cursor.getInt(cursor
									.getColumnIndex("PID"));

							int QTY = cursor.getInt(cursor
									.getColumnIndex("QTY"));

							DB_operations oobj = new DB_operations();
							oobj.insertOrder(PID, uid, QTY, Order.this);
							cursor.moveToNext();
						}

					}

					cursor.close();

					DB_operations DB_obj = new DB_operations();
					DB_obj.Clear_cart(getApplicationContext());

					SharedPreferences pref = getApplicationContext()
							.getSharedPreferences("USER_PREF", MODE_PRIVATE);
					Editor editor = pref.edit();
					editor.clear();
					editor.commit();

					Intent i = new Intent(Order.this, Search.class);
					startActivity(i);

				}
			});

		}

	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}

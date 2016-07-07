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
import android.transition.Visibility;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Cart extends Activity {

	LinearLayout linear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_cart);

		overridePendingTransition(R.anim.fadein, R.anim.fadeout);

		ActionBar bar = getActionBar();

		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3f51b5")));
		getActionBar().setTitle(
				Html.fromHtml("<font color=\"#FFFFFF\">"
						+ getString(R.string.title_activity_cart) + "</font>"));

		linear = (LinearLayout) findViewById(R.id.LinearLayout);
		TextView cartEmpty = (TextView) findViewById(R.id.textView2);
		ImageView CartEmpty_ic = (ImageView) findViewById(R.id.imageView1);

		DB_operations obj = new DB_operations();
		// Cursor cursor =
		SharedPreferences pref = this.getSharedPreferences("USER_PREF",
				this.MODE_PRIVATE);
		Editor editor = pref.edit();
		int uid = pref.getInt("UID", 0);
		Cursor cursor = obj.Get_Cart_Data(Cart.this, uid);

		Data data = new Data();

		if (cursor.moveToFirst()) {

			while (cursor.isAfterLast() == false) {

				int PID = cursor.getInt(cursor.getColumnIndex("PID"));

				String QTY = cursor.getString(cursor.getColumnIndex("QTY"));

				TextView name1 = new TextView(this);
				name1.setText(data.Get_Product_Name(PID));
				name1.setLines(3);

				TextView price = new TextView(this);
				price.setText(data.Get_Product_Price(PID));
				price.setTextColor(getResources().getColor(R.color.red));
				price.setLines(2);

				LinearLayout sub = new LinearLayout(this);
				sub.setOrientation(LinearLayout.HORIZONTAL);

				TextView size = new TextView(this);
				size.setText("Size: " + data.Get_Product_Size(PID)
						+ "    |   Qty: ");

				size.setGravity(Gravity.START);

				TextView qty = new TextView(this);
				qty.setText(QTY + "");

				qty.setGravity(Gravity.END);

				sub.addView(size);
				sub.addView(qty);

				linear.addView(name1);
				linear.addView(price);
				linear.addView(sub);

				Button btn = new Button(this);
				btn.setText("Check Out");

				linear.addView(btn);

				btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						Toast.makeText(Cart.this, "Proceeding for CheckOut",
								Toast.LENGTH_LONG).show();
						Intent i = new Intent(getApplicationContext(),
								Order.class);
						startActivity(i);
					}
				});

				Log.i("DATA of CART",
						cursor.getString(cursor.getColumnIndex("USER_ID")));

				cursor.moveToNext();
			}
		} else {
			cartEmpty.setVisibility(View.VISIBLE);
			CartEmpty_ic.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater menuinf = getMenuInflater();
		menuinf.inflate(R.menu.cart, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == R.id.clear_cart) {
			DB_operations DB_obj = new DB_operations();
			DB_obj.Clear_cart(getApplicationContext());

			SharedPreferences pref = getApplicationContext()
					.getSharedPreferences("USER_PREF", MODE_PRIVATE);
			Editor editor = pref.edit();
			editor.clear();
			editor.commit();
			DB_obj.Clear_cart(Cart.this);

			Intent i = new Intent(getApplicationContext(), Search.class);
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

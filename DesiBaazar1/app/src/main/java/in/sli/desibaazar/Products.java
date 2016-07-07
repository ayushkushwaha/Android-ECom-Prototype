package in.sli.desibaazar;

import DAO.DB_operations;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

public class Products extends Activity {

	String[] Product_name = new String[] {
			"Skater Dress With Sweetheart Neck And Short Sleeves.",
			"Vivaa Party Printed Women's Kurti.",
			"Metal Fade Bridge Round Dress." };
	String[] Product_price = new String[] { "Rs. 2000", "Rs. 1000", "Rs. 6700" };
	String[] Product_size = new String[] { "US 4 / Pink", "US 4 / Pink",
			"US 9 / Black" };
	String[] Product_discount = new String[] { "Reduced from Rs. 3000",
			"Reduced from Rs. 2000", "Reduced from Rs. 9000" };
	int[] Product_image = new int[] { R.drawable.img1, R.drawable.img3,
			R.drawable.img4 };
	
	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products);

		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3f51b5")));
		getActionBar().setTitle(
				Html.fromHtml("<font color=\"#FFFFFF\">"
						+ getString(R.string.title_activity_products)
						+ "</font>"));
		
		list = (ListView) findViewById(R.id.ListView);
		list.setAdapter(new Product_data(Products.this, Product_name, Product_price, Product_size, Product_discount,Product_image));
		

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
	
	
}

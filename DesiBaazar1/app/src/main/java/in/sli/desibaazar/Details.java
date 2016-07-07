package in.sli.desibaazar;

import java.util.ArrayList;

import DAO.DB_operations;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends Activity {

	ImageView image;
	TextView name, size, price, discount;
	EditText qty;
	Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		image = (ImageView) findViewById(R.id.imageView1);
		name = (TextView) findViewById(R.id.Name);
		size = (TextView) findViewById(R.id.Size);
		price = (TextView) findViewById(R.id.Price);
		qty = (EditText) findViewById(R.id.QTY);
		btn = (Button) findViewById(R.id.button1);

		Intent i = getIntent();

		final ArrayList<String> Product_Data = i
				.getStringArrayListExtra("Product_Data");

		name.setText(Product_Data.get(0));
		size.setText(Product_Data.get(1));
		price.setText(Product_Data.get(2) + " | Price: " + Product_Data.get(3));
		// qty.setText(i.getStringExtra("QTY"));
		image = (ImageView) findViewById(R.id.imageView1);
		final Data o = new Data();

		image.setImageResource(o.Get_Product_Image(Integer
				.parseInt(Product_Data.get(5))));

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Boolean flag = true;

				try {
					if (qty.getText().toString().equals("")
							|| qty.getText() == null) {
						flag = false;
						Toast.makeText(Details.this,
								"Quantity can not be blank!",
								Toast.LENGTH_SHORT).show();
					}
				} catch (Exception ex) {

				}

				DB_operations op = new DB_operations();

				SharedPreferences pref = getApplicationContext()
						.getSharedPreferences("USER_PREF", MODE_PRIVATE);
				Editor editor = pref.edit();
				int uid = pref.getInt("UID", 0);

				int count = (o.Get_Product_Qty(Integer.parseInt(Product_Data
						.get(5))))
						- op.Get_Product_QTY(Details.this,
								Integer.parseInt(Product_Data.get(5)));

				if (Integer.parseInt(qty.getText().toString()) > count) {
					flag = false;
				}

				if (qty.getText().toString().equals("")
						|| qty.getText() == null) {
					flag = false;
				}

				if (flag) {
					DB_operations DB_obj = new DB_operations();
					DB_obj.insertCART(Integer.parseInt(Product_Data.get(5)),
							uid, Integer.parseInt(qty.getText().toString()),
							Details.this);
					Intent i = new Intent(Details.this, Cart.class);
					startActivity(i);

				} else {
					Toast.makeText(Details.this,
							"Quantity can not exceed from " + count,
							Toast.LENGTH_SHORT).show();
				}

			}
		});

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

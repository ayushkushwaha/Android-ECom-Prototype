package in.sli.desibaazar;

import DAO.DB_operations;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Search extends Activity {

	ImageButton imagebutton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3f51b5")));
		getActionBar()
				.setTitle(
						Html.fromHtml("<font color=\"#FFFFFF\">"
								+ getString(R.string.title_activity_search)
								+ "</font>"));

		imagebutton = (ImageButton) findViewById(R.id.imageButton1);

		imagebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(), Products.class);
				startActivity(i);

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
		}else if (id == R.id.profile) {
			Intent i = new Intent(getApplicationContext(), Profile.class);
			startActivity(i);
		}

		return super.onOptionsItemSelected(item);
	}
}

package in.sli.desibaazar;

import DAO.DB_operations;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class Registration extends Activity {
	String email = "", password = "", name = "", gender = "", country = "";
	int id = 1;

	EditText Name, Email, Password, repassword;

	Spinner Country;
	RadioGroup Gender;
	Button Submit;
	ProgressBar PB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		overridePendingTransition(R.anim.fadein, R.anim.fadeout);

		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3f51b5")));
		getActionBar().setTitle(
				Html.fromHtml("<font color=\"#FFFFFF\">"
						+ getString(R.string.app_name) + "</font>"));
		bar.setHomeButtonEnabled(true);
		bar.setDisplayHomeAsUpEnabled(true);

		Name = (EditText) findViewById(R.id.editName);
		Email = (EditText) findViewById(R.id.editText1);
		Password = (EditText) findViewById(R.id.editPassword);
		repassword = (EditText) findViewById(R.id.editRePassword);

		Country = (Spinner) findViewById(R.id.editCountry);

		Submit = (Button) findViewById(R.id.Button);
		Gender = (RadioGroup) findViewById(R.id.radioGender);

		Submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				boolean flag = true;
				int selectedId = Gender.getCheckedRadioButtonId();

				// find the radiobutton by returned id
				RadioButton radioGender = (RadioButton) findViewById(selectedId);
				String contry = Country.getSelectedItem().toString();

				name = Name.getText().toString();
				email = Email.getText().toString();
				password = Password.getText().toString();
				gender = radioGender.getText().toString();
				country = contry;

				if (name.equals("") || name == "") {
					flag = false;
					Toast.makeText(getApplicationContext(),
							"Name is required.", Toast.LENGTH_LONG).show();
					Name.setFocusable(true);

				} else if (email.equals("") || email == "") {
					flag = false;
					Toast.makeText(getApplicationContext(),
							"Email is required.", Toast.LENGTH_LONG).show();
					Email.setFocusable(true);

				} else if (email.contains("@") != true) {
					flag = false;
					Toast.makeText(getApplicationContext(), "Invalid email.",
							Toast.LENGTH_LONG).show();
					Email.setFocusable(true);

				} else if (password.equals("") || password == "") {
					flag = false;
					Toast.makeText(getApplicationContext(),
							"Password is required.", Toast.LENGTH_LONG).show();
					Password.setFocusable(true);

				} else if (contry.toLowerCase().equals("select country")) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							"Country is required.", Toast.LENGTH_LONG).show();
					// Country.setFocusable(true);

				} else if (radioGender.getText().equals("")) {
					flag = false;
					Toast.makeText(getApplicationContext(),
							"Gender is required.", Toast.LENGTH_LONG).show();
					Gender.setFocusable(true);

				}

				if (flag) {
					
					try {
						DB_operations DBObj = new DB_operations();
						boolean status = DBObj.insertQuery(id, name, email,
								password, gender, country, Registration.this);
						id++;

						if (status) {
							Toast.makeText(getApplicationContext(),
									"You are registred successfully!!!",
									Toast.LENGTH_LONG).show();
							Intent i = new Intent(getApplicationContext(),
									Login.class);
							startActivity(i);
						} else {

						}

					} catch (Exception ex) {
						Toast.makeText(getApplicationContext(),
								"Failed while adding record", Toast.LENGTH_LONG)
								.show();
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			Intent i = new Intent(getApplicationContext(), Login.class);
			startActivity(i);
			return true;
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

package in.sli.desibaazar;

import DAO.DB_operations;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {

	TextView register;
	EditText email;
	EditText password;
	Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		getActionBar().hide();
		register = (TextView) findViewById(R.id.register);
		email = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editPassword);
		login = (Button) findViewById(R.id.Button);

		login.setOnClickListener(this);
		register.setText(Html.fromHtml("<u>Register Now</u>"));

		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(),
						Registration.class);
				startActivity(i);

			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		String Email = email.getText().toString();
		String Password = password.getText().toString();
		boolean flag = true;

		if (Email.equals("")) {
			flag = false;
			Toast.makeText(getApplicationContext(), "Email is required.",
					Toast.LENGTH_LONG).show();
		} else if (Email.contains("@") != true) {
			flag = false;
			Toast.makeText(getApplicationContext(), "Invalid email.",
					Toast.LENGTH_LONG).show();
		} else if (Email.contains(".") != true) {
			flag = false;
			Toast.makeText(getApplicationContext(), "Invalid email.",
					Toast.LENGTH_LONG).show();
		} else if (Password.equals("")) {
			flag = false;
			Toast.makeText(getApplicationContext(), "Password is required.",
					Toast.LENGTH_LONG).show();
		}

		if (flag) {
			try {
				DB_operations DBObj = new DB_operations();
				int result = DBObj.SelectQuery(Login.this, Email, Password);

				if (result > 0) {
					
					// for as SESSION
					SharedPreferences pref = getApplicationContext().getSharedPreferences("USER_PREF", MODE_PRIVATE);
					Editor editor = pref.edit();
					
					editor.putInt("UID", DBObj.Get_User_ID(Login.this, Email, Password));
					editor.commit();
					// END
					
					Intent i = new Intent(getApplicationContext(),
							Search.class);
					startActivity(i);
				} else {
					Toast.makeText(getApplicationContext(),
							"Email or password you entered is incorrect.",
							Toast.LENGTH_LONG).show();
				}

			} catch (Exception ex) {
				Toast.makeText(getApplicationContext(), ex.toString(),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		finish();
	}
}
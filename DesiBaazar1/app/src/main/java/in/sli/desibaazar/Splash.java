package in.sli.desibaazar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends Activity {

	ImageView imageview;
	TextView textview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		getActionBar().hide();
		imageview = (ImageView) findViewById(R.id.imageView1);
		textview = (TextView) findViewById(R.id.textView1);

		imageview.setVisibility(View.VISIBLE);
		TranslateAnimation slide = new TranslateAnimation(0, 0, 100, 0);
		slide.setDuration(5000);
		slide.setFillAfter(true);
		imageview.startAnimation(slide);

		AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(2000);
		textview.startAnimation(anim);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				Intent i = new Intent(getApplicationContext(),
						Login.class);
				startActivity(i);

				Splash.this.finish();

			}
		}, 3000);
	}

}

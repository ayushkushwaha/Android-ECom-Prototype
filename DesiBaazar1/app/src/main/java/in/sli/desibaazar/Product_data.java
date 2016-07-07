package in.sli.desibaazar;

import java.util.ArrayList;

import DAO.DB_operations;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Product_data extends BaseAdapter {

	String[] Product_name;
	String[] Product_price;
	String[] Product_size;
	String[] Product_discount;
	int[] Product_image;
	Context context;
	private static LayoutInflater inflater = null;
	
	ImageView product_image;
	TextView product_name;
	TextView product_price;
	TextView product_size;
	TextView product_discount;
	Button cartButton;
	Button viewDetails;
	


	public Product_data(Context context, String[] Product_name,
			String[] Product_price, String[] Product_size,
			String[] Product_discount, int[] Product_image) {

		this.Product_name = Product_name;
		this.Product_price = Product_price;
		this.Product_size = Product_size;
		this.Product_discount = Product_discount;
		this.Product_image = Product_image;
		this.context = context;
		
		inflater = ((Activity)context).getLayoutInflater();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Product_name.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final int pos = position;
		Data o = new Data();
		DB_operations op = new DB_operations();
		
		View rowview = inflater.inflate(R.layout.product_list_view, null);
		
		int count = (o.Get_Product_Qty(position) - op.Get_Product_QTY(context, position));
		
		Log.i("COunt ", op.Get_Product_QTY(context, o.Get_Product_Qty(position))+"");
		
		if(count < 0)
		{
			count = 0;
		}
		
		product_image = (ImageView) rowview.findViewById(R.id.imageView1);
		product_name = (TextView) rowview.findViewById(R.id.textView1);
		product_price  = (TextView) rowview.findViewById(R.id.textView2);
		product_size  = (TextView) rowview.findViewById(R.id.textView3);
		product_discount  = (TextView) rowview.findViewById(R.id.textView4);
		cartButton = (Button) rowview.findViewById(R.id.button2);
		viewDetails = (Button)rowview.findViewById(R.id.button1);
		
		product_image.setImageResource(o.Get_Product_Image(position));
		product_name.setText(o.Get_Product_Name(position));
		product_price.setText(o.Get_Product_Price(position));
		product_size.setText(o.Get_Product_Size(position));
		product_discount.setText(o.Get_Product_Discount(position) +"  | Qty. "+count);
		cartButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Data o = new Data();
				DB_operations op = new DB_operations();
				
				int count = (o.Get_Product_Qty(position) - op.Get_Product_QTY(context, position));
				// user ID
				SharedPreferences pref = context.getSharedPreferences("USER_PREF", context.MODE_PRIVATE);
				Editor editor = pref.edit();
		        int uid = pref.getInt("UID",0);
				// END
		       
		        if(count > 0){
				DB_operations DB_obj = new DB_operations();
				DB_obj.insertCART(position, uid, 1, context);
				
				Intent i = new Intent(context, Cart.class);
				context.startActivity(i);
				}
		        else
		        {
		        	Toast.makeText(context, "This product is out of stock", Toast.LENGTH_LONG).show();
		        }
				
			}
		});
		
		viewDetails.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Data o = new Data();
				ArrayList<String> Product_Data = new ArrayList<String>();
				Intent i = new Intent(context, Details.class);
				
				Product_Data.add(0,o.Get_Product_Name(position));
				Product_Data.add(1,o.Get_Product_Size(position));
				Product_Data.add(2,o.Get_Product_Discount(position));
				Product_Data.add(3,o.Get_Product_Price(position));
				Product_Data.add(4,o.Get_Product_Qty(position)+"");
				Product_Data.add(5,position+"");
				
				i.putExtra("Product_Data", Product_Data);
				
				Log.i("ID====================================================", pos+"");
				context.startActivity(i);
			}
		});
		
		return rowview;
	}

}

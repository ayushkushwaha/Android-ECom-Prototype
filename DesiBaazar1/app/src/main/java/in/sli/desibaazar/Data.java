package in.sli.desibaazar;

public class Data 
{

	String[] Product_name = new String[] {
			"Skater Dress With Sweetheart Neck And Short Sleeves.",
			"Vivaa Party Printed Women's Kurti.",
			"Metal Fade Bridge Round Dress." };
	String[] Product_price = new String[] { "2000", "1000", "6700" };
	String[] Product_size = new String[] { "US 4 / Pink", "US 4 / Pink",
			"US 9 / Black" };
	String[] Product_discount = new String[] { "Reduced from Rs. 3000",
			"Reduced from Rs. 2000", "Reduced from Rs. 9000" };
	int[] Product_image = new int[] { R.drawable.img1, R.drawable.img3,
			R.drawable.img4 };
	
	int[] Product_qty = new int[] { 60, 60,
			60 };
	
	public String Get_Product_Name(int position) 
	{
		return Product_name[position];
	}
	
	public String Get_Product_Price(int position) 
	{
		return Product_price[position];
	}
	
	public String Get_Product_Size(int position) 
	{
		return Product_size[position];
	}
	
	public String Get_Product_Discount(int position) 
	{
		return Product_discount[position];
	}
	
	public int Get_Product_Image(int position) 
	{
		return Product_image[position];
	}
	
	public int Get_Product_Qty(int position) 
	{
		return Product_qty[position];
	}
	
}

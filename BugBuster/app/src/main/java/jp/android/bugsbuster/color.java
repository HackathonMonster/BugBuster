/**

* RGBカラークラス

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         規則における改造

*/
package jp.android.bugsbuster;

public class color {

	public float r;
	public float g;
	public float b;
	public float a;

	public color()
	{
		setColor(0,0,0,255);
	}

	public color(int r, int g, int b, int a)
	{
		setColor(r,g,b,a);
	}
	
	public color(int r, int g, int b)
	{
		setColor(r,g,b, 255);
	}
	

	public void setColor(int r, int g, int b, int a)
	{
		this.r = r/255.f;
		this.g = g/255.f;
		this.b = b/255.f;
		this.a = a/255.f;
	}

	public void setColor(color color) {

		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
	}
}

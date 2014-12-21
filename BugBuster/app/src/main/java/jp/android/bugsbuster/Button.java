/**

* ボタンクラス

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         規則における改造

*/
package jp.android.bugsbuster;

public class Button extends PImage {

	protected color SELECT_COLOR = new color(255, 255, 0, 255); 
	protected color IDLE_COLOR = new color(255, 128, 0, 255); 
	protected boolean bSelect = false;
	
	public Button()
	{
		bSelect = false;
		init(300, 400, 100, 100);
	}
	
	public Button( int layer ){
		
		super(layer);
		bSelect = false;
		init(300, 400, 100, 100);
	}
	
	public Button(int x, int y, int sizeX, int sizeY)
	{
		bSelect = false;
		init(x, y, sizeX, sizeY);
		mUse = true;
	}
	
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		bSelect = false;
		mUse = true;
	}

	@Override
	public void update() {
		
		if(mUse)
		{
			if(Input.isTouch() && isHit((int)Input.getTouchX(), (int)Input.getTouchY()))
			{
				mColor.setColor(SELECT_COLOR);
				bSelect = true;
			}
			else
			{
				mColor.setColor(IDLE_COLOR);
				bSelect = false;
			}
			
			super.update();
		}
			
	}


	public boolean isSelected() 
	{

		return mUse && bSelect;
	}


	public void setIdleColor(int r, int g, int b, int a)
	{
		IDLE_COLOR.r = r/255.f;
		IDLE_COLOR.g = g/255.f;
		IDLE_COLOR.b = b/255.f;
		IDLE_COLOR.a = a/255.f;
	}

	public void setSelectColor(int r, int g, int b, int a)
	{
		SELECT_COLOR.r = r/255.f;
		SELECT_COLOR.g = g/255.f;
		SELECT_COLOR.b = b/255.f;
		SELECT_COLOR.a = a/255.f;
	}


}

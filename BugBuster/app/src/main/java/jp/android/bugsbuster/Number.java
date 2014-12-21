/**

* 番号のテキストクラス

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         規則における改造

*/
package jp.android.bugsbuster;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public class Number {
	
	private static final float MAX_FRAME = 16.0f;
	private int max_num = 0;
	private PImage mNumPolygon[];
	private int mPosX = 0;
	private int mPosY = 0;
	private int mWidth = 0;
	private int mHeight = 0;
	
	public Number(int digit)
	{
		mNumPolygon = new PImage[digit];
		max_num = digit;
		
		for(int i=0; i<max_num;i++)
		{
			mNumPolygon[i] = new PImage();
		};

		setNumber(0);
		setPosition(0,0);		
	}
	
	public void  init(int value, int x, int y)
	{		
		mPosX = x;
		mPosY = y;

		mWidth = 100;
		mHeight = 200;
		
		for(int i=0;i<max_num;i++)
		{
			mNumPolygon[i].init(0, 0, mWidth, mHeight);	

		}
		
		setNumber(value);	
		setPosition(x, y);	
	}

	public void setNumber(int value)
	{
		float tile = 1/MAX_FRAME;
		int digit = 1;
		for(int i=max_num-1;i>=0;i--)
		{
			int num = ( value / digit) % 10;
			mNumPolygon[i].image.setTile(tile, 1, num*tile, 0);			
			digit *= 10;
		}
	}
	
	public void setPosition(int x, int y)
	{
		mPosX = x;
		mPosY = y;

		int offset = (max_num/2)*mWidth;
		for(int i=0; i<max_num;i++)
		{
			mNumPolygon[i].setPosition(mPosX-offset+(i*mWidth), mPosY);
		}
	}


	public void update()
	{
	}

	public void draw(GL10 gl)
	{
	}
	

	public void loadTexture(GL10 gl, Context context, int id) {

		for(int i=0;i<max_num;i++)
		{
			mNumPolygon[i].loadTexture(gl, context, id);			
		}
	}
}

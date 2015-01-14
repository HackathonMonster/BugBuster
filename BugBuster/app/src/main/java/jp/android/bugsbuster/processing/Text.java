/**

* �e�L�X�g�\���N���X

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         �K���ɂ������

*/
package jp.android.bugsbuster.processing;

import android.content.Context;

import javax.microedition.khronos.opengles.GL10;

import jp.android.bugsbuster.R;
import jp.android.bugsbuster.Texture;
import jp.android.bugsbuster.color;

public class Text {
	
	private static final char START_CHAR = ' ';
	private static final float MAX_FRAME_X = 16.0f;
	private static final float MAX_FRAME_Y = 8.0f;
	private int max_num = 0;
	private PImage mPolygon[];
	private color mColor = new color(255,255,255,255);
	private int mPosX = 0;
	private int mPosY = 0;
	private int mWidth = 20;			// �t�H���g�T�C�Y
	private int mHeight = 40;			// �t�H���g�T�C�Y
	private static boolean mLoadTexture = false;	
	
	private String mText = ""; 			// �`�悷�镶����
	  
	static private Texture images = new Texture();

	static public void unloadFontTexture() {
		 mLoadTexture = false;	
	}
	static public void loadFontTexture(GL10 gl, Context context) {

		if(!mLoadTexture)
		{
			images = new Texture();
			images.loadGLTexture(gl, context, R.drawable.font);
			
			mLoadTexture = true;
		}
	}
	

		
	public Text(int num)
	{
		mPolygon = new PImage[num];
		max_num = num;
		
		for(int i=0; i<max_num;i++)
		{
			mPolygon[i] = new PImage(jp.android.bugsbuster.Object.LAYER_3);
			mPolygon[i].init(-mWidth, -mHeight, mWidth, mHeight);
			mPolygon[i].setColor(mColor);
			mPolygon[i].image.setTexture(images.textures);
		};	
	}

    //�F�ݒ�
    public void setColor(int r, int g, int b, int a)
	{
    	mColor.setColor(r,g,b,a);
    }
    
	void print(String str, int x, int y, int size_x, int size_y)
	{
		mWidth = size_x;
		mHeight = size_y;
		print(str, x, y);
	}
	
	public void print(String str, int x, int y)
	{
		mText = str;
		mPosX = x;
		mPosY = y;
		
		int show_num = mText.length();
		
		if(show_num > max_num)
		{
			show_num = max_num;
		}
		
		int offset = (show_num/2)*mWidth;
		for(int i=0; i < max_num; i++)
		{

			mPolygon[i].image.setTexture(images.textures);
			
			mPolygon[i].setSize(mWidth, mHeight);
			mPolygon[i].setPosition(mPosX-offset+(i*mWidth), mPosY);
			if(i < show_num)
			{
				char c = str.charAt(i);
				setText(i, (int)c);
				
				mPolygon[i].setShow(true);
			}
			else
			{
				setText(i, ' ');
				mPolygon[i].setShow(false);
			}
			
		}
		
	}
	
	public void setShow(boolean flag)
	{
		for(int i=0;i<max_num;i++)
		{
			mPolygon[i].setShow(flag);
		}
	}

	public void setText(int index, int text)
	{
		int char_index = text-(int)START_CHAR;
		float tileX = 1/MAX_FRAME_X;
		float tileY = 1/MAX_FRAME_Y;

		float numX = char_index%(int)MAX_FRAME_X;
		float numY = (int)(char_index/MAX_FRAME_X);

		mPolygon[index].image.setTile(tileX, tileY, numX*tileX, numY*tileY);	
		
	}
	
	public void setPosition(int x, int y)
	{
		mPosX = x;
		mPosY = y;
	}
	
	public void setTextSize(int x, int y) {
		mWidth = x;
		mHeight = y;
	}

}

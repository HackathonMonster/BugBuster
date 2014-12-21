/**

* ランニング中のメニュー管理

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         規則における改造

*/
package jp.android.bugsbuster;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;


public class PauseMenu {

	

	public final static int CONTINUE_BUTTON = 0;
	public final static int TITLE_BUTTON = 1;
	public final static int MAX_BUTTON = 2;

	final static int SIZE_X = 352;
	final static int SIZE_Y = 128;
	final static int INTERVAL = (int) (112);
	final static int START_INDEX = 1; 
	
	
	final static int BASE_POS_X = (int) (GLESRenderer.SCREEN_WIDTH/2);
	final static int BASE_POS_Y = (int) (GLESRenderer.SCREEN_HEIGHT/5*3)-40;
	

	final static int[][] BUTTON_INFO = {
		//x 						y 				width 		height
		{BASE_POS_X,		BASE_POS_Y-INTERVAL,	SIZE_X, 	SIZE_Y},		// CONTINUE_BUTTON
		{BASE_POS_X,		BASE_POS_Y+INTERVAL,	SIZE_X, 	SIZE_Y},		// TITLE_BUTTON
	};

	final static float GRID_SIZE = 1/2.0f;
	
	//texture uv info
	final static float[][] BUTTON_ICON_INFO = {
		//width 		height		offset x 		offset y 		
		{1, 		1*GRID_SIZE, 	0*GRID_SIZE, 	0*GRID_SIZE},			// CONTINUE_BUTTON
		{1, 		1*GRID_SIZE, 	0*GRID_SIZE, 	1*GRID_SIZE},			// TITLE_BUTTON
	};

	private PImage mBG = new PImage(Object.LAYER_3);
	private Button[] mButton = new Button[MAX_BUTTON];
	
	private boolean[] mPress = new boolean[MAX_BUTTON];
	private boolean mPause = false;
	
	// デバッグメニュー
	private Button mDebug = null;

	public PauseMenu()
	{
		mPause = false;
		
		for(int i=0; i<MAX_BUTTON; i++)
		{
			mPress[i] = false;
			mButton[i] = new Button(Object.LAYER_3);
		}
		
		init();
	}

	public void init()
	{
		mPause = false;
		mBG.init((int)GLESRenderer.SCREEN_WIDTH/2, (int)GLESRenderer.SCREEN_HEIGHT/2, 
				 (int)GLESRenderer.SCREEN_WIDTH, (int)GLESRenderer.SCREEN_HEIGHT);
		
		for(int i=0; i<MAX_BUTTON; i++)
		{
			mButton[i].init(BUTTON_INFO[i][0],BUTTON_INFO[i][1],BUTTON_INFO[i][2],BUTTON_INFO[i][3]);
			mButton[i].setTile(BUTTON_ICON_INFO[i][0],BUTTON_ICON_INFO[i][1],BUTTON_ICON_INFO[i][2],BUTTON_ICON_INFO[i][3]);
			mButton[i].setLayer( Object.LAYER_3 );
			mButton[i].setUse(true);
			mButton[i].setIdleColor(255, 255, 255, 255);
		}
			
		hide();
	}
	
	public void update() 
	{

		for(int i=0; i<MAX_BUTTON; i++)
		{
			mPress[i] = false;

		}
		
		for(int i=0; i<MAX_BUTTON; i++)
		{
			if(mButton[i].isSelected())
			{

				mPress[i] = true;
			}
		}
	}
	
	//テクスチャのリソースから読み込む
    public void loadTexture(GL10 gl, Context context )
    {
    	for(int i=0; i<MAX_BUTTON; i++)
		{
    		mButton[i].loadTexture(gl, context, R.drawable.pause_button );
		}
    	
    	mBG.loadTexture(gl, context, R.drawable.pause_bg);
    	    	
    }

    public boolean getPressButton(int index)
    {
    	//return mPress[index];
    	return mButton[index].isHit((int)Input.getTouchX(), (int)Input.getTouchY());
    }
    
    public boolean isPause()
    {
    	return mPause;
    }

	public void setUse(boolean flag) {

    	for(int i=0; i<MAX_BUTTON; i++)
		{
    		mButton[i].setUse(flag);
		}
    	
    	
	}
	
	public void setShow( boolean flag ) {
		for(int i=0; i<MAX_BUTTON; i++)
		{
    		mButton[i].setShow( flag );
		}
		
		mBG.setShow(flag);
	}

	public void show()
	{
		setShow(true);
		setUse(true);
	}
	
	public void hide()
	{
		setShow(false);
		setUse(false);
	}
}

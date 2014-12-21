/**

* シーンマネージャー

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         規則における改造

*/
package jp.android.bugsbuster;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.view.MotionEvent;

public class SceneManger{

	public static final int MODE_LOGO 		= 0;
	public static final int MODE_TITLE	 	= 1;
	public static final int MODE_GAME 		= 2;	
	public static final int MODE_RESULT 	= 3;	
	public static final int MODE_CREDIT 	= 4;	
	public static final int MODE_RANKING 	= 5;
	public static final int MODE_TUTORIAL 	= 6;
	public static final int MAX_SCENE 		= 7;
	
	public static int mNow = -1;
	public static int mNext = 0;
		
	private static Scene mScene = null;
	private static Context mContext;
	
	private static Transition mTransition = null;
	public static boolean mChange = false;
	public static boolean mReloadedTexture = false;
	
	//初期化に呼ばれる
	static public void init(Context context) {		
		GLESRenderer.setBackdropColor(0, 0, 0);

		//初期化
		mNow = -1;
		mNext = 0;
		mScene = null;
		mReloadedTexture = false;
		mChange = false;
		mContext = context;

		//オブジェクトリスト初期化
		Object.initList();
		
		Input.init();
				
		mChange = false;

		mTransition = new Transition();
		mTransition.reset();
		
		//first scene
		changeMode(MODE_LOGO);
		mTransition.setAlpha(255);
	}

	//初期化に呼ばれる
	static public void changeMode(int stage) {		
		
		if((!mChange) && (mNow != stage))
		{
			mNext = stage;

			mChange = true;

			mTransition.startFadeOut();
		}
	}

	//更新時に呼ばれる
	static public void update()
	{
		Input.upadate();
		
		if(mChange)
		{
			
			if(mTransition.isEndFadeOut())
			{
				if(mNow != mNext)
				{
					mNow = mNext;

					synchronized (Object.lock)
					{
						//オブジェクトリストクリア
						Object.clearList();
						
						switch(mNow)
						{
						case MODE_LOGO:
							mScene = new SceneLogo();
							break;
						case MODE_TITLE:
							mScene = new SceneTitle();
							break;
						case MODE_RANKING:
							mScene = new SceneRank();
							break;
						case MODE_TUTORIAL:
							mScene = new SceneTutorial();
							break;
						case MODE_GAME:
							mScene = new SceneGame();
							break;
						case MODE_RESULT:
							mScene = new SceneResult();
							break;
						case MODE_CREDIT:
						default:
							mScene = new SceneCredit();
							break;
						}
					
						mTransition.startFadeIn();
						
						if(mScene != null)
						{			
							mScene.init();
							mReloadedTexture = false;
						}

					}
				}
			}
			else if(mTransition.isEndFadeIn())
			{
				mTransition.stop();
				mChange = false;
				
			}
			
			if(mTransition != null)
			{
				mTransition.update();
			}
			
		}
		
		//シーンの更新
		else if(mScene != null)
		{
			synchronized (Object.lock)
			{
				mScene.update();
			}
		}
		
		synchronized (Object.lock)
		{
		//オブジェクトリスト更新
		Object.updateList();
		}
	}

	static public void reload()
	{
		mReloadedTexture = false;
	}
	
	//描画時に呼ばれる
	static public void draw(GL10 gl)
	{
		
		if(mScene != null)
		{
			if(!mReloadedTexture)
			{
				//シーンの初期化
				loadTexture(gl, mContext);
				mReloadedTexture = true;
				
				Text.loadFontTexture(gl, mContext);
			}
	
			//オブジェクトリスト描画
			Object.drawList(gl);
			
			//シーンの描画
			mScene.draw(gl);
		}

		if(mTransition != null)
		{
			mTransition.draw(gl);
		}	

	}
	
	//タッチ時に呼ばれる
	static public void updateInput(MotionEvent event)
	{        
		Input.upadate(event);

		if(mScene != null)
		{
			mScene.updateInput(event);
		}
	}

	public static void loadTexture(GL10 gl, Context mContext) {

		if(mScene != null)
		{
			mScene.loadTexture(gl, mContext);
		}
		

		//フォント
		Text.loadFontTexture(gl, mContext);

	}
	
}

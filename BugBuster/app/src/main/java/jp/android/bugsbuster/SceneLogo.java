/**

* ƒ`[ƒ€ƒƒS•\Ž¦ƒV[ƒ“

* @author WISITKARD WILASINEE

* @data 2013/10/06

* @update 22013/10/06 8:00
         
*         WISITKARD WILASINEE

*         ‹K‘¥‚É‚¨‚¯‚é‰ü‘¢

*/
package jp.android.bugsbuster;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.view.MotionEvent;


public class SceneLogo implements Scene {

	private PImage mPolygon;
	private int mCounter = 60;
	
	@Override
	public void init() {
		mPolygon = new PImage();
		mPolygon.init(	(int)GLESRenderer.SCREEN_WIDTH/2, 
						(int)GLESRenderer.SCREEN_HEIGHT/2,
						650, 300);

	    
		//”wŒi‚ÌF
		GLESRenderer.setBackdropColor(255,255,255);
		

		SoundManager.stopAllSound();
	}

	@Override
	public void update() {
		if(mCounter > 0)
		{
			mCounter--;
		}
		else
		{
			SceneManger.changeMode(SceneManger.MODE_TITLE);
		}
		
		if(Input.isMouseTrigger())
	    {
			SoundManager.playSound(SoundManager.SOUND_ID_SFX_OK);
			SceneManger.changeMode(SceneManger.MODE_TITLE);
	    }
	    
	}

	@Override
	public void updateInput(MotionEvent event) {
		
	}

	@Override
	public void draw(GL10 gl) {
		mPolygon.draw(gl);
	}

	@Override
	public void loadTexture(GL10 gl, Context context) {

		mPolygon.loadTexture(gl, context, ResourceConst.LOGO_IMG);
	}

	@Override
	public void uninit() {
		
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

}

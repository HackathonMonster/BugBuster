package jp.android.bugsbuster;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.view.MotionEvent;

public class SceneCredit  implements Scene {
	
		String WORD = "TAP to CONTINUE";
		PImage img = new PImage();
		Text mWord;
		int a;
		int aDir;	
		int counter;

		 public void init(){
		      a = 255;
		      aDir = -1;
		      counter = 300;
		      
		      mWord = new Text(WORD.length());
		      
		      GLESRenderer.setBackdropColor(0, 0, 0);

				img.init((int)GLESRenderer.SCREEN_WIDTH/2, (int)GLESRenderer.SCREEN_HEIGHT/2,
						(int)GLESRenderer.SCREEN_WIDTH, (int)GLESRenderer.SCREEN_HEIGHT);
		  }
		  
		 
		public void update()
		{
		    if(Input.isMouseTrigger())
		    {
		    	SoundManager.playSound(SoundManager.SOUND_ID_SFX_OK);
		    	SceneManger.changeMode(SceneManger.MODE_LOGO);
		    }
		    
		    if(counter > 0)
		    {
		      counter--;
		    }
		    else
		    {
		    	SceneManger.changeMode(SceneManger.MODE_LOGO);
		    }
		    
		    a += aDir*5;
		      
		    if((a < 128) || (a > 255))
		    {
		      aDir = -aDir;
		    }
		    
		    //TAP to START
		    mWord.setColor(35,26,26,a);

		    mWord.print(WORD, (int)GLESRenderer.SCREEN_WIDTH/2, 9*(int)GLESRenderer.SCREEN_HEIGHT/10);

		}
		  

		@Override
		public void uninit() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void updateInput(MotionEvent event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void draw(GL10 gl) {
			
		}

		@Override
		public void loadTexture(GL10 gl, Context context) {
			img.loadTexture(gl, context, R.drawable.credit);
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
		    
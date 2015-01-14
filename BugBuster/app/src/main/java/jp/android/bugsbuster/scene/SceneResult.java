package jp.android.bugsbuster.scene;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.view.MotionEvent;

import jp.android.bugsbuster.GLESRenderer;
import jp.android.bugsbuster.Input;
import jp.android.bugsbuster.R;
import jp.android.bugsbuster.SaveData;
import jp.android.bugsbuster.Scene;
import jp.android.bugsbuster.SoundManager;
import jp.android.bugsbuster.processing.PImage;
import jp.android.bugsbuster.processing.Text;

public class SceneResult  implements Scene {

		static boolean win =false;
		static int score = 0;
  
		String WORD = "TAP to CONTINUE";
		PImage img = new PImage();
		Text mWord;
		Text mScoreWord;
		int a;
		int aDir;	
		int counter;

		 public void init(){
		      a = 255;
		      aDir = -1;
		      counter = 1200;
		      
		      mWord = new Text(WORD.length());
		      mScoreWord = new Text(32);
		      

		      if(win){
		    	  SoundManager.playBGM(SoundManager.SOUND_ID_BGM_CLEAR);
		      }else{
		    	  SoundManager.playBGM(SoundManager.SOUND_ID_BGM_OVER);
		      }    
		      
		     SaveData.AddRecords(score);
		      
		  }
		  
		 
		public void update()
		{
		    if(Input.isMouseTrigger())
		    {
		    	SoundManager.playSound(SoundManager.SOUND_ID_SFX_OK);
		    	SceneManger.changeMode(SceneManger.MODE_CREDIT);
		    }
		    
		    if(counter > 0)
		    {
		      counter--;
		    }
		    else
		    {
		    	SceneManger.changeMode(SceneManger.MODE_RANKING);
		    }
		    
		    a += aDir*5;
		      
		    if((a < 128) || (a > 255))
		    {
		      aDir = -aDir;
		    }
		    

			img.init((int) GLESRenderer.SCREEN_WIDTH/2, (int)GLESRenderer.SCREEN_HEIGHT/2,
					(int)GLESRenderer.SCREEN_WIDTH, (int)GLESRenderer.SCREEN_HEIGHT);
		    
		    //TAP to START
		    mWord.setColor(35,26,26,a);

		    mWord.print(WORD, (int)GLESRenderer.SCREEN_WIDTH/2, 9*(int)GLESRenderer.SCREEN_HEIGHT/10);


		    mScoreWord.setColor(65,18,18,255);
		    mScoreWord.setTextSize(40, 80);
		    mScoreWord.print("Score:"+score,(int)(GLESRenderer.SCREEN_WIDTH/2),(int)(GLESRenderer.SCREEN_HEIGHT/7*3));
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

		    if(win){
		    	img.loadTexture(gl, context, R.drawable.clear);
		    }
		    else {
		    	img.loadTexture(gl, context, R.drawable.gameover);
		    }
			
			
		}


		@Override
		public void load() {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void release() {
			// TODO Auto-generated method stub
			
		}
			

		public static void setResult(boolean win2,int score2){
		    win=win2;
		    score=score2;
		}
}
		    
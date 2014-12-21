package jp.android.bugsbuster;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.view.MotionEvent;

public class SceneRank implements Scene {


	  PImage imgBg = new PImage();
	  int[] rank;
	  Text[] rank_score = new Text[SaveData.MAX_RANKING];
	  int a;
	  int aDir;
	  int counter = 200;
	  
	  //kaishi suru mae ni ikkai yobu (initial)
	  public void init()
	  {
		  imgBg.init((int)GLESRenderer.SCREEN_WIDTH/2, (int)GLESRenderer.SCREEN_HEIGHT/2,
				  (int)GLESRenderer.SCREEN_WIDTH, (int)GLESRenderer.SCREEN_HEIGHT);
		    rank = new int[SaveData.MAX_RANKING];

		    for(int i=0; i<SaveData.MAX_RANKING; i++)
		    {
		    	rank_score[i] = new Text(16);
		    	rank_score[i].setTextSize(42, 80);
		    }
		    
		    
		    MainActivity.data.loadRecords();
		    counter = 200;
	  }
	  
	  //maikai kurikaeshite koushin suru(loop)
	  public void update()
	  {
		  if(counter > 0)
		  {
			  counter--;
		  }
		  else
		  {
		    	SceneManger.changeMode(SceneManger.MODE_CREDIT);
		  }
		  
	    if(Input.isMouseTrigger())
	    {

	    	SoundManager.playSound(SoundManager.SOUND_ID_SFX_OK);
	    	SceneManger.changeMode(SceneManger.MODE_CREDIT);
	    }
	    
	    for(int i=0; i<SaveData.MAX_RANKING; i++)
	    {
	    	rank_score[i].print((i+1)+"."+String.format("%1$08d", MainActivity.data.load_data[i]), (int)GLESRenderer.SCREEN_WIDTH/3*2-65, 320+(i*100));	    	
	    }
	  }
	  	  
	  //owaru tokoro
	  public void release()
	  {
	    
	  }

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub

	      
	}

	@Override
	public void loadTexture(GL10 gl, Context context) {
		// TODO Auto-generated method stub
		imgBg.loadTexture(gl, context, R.drawable.ranking_bg);
	}
	
}

package jp.android.bugsbuster.scene;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.view.MotionEvent;

import jp.android.bugsbuster.Button;
import jp.android.bugsbuster.GLESRenderer;
import jp.android.bugsbuster.Input;
import jp.android.bugsbuster.R;
import jp.android.bugsbuster.Scene;
import jp.android.bugsbuster.SoundManager;
import jp.android.bugsbuster.game_model.Ant;
import jp.android.bugsbuster.processing.PImage;
import jp.android.bugsbuster.processing.Text;

public class SceneTitle  implements Scene {

	  final int TITLE_CURSOR_PLAY = 0;
	  final int TITLE_CURSOR_RANKING= 1;
	  final int TITLE_CURSOR_CREDIT = 2;
	  final int TITLE_CURSOR_MAX = 3;
	  
	  PImage imgBg = new PImage();
	  PImage imgTitle = new PImage(); 
	  Ant ant = new Ant();
	  PImage imgFrame = new PImage(); 
	  Button[] imgTitleMenu = new Button[TITLE_CURSOR_MAX];
	  int counter;
	  
	  int a;
	  int aDir;
	  int state;
	  
	  int cursor = -1;
	  boolean delay_input = true;
	        
	  final int TITLE_LOGO_START_FADEIN = 0;
	  final int TITLE_LOGO_FADEIN = 1;
	  final int TITLE_START_INPUT = 2;
	  final int TITLE_UPDATE_INPUT = 3;
	  final int TITLE_START_MENU = 4;
	  final int TITLE_UPDATE_MENU = 5;
	  
	    
	  
	  final float TITLE_CURSOR_START_X = GLESRenderer.SCREEN_WIDTH/2;
	  final float TITLE_CURSOR_START_Y = (3*GLESRenderer.SCREEN_HEIGHT/5);
	  final float TITLE_CURSOR_SIZE_X = 300;//432;
	  final float TITLE_CURSOR_SIZE_Y = 100;
	  
	  
	  String WORD = "TAP to START";
	  Text mWord;

		 public void init(){
		      a = 255;
		      aDir = -1;
	    	  cursor = -1;
		      counter = 1800;
		      
		      mWord = new Text(WORD.length());
		      mWord.setShow(true);
		      mWord.setTextSize(30, 60);
	    	    	    	    
	    	  state = TITLE_LOGO_START_FADEIN;
	    	  
			  imgBg.init((int)GLESRenderer.SCREEN_WIDTH/2, (int)GLESRenderer.SCREEN_HEIGHT/2,
					  (int)GLESRenderer.SCREEN_WIDTH, (int)GLESRenderer.SCREEN_HEIGHT);
			  
	    	  imgTitle.init((int)GLESRenderer.SCREEN_WIDTH/2, (int) (GLESRenderer.SCREEN_HEIGHT/4), 460, 228);
	    	  
			  
			  imgFrame.init((int)GLESRenderer.SCREEN_WIDTH/2, (int)GLESRenderer.SCREEN_HEIGHT/2,
					  (int)GLESRenderer.SCREEN_WIDTH, (int)GLESRenderer.SCREEN_HEIGHT);
	    	  
			  for(int i=0; i<TITLE_CURSOR_MAX;i++)
		      {
				  imgTitleMenu[i] = new Button();
		    	  imgTitleMenu[i].setSelectColor(255,255,0, 255);
		    	  imgTitleMenu[i].setIdleColor(255,255,255, 255);
			        
		          imgTitleMenu[i].setPosition(TITLE_CURSOR_START_X, TITLE_CURSOR_START_Y+(i*TITLE_CURSOR_SIZE_Y));
		          imgTitleMenu[i].setSize(TITLE_CURSOR_SIZE_X, TITLE_CURSOR_SIZE_Y);
		          
		          imgTitleMenu[i].setShow(false);
		         
		      }


		      imgTitle.setColor(255,255,255,0);
		      mWord.setColor(255,255,255,0);
		      
		      ant.init(0, 0, 96, 96);
		      ant.reset();
		      

		      mWord.print("", (int)GLESRenderer.SCREEN_WIDTH/2, 2*(int)GLESRenderer.SCREEN_HEIGHT/3);
		    	
			  //�w�i�̐F
			  GLESRenderer.setBackdropColor(0,0,0);
				
		      SoundManager.playBGM(SoundManager.SOUND_ID_BGM_TITLE);

	    }
		 
		 
		  
		 
		public void update()
		{
		    if(Input.isTouch())
		    {
		    	counter = 2000;
		    }
		    
		    if(counter > 0)
		    {
		      counter--;
		    }
		    else
		    {
		    	SceneManger.changeMode(SceneManger.MODE_RANKING);
		    }
		    		    
		    //TAP to START
		    mWord.setColor(255,255,255,a);
		    if((state >= TITLE_START_INPUT) && (state < TITLE_START_MENU))
		    {
		    	mWord.print(WORD, (int)GLESRenderer.SCREEN_WIDTH/2, 2*(int)GLESRenderer.SCREEN_HEIGHT/3);
		    }
		    
		 
		    switch(state)
		    {
		    case TITLE_LOGO_START_FADEIN:
		    
		      a = 1;
		      aDir = +1;
		      state = TITLE_LOGO_FADEIN;
		      
		      mWord.print("", (int)GLESRenderer.SCREEN_WIDTH/2, 2*(int)GLESRenderer.SCREEN_HEIGHT/3);
		    break;
		    case TITLE_LOGO_FADEIN:
		    
		      if(Input.isMouseTrigger() || a >= 255)
		      {

		    	  if(Input.isMouseTrigger())
		    	  {
		    		  SoundManager.playSound(SoundManager.SOUND_ID_SFX_OK);
		    	  }
		    	  
		    	  a = 255;
		    	  state = TITLE_START_INPUT;
		    	  

			      mWord.print("", (int)GLESRenderer.SCREEN_WIDTH/2, 2*(int)GLESRenderer.SCREEN_HEIGHT/3);
		      }
		      else
		      {
		        a += aDir*10;
		        if(a > 255)
		        {
		          aDir = -aDir;
		        }
		        
		      }
		      
		      imgTitle.setColor(255,255,255,a);
		    
		    break;
		    case TITLE_START_INPUT:
		      
		      a = 255;
		      aDir = -1;
		      state = TITLE_UPDATE_INPUT;

		      mWord.print("", (int)GLESRenderer.SCREEN_WIDTH/2, 2*(int)GLESRenderer.SCREEN_HEIGHT/3);
		    
		    break;
		    case TITLE_UPDATE_INPUT:
		      
		      if(Input.isMouseTrigger())
		      {
		    	  state = TITLE_START_MENU;
	    		  SoundManager.playSound(SoundManager.SOUND_ID_SFX_OK);
		      }
		      
		      a += aDir*5;
		      
		      if((a < 128) || (a > 255))
		      {
		        aDir = -aDir;
		      }

			  mWord.setPosition((int)GLESRenderer.SCREEN_WIDTH/2, 2*(int)GLESRenderer.SCREEN_HEIGHT/3);
		      mWord.print(WORD, (int)GLESRenderer.SCREEN_WIDTH/2, 2*(int)GLESRenderer.SCREEN_HEIGHT/3);
		    break;
		    case TITLE_START_MENU:
		      state = TITLE_UPDATE_MENU;
		      cursor = -1;

			  for(int i=0; i<TITLE_CURSOR_MAX;i++)
		      {
		          imgTitleMenu[i].setShow(true);
		      }

			  mWord.setPosition((int)GLESRenderer.SCREEN_WIDTH/2, 2*(int)GLESRenderer.SCREEN_HEIGHT/3);
		      mWord.print("", (int)GLESRenderer.SCREEN_WIDTH/2, 2*(int)GLESRenderer.SCREEN_HEIGHT/3);
		      
		      delay_input = true;
		    break;
		    case TITLE_UPDATE_MENU:


		        cursor = -1;

		        for(int i=0; i<TITLE_CURSOR_MAX;i++)
			    {
		        	  if(imgTitleMenu[i].isSelected())
			          {
			        	  cursor = i;
			          }		          
			          
			   }
		        
		      if(Input.isMouseTrigger())
		      {  
		    	  if(delay_input)
		    	  {
		    		  delay_input = false;
		    	  }
		      }
		      
		      if(!delay_input && Input.isMouseRelease())
		      {  
		        switch(cursor)
		        {
		          case TITLE_CURSOR_CREDIT:
		        	  SoundManager.playSound(SoundManager.SOUND_ID_SFX_OK);
		        	  SceneManger.changeMode(SceneManger.MODE_CREDIT);
		          break;
		          case TITLE_CURSOR_PLAY:
		        	  SoundManager.playSound(SoundManager.SOUND_ID_SFX_OK);
		        	  SceneManger.changeMode(SceneManger.MODE_TUTORIAL);
		          break;
		          case TITLE_CURSOR_RANKING:
		        	  SoundManager.playSound(SoundManager.SOUND_ID_SFX_OK);
		        	  SceneManger.changeMode(SceneManger.MODE_RANKING);
		          break;
		          
		        }
		        
		        
		      }


		    break;
		    }
		    

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
			imgBg.loadTexture(gl, context, R.drawable.title_bg);
			imgTitle.loadTexture(gl, context, R.drawable.title_logo);
			imgFrame.loadTexture(gl, context, R.drawable.title_frame);
			

	        imgTitleMenu[TITLE_CURSOR_PLAY].loadTexture(gl, context, R.drawable.play_button);
	        imgTitleMenu[TITLE_CURSOR_CREDIT].loadTexture(gl, context, R.drawable.credit_button);
	        imgTitleMenu[TITLE_CURSOR_RANKING].loadTexture(gl, context, R.drawable.ranking_button);
	        
	        
	        ant.loadTexture(gl, context, R.drawable.ant);
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
		    
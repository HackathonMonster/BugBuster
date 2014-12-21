package jp.android.bugsbuster;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundManager {


	static MediaPlayer sound_player = null;
	static SoundPool soundPool = null;
	static AssetManager assetManager;
	static AssetFileDescriptor[] assetFile;
	static int[] sfx_id;

	//sound index
	public static final int SOUND_ID_SFX_OK    = 0;
	public static final int SOUND_ID_SFX_DEAD  = 1;
	public static final int SOUND_ID_SFX_PICK  = 2;
	public static final int SOUND_ID_SFX_PUT   = 3;
	public static final int SOUND_ID_SFX_MOVE  = 4;
	public static final int SOUND_ID_SFX_BOX   = 5;
	public static final int SOUND_ID_SFX_EAT   = 6;
	public static final int SOUND_ID_SFX_SCORE = 7;

	public static final int SOUND_ID_BGM_TITLE = 8;
	public static final int SOUND_ID_BGM_GAME  = 9;
	public static final int SOUND_ID_BGM_CLEAR = 10;
	public static final int SOUND_ID_BGM_OVER  = 11;

	public static final int MAX_SOUND = 12;

	public static final int MAX_SFX = 8;
	public static final int MAX_BGM = MAX_SOUND - MAX_SFX;
	public static final int START_BGM_ID = MAX_SFX;

	static int now_bgm_id = -1;
	static boolean mPause = false;
		
	public static void InitSound(Context context)
	{
	  mPause = false;
	  String[] snd_filename = new String[MAX_SOUND];
	  
	  
	  snd_filename[SOUND_ID_BGM_TITLE]  = "BGM/title.mp3"; 
	  snd_filename[SOUND_ID_BGM_GAME]   = "BGM/game.mp3"; 
	  snd_filename[SOUND_ID_BGM_CLEAR]  = "BGM/gameclear.mp3"; 
	  snd_filename[SOUND_ID_BGM_OVER]   = "BGM/gameover.mp3"; 
	  
	  snd_filename[SOUND_ID_SFX_OK]     = "SE/ok.wav"; 
	  snd_filename[SOUND_ID_SFX_DEAD]   = "SE/dead.wav"; 
	  snd_filename[SOUND_ID_SFX_PICK]   = "SE/pick.wav"; 
	  snd_filename[SOUND_ID_SFX_PUT]    = "SE/put.wav"; 
	  snd_filename[SOUND_ID_SFX_MOVE]   = "SE/walk.wav";
	  snd_filename[SOUND_ID_SFX_BOX]    = "SE/box.wav"; 
	  snd_filename[SOUND_ID_SFX_EAT]    = "SE/eat.wav"; 
	  snd_filename[SOUND_ID_SFX_SCORE]  = "SE/score.wav"; 
	  

      try 
      {
          //Android
          
          //BGM
          sound_player = new MediaPlayer();
          assetManager = context.getAssets();
          assetFile = new AssetFileDescriptor[MAX_BGM];
               
           for(int i=0;i<MAX_BGM;i++)
           {
             assetFile[i] = assetManager.openFd(snd_filename[i+START_BGM_ID]); 
           }
           
           
           //SE
           soundPool = new SoundPool(MAX_SFX, AudioManager.STREAM_MUSIC, 0);     
           sfx_id =  new int[MAX_SFX];
           
           for(int i=0;i<MAX_SFX;i++)
           {
             sfx_id[i] = soundPool.load(assetManager.openFd(snd_filename[i]), 0); 
           }

       } 
       catch (IllegalArgumentException e) {
         e.printStackTrace();
       } 
       catch (IllegalStateException e) {
         e.printStackTrace();
       } 
       catch (IOException e) {
         e.printStackTrace();
       }
	  
	}
	
	

	//ANDROID
	 //SE 
	public static void playSound(int index)
	{
	  if((index >= 0) && (index < MAX_SOUND))
	  {
	      //Android
	      soundPool.play(sfx_id[index], 1, 1, 0, 0, 1);
	    
	  }
	}

	public static void playBGM(int index)
	{
	  if(now_bgm_id != index)
	  {
	    stopSound(now_bgm_id);
	    
	    if((index >= 0) && (index < MAX_SOUND))
	    {
	      
	          try
	          {
	             sound_player.reset();
	             sound_player.setDataSource(assetFile[index-START_BGM_ID].getFileDescriptor(), 
	                               assetFile[index-START_BGM_ID].getStartOffset(), 
	                               assetFile[index-START_BGM_ID].getLength());
	             sound_player.prepare();
	             sound_player.setLooping(true);
	             sound_player.start();
	          } 
	          catch (IllegalArgumentException e) {
	            e.printStackTrace();
	          } 
	          catch (IllegalStateException e) {
	            e.printStackTrace();
	          } catch (IOException e) {
	            e.printStackTrace();
	          }
	       
	    
	        
	    }
	    
	    now_bgm_id = index;
	  }  
	}



	public static void stopSound(int index)
	{
	  if(index == now_bgm_id)
	  {
	     try
	     {
	         //sound_player.stop();
	         sound_player.pause();
	         
	     }
	       catch (IllegalArgumentException e) {
	         e.printStackTrace();
	       } 
	       catch (IllegalStateException e) {
	         e.printStackTrace();
	       } 
	       
	     now_bgm_id = -1;
	  } 
	}


	public static void stopAllSound()
	{
	  for(int i=0; i<MAX_SOUND;i++)
	  {
	    stopSound(i);
	  } 
	}


 
	public static void ReleaseSound() {
	 
	  if(sound_player != null)
	  {
	    
	    sound_player.stop();
	    sound_player.release();
	  }
	   
	  
	  if(soundPool!=null) { 
	    soundPool.release();
	    soundPool = null;
	  }
	}



	public static void pause(boolean flag) {
		mPause = flag;
		
		if(mPause)
		{
	         sound_player.pause();
		}
		else
		{
			sound_player.start();
		}
	}

}

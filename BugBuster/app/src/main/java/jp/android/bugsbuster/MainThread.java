package jp.android.bugsbuster;

public class MainThread extends Thread {

	//FPS
	private static long lastTime = System.nanoTime();
	private static long lastFPSTime = lastTime;	
	private static int frames = 0;
	private static float FPS_count = 0.f;
	private static float FPS = 24.f;
	private static final long NANO_TIME = 1000000000;

	private boolean bEnd = false;

	public static float getFPS()
	{		
		return FPS_count;
	}
	
	@Override
	public void run() {

		while(!bEnd)
		{	
			//handler.post(new Runnable() {public void run(){mMission.Update();}});
			long currentTime = System.nanoTime(); 
			
			//FPSチェック時間
		    if( currentTime - lastFPSTime >= (NANO_TIME)) 
		    {
	        	
		    	FPS_count = (frames * NANO_TIME) / (currentTime - lastFPSTime);
		    	
		    	frames = 0;	 
		    	lastFPSTime = currentTime;
			}
		    
		    if( currentTime - lastTime >= (NANO_TIME/FPS)) 
		    {	        
		    	//時間更新　time syncronize
		        lastTime = currentTime; 
		        
		    	//シーンの更新
		    	SceneManger.update();

				
	        	frames++;
			}
		}
	}
	

	public void release()
	{
		bEnd = true;	
	}
}

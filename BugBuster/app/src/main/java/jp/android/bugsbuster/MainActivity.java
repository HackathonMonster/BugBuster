/**
* メインアクティビティクラス
* @author WISITKARD WILASINEE
*/
package jp.android.bugsbuster;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class MainActivity extends Activity implements 
SensorEventListener {

	//サウンド
	public static Activity activity;

	public static MainThread thread;
	
	//renderer
	private MainView mGLView;
	private GLESRenderer mRenderer; 
	
	//
	public static SaveData data;
	
	//サウンド
	//public static SoundManager sound;
			
	//option
	public final boolean RUN_IN_BACKGROUND = false;

	//センサー(仮)
	static public float[] mAccCoor = new float[3]; 
	static public float[] mGyroCoor = new float[3]; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        
        //画面の回転（固定）
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //SurfaceViewを生成
        mGLView = new MainView(this);
        
        //rendererを生成
        mRenderer = new GLESRenderer(mGLView.getContext());
        
        mGLView.setRenderer(mRenderer);
        
        //自分のsurfaceに設定
        setContentView(mGLView);
        
        //マネジャー初期化
        SceneManger.init(mGLView.getContext());
     	
        SoundManager.InitSound(this);
		
		//過去のデータ読み込む
        data = new SaveData(mGLView.getContext());
        SaveData.InitAllRecords();
        data.loadRecords();

        
        //アクティビティインスタンス
        activity = this;
        

		thread = new MainThread();
		thread.start();
        
    }
    
    //再開
    @Override
    protected void onPause() {

        super.onPause();
        
        if(!RUN_IN_BACKGROUND)
        {
        	mGLView.onPause();
        }

        
        //リロード
        Text.unloadFontTexture();
        
        SoundManager.pause(true);
        
    }

    //一時停止（APPから離れる）
    @Override
    protected void onResume() {

        super.onResume();
        
        if(!RUN_IN_BACKGROUND)
        {
        	mGLView.onResume();
        }
        
        //
        SoundManager.pause(false);
    }


    //backボタン
    protected void onDestroy() {

        super.onDestroy();
        
        SoundManager.stopAllSound();

		//過去のデータ書き込む
        //saveRecord();
        
        //サウンド停止
        //sound.stop();
    //  mSoundManager.ReleaseSound();

		// リソースがリークし、例外発生しない処置。
		thread.release();
    }
    
    
      
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		//sensor.UpdateSensor(event);
		if(event.sensor.getType()==Sensor.TYPE_GYROSCOPE){
			mGyroCoor[0] = event.values[0];
			mGyroCoor[1] = event.values[1];
			mGyroCoor[2] = event.values[2];
		}

		// check sensor type
		if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
			mAccCoor[0] = event.values[0];
			mAccCoor[1] = event.values[1];
			mAccCoor[2] = event.values[2];
		}
	}

}

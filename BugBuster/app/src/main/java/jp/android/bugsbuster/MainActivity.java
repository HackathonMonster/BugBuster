/**
* ���C���A�N�e�B�r�e�B�N���X
* @author WISITKARD WILASINEE
*/
package jp.android.bugsbuster;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import jp.android.bugsbuster.processing.Text;
import jp.android.bugsbuster.scene.SceneManger;

public class MainActivity extends Activity {

	//�T�E���h
	public static Activity activity;

	public static MainThread thread;
	
	//renderer
	private MainView mGLView;
	private GLESRenderer mRenderer; 
	
	//
	public static SaveData data;
	
	//�T�E���h
	//public static SoundManager sound;
			
	//option
	public final boolean RUN_IN_BACKGROUND = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        
        //��ʂ̉�]�i�Œ�j
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //SurfaceView�𐶐�
        mGLView = new MainView(this);
        
        //renderer�𐶐�
        mRenderer = new GLESRenderer(mGLView.getContext());
        
        mGLView.setRenderer(mRenderer);
        
        //������surface�ɐݒ�
        setContentView(mGLView);
        
        //�}�l�W���[����
        SceneManger.init(mGLView.getContext());
     	
        SoundManager.InitSound(this);
		
		//�ߋ��̃f�[�^�ǂݍ���
        data = new SaveData(mGLView.getContext());
        SaveData.InitAllRecords();
        data.loadRecords();

        
        //�A�N�e�B�r�e�B�C���X�^���X
        activity = this;
        

		thread = new MainThread();
		thread.start();
        
    }
    
    //�ĊJ
    @Override
    protected void onPause() {

        super.onPause();
        
        if(!RUN_IN_BACKGROUND)
        {
        	mGLView.onPause();
        }

        
        //�����[�h
        Text.unloadFontTexture();
        
        SoundManager.pause(true);
        
    }

    //�ꎞ��~�iAPP���痣���j
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


    //back�{�^��
    protected void onDestroy() {

        super.onDestroy();
        
        SoundManager.stopAllSound();

		//�ߋ��̃f�[�^��������
        //saveRecord();
        
        //�T�E���h��~
        //sound.stop();
    //  mSoundManager.ReleaseSound();

		// ���\�[�X�����[�N���A��O�������Ȃ����u�B
		thread.release();
    }

}

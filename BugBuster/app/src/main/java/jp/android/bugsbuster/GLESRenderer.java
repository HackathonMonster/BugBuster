/**
* OpenGLES�����_���[

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         �K���ɂ������

*/
package jp.android.bugsbuster;

import android.content.Context;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import android.opengl.GLSurfaceView;

import jp.android.bugsbuster.scene.SceneManger;

public class GLESRenderer implements GLSurfaceView.Renderer {

	//RGBA
	private static color mBackdropColor;		
	public static int SCREEN_SIZE_X  = 800;						//�[���ɂ��̃T�C�Y
	public static int SCREEN_SIZE_Y  = 1280;					//�[���ɂ��̃T�C�Y
	public static float SCREEN_SCALE  = 1.0f;
	public static float SCREEN_WIDTH  = 720.f;//750.0f;					//�W���̃T�C�Y(for fullscreen)
	public static float SCREEN_HEIGHT = 960.f;//1024.0f;				//�W���̃T�C�Y(for fullscreen)
	public static float SCREEN_RATIO = (SCREEN_SIZE_X/SCREEN_WIDTH);

	public static Context mContext;

	public GLESRenderer(Context context) {
		mContext = context;		
		mBackdropColor = new color();
		
		//����
		mBackdropColor.setColor(0, 0, 0, 255);
	}
	
	// �w�i�̐F�ݒ�
	public static void setBackdropColor(int r, int g, int b) {
		mBackdropColor.setColor(r, g, b, 255);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        //  ���W�̏���
        gl.glLoadIdentity();
		
		//�V�[���̏���
    	SceneManger.loadTexture(gl, mContext);
    }
	
	public void onSurfaceChanged(GL10 gl, int width, int height) {

		SCREEN_SIZE_X = width;
		SCREEN_SIZE_Y = height;
		
		SCREEN_SCALE = SCREEN_WIDTH/SCREEN_SIZE_X;
		
		// ��ʃT�C�Y�X�V
        gl.glViewport(0, 0, width, height);
		

		// ��ʂ̏c���䗦
		float ratio = (float) width / height;

        //  ���f���r���[�s��̎w��
        gl.glMatrixMode(GL10.GL_MODELVIEW);

        //  ���W�̏���
        gl.glLoadIdentity();
        
        //  ��p�̐ݒ�(left,right,bottom,top,near,far)
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 1000);
              
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		
		synchronized (Object.lock)
		{
			//backdrop color
			gl.glClearColor(mBackdropColor.r, mBackdropColor.g, mBackdropColor.b, mBackdropColor.a);
	        gl.glClear( GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT );

	        // Enable blending
	        gl.glEnable(GL10.GL_BLEND);
	        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	        
	        gl.glDisable( GL10.GL_DEPTH_TEST );
	        
	        //2D�`�悽��
			gl.glDisable(GL10.GL_LIGHTING);
				
	
		    //�V�[���̕`��		
			SceneManger.draw(gl);	
    	
		}

	}
}

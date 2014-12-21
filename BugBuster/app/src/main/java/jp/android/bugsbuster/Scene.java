/**

* �V�[���N���X

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         �K���ɂ��������

*/
package jp.android.bugsbuster;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.view.MotionEvent;

public interface Scene {

	//�ǂݍ���
	abstract public void load();
	
	//������
	abstract public void init();

	//�X�V
	abstract public void update();

	//�I��
	abstract public void uninit();
	
	//�J��
	abstract public void release();

	//���͍X�V
	abstract public void updateInput(MotionEvent event);

	//�`��
	abstract public void draw(GL10 gl);
	
	abstract public void loadTexture(GL10 gl, Context context);
}

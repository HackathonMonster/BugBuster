/**

* ���C���r���[

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         �K���ɂ��������

*/
package jp.android.bugsbuster;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MainView extends GLSurfaceView {
	
	public MainView(Context context) {
		super(context);
		
		
	}

	//�^�b�`���ɌĂ΂��
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		SceneManger.updateInput(event);
		
		return true;
	}
}

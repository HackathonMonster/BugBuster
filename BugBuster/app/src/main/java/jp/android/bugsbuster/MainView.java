/**

* メインビュー

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         規則における改造

*/
package jp.android.bugsbuster;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MainView extends GLSurfaceView {
	
	public MainView(Context context) {
		super(context);
		
		
	}

	//タッチ時に呼ばれる
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		SceneManger.updateInput(event);
		
		return true;
	}
}

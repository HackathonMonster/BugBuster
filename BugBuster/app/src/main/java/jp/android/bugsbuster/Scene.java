/**

* シーンクラス

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         規則における改造

*/
package jp.android.bugsbuster;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.view.MotionEvent;

public interface Scene {

	//読み込む
	abstract public void load();
	
	//初期化
	abstract public void init();

	//更新
	abstract public void update();

	//終了
	abstract public void uninit();
	
	//開放
	abstract public void release();

	//入力更新
	abstract public void updateInput(MotionEvent event);

	//描画
	abstract public void draw(GL10 gl);
	
	abstract public void loadTexture(GL10 gl, Context context);
}

/**

* ì¸óÕä«óù

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         ãKë•Ç…Ç®ÇØÇÈâ¸ë¢

*/
package jp.android.bugsbuster;

import android.view.MotionEvent;

public class Input {

	private static boolean touch = false;
	private static boolean touchLast = false;
	private static boolean touchTrigger = false;
	private static boolean touchRelease = false;

	private static float touch_x = 0;
	private static float touch_y = 0;
	 public static void init()
	 {
		 touch = false;
		 touchLast = false;
		 touchTrigger = false;
		 touchRelease = false;
	 }

	 public static void upadate(MotionEvent event)
	 {
		 //input update
		 if(event.getAction() == MotionEvent.ACTION_DOWN) 
		 {
			 touch = true;
		 }
		 
		 if((event.getAction() == MotionEvent.ACTION_UP) ||
			(event.getAction() == MotionEvent.ACTION_CANCEL) )
		 {
			 touch = false;
		 }
		 
		  touch_x = event.getX() * GLESRenderer.SCREEN_WIDTH / GLESRenderer.SCREEN_SIZE_X;
		  touch_y = event.getY() * GLESRenderer.SCREEN_HEIGHT / GLESRenderer.SCREEN_SIZE_Y;
		  
	 }

	 public static boolean isTouch()
	 {
		 return touch;
	 }

	 public static boolean isMouseTrigger() {
		return touchTrigger;
	 }

	 public static boolean isMouseRelease() {
		return touchRelease;
	 }

	 public static float getTouchX() {
		return touch_x;
	 }

	 public static float getTouchY() {
		return touch_y;
	 }

	public static void upadate() {


		 if(!touchLast && touch) 
		 {
			 touchTrigger = true;
		 }
		 else
		 {
			 touchTrigger = false;
		 }
		 
		 if(touchLast && !touch)
		 {
			 touchRelease = true;
		 }
		 else
		 {
			 touchRelease = false;
		 }
		 
		  touchLast = touch;
	}

}

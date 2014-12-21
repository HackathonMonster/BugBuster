package jp.android.bugsbuster;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public class Obstacle extends Object {
	
	ArrayList<Integer> originalSet = new ArrayList<Integer>();
	     
	@Override
	public void reset() {
		originalSet.clear();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(GL10 gl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadTexture(GL10 gl, Context context, int id) {
		// TODO Auto-generated method stub

	}

	   boolean contains(int x){
	      for(int i=0; i<originalSet.size(); i++){
	        if(originalSet.get(i).equals(x)){
	           return true;
	        }
	      }
	      return false;
	   }
	    
	   boolean objeAdd(int x){
	      if(contains(x)){
	         return false; 
	      }else{
	       originalSet.add(x); 
	      }
	      return true;
	   }
	   
	   int getSize(){
	     return originalSet.size();
	   }
}

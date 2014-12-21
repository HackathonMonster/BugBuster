/**

* ランニングのセーブデータ管理

* @author WISITKARD WILASINEE

* @data 2013/10/10

* @update 22013/10/10 8:00
         
*         WISITKARD WILASINEE

*         規則における改造

*/
package jp.android.bugsbuster;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveData {
	

	// 変数
	static private Context mContext = null;						// コンテキスト
	
	
	// 読みだしたデータ
	public final static int MAX_RANKING = 5;
	public static int[] load_data = new int[MAX_RANKING];		

	// 初期化
	SaveData( Context context ){
		mContext = context;
	}


	
	public static void saveRecord(int data, int index){
		SharedPreferences.Editor prefs = mContext.getSharedPreferences("jp.android.konsaijoshi", 0).edit();

		prefs.putInt("RANK"+index, data);
		//prefs.putString("SAVE_NAME"+index, data.mName);
		prefs.commit();
	}


	public static void InitAllRecords(){
		for(int i=0; i<MAX_RANKING; i++)
		{
			load_data[i] = 0;
		}
	}

	public static void saveAllRecords(){
		for(int i=0; i<MAX_RANKING; i++)
		{
			saveRecord(load_data[i], i);
		}
	}

	public  void loadRecords(){
		SharedPreferences prefs = mContext.getSharedPreferences("jp.android.konsaijoshi", 0);
		for(int i=0; i<MAX_RANKING; i++)
		{
			load_data[i] = prefs.getInt("RANK"+i, 0);
		}
	}
	
	public static void AddRecords(int data){
		int index = -1;
		for(int i=0; i<MAX_RANKING; i++)
		{
			if(data > load_data[i])
			{
				index = i;
				break;
			}
		}
		
		if(index >= 0)
		{
			for(int i=MAX_RANKING-1; i>index; i--)
			{
				load_data[i] = load_data[i-1];				
			}

			load_data[index] = data;
		}
		
		saveAllRecords();
	}

	
	
	
}

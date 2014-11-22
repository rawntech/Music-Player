package com.skarim.music_player.app.utils;


public class CommonValues {
	private static CommonValues commonValues;
	
	public int ExceptionCode = CommonConstraints.NO_EXCEPTION;
	public boolean IsServerConnectionError = false;	
	
	public static CommonValues getInstance(){
		return commonValues;
	}
	
	public static void Initalization(){
		if(commonValues == null){
			commonValues = new CommonValues();
		}
	}

}

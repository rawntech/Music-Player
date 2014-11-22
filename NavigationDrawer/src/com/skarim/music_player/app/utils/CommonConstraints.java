package com.skarim.music_player.app.utils;

public class CommonConstraints {
	public static final int NO_EXCEPTION = -1;
	public static final int GENERAL_EXCEPTION = 0;
	public static final int CLIENT_PROTOCOL_EXCEPTION=1;
	public static final int ILLEGAL_STATE_EXCEPTION=2;
	public static final int UNSUPPORTED_ENCODING_EXCEPTION=3;
	public static final int IO_EXCEPTION=4;	
	public final static String EncodingCode = "UTF8";
	
	public final static String SKM = "skm";
	
	public static final int ALL_MUSIC=1;
	public static final int ALL_ARTIST=2;
	public static final int ALL_ALBUM=3;
	public static int WHICH_MODE=1;
	public static int CURRENT_CURSOR_POSITION=0;
	public static final String SONG_PATH="SONG_PATH";
	public static String CURRENT_SONG_PATH="";
}

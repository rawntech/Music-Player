package com.skarim.music_player.app.classes;

import java.util.ArrayList;

public class Artist {
	private String albumname;
	private int albumcount;
	private String path;
	private static ArrayList<Artist> artistlist;
	
	public Artist(String artistname, int albumcount) {
		this.albumname = artistname;
		this.albumcount = albumcount;
	}
	
	public String getArtistName() {
		return albumname;
	}
	
	public String getAlbumCount() {
		return String.valueOf(albumcount);
	}

	public static void setArtistList(ArrayList<Artist> list) {
		artistlist = list;
	}
	
	public static int numberOfSongs(){
		return artistlist.size();
	}
	
	public static String getSongPath(int pos) {
		return artistlist.get(pos).getPath();
	}
	
	public String getPath() {
		return path;
	}
	
	public static String getArtistName(int pos) {
		return artistlist.get(pos).getArtistName();
	}
}

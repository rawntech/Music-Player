package com.skarim.music_player.app.classes;

import java.util.ArrayList;
import android.graphics.Bitmap;

public class Album {
	private String albumname;
	private String artistname;
	private int numberOfSongs;
	private Bitmap img;
	private String coverpath;
	private static ArrayList<Album> albumlist;
	
	public Album(String albumname, String artistname, Bitmap cover, String coverPath,int _numberOfSongs) {
		this.albumname = albumname;
		this.artistname = artistname;
		this.coverpath = coverPath;
		this.numberOfSongs=_numberOfSongs;
		this.img = cover;
	}
	
	public String getAlbumName() {
		return this.albumname;
	}
	
	public String getArtistName() {
		return this.artistname;
	}
	
	public Bitmap getCover() {
		return this.img;
	}
	
	public static void setAlbumList(ArrayList<Album> list) {
		albumlist = list;
	}
	
	public static String getAlbumNameNew(int pos) {
		return albumlist.get(pos).getAlbumName();
	}
	
	public static String getCoverPathNew(int pos) {
		return albumlist.get(pos).getCoverPath();
	}
	
	public String getCoverPath() {
		return this.coverpath;
	}
	
	public int getNumberOfSongs(){
		return this.numberOfSongs;
	}
	
	
}

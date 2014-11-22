package com.skarim.music_player.app.classes;

import java.io.Serializable;
import java.util.ArrayList;

import android.graphics.Bitmap;

public class Song  implements Serializable{
	
	private String songname;
	private String artistname;
	private String albumname;
	private String duration;
	private String path;
	private String coverpath;
	private Bitmap img;
	
	private static ArrayList<Song> aSongList=null;
	
	public Song(String name, String artist, String album, String dur,String _path,String _coverPath) {
		this.songname = name;
		this.artistname = artist;
		this.albumname = album;
		this.duration = dur;
		this.path=_path;
		this.coverpath=_coverPath;
	}
	
	public String getSongName() {
		return songname;
	}
	
	public String getArtistName() {
		return artistname;
	}
	
	public String getAlbumName() {
		return songname;
	}
	
	public String getPath() {
		return path;
	}
	
	
	public static String getCoverPathNew(int pos) {
		return aSongList.get(pos).getCoverPath();
	}
	
	public String getCoverPath() {
		return this.coverpath;
	}
	
	
	public Bitmap getCover() {
		return this.img;
	}
	
	
	public String getDuration() {
		return duration;
	}
	
	public static int numberOfSongs(){
		return aSongList.size();
	}
	
	public static void setSongList(ArrayList<Song> list) {
		aSongList = list;
	}
	
	public static ArrayList<Song> getSongsList() {
		return aSongList;
	}
	
	public static String getSongPath(int pos) {
		return aSongList.get(pos).getPath();
	}
}

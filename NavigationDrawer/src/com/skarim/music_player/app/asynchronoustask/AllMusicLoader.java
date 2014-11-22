package com.skarim.music_player.app.asynchronoustask;

import java.util.ArrayList;

import com.skarim.music_player.app.classes.Song;


import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AlbumColumns;

public class AllMusicLoader extends AsyncTaskLoader<ArrayList<Song>> {

	private ArrayList<Song> mSOngList;
	private Context mContext;
	private Bitmap cover;
	
	public AllMusicLoader(Context context) {
		super(context);
		this.mContext = context;
	}

	@Override
	public ArrayList<Song> loadInBackground() {
		try {
			mSOngList = new ArrayList<Song>();
			//Some audio may be explicitly marked as not being music
			String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
			//MediaStore.Audio.Albums._ID+ "=" + album_id

			String[] projection = {
			        MediaStore.Audio.Media._ID,
			        MediaStore.Audio.Media.ARTIST,
			        MediaStore.Audio.Media.TITLE,
			        MediaStore.Audio.Media.DATA,
			        MediaStore.Audio.Media.DISPLAY_NAME,
			        MediaStore.Audio.Media.DURATION,
			        MediaStore.Audio.Media.ALBUM,
			       
			};

			Cursor cursor = mContext.getContentResolver().query(
			        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
			        projection,
			        selection,
			        null,
			        null);
			if(cursor!=null && cursor.moveToFirst()) {
	            do {
	                // Get album name
	            	int index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
	            	String path = cursor.getString(index);
	                final String songname = cursor.getString(2);
	                final String artistname = cursor.getString(1);
	                final String albumname = cursor.getString(6);
	                final String duration = cursor.getString(5);
	                final String coverPath =null;
	                
	                // Create a new album
	                final Song album = new Song(songname, artistname, albumname, duration,path,coverPath);
	                mSOngList.add(album);
	            } while (cursor.moveToNext());
			}
	            
	        if (cursor != null) {
	            cursor.close();
	            cursor = null;
	        }
	  Song.setSongList(mSOngList);
		} catch (Exception e) {
			Log.e("AlbumLoaderManager", e.getMessage(), e);
		}
		return mSOngList;
	}
	
	Bitmap Shrinkmethod(String file, int width, int height){
        BitmapFactory.Options bitopt=new BitmapFactory.Options();
        bitopt.inJustDecodeBounds=true;
        Bitmap bit=BitmapFactory.decodeFile(file, bitopt);

        int h=(int) Math.ceil(bitopt.outHeight/(float)height);
        int w=(int) Math.ceil(bitopt.outWidth/(float)width);

        if(h>1 || w>1){
            if(h>w){
                bitopt.inSampleSize=h;

            }else{
                bitopt.inSampleSize=w;
            }
        }
        bitopt.inJustDecodeBounds=false;
        bit=BitmapFactory.decodeFile(file, bitopt);

        return bit;
    }
	
	@Override
	public void deliverResult(ArrayList<Song> albumList) {
		if (isReset()) {
			if(albumList != null) {
				onReleaseResources(albumList);
				return;
			}
		}
		ArrayList<Song> oldAlbum = mSOngList;
		mSOngList = albumList;
		
		if(isStarted()) {
			super.deliverResult(albumList);
		}
		
		if(oldAlbum != null && oldAlbum != albumList) {
			onReleaseResources(oldAlbum);
		}
	}
	
	protected void onReleaseResources(ArrayList<Song> albumList) {
		// Nothing to do here for a simple List
	}
	
	@Override
	public void forceLoad() {
		super.forceLoad();
	}
	
	@Override
	protected void onStartLoading() {
		if(mSOngList != null) {
			deliverResult(mSOngList);
		}
		
		if(takeContentChanged()) {
			forceLoad();
		} else if (mSOngList == null) {
			forceLoad();
		}
	}
	
	@Override
	protected void onStopLoading() {
		cancelLoad();
	}

	@Override
	protected void onReset() {
		onStopLoading();
		
		if(mSOngList != null) {
			onReleaseResources(mSOngList);
			mSOngList = null;
		}
	}
	
	@Override
	public void onCanceled(ArrayList<Song> albumList) {
		super.onCanceled(albumList);
		onReleaseResources(albumList);
	}
}

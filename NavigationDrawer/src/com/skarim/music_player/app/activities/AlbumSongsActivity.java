package com.skarim.music_player.app.activities;

import java.util.ArrayList;

import com.skarim.music_player.app.adapters.AlbumSongAdapter;
import com.skarim.music_player.app.classes.CurrentPlayingSongs;
import com.skarim.music_player.app.classes.Song;
import com.skarim.music_player.app.utils.CommonConstraints;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AlbumColumns;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class AlbumSongsActivity extends FragmentActivity implements
		OnItemClickListener {

	private String albumName;
	private AlbumSongAdapter mAdapter;
	ArrayList<Song> albumSongList;
	ListView lvAlbumSongsList;
	TextView tvArtistName;
	Cursor cursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_artist_all_songs);
		Bundle extras = getIntent().getExtras();
		albumName = extras.getString("ALBUM_NAME");
		initViews();
	}

	private void initViews() {
		tvArtistName = (TextView) findViewById(R.id.tvArtistName);
		tvArtistName.setText(albumName);
		lvAlbumSongsList = (ListView) findViewById(R.id.list);
		lvAlbumSongsList.setOnItemClickListener(this);
		

		albumSongList = getSongForArtist(albumName);
		Song.setSongList(albumSongList);
		Log.d("skm", "" + albumName);

		
		  mAdapter = new AlbumSongAdapter(this,
		  android.R.layout.simple_list_item_1, albumSongList);
		  lvAlbumSongsList.setAdapter(mAdapter);
		 
	}

	@SuppressLint("NewApi")
	private ArrayList<Song> getSongForArtist(String artistName2) {
		ArrayList<Song> _albumSongList = new ArrayList<Song>();
		try {
			String[] columns = { 
					MediaStore.Audio.Media._ID,
					MediaStore.Audio.Media.ARTIST,
					MediaStore.Audio.Media.TITLE, 
					MediaStore.Audio.Media.DATA,
					MediaStore.Audio.Media.DISPLAY_NAME,
					MediaStore.Audio.Media.DURATION,
					MediaStore.Audio.Media.ALBUM,
					MediaStore.Audio.AlbumColumns.ALBUM_ART};

			String where = android.provider.MediaStore.Audio.Media.ALBUM + "=?";

			String whereVal[] = { albumName };

			Cursor cursor = getContentResolver().query(
					MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns,
					where,whereVal, null, null);
			if (cursor != null && cursor.moveToFirst()) {
				do {
					// Get album name
					int index = cursor
							.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
					String path = cursor.getString(index);
					final String songname = cursor.getString(2);
					final String artistname = cursor.getString(1);
					final String albumname = cursor.getString(6);
					final String duration = cursor.getString(5);
					final String coverPath = cursor.getString(7);

					// Create a new album
					final Song album = new Song(songname, artistname,
							albumname, duration, path,coverPath);
					_albumSongList.add(album);
				} while (cursor.moveToNext());
			}

			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
			Song.setSongList(albumSongList);
		} catch (Exception e) {
			Log.e("AlbumLoaderManager", e.getMessage(), e);
		}
		return _albumSongList;

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Log.d("skm", "Position : " + position);
		 Intent player = new Intent(this, PlayerActivity.class);
		  player.putExtra(CommonConstraints.SONG_PATH, albumSongList.get(position)
		  .getPath());
		  CurrentPlayingSongs.setSongList(albumSongList);
		  CommonConstraints.CURRENT_SONG_PATH=albumSongList.get(position).getPath();
		  CommonConstraints.WHICH_MODE = CommonConstraints.ALL_ARTIST;
		  CommonConstraints.CURRENT_CURSOR_POSITION = position;
		  startActivity(player);
		 
	}
}

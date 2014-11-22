package com.skarim.music_player.app.activities;

import java.util.ArrayList;

import com.skarim.music_player.app.adapters.ArtistSongAdapter;
import com.skarim.music_player.app.classes.CurrentPlayingSongs;
import com.skarim.music_player.app.classes.Song;
import com.skarim.music_player.app.utils.CommonConstraints;

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

public class ArtistSongsActivity extends FragmentActivity implements
		OnItemClickListener {

	private String artistName;
	private ArtistSongAdapter mAdapter;
	ArrayList<Song> artistList;
	ListView lvArtistSongsList;
	TextView tvArtistName;
	Cursor cursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_artist_all_songs);
		Bundle extras = getIntent().getExtras();
		artistName = extras.getString("Artist");
		initViews();
	}

	private void initViews() {
		tvArtistName = (TextView) findViewById(R.id.tvArtistName);
		tvArtistName.setText(artistName);
		lvArtistSongsList = (ListView) findViewById(R.id.list);
		lvArtistSongsList.setOnItemClickListener(this);

		artistList = getSongForArtistgetSongsForArtist(artistName);

		mAdapter = new ArtistSongAdapter(this,
				android.R.layout.simple_list_item_1, artistList);
		lvArtistSongsList.setAdapter(mAdapter);
	}

	private ArrayList<Song> getSongForArtistgetSongsForArtist(String artistName2) {
		ArrayList<Song> artistSongList = new ArrayList<Song>();
		Song.setSongList(artistSongList);

		String[] columns = { 
				MediaStore.Audio.Media.DATA,
				MediaStore.Audio.Media._ID, 
				MediaStore.Audio.Media.TITLE,
				MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.ALBUM,
				MediaStore.Audio.Media.DURATION,
				MediaStore.Audio.Albums.ALBUM_ART };
		String where = android.provider.MediaStore.Audio.Media.ARTIST + "=?";

		String whereVal[] = { artistName };

		cursor = getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns, where,
				whereVal, MediaStore.Audio.Media.TITLE);

		if (cursor != null && cursor.moveToFirst()) {
			do {
				int index = cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
				String path = cursor.getString(index);
				// Get songname
				final String songname = cursor.getString(2);

				// Get artistname
				final String artistname = cursor.getString(3);

				// Get albumname
				final String albumname = cursor.getString(4);

				// Get duration
				final String duration = cursor.getString(5);
				final String coverPath = cursor.getString(6);

				// Create a new album
				final Song song = new Song(songname, artistname, albumname,
						duration, path,coverPath);
				artistSongList.add(song);
			} while (cursor.moveToNext());
		}
		return artistSongList;

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Log.d("skm", "Position : " + position);

		Intent player = new Intent(this, PlayerActivity.class);
		player.putExtra(CommonConstraints.SONG_PATH, artistList.get(position)
				.getPath());
		CurrentPlayingSongs.setSongList(artistList);
		CommonConstraints.CURRENT_SONG_PATH = artistList.get(position)
				.getPath();
		CommonConstraints.WHICH_MODE = CommonConstraints.ALL_ARTIST;
		CommonConstraints.CURRENT_CURSOR_POSITION = position;
		startActivity(player);
	}
}

package com.skarim.music_player.app.fragments;

import com.skarim.music_player.app.activities.ArtistSongsActivity;
import com.skarim.music_player.app.activities.PlayerActivity;
import com.skarim.music_player.app.activities.R;
import com.skarim.music_player.app.adapters.ArtistAdapter;
import com.skarim.music_player.app.asynchronoustask.ArtistLoader;
import com.skarim.music_player.app.classes.Artist;
import com.skarim.music_player.app.classes.CurrentPlayingSongs;
import com.skarim.music_player.app.utils.CommonConstraints;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ArtistListFragment extends Fragment implements
		LoaderManager.LoaderCallbacks<ArrayList<Artist>>, OnClickListener, OnItemClickListener {

	private View v;
	private ArtistAdapter mAdapter;
	private  ImageButton  bnexttrack, bprevioustrack, stopbutton;
	public static ImageButton play,ivCoverImage;
	TextView tvName,tvArtistName;
	LinearLayout llSongDescription;
	ListView lvMusicList;

	public ArtistListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		v = new View(getActivity());

		getLoaderManager().initLoader(1, null, this);

		mAdapter = new ArtistAdapter(getActivity(),
				android.R.layout.simple_list_item_1,null);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_all_album, container, false);
		initViews();
		return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updatePlayButton();
	}

	private void updatePlayButton() {
		if (PlayerActivity.mediaPlayer != null
				&&PlayerActivity.mediaPlayer.isPlaying()) {
			play.setBackgroundResource(R.drawable.btn_pause);
			String songName=CurrentPlayingSongs.getSongsList().get(CommonConstraints.CURRENT_CURSOR_POSITION).getSongName();
			String artistName=CurrentPlayingSongs.getSongsList().get(CommonConstraints.CURRENT_CURSOR_POSITION).getArtistName();
			
			if (songName.length() > 18) {
				songName = songName.substring(0, 18);
			} 
			
			
			tvName.setText(songName);
			
			if (artistName.length() > 18) {
				artistName = artistName.substring(0, 18);
			} 
			
			
			tvArtistName.setText(artistName);
		}
		else
			play.setBackgroundResource(R.drawable.btn_play);
	}

	private void initViews() {
		
		tvName= (TextView) v.findViewById(R.id.tvName);
		tvArtistName= (TextView) v.findViewById(R.id.tvArtistName);
		llSongDescription= (LinearLayout) v.findViewById(R.id.llSongDescription);
		llSongDescription.setOnClickListener(this);
		play = (ImageButton) v.findViewById(R.id.playbutton);
		play.setBackgroundResource(R.drawable.btn_play);
		play.setOnClickListener(this);

		bnexttrack = (ImageButton) v.findViewById(R.id.bnexttrack);
		bnexttrack.setOnClickListener(this);

		bprevioustrack = (ImageButton) v.findViewById(R.id.bprevioustrack);
		bprevioustrack.setOnClickListener(this);

		stopbutton = (ImageButton) v.findViewById(R.id.stopbutton);
		stopbutton.setOnClickListener(this);
		
		lvMusicList=(ListView) v.findViewById(R.id.list);
		lvMusicList.setAdapter(mAdapter);
		lvMusicList.setOnItemClickListener(this);

	}

	private void setUI() {
		if (PlayerActivity.mediaPlayer == null) {
			play.setBackgroundResource(R.drawable.btn_pause);

		} else if (PlayerActivity.mediaPlayer != null
				&& PlayerActivity.mediaPlayer.isPlaying()) {
			// PlayerActivity.stopAudio();
			play.setBackgroundResource(R.drawable.btn_pause);
		} else if (PlayerActivity.mediaPlayer != null
				&& !PlayerActivity.mediaPlayer.isPlaying()) {
			play.setBackgroundResource(R.drawable.btn_pause);
		} else {
			play.setBackgroundResource(R.drawable.btn_pause);
		}

	}

	@Override
	public Loader<ArrayList<Artist>> onCreateLoader(int arg0, Bundle arg1) {
		return new ArtistLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Artist>> arg0,
			ArrayList<Artist> arg1) {
		mAdapter.setData(arg1);
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Artist>> arg0) {
		mAdapter.setData(null);
	}

	@Override
	public void onClick(View view) {


		if (view.getId()==R.id.playbutton) {
			//Media player not started
			if (PlayerActivity.mediaPlayer == null) {

				Intent player = new Intent(getActivity(), PlayerActivity.class);
				player.putExtra(CommonConstraints.SONG_PATH, CurrentPlayingSongs
						.getSongPath(CommonConstraints.CURRENT_CURSOR_POSITION));
				CommonConstraints.CURRENT_SONG_PATH=CurrentPlayingSongs
						.getSongPath(CommonConstraints.CURRENT_CURSOR_POSITION);
				play.setBackgroundResource(R.drawable.btn_pause);
				startActivity(player);
			}
			//Media player is Running
			else if (PlayerActivity.mediaPlayer.isPlaying()) {
				play.setBackgroundResource(R.drawable.btn_play);
				PlayerActivity.playPauseResumeAudio(getActivity(), CommonConstraints.CURRENT_SONG_PATH);
			}//Media player is paused 
			else if (PlayerActivity.mediaPlayer != null
					&& !PlayerActivity.mediaPlayer.isPlaying()) {
				play.setBackgroundResource(R.drawable.btn_pause);
				PlayerActivity
				.playPauseResumeAudio(
						getActivity().getApplicationContext(),
						CommonConstraints.CURRENT_SONG_PATH);
				
			} 
		}
		else if (view.getId() == R.id.bnexttrack) {
			if (PlayerActivity.mediaPlayer == null) {
				Intent player = new Intent(getActivity(), PlayerActivity.class);				
				player.putExtra(CommonConstraints.SONG_PATH, CurrentPlayingSongs.getSongPath(CommonConstraints.CURRENT_CURSOR_POSITION));
				play.setBackgroundResource(R.drawable.btn_pause);
				startActivity(player);

			} else {
				if (CommonConstraints.CURRENT_CURSOR_POSITION == CurrentPlayingSongs
						.numberOfSongs() - 1) {
					CommonConstraints.CURRENT_CURSOR_POSITION = 0;
				} else
					CommonConstraints.CURRENT_CURSOR_POSITION++;

				play.setBackgroundResource(R.drawable.pause);
				PlayerActivity.playPauseResumeAudio(getActivity()
						.getApplicationContext(), CurrentPlayingSongs
						.getSongPath(CommonConstraints.CURRENT_CURSOR_POSITION));
			}
			updatePlayButton();
		} else if (view.getId() == R.id.bprevioustrack) {
			if (PlayerActivity.mediaPlayer == null) {
				Intent player = new Intent(getActivity(), PlayerActivity.class);
				player.putExtra(CommonConstraints.SONG_PATH, CurrentPlayingSongs.getSongPath(CommonConstraints.CURRENT_CURSOR_POSITION));
				//play.setBackgroundResource(R.drawable.pause);
				startActivity(player);

			} else {

				if (CommonConstraints.CURRENT_CURSOR_POSITION == 0) {
					CommonConstraints.CURRENT_CURSOR_POSITION = CurrentPlayingSongs.numberOfSongs() - 1;
				} else
					CommonConstraints.CURRENT_CURSOR_POSITION--;

				//play.setBackgroundResource(R.drawable.pause);
				PlayerActivity.playPauseResumeAudio(getActivity()
						.getApplicationContext(), CurrentPlayingSongs
						.getSongPath(CommonConstraints.CURRENT_CURSOR_POSITION));
			}
			updatePlayButton();
			
		} else if (view.getId() == R.id.stopbutton) {
			play.setBackgroundResource(R.drawable.play);
			PlayerActivity.stopAudio();

		}else if (view.getId()==R.id.llSongDescription) {
			
			Intent player = new Intent(getActivity(), PlayerActivity.class);
			player.putExtra(CommonConstraints.SONG_PATH, "");
			startActivity(player);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent player = new Intent(getActivity(), ArtistSongsActivity.class);
		player.putExtra("Artist", Artist.getArtistName(position));
		CommonConstraints.WHICH_MODE = CommonConstraints.ALL_ARTIST;
		CommonConstraints.CURRENT_CURSOR_POSITION = position;
		startActivity(player);
		
	}
}
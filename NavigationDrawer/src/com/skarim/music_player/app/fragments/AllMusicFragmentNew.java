package com.skarim.music_player.app.fragments;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.skarim.music_player.app.activities.PlayerActivity;
import com.skarim.music_player.app.activities.R;
import com.skarim.music_player.app.adapters.AllMusicAdapter;
import com.skarim.music_player.app.asynchronoustask.AllMusicLoader;
import com.skarim.music_player.app.classes.CurrentPlayingSongs;
import com.skarim.music_player.app.classes.Song;
import com.skarim.music_player.app.utils.CommonConstraints;

public class AllMusicFragmentNew extends Fragment implements
		LoaderManager.LoaderCallbacks<ArrayList<Song>>, OnClickListener,
		OnItemClickListener {

	private View v;
	private AllMusicAdapter mAdapter;
	private ImageButton bnexttrack, bprevioustrack, stopbutton;
	public static ImageButton play, ivCoverImage;
	TextView tvName, tvArtistName;
	LinearLayout llSongDescription;
	ListView lvMusicList;
	ArrayList<Song> allSongList;
	ArrayList<CurrentPlayingSongs> currentPlayingSongs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		v = new View(getActivity());
		getLoaderManager().initLoader(1, null, this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_all_music, container, false);
		initViews();
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private void initViews() {

		tvName = (TextView) v.findViewById(R.id.tvName);
		tvArtistName = (TextView) v.findViewById(R.id.tvArtistName);
		llSongDescription = (LinearLayout) v
				.findViewById(R.id.llSongDescription);
		llSongDescription.setOnClickListener(this);
		play = (ImageButton) v.findViewById(R.id.playbutton);
		play.setBackgroundResource(R.drawable.play);
		play.setOnClickListener(this);

		bnexttrack = (ImageButton) v.findViewById(R.id.bnexttrack);
		bnexttrack.setOnClickListener(this);

		bprevioustrack = (ImageButton) v.findViewById(R.id.bprevioustrack);
		bprevioustrack.setOnClickListener(this);

		stopbutton = (ImageButton) v.findViewById(R.id.stopbutton);
		stopbutton.setOnClickListener(this);

		lvMusicList = (ListView) v.findViewById(R.id.list);

		lvMusicList.setOnItemClickListener(this);

	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Intent player = new Intent(getActivity(), PlayerActivity.class);
		CurrentPlayingSongs.setSongList(allSongList);
		player.putExtra(CommonConstraints.SONG_PATH, CurrentPlayingSongs.getSongPath(position));
		player.putExtra("command", "play");
		CommonConstraints.WHICH_MODE = CommonConstraints.ALL_MUSIC;
		
		CommonConstraints.CURRENT_CURSOR_POSITION = position;
		startActivity(player);

	}

	@Override
	public void onClick(View view) {
		

		if (view.getId() == R.id.playbutton) {
			Intent intent = new Intent(getActivity(), PlayerActivity.class);
			intent.putExtra(CommonConstraints.SONG_PATH, CurrentPlayingSongs
					.getSongPath(CommonConstraints.CURRENT_CURSOR_POSITION));
			if(getResources().getResourceName(R.id.playbutton)=="play")
			intent.putExtra("command", "play");
			else
				intent.putExtra("command", "pause");
			CommonConstraints.CURRENT_SONG_PATH = CurrentPlayingSongs
					.getSongPath(CommonConstraints.CURRENT_CURSOR_POSITION);
			play.setBackgroundResource(R.drawable.pause);
			startActivity(intent);
		}
	}

	@Override
	public Loader<ArrayList<Song>> onCreateLoader(int arg0, Bundle arg1) {
		return new AllMusicLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Song>> arg0,
			ArrayList<Song> _allSongList) {
		allSongList = _allSongList;
		mAdapter = new AllMusicAdapter(getActivity(),
				android.R.layout.simple_list_item_1, allSongList);
		lvMusicList.setAdapter(mAdapter);
		mAdapter.setData(_allSongList);
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Song>> arg0) {
		mAdapter.setData(null);
	}

}

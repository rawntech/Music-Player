package com.skarim.music_player.app.activities;

import com.skarim.music_player.app.utils.Utilities;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class PlayerActivityNew extends Activity implements OnClickListener,
		OnSeekBarChangeListener {

	SeekBar seekBar;
	MediaPlayer mediaPlayer;
	private ImageButton play, bnexttrack, bprevioustrack, stopbutton;
	private TextView titelInfo;
	private TextView albumInfo;
	private TextView duration;
	private TextView current;
	private ImageView cover;
	private int dur, cur; // duration of Track, current Position
	String SongName, ArtistName, AlbumName, Duration, songPath;
	Bundle bundle;
	int playBackPosition;
	String commandFromOutSide;
	private Handler mHandler = new Handler();
	Utilities utils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		initViews();

		if (!getIntent().getExtras().getString("SONG_PATH").equals("")) {
			songPath = getIntent().getExtras().getString("SONG_PATH");
			commandFromOutSide = getIntent().getExtras().getString("command");
			Log.d("skarim", songPath);
			Log.d("skarim", commandFromOutSide);
		}

		if (commandFromOutSide.equals("play")) {

			if (mediaPlayer != null) {
				if (mediaPlayer.isPlaying()) {
					pauseMediaPlayer();
				} else {
					resumeMediaPlayer();
				}
			} else {
				startMediaPlayer();
			}
		}

	}

	private void initViews() {
		play = (ImageButton) findViewById(R.id.playbutton);
		play.setBackgroundResource(R.drawable.play);
		play.setOnClickListener(this);

		bprevioustrack = (ImageButton) findViewById(R.id.bprevioustrack);
		bprevioustrack.setOnClickListener(this);
		bnexttrack = (ImageButton) findViewById(R.id.bnexttrack);
		bnexttrack.setOnClickListener(this);
		stopbutton = (ImageButton) findViewById(R.id.stopbutton);
		stopbutton.setOnClickListener(this);
		titelInfo = (TextView) findViewById(R.id.titelInfo);
		albumInfo = (TextView) findViewById(R.id.albumInfo);
		duration = (TextView) findViewById(R.id.duration);
		current = (TextView) findViewById(R.id.current);
		seekBar = (SeekBar) findViewById(R.id.sbCurrentPossition);
		cover = (ImageView) findViewById(R.id.cover);
		seekBar.setOnSeekBarChangeListener(this);
		utils=new Utilities();

	}

	public void startMediaPlayer() {
		if (mediaPlayer == null) {
			mediaPlayer = new MediaPlayer();

		
		}
		try {
			mediaPlayer.setDataSource(songPath);
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (Exception exception) {
			exception.getStackTrace();

		}
		
		updateProgressBar();
	}

	@Override
	public void onClick(View view) {

		if (view.getId() == R.id.playbutton) {
			if (mediaPlayer.isPlaying()) {
				resumeMediaPlayer();
			} else {
				pauseMediaPlayer();
			}
		}
	}

	private void pauseMediaPlayer() {
		playBackPosition = mediaPlayer.getCurrentPosition();
		mediaPlayer.pause();
		play.setBackgroundResource(R.drawable.play);
	}

	private void resumeMediaPlayer() {
		mediaPlayer.seekTo(playBackPosition);
		mediaPlayer.start();
		play.setBackgroundResource(R.drawable.pause);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (mediaPlayer != null && fromUser) {
			mediaPlayer.seekTo(progress);
		}

	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		mHandler.removeCallbacks(mUpdateTimeTask);

	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mediaPlayer.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);
 
        // forward or backward to certain seconds
        mediaPlayer.seekTo(currentPosition);
 
        // update timer progress again
        updateProgressBar();

	}
	
	public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    } 
	
	/**
     * Background Runnable thread
     * */
    private Runnable mUpdateTimeTask = new Runnable() {
           public void run() {
               int totalDuration = mediaPlayer.getDuration();
               long currentDuration = mediaPlayer.getCurrentPosition();
               
               
               
            // Displaying Total Duration time
               duration.setText(""+utils.milliSecondsToTimer(totalDuration));
               // Displaying time completed playing
               current.setText(""+utils.milliSecondsToTimer(currentDuration));
               
               int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
               
               seekBar.setProgress(progress);
 
               // Running this thread after 100 milliseconds
               mHandler.postDelayed(this, 100);
           }
        };
        
        public int progressToTimer(int progress, int totalDuration) {
            int currentDuration = 0;
            totalDuration = (int) (totalDuration / 1000);
            currentDuration = (int) ((((double)progress) / 100) * totalDuration);
     
            // return current duration in milliseconds
            return currentDuration * 1000;
        }

}

package com.skarim.music_player.app.activities;

import java.io.File;
import java.io.IOException;

import com.skarim.music_player.app.classes.CurrentPlayingSongs;
import com.skarim.music_player.app.classes.Song;
import com.skarim.music_player.app.utils.CommonConstraints;
import com.skarim.music_player.app.utils.Utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerActivity extends Activity implements OnClickListener, OnSeekBarChangeListener {

	public static MediaPlayer mediaPlayer;
	private static int playbackPosition;
	private static ImageButton play, bnexttrack, bprevioustrack, stopbutton;
	private TextView titelInfo;
	private static TextView albumInfo;
	private static TextView duration;
	private static TextView current;
	private TextView bar;
	private ImageView cover;
	boolean musicRunning = false;
	public static String currentAudioPath;
	private static String dirPath;
	private int width = 4;
	private int dur, cur; // duration of Track, current Position
	Bundle bundle;
	String path;
	public static int position = 0;
	public static String sendingPath;
	String SongName,ArtistName,AlbumName,Duration;
	
	
	
	/**********************************22.11.2014******************/
	public static SeekBar seekBar;
	static Utilities utils;
	private static Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		initViews();

		if (!getIntent().getExtras().getString("SONG_PATH").equals("")) {
			sendingPath = getIntent().getExtras().getString("SONG_PATH");
			startForMediaPlayer(sendingPath);
		}
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateUI();
	}

	private void updateUI() {
		
		if (PlayerActivity.mediaPlayer != null
				&&PlayerActivity.mediaPlayer.isPlaying()) {
			play.setBackgroundResource(R.drawable.btn_pause);
		displayInfo();
		}
		else
			play.setBackgroundResource(R.drawable.btn_play);
		
	}

	private void startForMediaPlayer(String _path) {
		if (_path != null) {
			playPauseResumeAudio(this, _path);
			displayInfo();
		}

	}

	private void initViews() {
		play = (ImageButton) findViewById(R.id.playbutton);
		play.setBackgroundResource(R.drawable.btn_play);
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
		bar = (TextView) findViewById(R.id.bar);
		cover = (ImageView) findViewById(R.id.cover);
		seekBar=(SeekBar) findViewById(R.id.sbCurrentPossition);
		seekBar.setOnSeekBarChangeListener(this);
		utils=new Utilities();
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.playbutton) {
			playPauseResumeAudio(this,
					(CommonConstraints.CURRENT_SONG_PATH));
				displayInfo();
		} else if (view.getId() == R.id.bnexttrack) {
			
			if (CommonConstraints.CURRENT_CURSOR_POSITION == CurrentPlayingSongs
					.numberOfSongs() - 1) {
				CommonConstraints.CURRENT_CURSOR_POSITION = 0;
			} else
				CommonConstraints.CURRENT_CURSOR_POSITION++;
			
			playPauseResumeAudio(this,
					CurrentPlayingSongs.getSongPath(CommonConstraints.CURRENT_CURSOR_POSITION));
			
			displayInfo();

		} else if (view.getId() == R.id.bprevioustrack) {

			if (CommonConstraints.CURRENT_CURSOR_POSITION == 0) {
				CommonConstraints.CURRENT_CURSOR_POSITION = CurrentPlayingSongs
						.numberOfSongs() - 1;
			} else
				CommonConstraints.CURRENT_CURSOR_POSITION--;
			playPauseResumeAudio(this,
					CurrentPlayingSongs.getSongPath(CommonConstraints.CURRENT_CURSOR_POSITION));
			displayInfo();

		} else if (view.getId() == R.id.stopbutton) {
			stopAudio();
			stopAudio();
		}

	}

	public void displayInfo() {
		
		SongName=Song.getSongsList().get(CommonConstraints.CURRENT_CURSOR_POSITION).getSongName();
		AlbumName=Song.getSongsList().get(CommonConstraints.CURRENT_CURSOR_POSITION).getAlbumName();
		ArtistName=Song.getSongsList().get(CommonConstraints.CURRENT_CURSOR_POSITION).getArtistName();
	
		if (SongName == null) {
			SongName = "Unknown";
		}
		if (AlbumName == null) {
			AlbumName = "Unknown";
		}
		if (ArtistName == null) {
			ArtistName = "Unknown";
		}
		File file = new File(dirPath + "/AlbumArtSmall.jpg");
		if (file.exists()) {
			Toast.makeText(this, "Exists", Toast.LENGTH_SHORT).show();
			Bitmap bitmapImage = BitmapFactory.decodeFile(dirPath
					+ "/AlbumArtSmall.jpg");
			cover.setImageBitmap(bitmapImage);
		}
		
		
		if (SongName.length() > 25) {
			SongName = SongName.substring(0, 25);
			SongName = SongName + "...";
		}
		if (AlbumName.length() > 15) {
			AlbumName = AlbumName.substring(0, 15);
			AlbumName = AlbumName + "...";
		}
		if (ArtistName.length() > 14) {
			ArtistName = ArtistName.substring(0, 14);
			ArtistName = ArtistName + "...";
		}

		titelInfo.setText(SongName);
		albumInfo.setText(AlbumName + " - " + ArtistName);
	}


	public static void playPauseResumeAudio(Context context, String path) {
		if (path == null) {
			Toast.makeText(context, "No item Selected", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (path != null) {
			
			
			if (mediaPlayer == null ){
				//new player
				currentAudioPath = path;
				try {
					mediaPlayer = new MediaPlayer();
					
					mediaPlayer.setDataSource(path);
					mediaPlayer.prepare();
					mediaPlayer.start();
					play.setBackgroundResource(R.drawable.btn_pause);
					CommonConstraints.CURRENT_SONG_PATH=currentAudioPath;
					} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else if (mediaPlayer.isPlaying() && path .equals( currentAudioPath)){
				//playing it , so pause it
					playbackPosition = mediaPlayer.getCurrentPosition();
					mediaPlayer.seekTo(playbackPosition);
					mediaPlayer.pause();
					play.setBackgroundResource(R.drawable.btn_pause);
				}else if (mediaPlayer.isPlaying() && !path.equals(currentAudioPath)){
				//New Song , so play it
					try {
						killMediaPlayer();
						mediaPlayer=new MediaPlayer();
						mediaPlayer.setDataSource(path);
						mediaPlayer.prepare();
						mediaPlayer.start();
						CommonConstraints.CURRENT_SONG_PATH=currentAudioPath=path;
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					play.setBackgroundResource(R.drawable.btn_pause);
				}else if (!mediaPlayer.isPlaying() && path.equals(currentAudioPath)){
				//pause it , so resume it
					mediaPlayer.seekTo(playbackPosition);
					mediaPlayer.start();
					play.setBackgroundResource(R.drawable.btn_pause);
				}else if (!mediaPlayer.isPlaying() && !path.equals( currentAudioPath)){
				//pause it , new song ,so play new
					
					currentAudioPath = path;
					try {
						killMediaPlayer();
						mediaPlayer = new MediaPlayer();
						mediaPlayer.setDataSource(path);
						mediaPlayer.prepare();
						mediaPlayer.start();
						play.setBackgroundResource(R.drawable.btn_pause);
						CommonConstraints.CURRENT_SONG_PATH=currentAudioPath;
						
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			
			updateProgressBar();
			
		}
		

	}
	
	public static void pauseMediaPlayer() {
		if (mediaPlayer.isPlaying() ){
			//playing it , so pause it
				playbackPosition = mediaPlayer.getCurrentPosition();
				mediaPlayer.seekTo(playbackPosition);
				mediaPlayer.pause();
				play.setBackgroundResource(R.drawable.btn_play);
			}
	}
	
	public static void runMediaPlayer() {
		
		if (mediaPlayer == null ){
			try {
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setDataSource(currentAudioPath);
				mediaPlayer.prepare();
				mediaPlayer.start();
				play.setBackgroundResource(R.drawable.btn_pause);
				} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}		
		}

	private static void killMediaPlayer() {
		if (mediaPlayer != null) {
			try {
				mediaPlayer.release();
				play.setBackgroundResource(R.drawable.btn_play);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void stopAudio() {
		if (mediaPlayer != null) {
			mediaPlayer.seekTo(0);
			mediaPlayer.pause();
			play.setBackgroundResource(R.drawable.btn_play);
			playbackPosition = 0;
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if(mediaPlayer!=null &&fromUser){
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
	
	public static void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    } 
	
	/**
     * Background Runnable thread
     * */
    private static Runnable mUpdateTimeTask = new Runnable() {
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

}

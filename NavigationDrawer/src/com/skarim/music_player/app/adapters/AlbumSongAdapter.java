package com.skarim.music_player.app.adapters;


import java.util.ArrayList;

import com.skarim.music_player.app.activities.R;
import com.skarim.music_player.app.classes.Song;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AlbumSongAdapter extends ArrayAdapter<Song> {

	private ArrayList<Song> allMusicList;
	Context context;
	public AlbumSongAdapter (Context _context, int textViewResourceId, ArrayList<Song> _allSongList) {
		super(_context, textViewResourceId, _allSongList);
		allMusicList=new ArrayList<Song>();
		this.allMusicList = _allSongList;
		this.context=_context;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		AlbumViewHolder viewHolder;
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.all_music_item, null);
			
			viewHolder = new AlbumViewHolder();
			viewHolder.songName = (TextView) convertView.findViewById(R.id.tvSongName);
			viewHolder.artistName = (TextView) convertView.findViewById(R.id.tvArtistName);
			viewHolder.duration = (TextView) convertView.findViewById(R.id.tvDuration);
			viewHolder.cover = (ImageView) convertView.findViewById(R.id.ivCoverImage);
			convertView.setTag(viewHolder);
		} else {
		viewHolder = (AlbumViewHolder) convertView.getTag();
		}

			if (allMusicList.get(position).getSongName().length()>25) {
				String d=allMusicList.get(position).getSongName().substring(0, 22);
			    viewHolder.songName.setText(""+d+"...");
			}
			else{
				viewHolder.songName.setText(""+allMusicList.get(position).getSongName());
			}
			
			
			
			if (allMusicList.get(position).getArtistName().length()>25) {
				String d=allMusicList.get(position).getArtistName().substring(0, 22);
			    viewHolder.artistName.setText(""+d+"...");
			}
			else{
				viewHolder.artistName.setText(""+allMusicList.get(position).getArtistName());
			}
			
			if (allMusicList.get(position).getDuration()!=null) {
				int dur = Integer.parseInt(allMusicList.get(position).getDuration());
				String durSec = String.valueOf((dur % 60000) / 1000);
				String durMin = String.valueOf(dur / 60000);
				if (durSec.length() == 1) {
					viewHolder.duration.setText("0" + durMin + ":0" + durSec);
			    }else {
			    	viewHolder.duration.setText("0" + durMin + ":" + durSec);
			    }
				
			}
			
			/*if (allMusicList.get(position).getgetCover()==null) {
				viewHolder.cover.setImageResource(R.drawable.album_img_128);
			}
			else{
				viewHolder.cover.setImageBitmap(a.getCover());
			}*/
		
		return convertView;
	}
	
    static class AlbumViewHolder {
        TextView songName;
        TextView artistName;
        TextView duration;
        ImageView cover;
    }
    
    public void setData(ArrayList<Song> arg1) {
    	clear();
    	if(arg1 != null) {
    		for (int i=0; i < arg1.size(); i++) {
    			add(arg1.get(i));
    		}
    	}
    }
}

package com.skarim.music_player.app.adapters;

import java.util.ArrayList;

import com.skarim.music_player.app.activities.R;
import com.skarim.music_player.app.classes.Artist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArtistAdapter extends ArrayAdapter<Artist> {

	private ArrayList<Artist> artists;
	private Artist a;
	public ArtistAdapter (Context context, int textViewResourceId, ArrayList<Artist> artistlist) {
		super(context, textViewResourceId);
		this.artists = artistlist;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ArtistViewHolder viewHolder;
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.artist_item, null);
			
			viewHolder = new ArtistViewHolder();
			viewHolder.name = (TextView) convertView.findViewById(R.id.tvName);
			viewHolder.artist = (TextView) convertView.findViewById(R.id.bottomrow);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ArtistViewHolder) convertView.getTag();
		}
		
		if(artists != null) {
			a = artists.get(position);
		} else {
			a = getItem(position);
		}
			
			viewHolder.name.setText(a.getArtistName());
			viewHolder.artist.setText("Songs : "+a.getAlbumCount());
		
		return convertView;
	}
	
    static class ArtistViewHolder {
        TextView name;
        TextView artist;
    }
    
    public void setData(ArrayList<Artist> data) {
    	clear();
    	if(data != null) {
    		for (int i=0; i < data.size(); i++) {
    			add(data.get(i));
    		}
    	}
    }
}

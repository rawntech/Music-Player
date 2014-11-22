package com.skarim.music_player.app.adapters;

import java.util.ArrayList;

import com.skarim.music_player.app.activities.R;
import com.skarim.music_player.app.classes.Album;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AlbumAdapter extends ArrayAdapter<Album> {

	private ArrayList<Album> albumList;
	Context context;
	public AlbumAdapter (Context _context, int textViewResourceId,ArrayList<Album> _albumList) {
		super(_context, textViewResourceId);
		this.albumList=new ArrayList<Album>();
		this.albumList=_albumList;
		this.context=_context;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		AlbumViewHolder viewHolder;
		
		
		if (convertView == null) {
			viewHolder = new AlbumViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.album_item, null);
			viewHolder.name = (TextView) convertView.findViewById(R.id.tvAlbumName);
			viewHolder.artist = (TextView) convertView.findViewById(R.id.tvArtistName);
			viewHolder.tvSongCount = (TextView) convertView.findViewById(R.id.tvSongCount);
			viewHolder.cover = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(viewHolder);
		} else {
		viewHolder = (AlbumViewHolder) convertView.getTag();
		}
			viewHolder.name.setText(albumList.get(position).getAlbumName());
			viewHolder.artist.setText(albumList.get(position).getArtistName());
			viewHolder.tvSongCount.setText(String.valueOf(albumList.get(position).getNumberOfSongs()));
			
			
			if (albumList.get(position).getArtistName().length()>23) {
				String d=albumList.get(position).getArtistName().substring(0, 23);
			    viewHolder.artist.setText(""+d+"...");
			}
			else{
				viewHolder.name.setText(""+albumList.get(position).getArtistName());
			}
			
			if (albumList.get(position).getCover()==null) {
				viewHolder.cover.setImageResource(R.drawable.all_music);
			}
			else{
				viewHolder.cover.setImageBitmap(albumList.get(position).getCover());
			}
		
		return convertView;
	}
	
    static class AlbumViewHolder {
        TextView name;
        TextView artist;
        ImageView cover;
        TextView tvSongCount;
        
    }
    
    public void setData(ArrayList<Album> arg1) {
    	clear();
    	if(arg1 != null) {
    		for (int i=0; i < arg1.size(); i++) {
    			add(arg1.get(i));
    		}
    	}
    }
}

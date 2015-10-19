package com.emedinaa.comovil2015.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emedinaa.comovil2015.R;
import com.emedinaa.comovil2015.model.entity.SpeakerEntity;
import com.emedinaa.comovil2015.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by emedinaa on 21/09/15.
 */
public class SpeakerAdapter extends RecyclerView.Adapter<SpeakerAdapter.ViewHolder>  {

    private List<SpeakerEntity> speakerEntityList;
    private Context context;

    public SpeakerAdapter(Context context,List<SpeakerEntity>speakerEntityList) {
        this.context=context;
        this.speakerEntityList = speakerEntityList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_speaker, parent, false);
        ViewHolder itemHolder = new ViewHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final SpeakerEntity speakerEntity= speakerEntityList.get(position);
        holder.tviName.setText(speakerEntity.getSimpleName());
        holder.tviSkill.setText(speakerEntity.getSkill());
        String url= speakerEntity.getPhotoPath();
        if(!url.isEmpty())
        {
            Picasso.with(holder.iviSpeaker.getContext())
                    .load(url)
                    .resize(200, 200)
                    .transform(new CircleTransform())
                    .into(holder.iviSpeaker);
        }else
        {
            Picasso.with(holder.iviSpeaker.getContext())
                    .load(R.drawable.default_user)
                    .resize(200, 200)
                    .transform(new CircleTransform())
                    .into(holder.iviSpeaker);
        }

    }

    @Override
    public int getItemCount() {
        return speakerEntityList.size();
    }

    public void clear()
    {
        speakerEntityList.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iviSpeaker;
        TextView tviName;
        TextView tviSkill;

        public ViewHolder(View view) {
            super(view);

            iviSpeaker = (ImageView)view.findViewById(R.id.iviSpeaker);
            tviName = (TextView)view.findViewById(R.id.tviName);
            tviSkill = (TextView)view.findViewById(R.id.tviSkill);
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}


package com.emedinaa.monkeyexample.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emedinaa.monkeyexample.R;
import com.emedinaa.monkeyexample.model.entity.TypeEntity;

import java.util.List;

/**
 * Created by emedinaa on 24/10/15.
 */
public class TypeAdapter extends BaseAdapter {

    private Context context;
    private List<TypeEntity> typeEntityList;
    private static final int FIRST=0;

    public TypeAdapter(Context context,List<TypeEntity> typeEntityList)
    {
        this.context= context;
        this.typeEntityList= typeEntityList;
    }

    @Override
    public int getCount() {
        return this.typeEntityList.size();
    }


    @Override
    public TypeEntity getItem(int i) {
        return this.typeEntityList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return typeEntityList.indexOf(getItem(i));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater =LayoutInflater.from(context);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_type, null);
            holder = new ViewHolder();
            holder.tviType = (TextView) convertView.findViewById(R.id.tviType);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TypeEntity entity = getItem(position);
        if (entity != null)
        {
            String type= entity.getName();
            if(position==FIRST)
            {
                holder.tviType.setText("Seleccionar");

            }else
            {
                holder.tviType.setText(type);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tviType;
    }
}

package com.run.sg.find;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.run.sg.amap3d.R;

/**
 * Created by yq on 2017/5/29.
 */
public class SGFindListViewAdapter extends BaseAdapter {
    private String[] mAllItems;
    private Context mContext;

    public SGFindListViewAdapter(Context context){
        mAllItems = context.getResources().getStringArray(R.array.found_dialog_list_content);
        mContext = context;
    }

    @Override
    public Object getItem(int position) {
        return mAllItems[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.la_sg_found_dialog_list_item_view,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mImageView.setImageDrawable(mContext.getDrawable(R.mipmap.ic_launcher));
        holder.mTextView.setText(mAllItems[position]);

        return convertView;
    }

    @Override
    public int getCount() {
        return mAllItems.length;
    }

    class ViewHolder{
        ImageView mImageView;
        TextView mTextView;
        View mDownDivider;

        ViewHolder(View convertView){
            mImageView = (ImageView) convertView.findViewById(R.id.image);
            mTextView = (TextView) convertView.findViewById(R.id.text);
            mDownDivider = (View) convertView.findViewById(R.id.down_divider);
        }
    }
}

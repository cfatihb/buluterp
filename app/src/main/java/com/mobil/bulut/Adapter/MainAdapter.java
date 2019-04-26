package com.mobil.bulut.Adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobil.bulut.Helpers.CodeHelper;
import com.mobil.bulut.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private Context mContext;
    public List dataList;
    private SparseBooleanArray selectedItems;
    int screenSizeWidth, screenSizeHeight;

    private SparseBooleanArray animationItemsIndex;
    private static int currentSelectedIndex = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        public TextView nameTxt;
        public ImageView mainImage;
        public RelativeLayout itemRow;

        public MyViewHolder(View view) {
            super(view);

            mainImage = (ImageView) view.findViewById(R.id.mainImage);
            nameTxt = (TextView) view.findViewById(R.id.nameTxt);
            itemRow = (RelativeLayout) view.findViewById(R.id.itemRow);

            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }

    public MainAdapter(Context mContext, List list) {
        this.mContext = mContext;
        this.dataList = list;
        selectedItems = new SparseBooleanArray();
        animationItemsIndex = new SparseBooleanArray();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_adapter_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        String menu = (String) dataList.get(position);

        holder.nameTxt.setText(menu);

        menu = CodeHelper.getChangeCharacter(menu);

        holder.mainImage.setImageBitmap(CodeHelper.getImageAssent(mContext,menu+".png"));

        int orientation = mContext.getResources().getConfiguration().orientation;

        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        screenSizeWidth = metrics.widthPixels;

        int width = 1080;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            width = (int) (((screenSizeWidth / 8)/10)*8);
        } else {
            width = (int) (((screenSizeWidth / 4)/10)*8);
        }

        holder.itemRow.getLayoutParams().height = width;
        holder.itemRow.getLayoutParams().width = width;


    }

    public void clearItems() {
        this.dataList.clear();
        notifyDataSetChanged();
    }


    public void addAll(List list) {
        dataList.clear();
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {


        return dataList.size();
    }

    public void removeData(int position) {
        dataList.remove(position);
        resetCurrentIndex();
    }

    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }


}
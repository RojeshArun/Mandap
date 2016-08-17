package com.mandap.adapters;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.mandap.R;
import com.utils.MandapHolder;

public class AdapterFavourites extends BaseAdapter {

	private Context mContext;
	private ArrayList<MandapHolder> mFavouritesList;
	private ArrayList<MandapHolder> mFavouritesListCopy;
	private AQuery mQuery;

	@SuppressWarnings("unchecked")
	public void addItems(ArrayList<MandapHolder> mFavouritesList) {
		this.mFavouritesList = mFavouritesList;
		this.mFavouritesListCopy = (ArrayList<MandapHolder>) mFavouritesList
				.clone();
		mQuery = new AQuery(mContext);

	}

	public AdapterFavourites(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return mFavouritesList.size();
	}

	@Override
	public MandapHolder getItem(int position) {
		return mFavouritesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	class Holder {
		private ImageView mImgSupplier;
		private TextView mtxtSupplierName, mtxtSupplierLocation;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder mHolder;
		if (convertView == null) {
			mHolder = new Holder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_favourite, null);
			mHolder.mImgSupplier = (ImageView) convertView
					.findViewById(R.id.imgSupplier);
			mHolder.mtxtSupplierName = (TextView) convertView
					.findViewById(R.id.txtSupplierName);
			mHolder.mtxtSupplierLocation = (TextView) convertView
					.findViewById(R.id.txtLocation);
			convertView.setTag(mHolder);
		} else {
			mHolder = (Holder) convertView.getTag();
		}

		MandapHolder mdataHodler = getItem(position);
		mHolder.mtxtSupplierName.setText(mdataHodler.getSupplierName());
		mHolder.mtxtSupplierLocation.setText(mdataHodler
				.getSupplierLocationName());

		if (mdataHodler.getSupplierImage().contains("noimage")
				|| TextUtils.isEmpty(mdataHodler.getSupplierImage())) {

		} else {
			mQuery.id(mHolder.mImgSupplier).image(
					mdataHodler.getSupplierImage());
		}

		return convertView;
	}

	public boolean matchfound;

	public void onTextChange(String text) {
		mFavouritesList.clear();
		matchfound = false;
		if (mFavouritesListCopy == null)
			return;

		for (MandapHolder mData : mFavouritesListCopy) {
			if (mData.getSupplierName().toLowerCase(new Locale(text))
					.contains(text.toLowerCase(new Locale(text)))) {
				matchfound = true;
				mFavouritesList.add(mData);
			}
		}

		notifyDataSetChanged();
		if (!matchfound)
			mFavouritesList.clear();
	}

}

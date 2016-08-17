package com.mandap.adapters;

import java.util.ArrayList;

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

public class AdapterMyRequests extends BaseAdapter {

	private Context mContext;
	private ArrayList<MandapHolder> mFavouritesList;
	private AQuery mQuery;

	public void addItems(ArrayList<MandapHolder> mFavouritesList) {
		this.mFavouritesList = mFavouritesList;
		mQuery = new AQuery(mContext);

	}

	public AdapterMyRequests(Context mContext) {
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
		private TextView mtxtRequestName, mtxtRequestDate;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder mHolder;
		if (convertView == null) {
			mHolder = new Holder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_myrequets, null);
			mHolder.mImgSupplier = (ImageView) convertView
					.findViewById(R.id.imgSupplier);
			mHolder.mtxtRequestName = (TextView) convertView
					.findViewById(R.id.txtSupplierName);
			mHolder.mtxtRequestDate = (TextView) convertView
					.findViewById(R.id.txtLocation);
			convertView.setTag(mHolder);
		} else {
			mHolder = (Holder) convertView.getTag();
		}

		MandapHolder mdataHodler = getItem(position);
		mHolder.mtxtRequestName.setText(mdataHodler.getSupplierName());
		mHolder.mtxtRequestDate.setText(mdataHodler.getRequestDate());

		if (mdataHodler.getSupplierImage().contains("noimage")
				|| TextUtils.isEmpty(mdataHodler.getSupplierImage())) {

		} else {
			mQuery.id(mHolder.mImgSupplier).image(
					mdataHodler.getSupplierImage());
		}

		return convertView;
	}

}

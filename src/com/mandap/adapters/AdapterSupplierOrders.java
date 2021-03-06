package com.mandap.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mandap.R;
import com.utils.MandapHolder;

public class AdapterSupplierOrders extends BaseAdapter {

	private Context mContext;
	private ArrayList<MandapHolder> mProductsArray;

	public void addItems(ArrayList<MandapHolder> mFavouritesList) {
		this.mProductsArray = mFavouritesList;

	}

	public AdapterSupplierOrders(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return mProductsArray.size();
	}

	@Override
	public MandapHolder getItem(int position) {
		return mProductsArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	class Holder {
		private TextView mtxtProductName, mtxtProductQuantity,
				mtxtProductPrice;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder mHolder;
		if (convertView == null) {
			mHolder = new Holder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_supplier_products, null);
			mHolder.mtxtProductName = (TextView) convertView
					.findViewById(R.id.txtProductName);
			mHolder.mtxtProductQuantity = (TextView) convertView
					.findViewById(R.id.baleNumber);
			mHolder.mtxtProductPrice = (TextView) convertView
					.findViewById(R.id.priceUnit);
			convertView.setTag(mHolder);
		} else {
			mHolder = (Holder) convertView.getTag();
		}

		MandapHolder mdataHodler = getItem(position);
		mHolder.mtxtProductName.setText(mdataHodler.getProductName());
		mHolder.mtxtProductPrice.setText("Rs. :" + mdataHodler.getPrice());
		mHolder.mtxtProductQuantity.setText("Stock: "
				+ mdataHodler.getQuantity());
		mHolder.mtxtProductName.setSelected(true);
		mHolder.mtxtProductPrice.setSelected(true);
		mHolder.mtxtProductQuantity.setSelected(true);

		return convertView;
	}
}

package com.mandap.adapters;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mandap.R;
import com.mandap.interfaces.SearchCallbacks.ISearchCheck;
import com.utils.MandapHolder;
import com.utils.StaticUtils;

public class AdapterSearch extends BaseAdapter {

	private Context mContext;
	private ArrayList<MandapHolder> mProductsArray;
	private ArrayList<MandapHolder> mProductsArrayCopy;

	private ISearchCheck mSearchCheck;

	@SuppressWarnings("unchecked")
	public void addItems(ArrayList<MandapHolder> mFavouritesList) {
		this.mProductsArray = mFavouritesList;
	}

	public AdapterSearch(Context mContext) {
		this.mContext = mContext;
	}

	public void setCheckCallback(ISearchCheck mCheck) {
		this.mSearchCheck = mCheck;
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
		private ImageView mbtnCheck;
		private TextView mtxtColor;
		private EditText meditQuantity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder mHolder;
		if (convertView == null) {
			mHolder = new Holder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_search, null);
			mHolder.mbtnCheck = (ImageView) convertView
					.findViewById(R.id.imgCheck);
			mHolder.mtxtColor = (TextView) convertView
					.findViewById(R.id.txtColor);
			mHolder.meditQuantity = (EditText) convertView
					.findViewById(R.id.editQuantity);
			convertView.setTag(mHolder);
		} else {
			mHolder = (Holder) convertView.getTag();
		}

		StaticUtils.setEditTextHintFont(mHolder.meditQuantity, mContext);

		final MandapHolder mdataHodler = getItem(position);
		mHolder.mtxtColor.setText(mdataHodler.getProductName());

		if (mdataHodler.isProductChecked()) {
			mHolder.mbtnCheck.setImageResource(R.drawable.check_box_h);
		} else {
			mHolder.mbtnCheck.setImageResource(R.drawable.check_box);
		}

		mHolder.mbtnCheck.setOnClickListener(new onSearchClick(position));

		mHolder.meditQuantity.setText(mdataHodler.getQuantity());

		mHolder.meditQuantity.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				mdataHodler.setQuantity("" + s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		return convertView;
	}

	public class onSearchClick implements OnClickListener {

		private int position;

		public onSearchClick(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			if (mSearchCheck != null)
				mSearchCheck.onSearchClicked(position);
		}

	}

}

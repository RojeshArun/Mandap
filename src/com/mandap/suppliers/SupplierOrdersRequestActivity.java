package com.mandap.suppliers;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.mandap.BaseActivity;
import com.mandap.R;
import com.mandap.adapters.AdapterSearch;
import com.mandap.interfaces.SearchCallbacks.ISearchCheck;
import com.utils.MandapHolder;

public class SupplierOrdersRequestActivity extends BaseActivity implements
		OnItemClickListener, ISearchCheck, OnClickListener {

	private MandapHolder mHolder;

	private ArrayList<MandapHolder> mProductsArray;
	private ListView mListOrderRequests;

	private ImageView mImgSupplier;
	private TextView mTxtSupplierName, mTxtLocation, mTxtSubmit, mTxtCancel;
	private EditText mEdtMessage;

	private AdapterSearch mAdapter;
	private String Keyword;

	private AQuery mQuery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addContentView(R.layout.activity_order_request);
		setTopbarTitle(getString(R.string.order_requests));
		mQuery = new AQuery(this);
		getLayoutReferences();
		getIntentData();
		setClickListeners();
		setOrderDetails();
		loadDummyData();
	}

	private void getIntentData() {
		if (getIntent().getExtras() != null) {
			if (getIntent().getExtras().containsKey("data")) {
				mHolder = (MandapHolder) getIntent().getExtras()
						.getSerializable("data");
			}
		}
	}

	private void setOrderDetails() {
		if (mHolder != null) {
			mQuery.id(mImgSupplier).image(mHolder.getSupplierImage());
			mTxtSupplierName.setText(mHolder.getSupplierName());
			mTxtLocation.setText(mHolder.getSupplierLocationName());
		}
	}

	private void loadDummyData() {
		mProductsArray = new ArrayList<MandapHolder>();
		for (int i = 0; i < 6; i++) {
			MandapHolder mDataHolder = new MandapHolder();
			mDataHolder.setColorName("Color");
			mDataHolder.setQuantity("");
			mDataHolder.setProductChecked(false);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// initiateProductListWS();
	}

	private void getLayoutReferences() {
		mListOrderRequests = (ListView) findViewById(R.id.listProducts);

		mImgSupplier = (ImageView) findViewById(R.id.imgSupplier);

		mEdtMessage = (EditText) findViewById(R.id.editMessage);
		mTxtSupplierName = (TextView) findViewById(R.id.txtSupplierName);
		mTxtLocation = (TextView) findViewById(R.id.txtLocation);
		mTxtSubmit = (TextView) findViewById(R.id.btnSubmit);
		mTxtCancel = (TextView) findViewById(R.id.btnCancel);
	}

	private void setClickListeners() {
		mListOrderRequests.setOnItemClickListener(this);
		mTxtSubmit.setOnClickListener(this);
		mTxtCancel.setOnClickListener(this);
	}

	// private void initiateProductListWS() {
	// showProgress();
	// Bundle params = new Bundle();
	// JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
	// StaticUtils.encodeUrl(WSClass., params),
	// new Listener<JSONArray>() {
	//
	// @Override
	// public void onResponse(JSONArray response) {
	// hideProgress();
	// responseSuppliersList(response);
	// }
	// }, new ErrorListener() {
	//
	// @Override
	// public void onErrorResponse(VolleyError error) {
	// hideProgress();
	// }
	// });
	//
	// RequestQueue mQueue = ((BaseApplication) getApplication()).getQueue();
	// mQueue.add(mJsonRequestResponse);
	// }
	//
	// private void responseSuppliersList(JSONArray response) {
	// if (response != null && response.length() != 0) {
	// try {
	// JSONObject mObj = (JSONObject) response.get(0);
	// if (mObj.has(StaticUsage.SUCCESS)) {
	// try {
	// if (mObj.getString(StaticUsage.SUCCESS)
	// .equalsIgnoreCase("0")) {
	// showToast(mObj.getString(StaticUsage.MESSAGE));
	// mListOrderRequests.setVisibility(View.INVISIBLE);
	// } else {
	// mListOrderRequests.setVisibility(View.VISIBLE);
	// loadAllData(response);
	// }
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// }
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	//
	// private void loadAllData(JSONArray response) {
	// mProductsArray = new ArrayList<MandapHolder>();
	// for (int i = 0; i < response.length(); i++) {
	// MandapHolder mDataHolder = new MandapHolder();
	// try {
	// JSONObject mObj = (JSONObject) response.get(i);
	// if (mObj.has(StaticUsage.QUOTE_ID)) {
	// mDataHolder
	// .setQuoteId(mObj.getString(StaticUsage.QUOTE_ID));
	// }
	//
	// mProductsArray.add(mDataHolder);
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// if (mProductsArray != null && mProductsArray.size() > 0) {
	// mAdapter = new AdapterSearch(this);
	// mAdapter.addItems(mProductsArray);
	// mListOrderRequests.setAdapter(mAdapter);
	// }
	// }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	}

	@Override
	public void onSearchClicked(int pos) {
		MandapHolder mHolder = mProductsArray.get(pos);
		if (mHolder.isProductChecked())
			mHolder.setProductChecked(false);
		else
			mHolder.setProductChecked(true);
		mProductsArray.set(pos, mHolder);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btnSubmit:
			btnSubmitClicked();
			break;
		case R.id.btnCancel:
			btnCancelClicked();
			break;
		default:
			break;
		}
	}

	private void btnCancelClicked() {

	}

	private void btnSubmitClicked() {

	}
}

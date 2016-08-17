package com.mandap;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.androidquery.AQuery;
import com.mandap.BaseActivity;
import com.mandap.R;
import com.mandap.adapters.AdapterSearch;
import com.mandap.interfaces.SearchCallbacks.ISearchCheck;
import com.utils.MandapHolder;
import com.utils.StaticUsage;
import com.utils.StaticUtils;
import com.utils.UserDetails;
import com.utils.WSClass;

public class RequestQuoteActivity extends BaseActivity implements
		OnItemClickListener, ISearchCheck, OnClickListener {

	private MandapHolder mHolder;

	private ArrayList<MandapHolder> mProductsArray;
	private ListView mListOrderRequests;

	private ImageView mImgSupplier;
	private TextView mTxtSupplierName, mTxtLocation, mTxtSubmit, mTxtCancel;
	private EditText mEdtMessage;

	private AdapterSearch mAdapterSearch;
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

	@Override
	protected void onResume() {
		super.onResume();
		initiateProductListWS();
	}

	private void getLayoutReferences() {
		mListOrderRequests = (ListView) findViewById(R.id.listProduct);

		mImgSupplier = (ImageView) findViewById(R.id.imgSupplier);

		mEdtMessage = (EditText) findViewById(R.id.editMessage);
		StaticUtils.setEditTextHintFont(mEdtMessage, this);
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

	private void initiateProductListWS() {
		showProgress();
		Bundle params = new Bundle();
		JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
				StaticUtils.encodeUrl(WSClass.PRODUCT_LIST_SEARCH_SCREEN,
						params), new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						hideProgress();
						responseProductList(response);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						hideProgress();
					}
				});

		RequestQueue mQueue = ((BaseApplication) getApplication()).getQueue();
		mQueue.add(mJsonRequestResponse);
	}

	private void responseProductList(JSONArray response) {
		if (response != null && response.length() != 0) {
			try {
				JSONObject mObj = (JSONObject) response.get(0);
				if (mObj.has(StaticUsage.SUCCESS)) {
					try {
						if (mObj.getString(StaticUsage.SUCCESS)
								.equalsIgnoreCase("0")) {
							showToast(mObj.getString(StaticUsage.MESSAGE));
						} else {
							loadAllData(response);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void loadAllData(JSONArray response) {
		mProductsArray = new ArrayList<MandapHolder>();
		for (int i = 0; i < response.length(); i++) {
			MandapHolder mDataHolder = new MandapHolder();
			try {
				JSONObject mObj = (JSONObject) response.get(i);
				if (mObj.has(StaticUsage.PRODUCT_ID)) {
					mDataHolder.setProductid(mObj
							.getString(StaticUsage.PRODUCT_ID));
				}
				if (mObj.has(StaticUsage.PRODUCT_NAME)) {
					mDataHolder.setProductName(mObj
							.getString(StaticUsage.PRODUCT_NAME));
				}
				mDataHolder.setProductChecked(false);
				mProductsArray.add(mDataHolder);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		setListAdapter();
	}

	private void setListAdapter() {
		mAdapterSearch = new AdapterSearch(this);
		mAdapterSearch.setCheckCallback((ISearchCheck) this);
		mAdapterSearch.addItems(mProductsArray);
		mListOrderRequests.setAdapter(mAdapterSearch);
	}

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
		mAdapterSearch.notifyDataSetChanged();
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
		finish();
	}

	private void btnSubmitClicked() {
		initiateRequestQuoteWS();
	}

	private void initiateRequestQuoteWS() {
		showProgress();
		Bundle params = new Bundle();
		params.putString("supplier_id", mHolder.getSupplierId());
		params.putString("user_id",
				String.valueOf(UserDetails.getInstance(this).getUserId()));
		params.putString("attribute_id", "");
		params.putString("comment", mEdtMessage.getText().toString());
		JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
				StaticUtils.encodeUrl(WSClass.REQUEST_QUOTE, params),
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						hideProgress();
						responseProductList(response);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						hideProgress();
					}
				});

		RequestQueue mQueue = ((BaseApplication) getApplication()).getQueue();
		mQueue.add(mJsonRequestResponse);
	}
}

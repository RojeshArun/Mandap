package com.mandap;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.utils.CommonUtils;
import com.utils.MandapHolder;
import com.utils.StaticUsage;
import com.utils.StaticUtils;
import com.utils.WSClass;
import com.utils.CommonUtils.SpinnerCallBack;

public class SignUpActivity extends BaseActivity implements
		android.view.View.OnClickListener {

	private EditText meditFirstName, meditLastName, meditEmail,
			meditAddressLine1, meditAddressLine2, meditContactNumber,
			meditPassword, meditConfirmPassword;

	private TextView mbtnSignUp, mbtnSelectState, mbtnSelectCity;

	private ArrayList<MandapHolder> mCityList;
	private ArrayList<MandapHolder> mStateList;
	private String mCityCode = "";
	private String mStateCode = "";
	private String mCountryCode = "99";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addContentView(R.layout.activity_registration);
		setTopbarTitle(getString(R.string.registration));
		getLayoutRefernces();
		setClickListeners();
		initiateStateWS(mCountryCode);
	}

	private void setClickListeners() {
		mbtnSignUp.setOnClickListener(this);
		mbtnSelectState.setOnClickListener(this);
		mbtnSelectCity.setOnClickListener(this);
	}

	public void getLayoutRefernces() {
		meditFirstName = (EditText) findViewById(R.id.editFirstName);
		meditLastName = (EditText) findViewById(R.id.editLastName);
		meditEmail = (EditText) findViewById(R.id.editEmail);
		meditAddressLine1 = (EditText) findViewById(R.id.editAddressLine1);
		meditAddressLine2 = (EditText) findViewById(R.id.editAddressLine2);
		meditContactNumber = (EditText) findViewById(R.id.editContactNumber);
		meditPassword = (EditText) findViewById(R.id.editPassword);
		meditConfirmPassword = (EditText) findViewById(R.id.editConfirmPassword);

		mbtnSignUp = (TextView) findViewById(R.id.btnSignUp);

		meditFirstName.setHintTextColor(getResources().getColor(R.color.white));
		meditLastName.setHintTextColor(getResources().getColor(R.color.white));
		meditAddressLine1.setHintTextColor(getResources().getColor(
				R.color.white));
		meditAddressLine2.setHintTextColor(getResources().getColor(
				R.color.white));

		meditContactNumber.setHintTextColor(getResources().getColor(
				R.color.white));
		meditEmail.setHintTextColor(getResources().getColor(R.color.white));
		meditPassword.setHintTextColor(getResources().getColor(R.color.white));
		meditConfirmPassword.setHintTextColor(getResources().getColor(
				R.color.white));

		mbtnSelectCity = (TextView) findViewById(R.id.editSelectCity);
		mbtnSelectState = (TextView) findViewById(R.id.editSelectState);

		StaticUtils.setEditTextHintFont(meditFirstName, this);
		StaticUtils.setEditTextHintFont(meditLastName, this);
		StaticUtils.setEditTextHintFont(meditEmail, this);
		StaticUtils.setEditTextHintFont(meditAddressLine1, this);
		StaticUtils.setEditTextHintFont(meditAddressLine2, this);
		StaticUtils.setEditTextHintFont(meditContactNumber, this);
		StaticUtils.setEditTextHintFont(meditPassword, this);
		StaticUtils.setEditTextHintFont(meditConfirmPassword, this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSignUp:
			dismissKeyboard(v);
			btnSignUpClicked();
			break;
		case R.id.editSelectState:
			btnSelectStateClicked();
			break;
		case R.id.editSelectCity:
			btnSelectCityClicked();
			break;
		}
	}

	private void btnSelectCityClicked() {
		if (mCityList != null && mCityList.size() != 0) {
			final String[] mCityNames = new String[mCityList.size()];
			for (int i = 0; i < mCityList.size(); i++) {
				mCityNames[i] = mCityList.get(i).getCityName();
			}

			new CommonUtils().SingleChoice("Select City", this, mCityNames,
					mbtnSelectCity, new SpinnerCallBack() {

						@Override
						public void onItemSelected(int pos) {
							mbtnSelectCity.setText(mCityNames[pos]);
							mCityCode = mCityList.get(pos).getCityId();
						}
					});
		}
	}

	private void btnSelectStateClicked() {
		if (mStateList != null && mStateList.size() != 0) {
			final String[] mStateNames = new String[mStateList.size()];
			for (int i = 0; i < mStateList.size(); i++) {
				mStateNames[i] = mStateList.get(i).getStateName();
			}

			new CommonUtils().SingleChoice("Select State", this, mStateNames,
					mbtnSelectState, new SpinnerCallBack() {

						@Override
						public void onItemSelected(int pos) {
							mbtnSelectState.setText(mStateNames[pos]);
							mStateCode = mStateList.get(pos).getStateId();
							initiateCityWS(mCountryCode, mStateCode);
						}
					});
		}
	}

	private void btnSignUpClicked() {

		if (TextUtils.isEmpty(meditFirstName.getText().toString())) {
			meditFirstName.requestFocus();
			meditFirstName.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.shake));
		} else if (TextUtils.isEmpty(meditLastName.getText().toString())) {
			meditLastName.requestFocus();
			meditLastName.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.shake));
		} else if (TextUtils.isEmpty(meditEmail.getText().toString())) {
			meditEmail.requestFocus();
			meditEmail.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.shake));
		} else if (!StaticUtils.isValidEmail(meditEmail.getText().toString())) {
			meditEmail.requestFocus();
			meditEmail.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.shake));
		} else if (TextUtils.isEmpty(meditAddressLine1.getText().toString())) {
			meditAddressLine1.requestFocus();
			meditAddressLine1.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.shake));
		} else if (TextUtils.isEmpty(meditAddressLine2.getText().toString())) {
			meditAddressLine2.requestFocus();
			meditAddressLine2.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.shake));
		} else if (TextUtils.isEmpty(meditContactNumber.getText().toString())) {
			meditContactNumber.requestFocus();
			meditContactNumber.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.shake));
		} else if (TextUtils.isEmpty(meditPassword.getText().toString())) {
			meditPassword.requestFocus();
			meditPassword.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.shake));
		} else if (TextUtils.isEmpty(meditConfirmPassword.getText().toString())) {
			meditConfirmPassword.requestFocus();
			meditConfirmPassword.setAnimation(AnimationUtils.loadAnimation(
					this, R.anim.shake));
		} else if (!TextUtils.equals(meditPassword.getText().toString(),
				meditConfirmPassword.getText().toString())) {
			meditConfirmPassword.setText("");
			meditPassword.requestFocus();
			meditPassword.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.shake));
		} else {
			initiateRegisterWS();
		}
	}

	private void initiateRegisterWS() {
		showProgress();

		Bundle params = new Bundle();
		params.putString("first_name", meditFirstName.getText().toString());
		params.putString("last_name", meditLastName.getText().toString());
		params.putString("email", meditEmail.getText().toString());
		params.putString("password", meditPassword.getText().toString());

		params.putString("address", meditAddressLine1.getText().toString());
		params.putString("city", "99");
		params.putString("state", "1458");
		params.putString("contact_no", meditContactNumber.getText().toString());
		JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
				StaticUtils.encodeUrl(WSClass.SIGN_UP, params),
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						hideProgress();
						responseSignUp(response);
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

	private void responseSignUp(JSONArray response) {
		if (response != null && response.length() != 0) {
			try {
				JSONObject mObj = (JSONObject) response.get(0);
				if (mObj.has(StaticUsage.SUCCESS)) {
					try {
						if (mObj.getString(StaticUsage.SUCCESS)
								.equalsIgnoreCase("0")) {
							showToast(mObj.getString(StaticUsage.MESSAGE));
						} else {
							showToast(mObj.getString(StaticUsage.MESSAGE));
							navigateBack();
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

	private void navigateBack() {
		overridePendingTransition(R.anim.push_anim, R.anim.pop_anim);
		finish();
	}

	private void initiateStateWS(String CountryId) {
		Bundle params = new Bundle();
		params.putString("country_id", CountryId);
		JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
				StaticUtils.encodeUrl(WSClass.STATE_LIST, params),
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						hideProgress();
						responseStateList(response);
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

	private void responseStateList(JSONArray response) {
		if (response != null && response.length() != 0) {
			try {
				JSONObject mObj = (JSONObject) response.get(0);
				if (mObj.has(StaticUsage.SUCCESS)) {
					try {
						if (mObj.getString(StaticUsage.SUCCESS)
								.equalsIgnoreCase("0")) {
							showToast(mObj.getString(StaticUsage.MESSAGE));
						} else {
							loadStateData(response);
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

	private void loadStateData(JSONArray response) {
		mStateList = new ArrayList<MandapHolder>();
		for (int i = 0; i < response.length(); i++) {
			MandapHolder mDataHolder = new MandapHolder();
			try {
				JSONObject mObj = (JSONObject) response.get(i);
				if (mObj.has(StaticUsage.USER_STATE_ID)) {
					mDataHolder.setStateId(mObj
							.getString(StaticUsage.USER_STATE_ID));
				}
				if (mObj.has(StaticUsage.USER_STATE_NAME)) {
					mDataHolder.setStateName(mObj
							.getString(StaticUsage.USER_STATE_NAME));
				}
				mStateList.add(mDataHolder);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void initiateCityWS(String CountryId, String StateId) {
		showProgress();
		Bundle params = new Bundle();

		params.putString("state_id", StateId);
		params.putString("country_id", CountryId);
		JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
				StaticUtils.encodeUrl(WSClass.CITY_LIST, params),
				new Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						hideProgress();
						responseCityList(response);
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

	private void responseCityList(JSONArray response) {
		if (response != null && response.length() != 0) {
			try {
				JSONObject mObj = (JSONObject) response.get(0);
				if (mObj.has(StaticUsage.SUCCESS)) {
					try {
						if (mObj.getString(StaticUsage.SUCCESS)
								.equalsIgnoreCase("0")) {
						} else {
							loadCityData(response);
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

	private void loadCityData(JSONArray response) {
		mCityList = new ArrayList<MandapHolder>();
		for (int i = 0; i < response.length(); i++) {
			MandapHolder mDataHolder = new MandapHolder();
			try {
				JSONObject mObj = (JSONObject) response.get(i);
				if (mObj.has(StaticUsage.USER_CITY_ID)) {
					mDataHolder.setCityId(mObj
							.getString(StaticUsage.USER_CITY_ID));
				}
				if (mObj.has(StaticUsage.USER_CITY_NAME)) {
					mDataHolder.setCityName(mObj
							.getString(StaticUsage.USER_CITY_NAME));
				}
				mCityList.add(mDataHolder);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}
}

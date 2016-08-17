package com.mandap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.utils.StaticUsage;
import com.utils.StaticUtils;
import com.utils.UserDetails;
import com.utils.WSClass;

public class EditProfile extends BaseActivity implements OnClickListener {
	private AQuery mQuery;
	private ImageOptions mOptions;

	private ImageView mSuplierImage;
	private EditText medtCompanyName, medtLastname, medtEmail, medtAddress,
			medtPhoneNumber, medtDescription;
	private TextView mtxtbtnSave, mtxtbtnCancel;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mQuery = new AQuery(this);
		mOptions = new ImageOptions();
		addContentView(R.layout.activity_editprofile);
		setTopbarTitle(getString(R.string.edit_profile));
		getLayoutRefferences();
		setOnClickListeners();
		intiateViewProfileWS();
	}

	private void getLayoutRefferences() {
		mSuplierImage = (ImageView) findViewById(R.id.imgSupplier);

		medtCompanyName = (EditText) findViewById(R.id.editFirstName);
		medtLastname = (EditText) findViewById(R.id.editLastName);
		medtEmail = (EditText) findViewById(R.id.editEmail);
		medtAddress = (EditText) findViewById(R.id.editAddressLine1);
		medtPhoneNumber = (EditText) findViewById(R.id.editContactNumber);
		medtDescription = (EditText) findViewById(R.id.edtDescription);
		mtxtbtnSave = (TextView) findViewById(R.id.btnSaveChanges);
		mtxtbtnCancel = (TextView) findViewById(R.id.btnCancel);

		StaticUtils.setEditTextHintFont(medtCompanyName, this);
		StaticUtils.setEditTextHintFont(medtLastname, this);
		StaticUtils.setEditTextHintFont(medtEmail, this);
		StaticUtils.setEditTextHintFont(medtAddress, this);
		StaticUtils.setEditTextHintFont(medtPhoneNumber, this);
		StaticUtils.setEditTextHintFont(medtDescription, this);

	}

	private void setOnClickListeners() {
		mtxtbtnSave.setOnClickListener(this);
		mtxtbtnCancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSaveChanges:
			btnSaveClicked();
			break;
		case R.id.btnCancel:
			btnCancelClicked();
			break;
		default:
			break;
		}
	}

	private void intiateViewProfileWS() {
		Bundle mParams = new Bundle();
		mParams.putString("supplier_id",
				String.valueOf(UserDetails.getInstance(this).getUserId()));

		if (UserDetails.getInstance(this).getUserType()
				.equalsIgnoreCase("supplier")) {
			JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
					StaticUtils.encodeUrl(WSClass.SUPLLIER_VIEW_SETTINGS,
							mParams), new Listener<JSONArray>() {

						@Override
						public void onResponse(JSONArray response) {
							hideProgress();
							responseViewProfile(response);
						}

					}, new ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							hideProgress();
						}
					});

			RequestQueue mQueue = ((BaseApplication) getApplication())
					.getQueue();
			mQueue.add(mJsonRequestResponse);
		} else {
			JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
					StaticUtils.encodeUrl(WSClass.CONSUMER_VIEW_SETTINGS,
							mParams), new Listener<JSONArray>() {

						@Override
						public void onResponse(JSONArray response) {
							hideProgress();
							responseViewProfile(response);
						}

					}, new ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							hideProgress();
						}
					});

			RequestQueue mQueue = ((BaseApplication) getApplication())
					.getQueue();
			mQueue.add(mJsonRequestResponse);
		}

	}

	private void btnCancelClicked() {
		finish();
	}

	private void btnSaveClicked() {
		if (TextUtils.isEmpty(medtCompanyName.getText().toString())) {
			medtCompanyName.requestFocus();
			medtCompanyName.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.shake));
		} else if (TextUtils.isEmpty(medtEmail.getText().toString())) {
			medtEmail.requestFocus();
			medtEmail.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.shake));
		} else if (!StaticUtils.isValidEmail(medtEmail.getText().toString())) {
			medtEmail.requestFocus();
			medtEmail.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.shake));
		} else if (TextUtils.isEmpty(medtAddress.getText().toString())) {
			medtAddress.requestFocus();
			medtAddress.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.shake));
		} else if (TextUtils.isEmpty(medtPhoneNumber.getText().toString())) {
			medtPhoneNumber.requestFocus();
			medtPhoneNumber.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.shake));
		} else if (TextUtils.isEmpty(medtDescription.getText().toString())) {
			medtDescription.requestFocus();
			medtDescription.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.shake));
		} else {
			initiateSaveEditProfileWS();
		}
	}

	private void initiateSaveEditProfileWS() {
		showProgress();
		Bundle params = new Bundle();
		params.putString("first_name", medtCompanyName.getText().toString());
		params.putString("last_name", medtLastname.getText().toString());
		params.putString("email", medtEmail.getText().toString());
		params.putString("address", medtAddress.getText().toString());
		params.putString("city", "99");
		params.putString("state", "1458");
		params.putString("contact_no", medtPhoneNumber.getText().toString());
		if (UserDetails.getInstance(this).getUserType()
				.equalsIgnoreCase("supplier")) {
			params.putString("supplier_id", String.valueOf(UserDetails
					.getInstance(EditProfile.this).getUserId()));
			JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
					StaticUtils.encodeUrl(WSClass.SUPLLIER_SAVE_SETTINGS,
							params), new Listener<JSONArray>() {

						@Override
						public void onResponse(JSONArray response) {
							hideProgress();
							responseEditProfile(response);
						}

					}, new ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							hideProgress();
						}
					});
			RequestQueue mQueue = ((BaseApplication) getApplication())
					.getQueue();
			mQueue.add(mJsonRequestResponse);
		} else {
			params.putString("user_id", String.valueOf(UserDetails.getInstance(
					EditProfile.this).getUserId()));
			JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
					StaticUtils.encodeUrl(WSClass.CONSUMER_SAVE_SETTINGS,
							params), new Listener<JSONArray>() {

						@Override
						public void onResponse(JSONArray response) {
							hideProgress();
							responseEditProfile(response);
						}

					}, new ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							hideProgress();
						}
					});
			RequestQueue mQueue = ((BaseApplication) getApplication())
					.getQueue();
			mQueue.add(mJsonRequestResponse);
		}

	}

	protected void responseEditProfile(JSONArray response) {
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
							finish();
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

	private void responseViewProfile(JSONArray response) {
		if (response != null && response.length() != 0) {
			try {
				JSONObject mObj = (JSONObject) response.get(0);
				if (mObj.has(StaticUsage.SUCCESS)) {
					try {
						if (mObj.getString(StaticUsage.SUCCESS)
								.equalsIgnoreCase("1")) {
							setProfileData(mObj);
						} else {
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

	private void setProfileData(JSONObject mObject) {
		if (mObject != null) {
			try {
				if (mObject.getString(StaticUsage.SUPPLIER_IMAGE).contains(
						"http://")) {
					if (mObject.getString(StaticUsage.SUPPLIER_IMAGE).contains(
							"noimage")) {
					} else {
						mQuery.id(mSuplierImage).image(
								mObject.getString(StaticUsage.SUPPLIER_IMAGE));
					}
				} else {
				}
				medtCompanyName.setText(mObject
						.getString(StaticUsage.FIRST_NAME));
				medtEmail.setText(mObject.getString(StaticUsage.EMAIL));
				medtPhoneNumber.setText(mObject
						.getString(StaticUsage.VPHONENUMBER));
				medtAddress.setText(mObject.getString(StaticUsage.VADDRESS));
				medtDescription.setText(mObject
						.getString(StaticUsage.DESCRIPTION));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}

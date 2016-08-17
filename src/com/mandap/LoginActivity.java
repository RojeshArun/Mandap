package com.mandap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mandap.suppliers.SuppliersHome;
import com.utils.CommonUtils;
import com.utils.CommonUtils.SpinnerCallBack;
import com.utils.StaticUsage;
import com.utils.StaticUtils;
import com.utils.UserDetails;
import com.utils.WSClass;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private TextView mbtnLogin, mbtnForgotPassword, mbtnSignUp;
	private EditText meditEmail, meditPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addContentView(R.layout.activity_login);
		hideTopbar();
		getAllReferences();
		setClickListeners();
	}

	private void setClickListeners() {
		mbtnLogin.setOnClickListener(this);
		mbtnSignUp.setOnClickListener(this);
		mbtnForgotPassword.setOnClickListener(this);
	}

	private void getAllReferences() {
		mbtnLogin = (TextView) findViewById(R.id.btnLogin);
		mbtnForgotPassword = (TextView) findViewById(R.id.btnForgotPassword);
		mbtnSignUp = (TextView) findViewById(R.id.btnSignUp);

		meditEmail = (EditText) findViewById(R.id.editLogin);
		meditPassword = (EditText) findViewById(R.id.editPassword);

		meditEmail.setHintTextColor(getResources().getColor(R.color.white));
		meditPassword.setHintTextColor(getResources().getColor(R.color.white));
		StaticUtils.setEditTextHintFont(meditEmail, this);
		StaticUtils.setEditTextHintFont(meditPassword, this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLogin:
			dismissKeyboard(v);
			btnLoginClicked();
			break;
		case R.id.btnSignUp:
			btnSingUpClicked();
			break;
		case R.id.btnForgotPassword:
			btnForgotPassword();
			break;
		default:
			break;
		}
	}

	private void btnLoginClicked() {

		if (TextUtils.isEmpty(meditEmail.getText().toString())) {
			meditEmail.requestFocus();
			showToast(getResources().getString(R.string.please_enter_email_));
		} else if (!StaticUtils.isValidEmail(meditEmail.getText().toString())) {
			meditEmail.requestFocus();
			showToast(getResources().getString(
					R.string.please_enter_valid_email_));
		} else if (TextUtils.isEmpty(meditPassword.getText().toString())) {
			meditPassword.requestFocus();
			showToast(getResources().getString(R.string.please_enter_password_));
		} else {
			final String[] mLoginTypes = { "Buyer", "Supplier" };
			new CommonUtils().SingleChoice("Select Login Via", this,
					mLoginTypes, mbtnLogin, new SpinnerCallBack() {

						@Override
						public void onItemSelected(int pos) {
							String mType = mLoginTypes[pos];
							initiateLoginWS(mType);
						}
					});
		}
	}

	private void initiateLoginWS(String mType) {
		showProgress();
		Bundle params = new Bundle();
		params.putString("email", meditEmail.getText().toString());
		params.putString("password", meditPassword.getText().toString());
		if (mType.equalsIgnoreCase("Buyer")) {
			JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
					StaticUtils.encodeUrl(WSClass.LOGIN_CONSUMER, params),
					new Listener<JSONArray>() {

						@Override
						public void onResponse(JSONArray response) {
							hideProgress();
							responseLoginWS(response);
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
					StaticUtils.encodeUrl(WSClass.LOGIN_SUPPLIER, params),
					new Listener<JSONArray>() {

						@Override
						public void onResponse(JSONArray response) {
							hideProgress();
							responseLoginWS(response);
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

	private void btnSingUpClicked() {
		Intent mIntent = new Intent(this, SignUpActivity.class);
		overridePendingTransition(R.anim.push_anim, R.anim.pop_anim);
		startActivity(mIntent);
	}

	private void btnForgotPassword() {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View v = LayoutInflater.from(this).inflate(R.layout.forgot_alert, null);
		TextView btnSubmit = (TextView) v.findViewById(R.id.btnSubmit);
		TextView btnCancel = (TextView) v.findViewById(R.id.btnCancel);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		final EditText txtMessage = (EditText) v.findViewById(R.id.editEmail);
		StaticUtils.setEditTextHintFont(txtMessage, LoginActivity.this);

		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(txtMessage.getText().toString())) {
					showToast(getResources().getString(
							R.string.please_enter_email_));
				} else if (StaticUtils.isValidEmail(txtMessage.getText()
						.toString())) {
					showToast(getResources().getString(
							R.string.please_enter_valid_email_));
				} else {
					dialog.dismiss();
					decideBuyerSupplier(txtMessage.getText().toString());
				}

			}
		});

		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.setContentView(v);
		dialog.show();
	}

	protected void initiateForgotPasswordWS(String string, String mType) {

		showProgress();
		Bundle params = new Bundle();
		params.putString("email", meditEmail.getText().toString());
		if (mType.equalsIgnoreCase("Buyer")) {
			JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
					StaticUtils.encodeUrl(WSClass.FORGOT_CONSUMER, params),
					new Listener<JSONArray>() {

						@Override
						public void onResponse(JSONArray response) {
							hideProgress();
							responseForgotPasswordWS(response);
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
					StaticUtils.encodeUrl(WSClass.FORGOT_SUPPLIER, params),
					new Listener<JSONArray>() {

						@Override
						public void onResponse(JSONArray response) {
							hideProgress();
							responseForgotPasswordWS(response);
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

	private void decideBuyerSupplier(final String mEmailId) {
		final String[] mLoginTypes = { "Buyer", "Supplier" };
		new CommonUtils().SingleChoice("Select Login Via", this, mLoginTypes,
				mbtnLogin, new SpinnerCallBack() {

					@Override
					public void onItemSelected(int pos) {
						String mType = mLoginTypes[pos];
						initiateForgotPasswordWS(mEmailId, mType);
					}
				});
	}

	private void responseLoginWS(JSONArray response) {
		if (response != null && response.length() != 0) {
			try {
				JSONObject mObj = (JSONObject) response.get(0);
				if (mObj.has(StaticUsage.SUCCESS)) {
					try {
						if (mObj.getString(StaticUsage.SUCCESS)
								.equalsIgnoreCase("0")) {
							showToast(mObj.getString(StaticUsage.MESSAGE));
						} else {
							saveUserInformation(mObj);
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

	private void responseForgotPasswordWS(JSONArray response) {
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

	private void saveUserInformation(JSONObject response) {
		if (response.has(StaticUsage.IUSERID)) {
			try {
				UserDetails.getInstance(this).setUserId(
						Integer.parseInt(response
								.getString(StaticUsage.IUSERID)));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if (response.has(StaticUsage.ISUPPLIERID)) {
			try {
				UserDetails.getInstance(this).setUserId(
						Integer.parseInt(response
								.getString(StaticUsage.ISUPPLIERID)));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (response.has(StaticUsage.EUSERTYPE)) {
			try {
				UserDetails.getInstance(this).setUserType(
						response.getString(StaticUsage.EUSERTYPE));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if (response.has(StaticUsage.ISUPPLIERID)) {
			UserDetails.getInstance(this).setUserType("supplier");
		} else if (response.has(StaticUsage.IUSERID)) {
			UserDetails.getInstance(this).setUserType("wholeseller");
		}

		if (response.has(StaticUsage.USER_PHONE_NUMBER)) {
			try {
				UserDetails.getInstance(this).setPhoneNumber(
						response.getString(StaticUsage.USER_PHONE_NUMBER));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if (response.has(StaticUsage.VFIRSTNAME)) {
			try {
				UserDetails.getInstance(this).setFirstName(
						response.getString(StaticUsage.VFIRSTNAME));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if (response.has(StaticUsage.ISUPPLIERID)) {
			UserDetails.getInstance(this).setUserType("supplier");
		} else if (response.has(StaticUsage.IUSERID)) {
			UserDetails.getInstance(this).setUserType("wholeseller");
		}

		if (UserDetails.getInstance(this).getUserType()
				.equalsIgnoreCase("supplier")) {
			navigateToSuppliersHomeActivity();
		} else {
			navigateToHomeActivity();
		}
	}

	private void navigateToHomeActivity() {
		Intent mIntent = new Intent(this, HomeActivity.class);
		overridePendingTransition(R.anim.push_anim, R.anim.pop_anim);
		startActivity(mIntent);
		finish();
	}

	private void navigateToSuppliersHomeActivity() {
		Intent mIntent = new Intent(this, SuppliersHome.class);
		overridePendingTransition(R.anim.push_anim, R.anim.pop_anim);
		startActivity(mIntent);
		finish();
	}

}

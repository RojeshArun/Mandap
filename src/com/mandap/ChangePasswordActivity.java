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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.utils.StaticUsage;
import com.utils.StaticUtils;
import com.utils.UserDetails;
import com.utils.WSClass;

public class ChangePasswordActivity extends BaseActivity implements
		OnClickListener {

	private EditText meditCurrentPassword, meditNewPassword,
			meditConfirmPassword;

	private TextView mbtnSaveChanges, mbtnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addContentView(R.layout.activity_changepassword);
		setTopbarTitle(getString(R.string.change_password));
		getLayoutReferences();
		setOnClickListeners();
	}

	private void setOnClickListeners() {
		mbtnSaveChanges.setOnClickListener(this);
		mbtnCancel.setOnClickListener(this);
	}

	private void getLayoutReferences() {
		meditCurrentPassword = (EditText) findViewById(R.id.editPassword);
		meditNewPassword = (EditText) findViewById(R.id.editNewPassword);
		meditConfirmPassword = (EditText) findViewById(R.id.editConfirmPassword);

		mbtnSaveChanges = (TextView) findViewById(R.id.btnSaveChanges);
		mbtnCancel = (TextView) findViewById(R.id.btnCancel);

		StaticUtils.setEditTextHintFont(meditCurrentPassword, this);
		StaticUtils.setEditTextHintFont(meditNewPassword, this);
		StaticUtils.setEditTextHintFont(meditConfirmPassword, this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCancel:
			btnCancelClicked();
			break;
		case R.id.btnSaveChanges:
			btnSaveChangesClicked();
			break;
		default:
			break;
		}
	}

	private void btnCancelClicked() {
		navigateBack();
	}

	private void btnSaveChangesClicked() {
		if (TextUtils.isEmpty(meditCurrentPassword.getText().toString())) {
			meditCurrentPassword.requestFocus();
			meditCurrentPassword.setAnimation(AnimationUtils.loadAnimation(
					this, R.anim.shake));
		} else if (TextUtils.isEmpty(meditNewPassword.getText().toString())) {
			meditNewPassword.requestFocus();
			meditNewPassword.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.shake));
		} else if (TextUtils.isEmpty(meditConfirmPassword.getText().toString())) {
			meditConfirmPassword.requestFocus();
			meditConfirmPassword.setAnimation(AnimationUtils.loadAnimation(
					this, R.anim.shake));
		} else if (!TextUtils.equals(meditNewPassword.getText().toString(),
				meditConfirmPassword.getText().toString())) {
			meditConfirmPassword.setText("");
			meditNewPassword.requestFocus();
			meditNewPassword.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.shake));
		} else {
			initiateChangePasswordWS();
		}
	}

	private void initiateChangePasswordWS() {
		showProgress();
		Bundle params = new Bundle();
		params.putString(
				"user_id",
				String.valueOf(UserDetails.getInstance(
						ChangePasswordActivity.this).getUserId()));
		params.putString("old_password", meditCurrentPassword.getText()
				.toString());
		params.putString("new_password", meditNewPassword.getText().toString());
		JsonArrayRequest mJsonRequestResponse = new JsonArrayRequest(
				StaticUtils.encodeUrl(WSClass.CHANGE_PASSWORD, params),
				new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						hideProgress();
						responseChangePasswordData(response);
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

	private void responseChangePasswordData(JSONArray response) {
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
		finish();
		overridePendingTransition(R.anim.push_anim, R.anim.pop_anim);
	}

}

package com.mandap.suppliers;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.mandap.BaseActivity;
import com.mandap.R;
import com.utils.CommonUtils;
import com.utils.CommonUtils.SpinnerCallBack;
import com.utils.MandapHolder;
import com.utils.StaticUtils;
import com.utils.UserDetails;

public class OrderDetailsActivity extends BaseActivity {

	private MandapHolder mProductDetails;
	private TextView mtxtUserName, mtxtColor, mtxtSize, mtxtQuality,
			mtxtPhoneNumber, mtxtDate;
	private WebView webViewRequestMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addContentView(R.layout.activity_order_details);
		setTopbarTitle(getString(R.string.order_details));
		getLayoutReferences();
		getData();
	}

	private void getData() {
		if (getIntent() != null && getIntent().getExtras() != null) {
			if (getIntent().getExtras().containsKey("data")) {
				mProductDetails = (MandapHolder) getIntent().getExtras()
						.getSerializable("data");
				setData();
			}
		}
	}

	private void setData() {
		mtxtUserName.setText(mProductDetails.getProductName());
		mtxtColor.setText(mProductDetails.getColorName());
		mtxtSize.setText(mProductDetails.getSize());
		mtxtQuality.setText(mProductDetails.getVirginType());
		mtxtPhoneNumber.setText(UserDetails.getInstance(this).getPhoneNumber());
		mtxtDate.setText(mProductDetails.getRequestDate());

		StaticUtils.loadHtmlContent(webViewRequestMessage,
				mProductDetails.getDescription(), 13);
	}

	private void getLayoutReferences() {
		mtxtUserName = (TextView) findViewById(R.id.txtUserName);
		mtxtColor = (TextView) findViewById(R.id.txtColor);
		mtxtSize = (TextView) findViewById(R.id.txtSize);
		mtxtQuality = (TextView) findViewById(R.id.txtQuality);
		mtxtPhoneNumber = (TextView) findViewById(R.id.txtPhoneNumber);
		mtxtDate = (TextView) findViewById(R.id.txtDate);

		webViewRequestMessage = (WebView) findViewById(R.id.webViewRequestMessage);
	}

}

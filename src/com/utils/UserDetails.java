package com.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserDetails {

	public static final String PREF_NAME = "user_data";
	private static UserDetails instance;
	private SharedPreferences sh;
	private Editor edit;
	private boolean isViewed = false;

	private UserDetails(Context mContext) {
		sh = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		edit = sh.edit();
	}

	public static synchronized UserDetails getInstance(Context mContext) {
		if (instance == null) {
			instance = new UserDetails(mContext);
		}
		return instance;
	}

	public void setUserAddress(String membertype) {
		edit.putString("user_address", membertype).commit();
	}

	public String getUserAddress() {
		return sh.getString("user_address", "");
	}

	public void setUserContactNumber(String membertype) {
		edit.putString("user_contact_number", membertype).commit();
	}

	public String getUserContactNumber() {
		return sh.getString("user_contact_number", "");
	}

	public void setUserName(String membertype) {
		edit.putString("username", membertype).commit();
	}

	public String getUserName() {
		return sh.getString("username", "");
	}

	public void setBrandName(String membertype) {
		edit.putString("brand_name", membertype).commit();
	}

	public String getBrandName() {
		return sh.getString("brand_name", "");
	}

	public void setUserType(String membertype) {
		edit.putString("user_type", membertype).commit();
	}

	public String getUserType() {
		return sh.getString("user_type", "");
	}

	public void setUserSignedIn(String membertype) {
		edit.putString("user_signed", membertype).commit();
	}

	public String getUserSignedIn() {
		return sh.getString("user_signed", "no");
	}

	public void setAutoLogin(boolean membertype) {
		edit.putBoolean("auto_login", membertype).commit();
	}

	public void setAuthorizationToken(String AuthToken) {
		edit.putString("Auth_Token", AuthToken).commit();
	}

	public String getAuthorizationToken() {
		return sh.getString("Auth_Token", "");
	}

	public boolean getSupplierAutoLogin() {
		return sh.getBoolean("supplier_auto_login", false);
	}

	public void setSupplierAutoLogin(boolean membertype) {
		edit.putBoolean("supplier_auto_login", membertype).commit();
	}

	public boolean getAutoLogin() {
		return sh.getBoolean("auto_login", false);
	}

	public int getUserId() {
		return sh.getInt("userid", -1);
	}

	public void setUserId(int userid) {
		edit.putInt("userid", userid).commit();
	}

	public void clear() {
		edit.clear().commit();
	}

	public void setRemeber(boolean isflag) {
		edit.putBoolean("remember", isflag).commit();
	}

	public boolean isRemember() {
		return sh.getBoolean("remember", false);
	}

	public String getUserEmail() {
		return sh.getString("useremail", "");
	}

	public void setUserEmail(String useremail) {
		edit.putString("useremail", useremail).commit();
	}

	public String getPassword() {
		return sh.getString("password", "");
	}

	public void setPassword(String password) {
		edit.putString("password", password).commit();
	}

	public boolean isViewed() {
		return isViewed;
	}

	public void setViewed(boolean isViewed) {
		this.isViewed = isViewed;
	}

	public String getFirstName() {
		return sh.getString("first_name", "");
	}

	public void setFirstName(String first_name) {
		edit.putString("first_name", first_name).commit();
	}

	public String getLastName() {
		return sh.getString("last_name", "");
	}

	public void setLastName(String last_name) {
		edit.putString("last_name", last_name).commit();
	}

	public String getStatus() {
		return sh.getString("status", "");
	}

	public void setStatus(String status) {
		edit.putString("status", status).commit();
	}

	public String getTotalCredits() {
		return sh.getString("total_credits", "");
	}

	public void setTotalCredits(String total_credits) {
		edit.putString("total_credits", total_credits).commit();
	}

	public int getScreenIndex() {
		return sh.getInt("screenIndex", -1);
	}

	public void setScreenIndex(int index) {
		edit.putInt("screenIndex", index).commit();
	}

	public void setDeviceToken(String device_token) {
		edit.putString("device_token", device_token).commit();
	}

	public String getDeviceToken() {
		return sh.getString("device_token", "");
	}

	public void setPayPerJobPrice(String mPrice) {
		edit.putString("pay_per_job_price", mPrice).commit();
	}

	public String getPayPerJobPrice() {
		return sh.getString("pay_per_job_price", "");
	}

	public void setAddShadowPrice(String mPrice) {
		edit.putString("add_shadow_price", mPrice).commit();
	}

	public String getAddShadowPrice() {
		return sh.getString("add_shadow_price", "");
	}

	public void setAddPathPrice(String mPrice) {
		edit.putString("add_path_price", mPrice).commit();
	}

	public String getAddPathPrice() {
		return sh.getString("add_path_price", "");
	}

	public void setShowPopup(boolean showPop) {
		edit.putBoolean("show_login_pop", showPop).commit();
	}

	public boolean isShowPopup() {
		return sh.getBoolean("show_login_pop", false);
	}

	public void setIsCamera(boolean showPop) {
		edit.putBoolean("is_camera", showPop).commit();
	}

	public boolean isCamera() {
		return sh.getBoolean("is_camera", false);
	}

	public void setPhoneNumber(String phoneNumber) {
		edit.putString("phoneNumber", phoneNumber).commit();
	}

	public String getPhoneNumber() {
		return sh.getString("phoneNumber", "");
	}

}

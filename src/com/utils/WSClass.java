package com.utils;

public class WSClass {

	public static final String DUMMY_URL = "http://192.168.2.30/mandap_api/user/";
	public static final String LOCAL_URL = "http://127.0.0.1/mandap_api/user/";
	public static final String LIVE_URL = "http://mandapnet.com/mandap_api/user/";
	public static final String LIVE_CURRENT_URL = "http://mandapnet.com/api/";
	public static final String BASE_URL = LIVE_CURRENT_URL;

	/**
	 * @LOGIN_CONSUMER {@link requires}<br>
	 *                 email<br>
	 *                 password<br>
	 */
	public static final String LOGIN_CONSUMER = BASE_URL + "consumer/login?";

	/**
	 * @FORGOT_CONSUMER {@link requires}<br>
	 *                  email<br>
	 */
	public static final String FORGOT_CONSUMER = BASE_URL
			+ "consumer/forgot_password?";

	// http://127.0.0.1/mandap_new/supplier/view_settings?supplier_id=1

	// http://mandapnet.com/api/supplier/getPriceAndWeight?type=weight&loginusertype=buyer

	/**
	 * @FORGOT_SUPPLIER {@link requires}<br>
	 *                  type<br>
	 *                  loginusertype<br>
	 * 
	 */
	public static final String GETPRICEANDWEIGHT = BASE_URL
			+ "supplier/getPriceAndWeight?";

	/**
	 * @FORGOT_SUPPLIER {@link requires}<br>
	 *                  supplier_id<br>
	 */
	public static final String view_settings = BASE_URL
			+ "/supplier/view_settings?";

	/**
	 * @FORGOT_SUPPLIER {@link requires}<br>
	 *                  email<br>
	 */
	public static final String FORGOT_SUPPLIER = BASE_URL
			+ "/supplier/forgot_password?";

	/**
	 * @LOGIN_SUPPLIER {@link requires}<br>
	 *                 email<br>
	 *                 password<br>
	 */
	public static final String LOGIN_SUPPLIER = BASE_URL + "supplier/login?";

	/**
	 * @PRODUCT_LIST_SEARCH_SCREEN
	 * 
	 */
	public static final String PRODUCT_LIST_SEARCH_SCREEN = BASE_URL
			+ "consumer/product_list_search_screen";

	/**
	 * @SUPPLIER_LIST {@link requires}<br>
	 *                user_id<br>
	 *                keyword<br>
	 * 
	 */
	public static final String SUPPLIER_LIST = BASE_URL
			+ "consumer/supplier_list?";

	/**
	 * @FAVOURITE_SUPPLIER_LIST {@link requires}<br>
	 *                          user_id<br>
	 *                          keyword<br>
	 */
	public static final String FAVOURITE_SUPPLIER_LIST = BASE_URL
			+ "consumer/fav_supplier_list?";

	/**
	 * @READ_PROFILE {@link requires}<br>
	 *               user_id<br>
	 *               keyword<br>
	 */
	public static final String READ_PROFILE = BASE_URL
			+ "consumer/view_settings?";

	/**
	 * @SAVE_PROFILE {@link requires}<br>
	 *               user_id<br>
	 *               keyword<br>
	 */
	public static final String SAVE_PROFILE = BASE_URL
			+ "consumer/save_settings?";

	/**
	 * @CHANGE_PASSWORD {@link requires}<br>
	 *                  user_id<br>
	 *                  keyword<br>
	 */
	public static final String CHANGE_PASSWORD = BASE_URL
			+ "consumer/change_password?";

	/**
	 * @CHANGE_PASSWORD {@link requires}<br>
	 *                  user_id<br>
	 *                  keyword<br>
	 */
	public static final String CHANGE_PASSWORD_SUPPLIER = BASE_URL
			+ "supplier/change_password?";

	/**
	 * @MY_REQUESTS {@link requires}<br>
	 *              user_id<br>
	 *              keyword<br>
	 */
	public static final String MY_REQUESTS = BASE_URL + "consumer/my_requests?";

	/**
	 * @SIGN_UP {@link requires}<br>
	 *          user_id<br>
	 *          keyword<br>
	 */
	public static final String SIGN_UP = BASE_URL + "register?";

	/**
	 * @CITY_LIST {@link requires}<br>
	 *            country_id<br>
	 *            state_id<br>
	 */
	public static final String CITY_LIST = BASE_URL + "consumer/city_list?";
	/**
	 * @STATE_LIST {@link requires}<br>
	 *             country_id<br>
	 */
	public static final String STATE_LIST = BASE_URL + "consumer/state_list?";

	/**
	 * @COUNTRY_LIST {@link requires}<br>
	 * 
	 */
	public static final String COUNTRY_LIST = BASE_URL
			+ "consumer/country_list";

	/**
	 * @ADD_FAV_SUPPLIER {@link requires}<br>
	 *                   supplier_id<br>
	 *                   user_id
	 * 
	 */
	public static final String ADD_FAV_SUPPLIER = BASE_URL
			+ "consumer/add_fav_supplier?";

	/**
	 * @SUPPLIER_MY_PRODUCT {@link requires}<br>
	 *                      supplier_id<br>
	 * 
	 */
	public static final String SUPPLIER_MY_PRODUCT = BASE_URL
			+ "supplier/supplier_my_product?";

	/**
	 * @SUPPLIER_MY_ORDERS {@link requires}<br>
	 *                     supplier_id<br>
	 * 
	 */
	public static final String SUPPLIER_MY_ORDERS = BASE_URL
			+ "supplier/supplier_my_orders?";

	/**
	 * @SUPPLIER_DETAILS {@link requires}<br>
	 *                   supplier_id<br>
	 *                   user_id
	 * 
	 */
	public static final String SUPPLIER_DETAILS = BASE_URL
			+ "consumer/supplier_details?";

	/**
	 * @SUPPLIER_PRODUCT_LIST {@link requires}<br>
	 * 
	 */
	public static final String SUPPLIER_PRODUCT_LIST = BASE_URL
			+ "supplier/supplier_product_list?";

	/**
	 * @SUPPLIER_ADD_PRODUCT {@link requires}<br>
	 *                       no_of_bale<br>
	 *                       supplier_id<br>
	 *                       product_id<br>
	 *                       quantity<br>
	 *                       quality<br>
	 *                       price_for_retailer<br>
	 *                       price_for_wholeseller<br>
	 *                       size<br>
	 * 
	 * 
	 * 
	 */
	public static final String SUPPLIER_ADD_PRODUCT = BASE_URL
			+ "supplier/supplier_add_product?";

	/**
	 * @SAVE_SETTINGS {@link requires}<br>
	 *                user_id<br>
	 *                first_name<br>
	 *                last_name<br>
	 *                email<br>
	 *                address<br>
	 *                city<br>
	 *                state<br>
	 *                contact_no<br>
	 * 
	 */
	public static final String CONSUMER_SAVE_SETTINGS = BASE_URL
			+ "consumer/save_settings?";

	/**
	 * @VIEW_SETTINGS {@link requires}<br>
	 *                supplier_id<br>
	 * 
	 */
	public static final String CONSUMER_VIEW_SETTINGS = BASE_URL
			+ "consumer/view_settings?";

	/**
	 * @SAVE_SETTINGS {@link requires}<br>
	 *                user_id<br>
	 *                first_name<br>
	 *                last_name<br>
	 *                email<br>
	 *                address<br>
	 *                city<br>
	 *                state<br>
	 *                contact_no<br>
	 * 
	 */
	public static final String SUPLLIER_SAVE_SETTINGS = BASE_URL
			+ "supplier/save_settings?";

	/**
	 * @VIEW_SETTINGS {@link requires}<br>
	 *                supplier_id<br>
	 * 
	 */
	public static final String SUPLLIER_VIEW_SETTINGS = BASE_URL
			+ "supplier/view_settings?";

	/**
	 * @REQUEST_QUOTE {@link requires}<br>
	 *                supplier_id<br>
	 *                user_id<br>
	 *                attribute_id <br>
	 *                comment
	 * 
	 */
	public static final String REQUEST_QUOTE = BASE_URL
			+ "/consumer/request_quote?";

	
	
	public static final String MY_REQUEST_DETAILS = BASE_URL
			+ "consumer/my_requests_details?";

	// /my_requests_details?

}

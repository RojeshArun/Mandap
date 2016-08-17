package com.utils;

import java.io.Serializable;
import java.util.ArrayList;

public class MandapHolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<MandapHolder> mProducts;

	public ArrayList<MandapHolder> getProducts() {
		return mProducts;
	}

	public void setProducts(ArrayList<MandapHolder> mProducts) {
		this.mProducts = mProducts;
	}

	public ArrayList<MandapHolder> getComments() {
		return mComments;
	}

	public void setComments(ArrayList<MandapHolder> mComments) {
		this.mComments = mComments;
	}

	private ArrayList<MandapHolder> mComments;

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderImage() {
		return senderImage;
	}

	public void setSenderImage(String senderImage) {
		this.senderImage = senderImage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private String senderId;
	private String senderName;
	private String senderImage;
	private String address;

	private String productid;
	private String productName;
	private String supplierId;

	private String quoteId;
	private String quantity;
	private String description;

	private boolean isProductChecked;

	private String supplierName;
	private String supplierLocationName;
	private String supplierImage;
	private String requestDate;

	private String isFavourite;
	private String price;

	private String stateName;
	private String cityId;
	private String cityName;
	private String countryId;
	private String countryName;
	private String comment;
	private String commentDate;

	/******* Supplier Product Parameters ************/

	private String attributeId;

	public String getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}

	public String getSequenceID() {
		return sequenceID;
	}

	public void setSequenceID(String sequenceID) {
		this.sequenceID = sequenceID;
	}

	public String getPriceSeller() {
		return priceSeller;
	}

	public void setPriceSeller(String priceSeller) {
		this.priceSeller = priceSeller;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getBale() {
		return bale;
	}

	public void setBale(String bale) {
		this.bale = bale;
	}

	public String getGsm() {
		return gsm;
	}

	public void setGsm(String gsm) {
		this.gsm = gsm;
	}

	public String getAddedUserId() {
		return addedUserId;
	}

	public void setAddedUserId(String addedUserId) {
		this.addedUserId = addedUserId;
	}

	public String getPriceWholeSeller() {
		return priceWholeSeller;
	}

	public void setPriceWholeSeller(String priceWholeSeller) {
		this.priceWholeSeller = priceWholeSeller;
	}

	public String getVirginType() {
		return virginType;
	}

	public void setVirginType(String virginType) {
		this.virginType = virginType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String sequenceID;
	private String priceSeller;
	private String size;
	private String weight;
	private String bale;
	private String gsm;
	private String addedUserId;
	private String priceWholeSeller;
	private String virginType;
	private String status;

	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierLocationName() {
		return supplierLocationName;
	}

	public void setSupplierLocationName(String supplierLocationName) {
		this.supplierLocationName = supplierLocationName;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSupplierImage() {
		return supplierImage;
	}

	public void setSupplierImage(String supplierImage) {
		this.supplierImage = supplierImage;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public boolean isProductChecked() {
		return isProductChecked;
	}

	public void setProductChecked(boolean isProductChecked) {
		this.isProductChecked = isProductChecked;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFavourite() {
		return isFavourite;
	}

	public void setFavourite(String isFavourite) {
		this.isFavourite = isFavourite;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName
	 *            the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the countryId
	 */
	public String getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId
	 *            the countryId to set
	 */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName
	 *            the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	private String stateId;

	/**
	 * @return the stateId
	 */
	public String getStateId() {
		return stateId;
	}

	/**
	 * @param stateId
	 *            the stateId to set
	 */
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param stateName
	 *            the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getDateTime() {
		return dateTime;
	}

	private String colorName;
	private String dateTime;

}

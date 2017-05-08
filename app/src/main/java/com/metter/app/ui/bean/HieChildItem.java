package com.metter.app.ui.bean;

public class HieChildItem {
	private String title;//
	private int markerImgId;//
	
	public HieChildItem(String title, int markerImgId)
	{
		this.title = title;
		this.markerImgId = markerImgId;
		
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getMarkerImgId() {
		return markerImgId;
	}
	public void setMarkerImgId(int markerImgId) {
		this.markerImgId = markerImgId;
	}
	

}

package com.example.springboot1.pojo;

import java.util.List;

/**
 * 该类用于存储分页信息
 * 1.数据  （用户信息、商品信息等  多种多样的信息）
 * 2.当前页码
 * 3.最大页码
 * @author LC
 *
 */
public class PageInfo<T> {
	private List<T> datas;
	private int currentPage;
	private int maxPage;
	public PageInfo() {
		super();
	}
	public PageInfo(List<T> datas, int currentPage, int maxPage) {
		super();
		this.datas = datas;
		this.currentPage = currentPage;
		this.maxPage = maxPage;
	}
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	@Override
	public String toString() {
		return "PageInfo [datas=" + datas + ", currentPage=" + currentPage + ", maxPage=" + maxPage + "]";
	}
	
	

}

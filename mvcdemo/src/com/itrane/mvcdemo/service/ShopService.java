package com.itrane.mvcdemo.service;

import java.util.List;

import com.itrane.mvcdemo.model.Shop;
import com.itrane.mvcdemo.model.ShopNotFound;

public interface ShopService {
	
	public Shop create(Shop shop);
	public List<Shop> create(Iterable<Shop> shops);
	public Shop delete(long id) throws ShopNotFound;
	public List<Shop> findAll();
	public List<Shop> findAll(int pageIndex, int rowsOfPage);
	public List<Shop> findByPage(int pageNum, int pageSize, String sortDir, String... cols);
	public long countShops();
	public Shop update(Shop shop) throws ShopNotFound;
	public Shop findById(long id);
	public void deleteAll();
}

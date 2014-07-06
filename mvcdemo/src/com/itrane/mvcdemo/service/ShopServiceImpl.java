package com.itrane.mvcdemo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itrane.mvcdemo.model.Shop;
import com.itrane.mvcdemo.model.ShopNotFound;
import com.itrane.mvcdemo.repo.ShopRepository;

@Service
public class ShopServiceImpl implements ShopService {
	
	@Resource
	private ShopRepository shopRepository;
	
	public ShopServiceImpl() {}
	
	public ShopServiceImpl(ShopRepository repo) {
		this.shopRepository = repo;
	}

	@Override
	@Transactional
	public Shop create(Shop shop) {
		Shop createdShop = shop;
		return shopRepository.save(createdShop);
	}
	
	@Override
	@Transactional
	public List<Shop> create(Iterable<Shop> shops) {
		return shopRepository.save(shops);
	}
	
	@Override
	public Shop findById(long id) {
		return shopRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public Shop delete(long id) throws ShopNotFound {
		Shop deletedShop = shopRepository.findOne(id);
		
		if (deletedShop == null)
			throw new ShopNotFound();
		
		shopRepository.delete(deletedShop);
		return deletedShop;
	}

	@Override
	public List<Shop> findAll() {
		return shopRepository.findAll();
	}
	
	@Override
	public List<Shop> findAll(int pageIndex, int rowsOfPage) {
		Page<Shop> shopPage = shopRepository.findAll(new PageRequest(pageIndex, rowsOfPage));
		return shopPage.getContent();
	}
	
	@Override
	public List<Shop> findByPage(int pageNum, int pageSize, String sortDir, String... cols) {
		Sort.Direction dir = Sort.Direction.ASC;
		if (sortDir.equals("desc")) {
			dir = Sort.Direction.DESC;
		}
		PageRequest request = new PageRequest(pageNum, pageSize, dir, cols);
		Page<Shop> shopPage = shopRepository.findAll(request);
		return shopPage.getContent();
	}
	
	@Override
	public long countShops() {
		return shopRepository.count();
	}

	@Override
	@Transactional(rollbackFor=ShopNotFound.class)
	public Shop update(Shop shop) throws ShopNotFound {
		Shop updatedShop = shopRepository.findOne(shop.getId());
		
		if (updatedShop == null)
			throw new ShopNotFound();
		
		updatedShop.setName(shop.getName());
		updatedShop.setEmplNumber(shop.getEmplNumber());
		return updatedShop;
	}

	@Override
	@Transactional
	public void deleteAll() {
		shopRepository.deleteAll();
	}
}

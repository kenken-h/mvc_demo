package com.itrane.mvcdemo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.itrane.mvcdemo.model.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="delete from shops", nativeQuery=true)
	public void deleteAll();
	
	public List<Shop> findByName(String searchStr);
	public List<Shop> findByNameLike(String searchStr);
	
	public List<Shop> findByPhone(String searchStr);
	public List<Shop> findByPhoneLike(String searchStr);
	
	public List<Shop> findByEmail(String searchStr);
	public List<Shop> findByEmailLike(String searchStr);

	public List<Shop> findByKaitenBi(String searchStr);
	public List<Shop> findByKaitenBiLike(String searchStr);
	
	public List<Shop> findByEmplNumber(Integer searchInt);
	public List<Shop> findByEmplNumberGreaterThan(Integer searchInt);
	
	
}

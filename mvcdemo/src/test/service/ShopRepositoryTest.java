package test.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import test.config.TestDbConfig;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.itrane.mvcdemo.model.Shop;
import com.itrane.mvcdemo.repo.ShopRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDbConfig.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
@DatabaseSetup("shopData.xml")
public class ShopRepositoryTest {
	
	@Autowired
	private ShopRepository repo;
	
	@Test
	public void testFindAll() {
		System.out.println("testFindAll--------");
		List<Shop> shops = repo.findAll();
		for(Shop shop: shops) {
			System.out.println(shop.toString());
		}
		assertThat(shops.size(), is(12));
	}
	@Test
	public void testCount() {
		System.out.println("testCount--------");
		long count = repo.count();
		assertThat(count, is(12L));
	}
	
	@Test
	public void testFindByName() {
		System.out.println("testFindByName----------");
		List<Shop> shops = repo.findByName("店舗名００１");
		assertThat(shops.size(), is(1));
		assertThat(shops.get(0).getName(), is("店舗名００１"));
	}
	@Test
	public void testFindByNameNotFound() {
		System.out.println("testFindByNameNotFound----------");
		List<Shop> shops = repo.findByName("店舗名００１x");
		assertThat(shops.size(), is(0));
	}
	@Test
	public void testFindByNameLike() {
		System.out.println("testFindByNameLike----------");
		List<Shop> shops = repo.findByNameLike("店舗名%");
		assertThat(shops.size(), is(12));
		shops = repo.findByNameLike("店舗名００%");
		assertThat(shops.size(), is(9));
	}
	
	@Test
	public void testFindByEmplNumber() {
		System.out.println("testFindByEmplNumber----------");
		List<Shop> shops = repo.findByEmplNumber(5);
		assertThat(shops.size(), is(1));
		assertThat(shops.get(0).getEmplNumber(), is(5));
	}
	@Test
	public void testFindByEmplNumberNotFound() {
		System.out.println("testFindByEmplNumberNotFound----------");
		List<Shop> shops = repo.findByEmplNumber(15);
		assertThat(shops.size(), is(0));
		shops = repo.findByEmplNumber(2);
		assertThat(shops.size(), is(0));
	}
	@Test
	public void testFindByEmplNumberGT() {
		System.out.println("testFindByEmplNumberGT----------");
		List<Shop> shops = repo.findByEmplNumberGreaterThan(3);
		assertThat(shops.size(), is(12));
		shops = repo.findByEmplNumberGreaterThan(5);
		assertThat(shops.size(), is(11));
		shops = repo.findByEmplNumberGreaterThan(6);
		assertThat(shops.size(), is(4));
	}
	@Test
	public void testFindByEmplNumberGTNotFound() {
		System.out.println("testFindByEmplNumberGTNotFound---------");
		List<Shop> shops = repo.findByEmplNumberGreaterThan(7);
		assertThat(shops.size(), is(0));
	}
	
}

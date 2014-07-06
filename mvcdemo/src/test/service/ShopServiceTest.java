package test.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
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
import com.itrane.mvcdemo.service.ShopService;
import com.itrane.mvcdemo.service.ShopServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDbConfig.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
@DatabaseSetup("shopData.xml")
public class ShopServiceTest {
	
	@Autowired
	private ShopRepository repo;
	
	private ShopService service;
	
	@Before
	public void setUp() {
		service = new ShopServiceImpl(repo);
	}
	
	@Test
	public void testFindByPageOrderByIdAsc() {
		System.out.println("-------------testFindByPageOrderByIdAsc");
		List<Shop> shops = service.findByPage(0, 5, "asc", new String[] {"id"});
		assertThat(shops.size(), is(5));
		for(Shop shop: shops) {
			System.out.println(shop.toString());
		}
		shops = service.findByPage(1, 5, "asc", new String[] {"id"});
		assertThat(shops.size(), is(5));
		for(Shop shop: shops) {
			System.out.println(shop.toString());
		}
		shops = service.findByPage(2, 5, "asc", new String[] {"id"});
		assertThat(shops.size(), is(2));
		for(Shop shop: shops) {
			System.out.println(shop.toString());
		}
	}

	@Test
	public void testFindByPageOrderByIdDesc() {
		System.out.println("-------------testFindByPageOrderByIdDesc");
		List<Shop> shops = service.findByPage(0, 5, "desc", new String[] {"id"});
		assertThat(shops.size(), is(5));
		for(Shop shop: shops) {
			System.out.println(shop.toString());
		}
		shops = service.findByPage(1, 5, "desc", new String[] {"id"});
		assertThat(shops.size(), is(5));
		for(Shop shop: shops) {
			System.out.println(shop.toString());
		}
		shops = service.findByPage(2, 5, "desc", new String[] {"id"});
		assertThat(shops.size(), is(2));
		for(Shop shop: shops) {
			System.out.println(shop.toString());
		}
	}

	@Test
	public void testFindByPageOrderByNameAsc() {
		System.out.println("-------------testFindByPageOrderByNameAsc");
		List<Shop> shops = service.findByPage(0, 5, "asc", new String[] {"name"});
		assertThat(shops.size(), is(5));
		for(Shop shop: shops) {
			System.out.println(shop.toString());
		}
		shops = service.findByPage(1, 5, "asc", new String[] {"name"});
		assertThat(shops.size(), is(5));
		for(Shop shop: shops) {
			System.out.println(shop.toString());
		}
		shops = service.findByPage(2, 5, "asc", new String[] {"name"});
		assertThat(shops.size(), is(2));
		for(Shop shop: shops) {
			System.out.println(shop.toString());
		}
	}

	@Test
	public void testFindByPageOrderByNameDesc() {
		System.out.println("-------------testFindByPageOrderByNameAsc");
		List<Shop> shops = service.findByPage(0, 5, "desc", new String[] {"name"});
		assertThat(shops.size(), is(5));
		for(Shop shop: shops) {
			System.out.println(shop.toString());
		}
		shops = service.findByPage(1, 5, "desc", new String[] {"name"});
		assertThat(shops.size(), is(5));
		for(Shop shop: shops) {
			System.out.println(shop.toString());
		}
		shops = service.findByPage(2, 5, "desc", new String[] {"name"});
		assertThat(shops.size(), is(2));
		for(Shop shop: shops) {
			System.out.println(shop.toString());
		}
	}
}

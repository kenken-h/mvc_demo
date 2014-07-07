package com.itrane.mvcdemo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itrane.mvcdemo.model.DataTableObject;
import com.itrane.mvcdemo.model.Shop;
import com.itrane.mvcdemo.service.ShopService;

@Controller
@RequestMapping("/shop")
public class ShopController {
	
	final static private Logger logger = LoggerFactory.getLogger(ShopController.class);
	
	@Autowired
	private ShopService shopService;

	/**
	 * 店舗一覧ビューを表示する.
	 * @return ModelAndView: 表示ビューは shop_list.html
	 */
	@RequestMapping(value = "/list")
	public ModelAndView shopList() {
		logger.debug("");
		ModelAndView mav = new ModelAndView("/shop/shop_list");
		return mav;
	}

	/**
	 * 店舗一覧ビューの dataTables で指定ページのデータを取得する.
	 * @param HttpServletRequest: dataTables の表示ページ、表示件数、ソート列等を取得。
	 * @return String: DataTableObject<Shop> の JSon形式
	 */
	@RequestMapping(value = "/page", produces="text/html;charset=UTF-8")
	public @ResponseBody String getShopPage(HttpServletRequest request) {
	    Map<String, String[]> params = request.getParameterMap();
	    
	    String sortCol = params.get("mDataProp_"
	    		+ getParam("iSortCol_0", params, 0))[0];
	    String sortDir = getParam("sSortDir_0", params, "");

	    int iDisplayLength = getParam("iDisplayLength", params, 10);
		int pageNum = getParam("iDisplayStart", params, 0) / iDisplayLength;
		logger.debug("sortCol="+sortCol+", sortDir=" + sortDir 
				+ ", page=" + pageNum + ", size=" + iDisplayLength);

		List<Shop> shopList = shopService.findByPage(pageNum, 
				iDisplayLength, sortDir, new String[] {sortCol} );

		long total = shopService.countShops();

		DataTableObject<Shop> dt = new DataTableObject<Shop>();
		dt.setAaData(shopList);
		dt.setiTotalDisplayRecords((int)total);
		dt.setiTotalRecords(total);
		dt.setsEcho(getParam("sEcho", params, 0));
		return toJson(dt);
	}
	
	private int getParam(String key, Map<String, String[]> params, int def) {
		String[] vals = params.get(key);
		if (vals==null) {
			return def;
		} else {
			return Integer.valueOf(vals[0]);
		}
	}
	private String getParam(String key, Map<String, String[]> params, String def) {
		String[] vals = params.get(key);
		if (vals==null) {
			return def;
		} else {
			return vals[0];
		}
	}
	
	private String toJson(Object dt){
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(dt);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 店舗テーブルの全データを削除する.
	 * @return ModelAndView: 表示ビューは shop_list.html
	 */
	@RequestMapping(value="/deleteAll", method=RequestMethod.GET)
	public ModelAndView delAll() {
		logger.debug("");
		ModelAndView mav = new ModelAndView("/shop/shop_list");
		shopService.deleteAll();
		return mav;
	}

	/**
	 * 店舗テーブルに1000件のデータを追加する.
	 * @return ModelAndView: 表示ビューは shop_list.html
	 */
	@RequestMapping(value="/add1000", method=RequestMethod.GET)
	public ModelAndView add1000() {
		logger.debug("");
		ModelAndView mav = new ModelAndView("/shop/shop_list");

		//1000件追加処理
		long cnt = shopService.countShops();
		List<Shop> shops = new ArrayList<Shop>(1000);
		for (long i=cnt+1; i<=cnt+1000; i++) {
			Shop s = new Shop();
			s.setName("店舗" + (10000000 + i));
			s.setPhone("03" + (10000000 + i));
			s.setEmail("tenpo" + (10000000 +i) + "@mvc.com");
			s.setKaitenBi("2014/01/01");
			int n = (int)((i - cnt) % 80) + 1;
			s.setEmplNumber(10+80%n);
			shops.add(s);
		}
		shopService.create(shops);
		return mav;
	}
}

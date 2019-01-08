package com.basic.endpoint.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.basic.app.model.Response;
import com.basic.app.service.ApiService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="Web Service", value="v2")
@RestController
@RequestMapping("/api")
public class AppApiContoller {

	String gString = "";

	@Autowired ApiService apiService;
	
	@ApiOperation(value="WS101 get Province List", notes="get Province List")
	@RequestMapping(method=RequestMethod.GET, value="/getProvinceList")
	public Response<List<Map<String, Object>>> getProvinceList(){
		Response<List<Map<String, Object>>> result = new Response<List<Map<String, Object>>>();
		
		//success
		result = apiService.getProvinceList();
		
		return result;
	}
}

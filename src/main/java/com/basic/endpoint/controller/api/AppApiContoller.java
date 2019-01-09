package com.basic.endpoint.controller.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.basic.app.model.Response;
import com.basic.app.security.CustomAuthenticationProvider;
import com.basic.app.security.JwtProperties;
import com.basic.app.security.JwtTokenRequest;
import com.basic.app.security.TokenAuthenticationService;
import com.basic.app.service.ApiService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="Web Service", value="v2")
@RestController
@RequestMapping("/api")
public class AppApiContoller {


	private JwtProperties jwtProperties = new JwtProperties();
	
	@Autowired ApiService apiService;
	@Autowired CustomAuthenticationProvider customAuthenProvider;
	private TokenAuthenticationService tokenAuthenticationService = new TokenAuthenticationService();
	
	@ApiOperation(value="WS101 get Province List", notes="get Province List")
	@RequestMapping(method=RequestMethod.GET, value="/getProvinceList")
	public Response<List<Map<String, Object>>> getProvinceList(){
		Response<List<Map<String, Object>>> result = new Response<List<Map<String, Object>>>();
		
		//success
		result = apiService.getProvinceList();
		
		return result;
	}
	
	@ApiOperation(value="Authen", notes="Authen")
	@RequestMapping(method=RequestMethod.POST, value="/authen")
	public void authen(@RequestBody JwtTokenRequest credentials, HttpServletResponse response){
		
		//success
		
		TextEncryptor encrypted = Encryptors.queryableText(jwtProperties.getJwtSecret(), jwtProperties.getSalt());
		credentials.setPassword(encrypted.encrypt(credentials.getPassword()));
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getUsername(),
				credentials.getPassword());
		Authentication authen = customAuthenProvider.authenticate(token);
		tokenAuthenticationService.generateToken(response, authen.getName(), authen);
		
		
	}
}

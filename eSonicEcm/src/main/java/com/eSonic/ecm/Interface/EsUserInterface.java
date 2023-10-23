package com.eSonic.ecm.Interface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.eSonic.ecm.domain.EsResultDTO;
import com.eSonic.ecm.service.EsUserService;


@Controller
@RequestMapping(value = "/interface/user")
public class EsUserInterface {
	
	private final EsUserService esUserService;

	@Autowired
	public EsUserInterface(EsUserService esUserService) {
		this.esUserService = esUserService;
	}
	
	
	@GetMapping("/search/one/{esUserId}")
	@ResponseBody
	public  EsResultDTO getUser(@PathVariable String esUserId,  SessionStatus status) throws Exception {

	    EsResultDTO esResultDTO = esUserService.getUser(esUserId);

	    return esResultDTO;
	}
}

package com.eSonic.ecm.Interface;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.eSonic.ecm.VO.EsResultVO;
import com.eSonic.ecm.VO.EsVolumeVO;
import com.eSonic.ecm.service.EsVolumeService;


@Controller
@RequestMapping(value = "/interface/volume")
public class EsVolumeInterface {
	
	private final EsVolumeService esVolumeService;

	@Autowired
	public EsVolumeInterface(EsVolumeService esVolumeService) {
		this.esVolumeService = esVolumeService;
	}
	
	
	@GetMapping("/search/usedvol/{esArchiveId}/{esFileSize}")
	@ResponseBody
	public  EsResultVO getUsedVolume(@PathVariable String esArchiveId,@PathVariable String esFileSize,  SessionStatus status) throws Exception {
		
		EsVolumeVO esVolumeVO = new EsVolumeVO();
		esVolumeVO.setEsArchiveId(esArchiveId);
		esVolumeVO.setEsFileSize(esFileSize);

	    return esVolumeService.getUsedVolume(esVolumeVO);
	    
	    
	}
	
	@GetMapping("/update/{esVolumeId}/{esCreateFileSize}/{esRemoveFileSize}")
	@ResponseBody
	public  EsResultVO updateVolume(@PathVariable String esVolumeId,@PathVariable String esCreateFileSize,@PathVariable String esRemoveFileSize,  SessionStatus status) throws Exception {
		
		
		
		EsVolumeVO esVolumeVO = new EsVolumeVO();
		esVolumeVO.setEsVolumeId(esVolumeId);
		esVolumeVO.setEsCreateFileSize(esCreateFileSize);
		esVolumeVO.setEsRemoveFileSize(esRemoveFileSize);
	    return esVolumeService.updateVolume(esVolumeVO);
	    
	    
	}
	@GetMapping("/updaterpc/{esVolumeId}/{esUpdateFileSize}/{esContentId}")
	@ResponseBody
	public  EsResultVO updateVolumeReplace(@PathVariable String esVolumeId,@PathVariable String esUpdateFileSize,@PathVariable String esContentId,  SessionStatus status) throws Exception {
		
		
		
		EsVolumeVO esVolumeVO = new EsVolumeVO();
		esVolumeVO.setEsVolumeId(esVolumeId);
		esVolumeVO.setEsCreateFileSize(esUpdateFileSize);

		esVolumeVO.setEsContentId(esContentId);
		
		
	    return esVolumeService.updateVolumeReplace(esVolumeVO);
	    
	    
	}
	
	
}

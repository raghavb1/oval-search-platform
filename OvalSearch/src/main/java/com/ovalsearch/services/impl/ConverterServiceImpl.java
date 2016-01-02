/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ovalsearch.base.ApplicationsSro;
import com.ovalsearch.entity.Applications;
import com.ovalsearch.services.IConverterService;

/**
 * @version 1.0, 23-Dec-2015
 * @author deepanshu
 */
@Service
public class ConverterServiceImpl implements IConverterService {

	@Override
	public List<ApplicationsSro> getSroFromEntity(List<Applications> applications) {
		List<ApplicationsSro> sros = null;
		if (applications != null && applications.size() > 0) {
			sros = new ArrayList<ApplicationsSro>();
			for (Applications application : applications) {
				ApplicationsSro sro = getApplicationSroFromEntity(application);
				sros.add(sro);
			}
		}
		return sros;
	}

	@Override
	public ApplicationsSro getApplicationSroFromEntity(Applications application) {

		ApplicationsSro sro = new ApplicationsSro();
		if(application != null){
			sro.setCategory(application.getCategory());
			sro.setDownloads(application.getDownloads());
			sro.setIcon(application.getIcon());
			sro.setIconHd(application.getIconHd());
			sro.setName(application.getName());
			sro.setPath(application.getPath());
			sro.setRating(application.getRating());
			sro.setSize(application.getSize());
			sro.setApkid(application.getApkId());
		}

		return sro;
	}

}

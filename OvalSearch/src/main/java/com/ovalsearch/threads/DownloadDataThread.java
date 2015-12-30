/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */  
package com.ovalsearch.threads;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ovalsearch.dao.IEntityDao;
import com.ovalsearch.entity.Applications;

/**
 *  
 *  @version     1.0, 21-Dec-2015
 *  @author deepanshu
 */
public class DownloadDataThread implements Runnable {

	private String FILENAME = "information.xml";
	private String REMOTE_REPO = "http://apps.store.aptoide.com/info.xml";
	
	@Autowired
	private IEntityDao entityDAO;
	
    @Override
    public void run() {
    	downloadFile();
    	parseXML();
    }
    
    private void downloadFile(){
        // TODO Auto-generated method stub
    	URL website;
		try {
			website = new URL(REMOTE_REPO);
	    	ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	    	FileOutputStream fos = new FileOutputStream(FILENAME);
	    	fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	    	fos.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void parseXML(){
        try {	
            File inputFile = new File(FILENAME);
            DocumentBuilderFactory dbFactory 
               = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" 
               + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("package");
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
               Node nNode = nList.item(temp);
               Applications applications = new Applications();
               if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                  Element eElement = (Element) nNode;
                  applications.setCategory(getStringFromXML(eElement,"catg"));
                  applications.setDownloads(Integer.parseInt(getStringFromXML(eElement,"dwn")));
                  applications.setIcon(getStringFromXML(eElement,"icon"));
                  applications.setIconHd(getStringFromXML(eElement,"icon_hd"));
                  applications.setName(getStringFromXML(eElement,"name"));
                  applications.setPath(getStringFromXML(eElement,"path"));
                  applications.setRating(Float.parseFloat(getStringFromXML(eElement,"rat")));
                  applications.setSize(Integer.parseInt(getStringFromXML(eElement,"sz")));
                  
                  entityDAO.saveOrUpdate(applications);
               }
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
    }
    
    private String getStringFromXML(Element eElement, String tag){
    	
    	String output = null;
    	NodeList nodelist = eElement.getElementsByTagName(tag);
    	if(nodelist != null){
    		output = nodelist.item(0).getTextContent();
    	}
    	
    	return output;
    	
    }

}

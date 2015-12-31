/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.threads;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ovalsearch.dao.IEntityDao;
import com.ovalsearch.entity.Applications;

/**
 * @version 1.0, 21-Dec-2015
 * @author deepanshu
 */
public class DownloadDataThread implements Runnable {

    private String              FILENAME    = "information.xml";
    private String              REMOTE_REPO = "http://apps.store.aptoide.com/info.xml";

    private IEntityDao          entityDAO;

    private static final Logger LOG         = LoggerFactory.getLogger(DownloadDataThread.class);

    public DownloadDataThread(IEntityDao entityDAO) {
        super();
        this.entityDAO = entityDAO;
    }

    @Override
    public void run() {
        LOG.info("Thread Called");
        try {
            downloadFile();
            parseXML();
        } catch (IOException e) {
            LOG.error("Exception ", e);
        }

    }

    
    private void downloadFile() throws MalformedURLException, IOException {        
        
    	URL url = new URL(REMOTE_REPO);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
 
        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

 
            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + FILENAME);
 
            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = FILENAME;
             
            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
 
            int bytesRead = -1;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
 
            outputStream.close();
            inputStream.close();
 
            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }


    private void parseXML() {
        try {
            File inputFile = new File(FILENAME);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            LOG.info("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("package");
            LOG.info("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Applications applications = new Applications();
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    applications.setCategory(getStringFromXML(eElement, "catg"));
                    applications.setDownloads(Integer.parseInt(getStringFromXML(eElement, "dwn")));
                    applications.setIcon(getStringFromXML(eElement, "icon"));
                    applications.setIconHd(getStringFromXML(eElement, "icon_hd"));
                    applications.setName(getStringFromXML(eElement, "name"));
                    applications.setPath(getStringFromXML(eElement, "path"));
                    applications.setRating(Float.parseFloat(getStringFromXML(eElement, "rat")));
                    applications.setSize(Integer.parseInt(getStringFromXML(eElement, "sz")));

                    entityDAO.saveOrUpdate(applications);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception ", e);
        }
    }

    private String getStringFromXML(Element eElement, String tag) {

        String output = null;
        NodeList nodelist = eElement.getElementsByTagName(tag);
        if (nodelist != null) {
            output = nodelist.item(0).getTextContent();
        }
        return output;
    }

}

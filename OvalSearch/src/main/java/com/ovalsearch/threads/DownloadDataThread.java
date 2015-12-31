/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.threads;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ovalsearch.dao.IEntityDao;
import com.ovalsearch.das.IApplicationsDas;
import com.ovalsearch.entity.Applications;

/**
 * @version 1.0, 21-Dec-2015
 * @author deepanshu
 */
public class DownloadDataThread implements Runnable {

    private String              fileName;
    private String              remoteRepo;
    private IEntityDao          entityDAO;
    private IApplicationsDas    applicationsDas;

    private static final Logger LOG = LoggerFactory.getLogger(DownloadDataThread.class);

    public DownloadDataThread(String fileName, String remoteRepo, IEntityDao entityDAO, IApplicationsDas applicationsDas) {
        super();
        this.fileName = fileName;
        this.remoteRepo = remoteRepo;
        this.entityDAO = entityDAO;
        this.applicationsDas = applicationsDas;
    }

    @Override
    public void run() {
        try {
            LOG.info("Starting to download file from source");
            downloadFile();
            LOG.info("File downloaded successfully");
            parseXML();
            LOG.info("File processed successfully");
        } catch (IOException e) {
            LOG.error("Exception ", e);
        }

    }

    private void downloadFile() throws MalformedURLException, IOException {

        URL url = null;
        URLConnection con = null;
        int i;
        try {
            url = new URL(remoteRepo);
            con = url.openConnection();
            File file = new File(fileName);
            BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file.getName()));
            while ((i = bis.read()) != -1) {
                bos.write(i);
            }
            bos.flush();
            bos.close();
            bis.close();
        } catch (MalformedInputException malformedInputException) {
            LOG.error("Exception ", malformedInputException);
        } catch (IOException ioException) {
            LOG.error("Exception ", ioException);
        }
    }

    private void parseXML() {
        try {
            File inputFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            LOG.info("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("package");
            LOG.info("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Applications applications = null;
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    applications = applicationsDas.getApplicationByApkId(getStringFromXML(eElement, "apkid"));
                    if (applications == null) {
                        applications = new Applications();
                    }
                    applications.setCategory(getStringFromXML(eElement, "catg"));
                    applications.setDownloads(Integer.parseInt(getStringFromXML(eElement, "dwn")));
                    applications.setIcon(getStringFromXML(eElement, "icon"));
                    applications.setIconHd(getStringFromXML(eElement, "icon_hd"));
                    applications.setName(getStringFromXML(eElement, "name"));
                    applications.setPath(getStringFromXML(eElement, "path"));
                    applications.setRating(Float.parseFloat(getStringFromXML(eElement, "rat")));
                    applications.setSize(Integer.parseInt(getStringFromXML(eElement, "sz")));
                    applications.setApkId(getStringFromXML(eElement, "apkid"));
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
            if (nodelist.item(0) != null) {
                output = nodelist.item(0).getTextContent();
            }
        }
        return output;
    }

}

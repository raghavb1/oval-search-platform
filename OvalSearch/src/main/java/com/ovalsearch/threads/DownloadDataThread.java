/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ovalsearch.threads;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(REMOTE_REPO).openStream());
            fout = new FileOutputStream(FILENAME);

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
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

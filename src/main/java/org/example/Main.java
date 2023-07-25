package org.example;

import org.example.Entities.Currency;
import org.example.httpRequest.CentralBankConnection;
import org.example.parser.CurrencyParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        CentralBankConnection cb = new CentralBankConnection();
//       HttpResponse resp =  cb.getBodyFromRequest("http://www.cbr.ru/scripts/XML_daily.asp?date_req=02/03/2002");
//       File xml = cb.convertStringToXMLDocument(resp);
//        CurrencyParser currencyParser = new CurrencyParser();
//        SAXParserFactory factory = SAXParserFactory.newInstance();
//
//        try {
//            SAXParser parser = factory.newSAXParser();
//            parser.parse(xml, currencyParser);
//        }
//        catch (ParserConfigurationException | SAXException | IOException e){
//            e.printStackTrace();
//        }
//        List<Currency> list = currencyParser.getCurrencyList();
//        System.out.println();
        CurrencyConsoleInterface currencyConsoleInterface = new CurrencyConsoleInterface();
        currencyConsoleInterface.initCommand();

    }
}
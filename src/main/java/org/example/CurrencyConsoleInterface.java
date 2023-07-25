package org.example;

import org.example.Entities.Currency;
import org.example.httpRequest.CentralBankConnection;
import org.example.parser.CurrencyParser;
import org.example.utils.OperationType;
import org.xml.sax.SAXException;

import javax.xml.crypto.Data;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyConsoleInterface {
    private String baseUrl = "http://www.cbr.ru/scripts/XML_daily.asp?";
    private String type;
    private String code;
    private LocalDate date;

    public void parseCurrencyRatesForDate(){
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        Pattern pattern = Pattern.compile("(\\S+) --code=(\\S+) --date=(\\d{4}-\\d{2}-\\d{2})");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            this.type = matcher.group(1);
            this.code = matcher.group(2);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.date = LocalDate.parse(matcher.group(3), formatter);

        } else {
            System.out.println("Ошибка ввода параметров функции");
        }

        StringBuilder sb = new StringBuilder(baseUrl);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);
        sb.append("date_req="+formattedDate);

        CentralBankConnection cb = new CentralBankConnection();
        HttpResponse resp =  cb.getBodyFromRequest(sb.toString());
        File xml = cb.convertStringToXMLDocument(resp);
        CurrencyParser currencyParser = new CurrencyParser();
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(xml, currencyParser);
        }
        catch (ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace();
        }
        List<Currency> list = currencyParser.getCurrencyList();

        for (int i = 0; i < list.size(); i++){
            if(code != null && list.get(i).getCharCode().equals(code)){
                System.out.println(list.get(i).toString());
                break;
            }
        }

    }
}

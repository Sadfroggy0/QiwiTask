package org.example;

import org.example.Entities.Currency;
import org.example.httpRequest.CentralBankConnection;
import org.example.parser.CurrencyParser;
import org.example.utils.OperationType;
import org.xml.sax.SAXException;
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


    public void init(){
        //разбить по типу команды
        if(type.equals(OperationType.CurrencyRates.name())){

        }
    }
    public void parseCurrencyRatesForDate(){
        System.out.println("Введите комманду для поиска валюты в формате: currency_rates --code=USD --date=2022-10-08");
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

        //формируем строку для нового HTTP запроса из переданных в команде параметров
        StringBuilder sb = new StringBuilder(baseUrl);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);
        sb.append("date_req="+formattedDate);

        CentralBankConnection cb = new CentralBankConnection(); //создание http запроса
        HttpResponse resp =  cb.getBodyFromRequest(sb.toString()); //получение объекта httpresponse
        File xml = cb.convertStringToXMLDocument(resp); // httpresponse -> xml

        CurrencyParser currencyParser = new CurrencyParser(); //парсинг XML на объекты
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(xml, currencyParser);
        }
        catch (ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace();
        }
        List<Currency> list = currencyParser.getCurrencyList(); //список объектов

        boolean currencyFound = false;
        for (int i = 0; i < list.size(); i++){
            if(code != null && list.get(i).getCharCode().equals(code)){
                System.out.println(list.get(i).toString());
                currencyFound = true;
            }
        }
        if(!currencyFound){
            System.out.println("Не могу найти валюту. Вот весь список валют: ");
            for (int i = 0; i< list.size(); i++){
                System.out.println(list.get(i).toString());
            }
        }

    }
}

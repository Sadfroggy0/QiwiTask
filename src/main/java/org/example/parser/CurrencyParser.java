package org.example.parser;

import org.example.Entities.Currency;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyParser extends DefaultHandler {
    private static final String VAL_CURS ="ValCurs";
    private static final String VALUTE_ID = "Valute";
    private static final String NUM_CODE = "NumCode";
    private static final String CHAR_CODE = "CharCode";
    private static final String NOMINAL = "Nominal";
    private static final String NAME = "Name";
    private static final String VALUE = "Value";
    private List<Currency> currencyList;
    private String data;


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data = new String(ch,start,length);
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName){
            case VAL_CURS:
                currencyList = new ArrayList<>();
                break;
            case VALUTE_ID:
                currencyList.add(new Currency());
                break;
            case NUM_CODE:
                data = "";
                break;
            case CHAR_CODE:
                data = "";
                break;
            case NOMINAL:
                data = "";
                break;
            case NAME:
                data = "";
                break;
            case VALUE:
                data = "";
                break;

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (currencyList.size() > 0 ) {
            switch (qName) {
                case NUM_CODE:
                    getCurrentCurrency().setNumCode(data);
                    break;
                case CHAR_CODE:
                    getCurrentCurrency().setCharCode(data);
                    break;
                case NOMINAL:
                    getCurrentCurrency().setNominal(Integer.parseInt(data));
                    break;
                case NAME:
                    try {
                        byte[] unparsedBytes = data.getBytes("Windows-1251");
                        String utf8String = new String(unparsedBytes, "UTF-8");
                        getCurrentCurrency().setName(utf8String);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case VALUE:
                    getCurrentCurrency().setValue(Double.parseDouble(data.replace(",",".")));
                    break;
            }
        }
    }

    private Currency getCurrentCurrency() {
        List<Currency> list = this.currencyList;
        int index = list.size() - 1;
        return list.get(index);
    }

    public List<Currency> getCurrencyList() {
        return this.currencyList;
    }
}

package org.example;

import org.example.utils.OperationType;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyConsoleInterface {
    private String type;
    private String code;
    private LocalDate date;
    public void initCommand(){
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

    }
}

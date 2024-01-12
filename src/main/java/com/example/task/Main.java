package com.example.task;

import com.example.task.client.ApacheHttpClientApp;
import com.example.task.dbconnector.PostgreSQLJDBC;
import com.example.task.domain.Content;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
  public static void main(String[] args) {
    try {
      System.out.println("Введите даты lastUpdateFrom и lastUpdateTo в формате dd.MM.yyyy");
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      String[] date = reader.readLine().split(" ");
      SimpleDateFormat s = new SimpleDateFormat("dd.MM.yyyy");

      Date lastUpdateFrom = s.parse(date[0]);
      Date lastUpdateTo = s.parse(date[1]);

      Content[] content = new ApacheHttpClientApp().invoke(date[0], date[1]);
      new PostgreSQLJDBC().init(content, lastUpdateFrom.getTime(), lastUpdateTo.getTime());
    } catch (IOException e) {
      System.out.println("Неверный формат ввода");
    } catch (ParseException e) {
      System.out.println("Неверный формат даты");
    }
  }
}

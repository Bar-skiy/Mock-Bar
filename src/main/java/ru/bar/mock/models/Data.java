package ru.bar.mock.models;

import org.apache.commons.lang.RandomStringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Data {

  //   Генерация случайных значений
  String randomMoney = RandomStringUtils.randomNumeric(4) + "," + RandomStringUtils.randomNumeric(2) + " руб.";
  String randomMail = RandomStringUtils.randomAlphanumeric(10) + "@mail.ru";
  UUID uuid = UUID.randomUUID();

  //   Установка времени в формате
  LocalDateTime dateTime = LocalDateTime.now();
  DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' // 'HH:mm:ss");
  String lastLogon = dtFormatter.format(dateTime);

  private Request request;


  public String getRandomMoney() {
    return randomMoney;
  }

  public String getRandomMail() {
    return randomMail;
  }

  public UUID getUuid() {
    return uuid;
  }

  public String getLastLogon() {
    return lastLogon;
  }
}

package ru.bar.mock.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.tinylog.Logger;
import ru.bar.mock.models.Data;
import ru.bar.mock.models.Request;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class JSON_Controller {

    @GetMapping(value = "mock")
    public Object getResponse(@RequestParam(value = "id") String id, Data data) throws IOException {

//           Установка начального времени работы метода
        long initialTime = System.currentTimeMillis();

//           Чтение файла шаблона в строку
    String responseBodyTemplate = Files.readString(Paths.get
            ("src\\main\\resources\\templates\\getResponse.json"), StandardCharsets.UTF_8);

//           Подготовка тела ответа
        String responseBody = String.format(responseBodyTemplate, id, data.getUuid(), data.getRandomMoney(),
                data.getRandomMail(), data.getLastLogon());

//           Запись ответа в терминал и время отработки заглушки
        Logger.info(String.format("Время работы метода %s мс. ID аккаунта - %s. UUID ответа - %s.",
                System.currentTimeMillis() - initialTime, id, data.getUuid()));

//           Возврат ответа с параметризованным телом и заголовками
        return ResponseEntity.ok().header("content-type", "application/json").
                body(responseBody);
    }

    @PostMapping(value = "mock")
    public Object postResponse(@RequestBody String requestBody, Data data) throws IOException {

//           Установка начального времени работы метода
        long initialTime = System.currentTimeMillis();

//           Чтение файла шаблона в строку
        String responseBodyTemplate = Files.readString(Paths.get
                ("src\\main\\resources\\templates\\postResponse.json"), StandardCharsets.UTF_8);

        try {
//           Преобразование json в классы
            ObjectMapper objectMapper = new ObjectMapper();
            Request value = objectMapper.readValue(requestBody, Request.class);
//
//           Действия над значениями полученных из json
            int balanceNumeric = value.getDebit() - value.getCredit();
            String money = balanceNumeric + " у.е.";
            int numberOfItem = value.getItems().size();

//           Подготовка тела ответа
            String responseBody = String.format(responseBodyTemplate, value.getId(), data.getUuid(),
                    money, numberOfItem, value.getRegDate(), data.getLastLogon());

//           Запись ответа в терминал и время отработки заглушки
            Logger.info(String.format("Время работы метода %s мс. ID аккаунта - %s. UUID ответа - %s.",
                    System.currentTimeMillis() - initialTime, value.getId(), data.getUuid()));

//           Возврат ответа с параметризованным телом и заголовками
            return ResponseEntity.ok().
                    header("content-type", "application/json").
                    body(responseBody);

        } catch (Exception e) {
//           Запись в терминал сообщения об ошибке и тела запроса, если парсинг json не удался
            Logger.error(String.format("%s\n%s\n%s", "Передан неверный запрос json", e.getMessage(),
                    requestBody));

//           Возврат ответа HTTP со статусом 400 и телом запроса
            return ResponseEntity.badRequest().
                    header("content-type", "application/json").
                    body(String.format("{\"message\": \"Передан неверный запрос json\", \"request\": \"%s\"}",
                            requestBody));
        }
    }
}

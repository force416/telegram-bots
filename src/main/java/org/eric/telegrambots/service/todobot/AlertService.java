package org.eric.telegrambots.service.todobot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.eric.telegrambots.Repository.todobot.AlertRepository;
import org.eric.telegrambots.model.todobot.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service("alertService")
public class AlertService {
    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private TelegramBot telegramBot;

    public Alert addAlert(Alert alert) {
        return alertRepository.save(alert);
    }

    @Transactional
    public void sendAlert() {
        List<Alert> alerts = alertRepository.findAll();
        Date now = new Date();

        alerts.stream().forEach((alert) -> {
            long chatId = alert.getChatId();
            String content = alert.getContent();
            Date alertTime = alert.getAlertTime();

            if (alertTime.before(now)) {
                telegramBot.execute(new SendMessage(chatId, content));
                alertRepository.delete(alert);
            }
        });
    }
}

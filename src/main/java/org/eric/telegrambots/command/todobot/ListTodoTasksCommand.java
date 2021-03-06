package org.eric.telegrambots.command.todobot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.eric.telegrambots.model.todobot.Task;
import org.eric.telegrambots.service.todobot.TaskService;
import org.eric.telegrambots.utils.SpringContext;

import java.util.List;

public class ListTodoTasksCommand extends TodoListBotCommand {
    @Override
    public void run(Update update) {
        long chatId = update.message().chat().id();

        TaskService taskService = SpringContext.getBean(TaskService.class);
        List<Task> tasks = taskService.getTodoTasks(chatId);

        String rep = tasks.stream()
                .map((task) -> {
                    String hash = task.getHash();
                    String content = task.getContent();
                    return String.format("[ %s ] %s  \r\n", hash, content);
                })
                .reduce("", (str, content) -> {
                    return str + content;
                });

        if (rep == null || rep == "") {
            rep = "you don't have todo tasks now, please use /add_task command to add task.";
        }
        this.sendMsg(new SendMessage(chatId, rep)
                .disableWebPagePreview(true));
    }
}

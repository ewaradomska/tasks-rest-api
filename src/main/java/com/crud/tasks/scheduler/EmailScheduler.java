package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    private static final String SUBJECT = "Hello, this is your every-day tasks information.";
    private String message;

    @Autowired
    private SimpleEmailService emailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    @Scheduled(cron = "0 0 10 * * *")
    public void sendEmailWithInfo(){
        long size = taskRepository.count();
        if(size==1){
            message = "You have "+size+" task in your database.";
        } else if(size>1){
            message = "You have "+size+" tasks in your database.";
        } else if(size==0){
            message = "You don't have tasks in your database.";
        }
        emailService.send(new Mail(
                adminConfig.getAdminMail(), SUBJECT, message, ""
        ));

    }
}

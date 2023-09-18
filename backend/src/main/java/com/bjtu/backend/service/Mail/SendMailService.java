package com.bjtu.backend.service.Mail;


import java.util.Map;

public interface SendMailService
{
    Map<String, String> sendMailForRegister(String id);
}

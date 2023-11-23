package com.bjtu.backend.service.Reminder;

import java.util.Map;

public interface ShowReminderListService
{
    Map<String, Object> showReminder(String studentNumber, Long pageNo, Long pageSize);

    Map<String, Object> showRead(String studentNumber, Long pageNo, Long pageSize);

    Map<String, Object> showUnRead(String studentNumber, Long pageNo, Long pageSize);
}

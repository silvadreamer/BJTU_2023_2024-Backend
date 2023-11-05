package com.bjtu.backend.service.OSS;

public interface OSSDeleteService
{
    boolean delete(String fileName);

    boolean deleteHomeworkFile(String prefix, String fileName);
}

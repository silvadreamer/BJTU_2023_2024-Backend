package com.bjtu.backend.service.OSS;

import java.util.Map;
import java.util.Objects;

public interface OSSDownloadService
{
    Map<String, Object> download(String fileName);

    Map<String, Object> downloadHomework(String prefix, String fileName);
}

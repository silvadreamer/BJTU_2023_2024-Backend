package com.bjtu.backend.service.Code;

import com.bjtu.backend.pojo.Submission;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface CodeSubmitService
{
    Map<String, Object> submit(Submission submission) throws ExecutionException, InterruptedException, TimeoutException;
}

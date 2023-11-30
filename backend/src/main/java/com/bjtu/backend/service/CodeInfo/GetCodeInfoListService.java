package com.bjtu.backend.service.CodeInfo;


import java.util.Map;

public interface GetCodeInfoListService
{

    Map<String, Object> getListStudent(Long pageNo, Long pageSize);
}

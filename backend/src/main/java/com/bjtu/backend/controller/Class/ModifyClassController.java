package com.bjtu.backend.controller.Class;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Class.ModifyClassService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/class")
public class ModifyClassController
{
    final ModifyClassService modifyClassService;

    public ModifyClassController(ModifyClassService modifyClassService)
    {
        this.modifyClassService = modifyClassService;
    }

    @PostMapping("/modify")
    public Result<?> modify(@RequestParam int id, @RequestParam String info)
    {
        Map<String, String> map = modifyClassService.modify(id, info);

        return Result.success(map);
    }

}

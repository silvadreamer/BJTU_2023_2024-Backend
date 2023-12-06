package com.bjtu.backend.controller.Similarity;

import com.bjtu.backend.IO.Result;
import com.bjtu.backend.service.Similarity.GetWordsService;
import com.bjtu.backend.service.Similarity.JiebaSimilarityService;
import com.bjtu.backend.service.Similarity.WordsSimilarityService;
import lombok.var;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/similarity")
public class WordsSimilarityController
{
    final GetWordsService getWordsService;
    final WordsSimilarityService wordsSimilarityService;
    final JiebaSimilarityService jiebaSimilarityService;

    public WordsSimilarityController(GetWordsService getWordsService, WordsSimilarityService wordsSimilarityService, JiebaSimilarityService jiebaSimilarityService)
    {
        this.getWordsService = getWordsService;
        this.wordsSimilarityService = wordsSimilarityService;
        this.jiebaSimilarityService = jiebaSimilarityService;
    }

    @PostMapping("/words")
    public Result<?> getWords(int classId, int homeworkId)
    {
        var map = getWordsService.getContent(classId, homeworkId);

        var map2 = wordsSimilarityService.checkSimilarity(classId, homeworkId);

        return Result.success(map2);
    }

    @PostMapping("/jieba")
    public Result<?> jiebaSimilarity(int classId, int homeworkId)
    {
        var map = getWordsService.getContent(classId, homeworkId);

        var map2 = jiebaSimilarityService.jiebaSimilarity(classId, homeworkId);

        return Result.success(map2);
    }

}

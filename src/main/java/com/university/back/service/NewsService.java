package com.university.back.service;


import com.university.back.model.News;
import com.university.back.repository.NewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NewsService {
    private NewsRepository newsRepository;

    public void saveNews(News news) {
        newsRepository.save(news);
    }

    public void deleteNewsById(long id) {
        newsRepository.deleteById(id);
    }
    public List<News> getAllNews(){
        return newsRepository.findAllNews();
    }
}
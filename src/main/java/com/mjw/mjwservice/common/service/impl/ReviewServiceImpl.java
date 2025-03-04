package com.mjw.mjwservice.common.service.impl;

import com.mjw.mjwservice.common.model.Review;
import com.mjw.mjwservice.common.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {


    @Override
    public List<Review> getReviews() {
        final List<List<String>> reviewsData = List.of(
                List.of("Yamini Shrivastava", "5", "Excellent service thank you for help and assistance", "London"),
                List.of("Ajit Naik", "5", "It was a real fantastic Dubai trip Organised by My Journey Wings. We "
                        + "really enjoyed a lot. Full Marks to you & your team. thanks for your help!", "Dubai"),
                List.of("Sudhir Kumar", "5", "Nicely arranged the whole tour of Maldives and timely completed the all"
                        + " documents required for the travel.wish for the bright future.", "Dubai")
        );

        return reviewsData.stream()
                .map(reviewData -> Review.builder()
                        .date(LocalDate.now())
                        .rating(Integer.parseInt(reviewData.get(1)))
                        .username(reviewData.get(0))
                        .comment(reviewData.get(2))
                        .place(reviewData.get(3))
                        .build())
                .collect(Collectors.toList());
    }

}

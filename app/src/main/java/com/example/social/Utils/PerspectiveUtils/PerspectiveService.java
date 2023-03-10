package com.example.social.Utils.PerspectiveUtils;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PerspectiveService {
    @Headers({
            "Content-Type: application/json",
    })
    @POST("https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze?key=AIzaSyCAMDt4_MMnQzeWEEibmxzLIiJj-Xp27Lk")
    Call<AnalyzeCommentResponse> analyzeComment(@Body AnalyzeCommentRequest request);
}
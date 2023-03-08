package com.example.social.Utils.PerspectiveUtils;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PerspectiveService {
    @Headers({
            "Content-Type: application/json",
    })
    @POST("/commentanalyzer.googleapis.com/v1alpha1/comments:analyze?key=AIzaSyAivq0ytBNFns6xVPRMp9500QQcS_vt60Q")
    Call<AnalyzeCommentResponse> analyzeComment(@Body AnalyzeCommentRequest request);
}
package com.example.social.Home;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Post {
    String userIdentity, postContent,userName;
    Timestamp timePosted;


    public Post(String userIdentity, String postContent, String userName, Timestamp timePosted) {
        this.userIdentity = userIdentity;
        this.postContent = postContent;
        this.userName = userName;
        this.timePosted = timePosted;
    }

    public Post() {
    }

    public Timestamp getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(Timestamp timePosted) {
        this.timePosted = timePosted;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

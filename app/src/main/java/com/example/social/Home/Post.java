package com.example.social.Home;

public class Post {
    String userIdentity, postContent;

    public Post(String userIdentity, String postContent) {
        this.userIdentity = userIdentity;
        this.postContent = postContent;
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
}

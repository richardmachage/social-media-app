package com.example.social.Home;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class HomeFragmentViewModel extends ViewModel {


    //TODO this is just a function to generate test data,
    // get rid of it to implement real data from data source
     ArrayList<Post> populateListOfPosts() {
        ArrayList<Post> listOfPosts = new ArrayList<Post>();

        for (int i = 0; i < 20; i++) {
            listOfPosts.add(new Post("@username",
                    "This is just a sample text to see how this ui for the recycler view works."));
        }

        return listOfPosts;
    }
}

package com.example.social.Home;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class HomeFragmentViewModel extends ViewModel {

    public static ArrayList<Post> listOfPosts = new ArrayList<>();

    //TODO this is just a function to generate test data,
    // get rid of it to implement real data from data source

     /*ArrayList<Post> populateListOfPosts() {


        for (int i = 0; i < 10; i++) {
            listOfPosts.add(new Post("@username",
                    "This is just a sample text to see how this ui for the recycler view works."));
        }

        return listOfPosts;
    }*/
}

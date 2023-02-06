package com.example.social.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.social.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    HomeRecyclerAdapter homeRecyclerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initializeViews();
        recyclerView.setAdapter(homeRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void initializeViews() {
        recyclerView = view.findViewById(R.id.homeFragment_recyclerView);
        homeRecyclerAdapter = new HomeRecyclerAdapter(getContext(), populateListOfPosts());
    }


    //TODO this is just a function to generate test data,
    // get rid of it to implement real data from data source
    private ArrayList<Post> populateListOfPosts() {
        ArrayList<Post> listOfPosts = new ArrayList<Post>();

        for (int i = 0; i < 20; i++) {
            listOfPosts.add(new Post("@username",
                    "This is just a sample text to see how this ui for the recycler view works."));
        }

        return listOfPosts;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_fragment_toolbar_items, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addPost_icon:
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_createPostFragment);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home"); //hides the action/toolbar for this specific fragment
    }


}
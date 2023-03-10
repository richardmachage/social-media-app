package com.example.social.Home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.social.R;
import com.example.social.Utils.FirebaseUtils.FirebaseUtils;
import com.example.social.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    HomeRecyclerAdapter homeRecyclerAdapter;

    FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference postsCollection;
    CollectionReference usersCollection;
    private ArrayList<Post> listOfPosts;
    private FragmentHomeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        firebaseAuth = FirebaseUtils.getFirebaseAuthInstance();
        firebaseFirestore = FirebaseUtils.getFirestoreInstance();
        postsCollection = firebaseFirestore.collection("Posts");
        usersCollection = firebaseFirestore.collection("Users");
        //listOfPosts = getListOfPosts();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(getContext()));

        binding.homeFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeRecyclerAdapter = new HomeRecyclerAdapter(getContext(), getListOfPosts());
        binding.homeFragmentRecyclerView.setAdapter(homeRecyclerAdapter);

        binding.swipeToRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getListOfPostsOnRefresh();
                //binding.homeFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.homeFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                homeRecyclerAdapter = new HomeRecyclerAdapter(getContext(), getListOfPostsOnRefresh());
                binding.homeFragmentRecyclerView.setAdapter(homeRecyclerAdapter);
                binding.swipeToRefreshLayout.setRefreshing(false);
            }
        });
        return binding.getRoot();
    }

    public ArrayList<Post> getListOfPosts() {
        //binding.swipeToRefreshLayout.setRefreshing(true);
        ProgressDialog progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        ArrayList<Post> list = new ArrayList<>();

        postsCollection.orderBy("timePosted", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Post post = documentSnapshot.toObject(Post.class);
                            list.add(post);
                        }
                        homeRecyclerAdapter.notifyDataSetChanged();

                        // Toast.makeText(getContext(), "Posts fetched: " + list.size(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        //binding.swipeToRefreshLayout.setRefreshing(false);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //TODO add implementation to show error message
                        progressDialog.dismiss();
                        //binding.swipeToRefreshLayout.setRefreshing(false);

                        Toast.makeText(getContext(), "Failed to get data", Toast.LENGTH_SHORT).show();
                    }
                });

        return list;
    }

    public ArrayList<Post> getListOfPostsOnRefresh() {
        //binding.swipeToRefreshLayout.setRefreshing(true);
        /*ProgressDialog progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();*/


        ArrayList<Post> list = new ArrayList<>();

        postsCollection.orderBy("timePosted", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Post post = documentSnapshot.toObject(Post.class);
                            list.add(post);
                        }
                        homeRecyclerAdapter.notifyDataSetChanged();

                        // Toast.makeText(getContext(), "Posts fetched: " + list.size(), Toast.LENGTH_SHORT).show();
                       // progressDialog.dismiss();
                        //binding.swipeToRefreshLayout.setRefreshing(false);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //TODO add implementation to show error message
                       // progressDialog.dismiss();
                        //binding.swipeToRefreshLayout.setRefreshing(false);

                        Toast.makeText(getContext(), "Failed to get data", Toast.LENGTH_SHORT).show();
                    }
                });

        return list;
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
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_createPostFragment);

                break;

            case R.id.logOut_icon:
                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Logging Out...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                firebaseAuth.signOut();
                progressDialog.dismiss();

                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_logInActivity);
                getActivity().finish();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home"); //shows the action/toolbar for this specific fragment
    }


}
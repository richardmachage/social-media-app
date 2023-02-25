package com.example.social.Home;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.User;
import com.example.social.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    HomeRecyclerAdapter homeRecyclerAdapter;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference postsCollection;
    CollectionReference usersCollection;
    private ArrayList<Post> listOfPosts;
    private FragmentHomeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        firebaseFirestore = FirebaseFirestore.getInstance();
        postsCollection = firebaseFirestore.collection("Posts");
        usersCollection = firebaseFirestore.collection("Users");
        listOfPosts = getListOfPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(getContext()));

        binding.homeFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeRecyclerAdapter = new HomeRecyclerAdapter(getContext(), listOfPosts);
        binding.homeFragmentRecyclerView.setAdapter(homeRecyclerAdapter);


        return binding.getRoot();
    }

    public ArrayList<Post> getListOfPosts() {
        ProgressDialog progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        ArrayList<Post> list = new ArrayList<>();

        postsCollection.get()
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
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //TODO add implementation to show error message
                        progressDialog.dismiss();
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
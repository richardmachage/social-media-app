package com.example.social.Home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.databinding.FragmentCreatePostBinding;

public class CreatePostFragment extends Fragment {
    private FragmentCreatePostBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreatePostBinding.inflate(LayoutInflater.from(getContext()));

        binding.addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    addNewPost("@machage", binding.addPostTextInputEditText.getText().toString());
                }
            }
        });



        return binding.getRoot();
    }

    private void addNewPost(String userIdentity, String postContent) {
        Post newPost = new Post(userIdentity, postContent);


        //TODO this is for temporary testing,
        //TODO adds new post to the listOfPosts in HomeFragmentViewModel
        HomeFragmentViewModel.listOfPosts.add(newPost);

        Toast.makeText(getContext(),"post successfullly added", Toast.LENGTH_SHORT).show();

        binding.addPostTextInputEditText.setText(null);

//        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_createPostFragment_to_homeFragment);

    }

    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("New Post"); //hides the action/toolbar for this specific fragment
    }

    private boolean validateInput(){
        if(binding.addPostTextInputEditText.getText().toString().isEmpty()){
            binding.addPostTextInputLayout.setHelperTextEnabled(true);
            binding.addPostTextInputLayout.setHelperText("Add content to post");

            return false;
        }

        return true;
    }

}
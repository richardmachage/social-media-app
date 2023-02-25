package com.example.social.Home;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.User;
import com.example.social.databinding.FragmentCreatePostBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreatePostFragment extends Fragment {
    private FragmentCreatePostBinding binding;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String userUid;
    private String userName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userUid = firebaseAuth.getCurrentUser().getUid();
        getUserName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreatePostBinding.inflate(LayoutInflater.from(getContext()));
        progressDialog = new ProgressDialog(this.getContext());
        binding.addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {

                    progressDialog.setMessage("Uploading...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    //add post to Firestore Database
                    firebaseFirestore.collection("Posts")
                            .document()
                            .set(new Post(userUid, binding.addPostTextInputEditText.getText().toString().trim(), userName))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_createPostFragment_to_homeFragment);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Failed to upload: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });

                }
            }
        });



        return binding.getRoot();
    }

    private void getUserName(){

        firebaseFirestore.collection("Users").document(userUid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        userName = user.getName();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        userName = null;
                    }
                });
    }

    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("New Post"); //hides the action/toolbar for this specific fragment
    }

    private boolean validateInput(){
        if(binding.addPostTextInputEditText.getText().toString().trim().isEmpty() || binding.addPostTextInputEditText.getText().toString().trim().equals("")  ){
            binding.addPostTextInputLayout.setHelperTextEnabled(true);
            binding.addPostTextInputLayout.setHelperText("Add content to post");

            return false;
        }else{
            return true;
        }
    }

}
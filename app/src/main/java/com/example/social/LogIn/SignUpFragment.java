package com.example.social.LogIn;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.User;
import com.example.social.databinding.FragmentSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;

public class SignUpFragment extends Fragment {


    private ProgressDialog progressDialog;
    private FragmentSignUpBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //creating an instance of Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(LayoutInflater.from(getContext()));

        progressDialog = new ProgressDialog(this.getContext());

        binding.backNavigationImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_logInFragment);
            }
        });

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO add logic for submitting details to database

                if (validateInputs()) {

                    progressDialog.setMessage("Creating account...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    String userEmail = binding.inputEmailEditText.getText().toString().trim();
                    String password = binding.inputNewPasswordEditText.getText().toString().trim();
                    String userName = binding.inputNameEditText.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(userEmail, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        firebaseFirestore.collection("Users")
                                                .document(firebaseAuth.getCurrentUser().getUid())
                                                .set(new User(firebaseAuth.getCurrentUser().getUid(),userEmail,userName ))
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(getContext(),"Sign up successful",Toast.LENGTH_SHORT).show();

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d("Sign up log ", "Adding to firestore failed "+e.getMessage());
                                                        Toast.makeText(getContext(),"Adding to database failed "+e.getMessage(),Toast.LENGTH_SHORT).show();

                                                    }
                                                });


                                        progressDialog.dismiss();
                                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_signUpFragment_to_logInFragment);
                                    }else{

                                        progressDialog.dismiss();
                                        Toast.makeText(getContext(),"SignUp failed "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide(); //hides the action/toolbar for this specific fragment

    }

    @Override
    public void onPause() {
        super.onPause();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show(); //brings back the action/toolbar for the rest of the fragments in the activity

    }


    private boolean emailValidator(String email) {
        Matcher matcher = LogInFragment.VALID_EMAIL_ADDRESS_REGEX.matcher(email);  // calls the variable for valid email address from the LogInClass
        return matcher.find();
    }

    private boolean validateInputs() {
        //TODO implement functionality for data validation on all editTexts

        //Name field
        if(binding.inputNameEditText.getText()
                .toString()
                .isEmpty()){ //if the input name field is empty
            binding.inputNameTextInputLayout.setHelperTextEnabled(true);
            binding.inputNameTextInputLayout.setHelperText("Name is Required");
            binding.inputNameEditText.requestFocus();

            return false;
        }else{
            binding.inputNameTextInputLayout.setHelperTextEnabled(false);
        }

        //Email field
        if (binding.inputEmailEditText.getText().toString().isEmpty()) { //check for empty email address field
            binding.inputEmailTextInputLayout.setHelperTextEnabled(true);
            binding.inputEmailTextInputLayout.setHelperText("Email is Required");
            binding.inputEmailEditText.requestFocus();

            return false;
        } else if (!emailValidator(binding.inputEmailEditText.getText().toString())) {

            binding.inputEmailTextInputLayout.setHelperText("Input correct email");
            binding.inputEmailEditText.requestFocus();

            return false;
        } else {
            binding.inputEmailTextInputLayout.setHelperTextEnabled(false);
        }

        //Password fields
        if(binding.inputNewPasswordEditText.getText()
                .toString()
                .isEmpty()) { //if password field is empty
            binding.inputNewPasswordTextInputLayout.setHelperTextEnabled(true);
            binding.inputNewPasswordTextInputLayout.setHelperText("Password is required");
            binding.inputNewPasswordEditText.requestFocus();

            return false;
        }else if(//if new password is not equal to confirm password
               !binding.inputNewPasswordEditText.getText().toString().equals( binding.inputConfirmPasswordEditText.getText().toString() ))
        {
            binding.inputNewPasswordTextInputLayout.setHelperTextEnabled(false);
            binding.inputConfirmPasswordTextInputLayout.setHelperTextEnabled(true);
            binding.inputConfirmPasswordTextInputLayout.setHelperText("Passwords do not match");
            return false;
        }else{
            binding.inputNewPasswordTextInputLayout.setHelperTextEnabled(false);
            binding.inputConfirmPasswordTextInputLayout.setHelperTextEnabled(false);
        }

        //agreement checkBox
        if(!binding.agreementCheckBox.isChecked()){ // if the checkbox is not checked
            Toast.makeText(getContext(),"Accept terms & conditions to sign up", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void submitDetails(String userName, String email, String password) {
        //TODO implement logic

        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_signUpFragment_to_logInFragment);
    }


}
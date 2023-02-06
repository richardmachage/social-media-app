package com.example.social.LogIn;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.databinding.FragmentSignUpBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;

public class SignUpFragment extends Fragment {


    private View view;
    private ImageButton backNavigationImageButton;
    private Button submitButton;
    private TextInputLayout userNameTextInputLayout, emailTextInputLayout, newPasswordTextInputLayout, confirmPasswordTextInputLayout;
    private TextInputEditText inputUsernameEditText, inputEmailEditText, inputNewPasswordEditText, confirmPasswordEditText;
    private FragmentSignUpBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(LayoutInflater.from(getContext()));

//        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
//        initializeViews();

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
                    submitDetails(binding.inputNameEditText.getText().toString(), //username
                            binding.inputEmailEditText.getText().toString(), //email address
                            binding.inputNewPasswordEditText.getText().toString()); //password
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

    private void initializeViews() {
        backNavigationImageButton = view.findViewById(R.id.backNavigation_imageButton);
        submitButton = view.findViewById(R.id.submit_button);
        userNameTextInputLayout = view.findViewById(R.id.inputName_textInputLayout);
        emailTextInputLayout = view.findViewById(R.id.inputEmail_textInputLayout);
        newPasswordTextInputLayout = view.findViewById(R.id.inputPassword_textInputLayout);
        confirmPasswordTextInputLayout = view.findViewById(R.id.inputConfirmPassword_textInputLayout);
        inputUsernameEditText = view.findViewById(R.id.inputName_editText);
        inputEmailEditText = view.findViewById(R.id.inputEmail_editText);
        inputNewPasswordEditText = view.findViewById(R.id.inputNewPassword_editText);
        confirmPasswordEditText = view.findViewById(R.id.inputConfirmPassword_editText);
    }


}
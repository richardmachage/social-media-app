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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpFragment extends Fragment {
    private View view;
    private ImageButton backNavigationImageButton;
    private Button submitButton;
    private TextInputLayout userNameTextInputLayout, emailTextInputLayout, newPasswordTextInputLayout, confirmPasswordTextInputLayout;
    private TextInputEditText inputUsernameEditText, inputEmailEditText, inputNewPasswordEditText, confirmPasswordEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initializeViews();

        backNavigationImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_logInFragment);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "submit button clicked", Toast.LENGTH_SHORT).show();
                //TODO add logic for submitting details to database
                if (validateInputs()) {
                    submitDetails(inputUsernameEditText.getText().toString(), //username
                            inputEmailEditText.getText().toString(), //email address
                            inputNewPasswordEditText.getText().toString()); //password
                }
            }
        });


        return view;
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

    private boolean validateInputs() {
        //TODO implement functionality for data validation on all editTexts


        return true;
    }

    private void submitDetails(String userName, String email, String password) {

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
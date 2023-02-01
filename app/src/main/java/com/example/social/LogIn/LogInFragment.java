package com.example.social.LogIn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.social.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LogInFragment extends Fragment {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private View view;
    private Button logInButton;
    private TextView forgotPasswordTextView, signUpTextView;
    private TextInputEditText inputEmailEditText, inputPasswordEditText;
    private TextInputLayout inputEmailLayout, inputPasswordLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_log_in, container, false);

        initializeViews();

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn(validateInputs());
            }
        });

        inputEmailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                inputEmailLayout.setHelperTextEnabled(false);
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_signUpFragment);
            }
        });


        return view;
    }

    private void logIn(boolean validation) {
        if (validation) {
            Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_mainActivity);
        }

    }

    private boolean validateInputs() {
        String emailAddress = inputEmailEditText.getText().toString();
        String password = inputPasswordEditText.getText().toString();


        if (emailAddress.isEmpty()) { //check for empty field
            inputEmailLayout.setHelperTextEnabled(true);
            inputEmailLayout.setHelperText("Required");
            inputEmailEditText.requestFocus();
            return false;
        } else if (!emailValidator(emailAddress)) { // validates if the format of the email is correct
            inputEmailLayout.setHelperText("Input correct email");
            inputEmailEditText.requestFocus();
            return false;
        }else{
            inputEmailLayout.setHelperTextEnabled(false);
            inputPasswordEditText.requestFocus();

        }

        if (password.isEmpty()) { //check for empty field
            inputPasswordLayout.setHelperTextEnabled(true);
            inputPasswordLayout.setHelperText("Required");
            inputPasswordEditText.requestFocus();
            return false;
        }

        return true;
    }

    private boolean emailValidator(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    private void initializeViews() {
        logInButton = view.findViewById(R.id.logIn_button);
        inputEmailEditText = view.findViewById(R.id.inputEmail_editText);
        inputPasswordEditText = view.findViewById(R.id.inputPassword_textInputEditText);
        inputEmailLayout = view.findViewById(R.id.inputEmail_textInputLayout);
        inputPasswordLayout = view.findViewById(R.id.inputPassword_textInputLayout);
        signUpTextView = view.findViewById(R.id.signUp_textView);
        forgotPasswordTextView = view.findViewById(R.id.forgotPassword_textview);

    }
}
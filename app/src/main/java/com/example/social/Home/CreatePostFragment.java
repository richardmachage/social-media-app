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
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.example.social.CheckBadWords;
import com.example.social.R;
import com.example.social.User;
import com.example.social.Utils.FirebaseUtils.FirebaseUtils;
import com.example.social.Utils.PerspectiveUtils.AnalyzeCommentRequest;
import com.example.social.Utils.PerspectiveUtils.AnalyzeCommentResponse;
import com.example.social.Utils.PerspectiveUtils.PerspectiveService;
import com.example.social.databinding.FragmentCreatePostBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        setHasOptionsMenu(true);
        firebaseAuth = FirebaseUtils.getFirebaseAuthInstance();
        firebaseFirestore = FirebaseUtils.getFirestoreInstance();
        userUid = firebaseAuth.getCurrentUser().getUid();
        getUserName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreatePostBinding.inflate(LayoutInflater.from(getContext()));
        progressDialog = new ProgressDialog(this.getContext());
        InputStream inputStream;
        ArrayList<String> badWords = new ArrayList();
        try {
            inputStream = getContext().getAssets().open("bad-words.txt");
            badWords = CheckBadWords.loadBadWordsFromTextfile(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayList<String> finalBadWords = badWords;
        binding.addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {

                    progressDialog.setMessage("Uploading...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    boolean checkIfVulgar = CheckBadWords.checkBadWords(binding.addPostTextInputEditText.getText().toString().trim(), finalBadWords);

                    if (checkIfVulgar) {
                        Toast.makeText(getContext(), "Can not post vulgar Language", Toast.LENGTH_SHORT).show();
                        //  binding.addPostTextInputLayout.setHelperText("Vulgar language detected!");
                        progressDialog.dismiss();

                    } else {


                        //add post to Firestore Database
                        firebaseFirestore.collection("Posts")
                                .document()
                                .set(new Post(userUid, binding.addPostTextInputEditText.getText().toString().trim(), userName, Timestamp.now()))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                                        getParentFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_createPostFragment_to_homeFragment);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getContext(), "Failed to upload: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }
                }

            }
        });


        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.createpost_fragment_toolbar_items, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home_icon) {

            getParentFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_createPostFragment_to_homeFragment);
        }


        return super.onOptionsItemSelected(item);

    }

    private Retrofit getRetrofitInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://commentanalyzer.googleapis.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    private PerspectiveService getPerspectiveServiceInstance() {
        PerspectiveService perspectiveService = getRetrofitInstance().create(PerspectiveService.class);

        return perspectiveService;
    }

    private AnalyzeCommentRequest getRequest(String textToAnalyze) {
        AnalyzeCommentRequest.Comment comment = new AnalyzeCommentRequest.Comment(textToAnalyze);
        AnalyzeCommentRequest.RequestedAttributes.Toxicity toxicity = new AnalyzeCommentRequest.RequestedAttributes.Toxicity(0.7);
        AnalyzeCommentRequest.RequestedAttributes requestedAttributes = new AnalyzeCommentRequest.RequestedAttributes(toxicity);
        AnalyzeCommentRequest request = new AnalyzeCommentRequest(comment, requestedAttributes);

        return request;
    }

    private Boolean isPostToxic() {
        final Boolean[] isToxic = new Boolean[1];
        Call<AnalyzeCommentResponse> call = getPerspectiveServiceInstance().analyzeComment(getRequest(binding.addPostTextInputEditText.getText().toString().trim()));
        call.enqueue(new Callback<AnalyzeCommentResponse>() {
            @Override
            public void onResponse(Call<AnalyzeCommentResponse> call, Response<AnalyzeCommentResponse> response) {
                if (response.isSuccessful()) {
                    AnalyzeCommentResponse.AttributeScores.Toxicity toxicity = response.body().getAttributeScores().getToxicity();
                    double summaryScore = toxicity.getSummaryScore();
                    if (summaryScore >= 0.7) {
                        //this post is toxic
                        isToxic[0] = true;
                    } else {
                        //this post is not Toxic
                        isToxic[0] = false;
                    }
                } else {
                    //Something went wrong and response is not successful
                    isToxic[0] = null;
                    Toast.makeText(getContext(), "response code : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<AnalyzeCommentResponse> call, Throwable t) {
                //TODO handle the error
                isToxic[0] = null;
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getStackTrace());
            }

        });

        return isToxic[0];

    }

    private void getUserName() {

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

    private boolean validateInput() {
        if (binding.addPostTextInputEditText.getText().toString().trim().isEmpty() || binding.addPostTextInputEditText.getText().toString().trim().equals("")) {
            binding.addPostTextInputLayout.setHelperTextEnabled(true);
            binding.addPostTextInputLayout.setHelperText("Add content to post");

            return false;
        } else {
            return true;
        }
    }

}
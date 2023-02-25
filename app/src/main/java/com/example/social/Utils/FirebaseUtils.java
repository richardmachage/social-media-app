package com.example.social.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtils {

    private static FirebaseFirestore firestore;
    private static FirebaseAuth firebaseAuth;

    private FirebaseUtils(){}

    public static FirebaseFirestore getFirestoreInstance() {
        if(firestore == null){
            firestore = FirebaseFirestore.getInstance();
        }
        return firestore;
    }

    public static FirebaseAuth getFirebaseAuthInstance(){
        if(firebaseAuth == null){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }
}

package com.example.social.Utils;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtils {

    private static FirebaseFirestore firestore;

    private FirebaseUtils(){}

    public static FirebaseFirestore getFirestoreInstance() {
        if(firestore == null){
            firestore = FirebaseFirestore.getInstance();
        }
        return firestore;
    }
}

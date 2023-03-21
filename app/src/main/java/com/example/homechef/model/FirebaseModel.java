package com.example.homechef.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FirebaseModel {
    FirebaseFirestore db;
    FirebaseStorage storage;

    FirebaseModel() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        storage = FirebaseStorage.getInstance();

    }

    public void getAllPostsSince(Long since, Model.Listener<List<Post>> callback) {
        db.collection(Post.COLLECTION).whereGreaterThanOrEqualTo(Post.LAST_UPDATED, new Timestamp(since, 0)).get()
                .addOnCompleteListener(task -> {
                    List<Post> list = new LinkedList<>();
                    Post post = null;
                    if (task.isSuccessful() && task.getResult() != null) {
                        QuerySnapshot jsonList = task.getResult();
                        for (DocumentSnapshot json : jsonList) {
                            Post p = Post.fromJson(json.getData());
                            list.add(p);
                        }
                    }
                    callback.onComplete(list);
                });
        // db.collection(Post.COLLECTION)
        // .whereGreaterThanOrEqualTo(Post.LAST_UPDATED, new Timestamp(since,0))
        // .get()
        // .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        // @Override
        // public void onComplete(@NonNull Task<QuerySnapshot> task) {
        // List<Post> list = new LinkedList<>();
        // if (task.isSuccessful()){
        // QuerySnapshot jsonList = task.getResult();
        // for (DocumentSnapshot json: jsonList){
        // Post st = Post.fromJson(json.getData());
        // list.add(st);
        // }
        // }
        // callback.onComplete(list);
        // }
        // });
    }

    public void addPost(Post post, Model.Listener<Post> listener) {
        Map<String, Object> postJson = post.toJson();
        db.collection(Post.COLLECTION)
                .document(post.getId())
                .set(postJson)
                .addOnSuccessListener(unused -> listener.onComplete(post))
                .addOnFailureListener(e -> listener.onComplete(post));
    }

    void uploadImage(String name, Bitmap bitmap, Model.Listener<String> listener) {
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images/" + name + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onComplete(uri.toString());
                    }
                });
            }
        });
    }

    public void addUser(User user, Model.Listener<User> listener) {
        Map<String, Object> userJson = user.toJson();
        db.collection(User.COLLECTION)
                .document(user.getEmail())
                .set(userJson)
                .addOnSuccessListener(unused -> listener.onComplete(user))
                .addOnFailureListener(e -> listener.onComplete(user));
    }

    public void signUp(User user, String password, Model.Listener<User> listener) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        addUser(user, listener);
                    }
                });
    }

    public void login(String email, String password, Model.Listener<Void> listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        listener.onComplete(null);
                    }
                });
    }

    public void getUserById(String email, Model.Listener<User> listener) {
        db.collection(User.COLLECTION)
                .document(email)
                .get()
                .addOnCompleteListener(task -> {
                    User user = null;
                    if (task.isSuccessful() && task.getResult() != null) {
                        user = user.fromJson(task.getResult().getData());
                    }
                    listener.onComplete(user);
                });
    }

    public String getConnectedUser() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        return firebaseUser.getEmail();
    }
}

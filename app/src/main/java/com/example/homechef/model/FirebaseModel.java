package com.example.homechef.model;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FirebaseModel {
    FirebaseFirestore db;
    FirebaseStorage storage;
    FirebaseAuth mAuth;

    FirebaseModel(){
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public void getAllPostsSince(Long since, Model.Listener<List<Post>> callback){
        db.collection(Post.COLLECTION)
                .whereGreaterThanOrEqualTo(Post.LAST_UPDATED, new Timestamp(since,0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Post> list = new LinkedList<>();
                        if (task.isSuccessful()){
                            QuerySnapshot jsonList = task.getResult();
                            for (DocumentSnapshot json: jsonList){
                                Post st = Post.fromJson(json.getData());
                                list.add(st);
                            }
                        }
                        callback.onComplete(list);
                    }
                });
    }

    public void addPost(Post post, Model.Listener<Void> listener) {
        db.collection(Post.COLLECTION).document(post.getId()).set(post.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete(null);
                    }
                });
    }

    void uploadImage(String userName, Bitmap bitmap, Model.Listener<String> listener){
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images/" + userName + ".jpg");
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
}

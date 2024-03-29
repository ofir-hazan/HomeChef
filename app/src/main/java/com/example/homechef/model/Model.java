package com.example.homechef.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public enum LoadingState {
        LOADING,
        NOT_LOADING
    }

    final public MutableLiveData<LoadingState> EventPostsListLoadingState = new MutableLiveData<LoadingState>(
            LoadingState.NOT_LOADING);
    private LiveData<List<Post>> postList;
    private LiveData<List<Post>> myPostList;

    public static Model instance() {
        return _instance;
    }

    private Model() {
    }

    public interface Listener<T> {
        void onComplete(T data);
    }


    public LiveData<List<Post>> getAllPosts() {
        if (postList == null) {
            postList = localDb.PostDao().getAll();
            refreshAllPosts();
        }
        return postList;
    }

    public LiveData<List<Post>> getUserPosts() {
        String email = getConnectedUser();
        if (myPostList == null || myPostList.getValue() == null) {
            myPostList = localDb.PostDao().getPostsByEmail(email);
        }
        return myPostList;
    }

    public void refreshAllPosts() {
        EventPostsListLoadingState.setValue(LoadingState.LOADING);
        // get local last update
        Long localLastUpdate = postList.getValue() == null ? 0L : Post.getLocalLastUpdate();
        // get all updated recorde from firebase since local last update
        firebaseModel.getAllPostsSince(localLastUpdate, list -> {
            executor.execute(() -> {
                Log.d("TAG", " firebase return : " + list.size());
                long time = localLastUpdate;
                for (Post post : list) {
                    // insert new records into ROOM
                    localDb.PostDao().insertAll(post);
                    if (time < post.getLocalLastUpdate()) {
                        time = post.getLocalLastUpdate();
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // update local last update
                Post.setLocalLastUpdated(time);
                EventPostsListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }

    public void addPost(Post post, Listener<Void> listener) {
        firebaseModel.addPost(post, (Void) -> {
            refreshAllPosts();
            listener.onComplete(null);
        });
    }

    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name, bitmap, listener);
    }

    public void getUserById(String email, Listener<User> listener) {
        firebaseModel.getUserById(email, listener);
    }

    public void login(String email, String password, Listener<Void> listener) {
        firebaseModel.login(email, password, listener);
    }

    public void signUp(User user, String password, Listener<User> listener) {
        firebaseModel.signUp(user, password, listener);
    }

    public String getConnectedUser() {
        return firebaseModel.getConnectedUser();
    }

    public void editInfo(User user, Listener<User> listener) {
        firebaseModel.addUser(user, listener);
    }

    public void getPostById(String id, Listener<Post> listener) {
        firebaseModel.getPostById(id, listener);
    }

    public void logout() {
        firebaseModel.logout();
    }
}

package com.example.social.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.social.R;

import java.util.ArrayList;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeRecyclerVH>{
    Context context;
    ArrayList<Post> listOfPosts;

    public HomeRecyclerAdapter(Context context, ArrayList<Post> listOfPosts) {
        this.context = context;
        this.listOfPosts = listOfPosts;
    }


    @NonNull
    @Override
    public HomeRecyclerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_post,parent,false);
        return new HomeRecyclerVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerVH holder, int position) {
        Post post = listOfPosts.get(position);

        holder.userIdentity_textView.
                setText("@"+ HomeFragment.listOfUsers.get(
                        HomeFragment.userNamesIndex.get(post.userIdentity)
                ).getName()
                );

        holder.postContent_textView.
                setText(post.postContent);
    }


    @Override
    public int getItemCount() {
        return listOfPosts.size();
    }

    public class HomeRecyclerVH extends RecyclerView.ViewHolder {

        TextView userIdentity_textView, postContent_textView;

        public HomeRecyclerVH(@NonNull View itemView) {
            super(itemView);
            userIdentity_textView = itemView.findViewById(R.id.userIdentity_textView);
            postContent_textView = itemView.findViewById(R.id.postContent_textView);
        }
    }
}

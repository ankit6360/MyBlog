package com.myblog.myblog.servicelayer;

import com.myblog.myblog.entity.Post;
import com.myblog.myblog.payload.PostDto;
import com.myblog.myblog.servicelayer.impl.PostResponse;

import java.util.List;

public interface PostService {



   public PostDto createPost(PostDto postDto);

   PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String Dir);


   PostDto getPostById(long id);

   PostDto updatePost(PostDto postDto, long id);



  void deletePostById(long id);
}

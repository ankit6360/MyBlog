package com.myblog.myblog.servicelayer.impl;

import com.myblog.myblog.entity.Post;

import com.myblog.myblog.exception.ResourceNotFoundException;
import com.myblog.myblog.payload.PostDto;
import com.myblog.myblog.repository.PostRepository;
import com.myblog.myblog.servicelayer.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


  private PostRepository postRepository;

  private ModelMapper mapper;
    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper=mapper;
    }
    @Override
public PostDto createPost(PostDto postDto){
        Post post = mapToEntity(postDto);

        Post newpost = postRepository.save(post);
       PostDto postResponse = mapToDto(newpost);
       return postResponse;


    }

    @Override
    public PostResponse getAllPost(int pageNo,int pagesize,String sortBy,String sortDir) {
       Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
               Sort.by(sortBy).ascending():
               Sort.by(sortBy).descending();

//       if(sortDir.equalsIgnoreCase("asc")) {
//           sort=Sort.by(sortBy).ascending();
//       } else{
//          sort= Sort.by(sortBy).ascending();
//       }
        PageRequest pageable = PageRequest.of(pageNo,pagesize,sort);
        Page<Post> content=postRepository.findAll(pageable);
        List<Post> posts=content.getContent();

        List<PostDto> dto=posts.stream().map(post->mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(dto);
        postResponse.setPageNo(content.getNumber());
        postResponse.setPageSize(content.getSize());
        postResponse.setTotalPages(content.getTotalPages());
        postResponse.setTotalElements((int)content.getNumberOfElements());
        postResponse.setLast(content.isLast());

        return postResponse;

    }

    @Override
    public PostDto getPostById(long id) {
        Post post=postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",id)
        );
        return mapToDto(post);

    }
//http://localhost:8080/api/posts
    @Override
    public PostDto updatePost(@RequestBody  PostDto postDto, long id) {
       Post post= postRepository.findById(id).orElseThrow(
                ()->  new ResourceNotFoundException("Post","Id",id)
        );
       post.setTitle(postDto.getTitle());
       post.setDescription(postDto.getDescription());
       post.setContent(postDto.getContent());
        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", id)
        );
  postRepository.deleteById(id);

    }

    public PostDto  mapToDto(Post post){
        PostDto postDto=mapper.map(post,PostDto.class);
        return postDto;
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setContent(post.getContent());
//        dto.setDescription(post.getDescription());
//        return dto;
    }

    Post mapToEntity(PostDto dto){
        Post post=mapper.map(dto,Post.class);
        return post;
    //public  Post mapToEntity(PostDto postDto){
//        Post post = new Post();
        //post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
//        return  post;
    }

}

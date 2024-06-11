package com.myblog.myblog.controller;

//import ch.qos.logback.core.net.SyslogOutputStream;
import com.myblog.myblog.payload.PostDto;
import com.myblog.myblog.servicelayer.PostService;

import com.myblog.myblog.servicelayer.impl.PostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RestController
@RequestMapping("/api/post")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {

        this.postService = postService;
    }

    //http://localhost:8080/api/post
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result) {

        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    // public ResponseEntity<PostDto> createPost( @RequestBody PostDto postDto) {
//        System.out.println((postDto.getContents()));

//    }
//http://localhost:8080/api/posts/1?pageNo=0&pageSize=10&sortBy=title&sortDir=asc

    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value ="pageNo",defaultValue = "0",required = false)int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue="id",required=false)String sortBy,
        @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir

    ) {

        PostResponse postResponse= postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
        return postResponse;
    }
    //http://localhost:8080/api/posts/1


    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getDataById(

            @PathVariable long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    //http://localhost:8080/api/posts/1
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @RequestBody PostDto postDto,
            @PathVariable("id") long id
    ) {
        PostDto postResponse = postService.updatePost(postDto, id);
        return ResponseEntity.ok(postResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(
            @PathVariable("id") long id
    ) {
        postService.deletePostById(id);
        return new ResponseEntity<String>("Post id deleted!!", HttpStatus.OK);


    }
}


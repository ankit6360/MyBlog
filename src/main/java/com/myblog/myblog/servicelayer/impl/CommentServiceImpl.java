package com.myblog.myblog.servicelayer.impl;

import com.myblog.myblog.entity.Comment;
import com.myblog.myblog.entity.Post;
import com.myblog.myblog.exception.BlogAPIException;
import com.myblog.myblog.exception.ResourceNotFoundException;
import com.myblog.myblog.payload.CommentDto;
import com.myblog.myblog.repository.CommentRepository;
import com.myblog.myblog.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
   private CommentRepository commentRepository;
   private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId,CommentDto commentDto) {


        Post post=postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post","id",postId));
        Comment comment=mapToEntity(commentDto);


        comment.setPost(post);
        //comment entity to DB
        Comment save = commentRepository.save(comment);

        return mapToDTO(save);


    }
    @Override
    public List<CommentDto> getCommentsByPostId(long postId){
        List<Comment> comments=commentRepository.findByPostId(postId);
        return comments.stream().map(comment ->mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",postId)

        );
                Comment comment=commentRepository.findById(commentId).orElseThrow(()->
                        new ResourceNotFoundException("Comment", "id",commentId)

                );

                if(comment.getPost().getId()!=(post.getId())){
            throw new BlogAPIException((HttpStatus.BAD_REQUEST),"Comment does not belong to post");
        }
        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(long postId,long commentId, CommentDto commentDto) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id", postId));

                Comment comment=commentRepository.findById(commentId).orElseThrow(
                        ()->new ResourceNotFoundException("Comment","id",commentId));

                if(comment.getPost().getId() != post.getId()){
                    throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment does not exist");
                }
                commentRepository.deleteById(comment.getId());

                comment.setName(commentDto.getName());
                comment.setEmail(commentDto.getEmail());
                comment.setBody(commentDto.getBody());

                Comment updatedComment=commentRepository.save(comment);

                return mapToDTO(updatedComment);

    }

    @Override
    public void deleteComment(long postId, long id) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id",id));
        if(comment.getPost().getId()!=(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }
        commentRepository.delete(comment);
    }

    CommentDto mapToDTO(Comment newComment){
        CommentDto dto=new CommentDto();
        dto.setId(newComment.getId());
        dto.setName(newComment.getName());
        dto.setEmail(newComment.getEmail());
        dto.setBody(newComment.getBody());
        return dto;
    }

     Comment mapToEntity(CommentDto commentDto) {
        Comment comment=new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
}

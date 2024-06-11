package com.myblog.myblog.servicelayer.impl;

import com.myblog.myblog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);
    CommentDto getCommentById(Long postId,Long CommentId);

    CommentDto updateComment(long postId,long commentId, CommentDto commentdto);

    void deleteComment(long postId, long id);
}

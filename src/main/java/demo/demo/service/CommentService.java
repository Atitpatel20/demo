package demo.demo.service;

import demo.demo.payload.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAllComments(int pageNo, int pageSize, String sortBy, String sortDir);
    CommentDto createComment(CommentDto commentDto, long postId);

    CommentDto updateComment(long id, CommentDto commentDto, long postId);

    void deleteComment(long id);
}


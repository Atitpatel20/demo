package demo.demo.service.impl;

import demo.demo.entity.Comment;
import demo.demo.entity.Post;
import demo.demo.exception.ResourceNotFoundException;
import demo.demo.payload.CommentDto;
import demo.demo.repository.CommentRepository;
import demo.demo.repository.PostRepository;
import demo.demo.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public List<CommentDto> getAllComments(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Comment> pageComments = commentRepository.findAll(pageable);
        List<Comment> content = pageComments.getContent();

        return content.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id")
        );
        Comment comment = mapToEntity(commentDto);
        comment.setId(commentDto.getId());
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return mapToDto(savedComment);
    }

    @Override
    public CommentDto updateComment(long id, CommentDto commentDto, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with postId"+postId)
        );
        Comment comments = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("comment not found with id"+id)
        );
        Comment c = mapToEntity(commentDto);
        c.setId(comments.getId());
        c.setPost(post);
        Comment save = commentRepository.save(c);
        return mapToDto(save);
    }

    @Override
    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setText(comment.getText());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setEmail(commentDto.getEmail());
//        comment.setText(commentDto.getText());
        return comment;
    }
}

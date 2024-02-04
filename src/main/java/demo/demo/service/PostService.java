package demo.demo.service;

import demo.demo.payload.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostDto getPostById(long id);

    void deletePostById(long id);

    PostDto updatePostById(long id, PostDto postDto);

//    List<PostDto> getAllPosts(Pageable pageable);

    List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

//    List<PostDto> getAllPosts();
}

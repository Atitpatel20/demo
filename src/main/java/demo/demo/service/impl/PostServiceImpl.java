package demo.demo.service.impl;

import demo.demo.entity.Post;
import demo.demo.exception.ResourceNotFoundException;
import demo.demo.payload.PostDto;
import demo.demo.repository.PostRepository;
import demo.demo.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post posts = mapToEntity(postDto);
//        Post posts = new Post();
//        posts.setId(postDto.getId());
//        posts.setTitle(postDto.getTitle());
//        posts.setDescription(postDto.getDescription());
//        posts.setContent(postDto.getContent());
        Post savedPost = postRepository.save(posts);

//        PostDto dto = new PostDto();
//        dto.setId(savedPost.getId());
//        dto.setDescription(savedPost.getDescription());
//        dto.setTitle(savedPost.getTitle());
//        dto.setContent(savedPost.getContent());
        return  mapToDto(savedPost);

    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id:" + id)
        );
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setDescription(post.getDescription());
//        dto.setTitle(post.getTitle());
//        dto.setContent(post.getContent());
        return mapToDto(post);

    }

    @Override
    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public PostDto updatePostById(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id")
        );
        Post p = mapToEntity(postDto);
        p.setId(postDto.getId());
//        post.setId(postDto.getId());
//        post.setDescription(postDto.getDescription());
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
        Post update = postRepository.save(p);
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setDescription(post.getDescription());
//        dto.setTitle(post.getTitle());
//        dto.setContent(post.getContent());
        return mapToDto(update);
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> pagePosts = postRepository.findAll(pageable);
        List<Post> content = pagePosts.getContent();

        // Convert the List<Post> to List<PostDto>
        List<PostDto> postDtos = content.stream()
                .map(m->mapToDto(m))
                .collect(Collectors.toList());

        return postDtos;
    }

//    @Override
//    public List<PostDto> getAllPosts() {
//        List<Post> posts = postRepository.findAll();
//        List<PostDto> collect = posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
//        return collect;
//    }

    PostDto mapToDto(Post post) {

        PostDto dto = modelMapper.map(post, PostDto.class);
//        PostDto dto = new PostDto();
////        dto.setId(post.getId());
//        dto.setDescription(post.getDescription());
//        dto.setTitle(post.getTitle());
//        dto.setContent(post.getContent());
        return dto;
    }

    Post mapToEntity(PostDto postDto) {
        Post dtos = modelMapper.map(postDto, Post.class);
//        Post dtos = new Post();
//        dtos.setId(postDto.getId());
//        dtos.setDescription(postDto.getDescription());
//        dtos.setTitle(postDto.getTitle());
//        dtos.setContent(postDto.getContent());
        return dtos;
    }
}

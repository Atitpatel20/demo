package demo.demo.config;

import demo.demo.payload.PostDto;
import demo.demo.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;
    }

    // http://localhost:8080/api/posts
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //    http://localhost:8080/api/posts/2
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getAllPostById(@PathVariable long id) {
        PostDto dtos = postService.getPostById(id);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    //    @GetMapping
//    public ResponseEntity<List<PostDto>> getAllPosts() {
//        List<PostDto> dtos = postService.getAllPosts();
//        return new ResponseEntity<>(dtos, HttpStatus.OK);ortBy=id
    @GetMapping
//    http://localhost:8080/api/posts?pageNo=3&pageSize=3&sortBy=title&sortDir=desc
    public ResponseEntity<List<PostDto>> getAllPosts (
            @RequestParam (name="pageNo",required = false,defaultValue = "0")int pageNo,
            @RequestParam (name="pageSize",required = false,defaultValue = "3")int pageSize,
            @RequestParam (name="sortBy",required = false,defaultValue = "id")String sortBy,
            @RequestParam (name="sortDir",required = false,defaultValue = "asc")String sortDir
    ) {
        List<PostDto> dtos = postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    //    http://localhost:8080/api/posts/3
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Record is deleted", HttpStatus.OK);
    }

    // http://localhost:8080/api/posts/2
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@PathVariable long id, @RequestBody PostDto postDto) {
        PostDto dto = postService.updatePostById(id, postDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}

package demo.demo.config;

import demo.demo.payload.CommentDto;
import demo.demo.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments(
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "3") int pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", required = false, defaultValue = "desc") String sortDir
    ) {
        List<CommentDto> dtos = commentService.getAllComments(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    // http://localhost:8080/api/comments?postId=1
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@RequestParam long postId) {
        CommentDto createdComment = commentService.createComment(commentDto,postId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }
//    http://localhost:8080/api/comments/1/post/1
    @PutMapping("/{id}/post/{postId}")
    public ResponseEntity<CommentDto>updateComment(@PathVariable long id,
     @RequestBody CommentDto commentDto,
     @PathVariable long postId){
        CommentDto dto= commentService.updateComment(id,commentDto,postId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    // http://locahost:8080/api/comments/3
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteCommentById(@PathVariable long id){
        commentService.deleteComment(id);
        return new ResponseEntity<>("comment is deleted!!!!",HttpStatus.OK);
    }
}

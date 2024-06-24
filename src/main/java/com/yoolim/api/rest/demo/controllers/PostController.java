package com.yoolim.api.rest.demo.controllers;

import com.fasterxml.jackson.core.JacksonException;
import com.yoolim.api.rest.demo.dtos.PostDto;
import com.yoolim.api.rest.demo.exceptions.PostNotFound;
import com.yoolim.api.rest.demo.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/posts")
@RestController
public class PostController {
    private final PostService postService;

    public PostController() {
        this.postService = new PostService();
    }

    @GetMapping
    public List<PostDto> list() {
        List<PostDto> posts = postService.getPostList();
        return posts;
    }

    @GetMapping("/{id}")
    public PostDto detail(
            @PathVariable("id") String id) throws JacksonException {
        PostDto postDto = postService.getPostId(id);

        return postDto;
    }

    public String addPost(
            @PathVariable("id") String id, @RequestBody String body
    ) {
        return "{\"action\": \"게시글 생성\"," +
                " \"id\": \"" + id + "\"," +
                " \"body\": " + body +
                "}";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto create(@RequestBody PostDto postDto) {

        PostDto created = postService.create(postDto);

        return created;
    }

    @PatchMapping("/{id}")
    public PostDto update(
            @PathVariable("id") String id, @RequestBody PostDto postDto
    ) throws JacksonException {
        PostDto dto = postService.update(id, postDto);

        return dto;
    }

    @DeleteMapping("/{id}")
    public PostDto delete(
            @PathVariable("id") String id
    ) {
//        https://dev-jwblog.tistory.com/72

        PostDto postDto = postService.deletePost(id);

        return postDto;
    }

    @ExceptionHandler(PostNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String postNotFound() {
        return "게시물을 찾을 수 없습니다.\n";
    }
}

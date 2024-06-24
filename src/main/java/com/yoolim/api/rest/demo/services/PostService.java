package com.yoolim.api.rest.demo.services;
import com.yoolim.api.rest.demo.dtos.PostDto;
import com.yoolim.api.rest.demo.exceptions.PostNotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@Service
public class PostService {
    // PostDTO 목록 관리 --> 기능을 위한 토대
    private List<PostDto> postDtos = List.of(
            new PostDto("1", "first title", "first content"),
            new PostDto("2", "second title", "second content")
    );

    //    https://kim-jong-hyun.tistory.com/31 -> Arrays.asList()와 List.of()의 차이

    public List<PostDto> getPostList() {
        return postDtos;
    }

    public PostDto getPostId(String id) {
        return findPost(id);
    }

    private PostDto findPost(String id) {
        return postDtos.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new PostNotFound());
    }

    public PostDto create(PostDto postDto) {
        String id = UUID.randomUUID().toString().replace("-", "");
        postDto.setId(id);
        postDtos.add(postDto);

        return postDto;
    }

    public PostDto update(String id, PostDto postDto) {
        PostDto found = findPost(id);

        found.setTitle(postDto.getTitle());
        found.setContent(postDto.getContent());

        return found;
    }

    public PostDto deletePost(String id) {
        PostDto post = findPost(id);
        postDtos.remove(post);

        return post;
    }

//    private PostDto findPost(String id) { // Extract Method(https://refactoring.com/catalog/extractFunction.html)
//        return postDtos.stream().filter(post -> post.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new PostNotFound());
//    }
}

package com.yjblog.service;

import com.yjblog.domain.Post;
import com.yjblog.repository.PostRepository;
import com.yjblog.request.PostCreate;
import com.yjblog.request.PostSearch;
import com.yjblog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate){
        //postCreate -> Entity
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

       postRepository.save(post);
    }

    public PostResponse get(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        return postResponse;
    }


    //글이 너무 많으면 비용이 필요 이상으로 많이 든다.
    public List<PostResponse> getList(PostSearch postSearch) {
         return postRepository.getList(postSearch).stream()
               .map(post -> new PostResponse(post))
                 .collect(Collectors.toList());
    }
}

package com.yjblog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yjblog.domain.Post;
import com.yjblog.domain.QPost;
import com.yjblog.request.PostSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.yjblog.domain.QPost.*;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch){

        return jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }
}

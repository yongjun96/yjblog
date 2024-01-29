package com.yjblog.repository;

import com.yjblog.domain.Post;
import com.yjblog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}

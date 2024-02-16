package com.yjblog.domain;

import com.yjblog.response.PostEdit;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Lob // LongText
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void edit(PostEdit postEdit) {
        this.title = postEdit.getTitle() != null ? postEdit.getTitle() : this.title;
        this.content = postEdit.getContent() != null ? postEdit.getContent() : this.content;
    }

    public Long getUserId(){
        return this.user.getId();
    }
}

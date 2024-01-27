package com.yjblog.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PostCreate {

    private String title;
    private String content;

}

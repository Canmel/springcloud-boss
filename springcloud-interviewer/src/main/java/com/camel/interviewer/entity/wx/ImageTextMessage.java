package com.camel.interviewer.entity.wx;

import java.util.List;

public class ImageTextMessage extends BaseMessage {
    private int ArticleCount;
    private List<ImageText> Articles;

    public ImageTextMessage(String toUserName, String fromUserName, long createTime, String msgType, int articleCount, List<ImageText> articles) {
        super(toUserName, fromUserName, createTime, msgType);
        ArticleCount = articleCount;
        Articles = articles;
    }

    public ImageTextMessage() {
    }

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<ImageText> getArticles() {
        return Articles;
    }

    public void setArticles(List<ImageText> articles) {
        Articles = articles;
    }
}

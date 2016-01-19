package com.alvincezy.universalwxmp.generic.message.resp;

import com.alvincezy.universalwxmp.util.xml.annotation.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class RespNews extends RespMsg {

    @Element(name = DOC_ELE_ARTICLES, embed = true)
    private List<NewsArticle> mArticles;

    @Element(name = DOC_ELE_ARTICLE_COUNT)
    private int mArticleCount;

    public RespNews(String fromUser, String toUser) {
        super(fromUser, toUser, MSG_TYPE_NEWS);
        mArticleCount = 0;
    }

    public int getArticleCount() {
        return mArticleCount;
    }

    public void addArticle(NewsArticle article) {
        if (article != null) {
            if (mArticleCount == 10) {
                throw new IllegalStateException("There are 10 items already.");
            }

            getArticles().add(article);
            mArticleCount++;
        }
    }

    public List<NewsArticle> getArticles() {
        if (mArticles == null) {
            mArticles = new ArrayList<NewsArticle>();
        }
        return mArticles;
    }
}

package com.example.csust_hot_wall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.csust_hot_wall.configuration.ResultException;
import com.example.csust_hot_wall.entity.Article;
import com.example.csust_hot_wall.entity.Comments;
import com.example.csust_hot_wall.entity.User;
import com.example.csust_hot_wall.mapper.ArticleMapper;
import com.example.csust_hot_wall.mapper.ReplyMapper;
import com.example.csust_hot_wall.mapper.UserMapper;
import com.example.csust_hot_wall.service.CommentsService;
import com.example.csust_hot_wall.mapper.CommentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class CommentsServiceImpl extends BaseServiceImpl<CommentsMapper, Comments>
    implements CommentsService{

    @Autowired
    CommentsMapper commentsMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ReplyMapper replyMapper;


    @Override
    public boolean save(Comments entity) {
        // 评论用户和文章必须有效
        User user = userMapper.selectById(entity.getUserId());
        if (user == null) throw new ResultException("用户无效！");
        Article article = articleMapper.selectById(entity.getArticleId());
        if (article == null) throw new ResultException("文章无效！");
        return super.save(entity);
    }

    @Override
    public boolean updateById(Comments entity) {
        // 不能修改
        return false;
    }

    @Override
    public List<Comments> listByArticleId(Integer articleId) {
        List<Comments> comments = commentsMapper.selectByArticleId(articleId);
        redundancy(comments);
        return comments;
    }

    @Override
    public List<Comments> listByUserId(Integer userId) {
        return redundancy(commentsMapper.selectByUserId(userId));
    }

    @Override
    protected Comments redundancy(Comments comments) {
        // 所有评论信息增加评论用户名、用户头像、文章名、文章作者、文章简介、回复数量
        // 用户
        User user = userMapper.selectById(comments.getUserId());
        if (user == null) comments.setAuthor("未知用户");
        else{
            comments.setAuthor(user.getNickname()); // 昵称
            comments.setHeadImg(user.getImgPath()); // 头像
        }
        // 文章相关
        Article article = articleMapper.selectById(comments.getArticleId());
        if (article != null){
            comments.setArticle(article.getTitle());
            User auser = userMapper.selectById(article.getUserId());
            // 当文章作者不存在时
            if (auser == null) comments.setArticleAuthor("未知作者");
            else comments.setArticleAuthor(auser.getNickname());
            comments.setArticleIntro(article.getIntro()); // 文章简介
        }
        // 回复数量
        comments.setReplyCount(replyMapper.countByCommentId(comments.getId()));
        return comments;
    }

    /* 关键字查询*/

    static private class UserIdKey extends SearchKey<Comments>{
        /**
         * 通过用户id查询
         */
        private UserIdKey(){super("uid");}

        @Override
        public void search(LambdaQueryWrapper<Comments> wrapper, String value) {
            Integer uid = Integer.parseInt(value);
            wrapper.eq(Comments::getUserId,uid); // 通过用户id查询
            wrapper.orderByDesc(Comments::getCreationTime); // 时间倒序
        }
    }

    static private class ArticleIdKey extends SearchKey<Comments>{
        /**
         * 通过文章id查询
         */
        private ArticleIdKey(){super("aid");}

        @Override
        public void search(LambdaQueryWrapper<Comments> wrapper, String value) {
            Integer aid = Integer.parseInt(value);
            wrapper.eq(Comments::getArticleId,aid); // 通过文章id查询
            wrapper.orderByDesc(Comments::getCreationTime); // 时间倒序
        }
    }
}





package com.example.csust_hot_wall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.csust_hot_wall.configuration.ResultException;
import com.example.csust_hot_wall.entity.Comments;
import com.example.csust_hot_wall.entity.Reply;
import com.example.csust_hot_wall.entity.User;
import com.example.csust_hot_wall.mapper.CommentsMapper;
import com.example.csust_hot_wall.mapper.UserMapper;
import com.example.csust_hot_wall.service.ReplyService;
import com.example.csust_hot_wall.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ReplyServiceImpl extends BaseServiceImpl<ReplyMapper, Reply>
    implements ReplyService{

    @Autowired
    CommentsMapper commentsMapper;
    @Autowired
    ReplyMapper replyMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public boolean save(Reply entity) {
        // 父评论Id必须是有效的
        Comments comments = commentsMapper.selectById(entity.getCommentId());
        if (comments == null) throw new ResultException("父评论编号是无效的！");
        // 回复他人回复时他人回复Id必须是有效的
        if (entity.getReplyId() != null){
            Reply reply = replyMapper.selectById(entity.getReplyId());
            if (reply == null) throw new ResultException("回复编号必须是有效的！");
            if (!reply.getCommentId().equals(entity.getCommentId())) throw new ResultException("回复的评论不再该父评论下！");
        }
        return super.save(entity);
    }

    @Override
    protected Reply redundancy(Reply reply) {
        // 增加用户名、用户头像、回复用户名
        // 设置用户名、用户头像
        User user = userMapper.selectById(reply.getUserId());
        if (user == null) reply.setNickname("未知用户");
        else {
            reply.setNickname(user.getNickname());
            reply.setHeadImg(user.getImgPath());
        }
        // 设置回复用户名
        String replyName = "未知用户";
        if (reply.getReplyId() < 1){
            Comments comments = commentsMapper.selectById(reply.getCommentId());
            if (comments != null){
                user = userMapper.selectById(comments.getUserId());
                if (user != null) replyName = user.getNickname();
            }
        }else {
            Reply reply1 = replyMapper.selectById(reply.getReplyId());
            if (reply1 != null){
                user = userMapper.selectById(reply1.getUserId());
                if (user != null) replyName = user.getNickname();
            }
        }
        reply.setReplyName(replyName);
        return super.redundancy(reply);
    }

    /* 关键字 */
    static class UserIdKey extends SearchKey<Reply>{
        /**
         * 根据用户id查询回复
         */
        private UserIdKey(){super("uid");}
        @Override
        public void search(LambdaQueryWrapper<Reply> wrapper, String value) {
            wrapper.eq(Reply::getUserId, value);
            wrapper.orderByDesc(Reply::getCreationTime); // 时间排序
        }
    }

    static class CommentIdKey extends SearchKey<Reply>{
        /**
         * 根据父评论id查询回复
         */
        private CommentIdKey(){super("cid");}
        @Override
        public void search(LambdaQueryWrapper<Reply> wrapper, String value) {
            wrapper.eq(Reply::getCommentId, value);
        }
    }


}





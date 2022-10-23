package com.example.csust_hot_wall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 评论
 * @TableName comments
 */
@TableName(value ="comments")
@Data
public class Comments extends Entity implements Serializable {
    /**
     * 评论编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 回复id
     */
    private Integer replyId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 状态
     */
    private String state;

    // 所有评论信息增加评论用户名、文章名、文章作者、文章简介、回复用户名、回复评论信息
    /**
     * 评论作者
     */
    @TableField(exist = false)
    private String author;
    /**
     * 文章名
     */
    @TableField(exist = false)
    private String article;
    /**
     * 文章作者
     */
    @TableField(exist = false)
    private String articleAuthor;
    /**
     * 文章简介
     */
    @TableField(exist = false)
    private String articleIntro;
    /**
     * 回复用户名
     */
    @TableField(exist = false)
    private String reply;
    /**
     * 回复评论内容
     */
    @TableField(exist = false)
    private String replyContent;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Comments other = (Comments) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getArticleId() == null ? other.getArticleId() == null : this.getArticleId().equals(other.getArticleId()))
            && (this.getReplyId() == null ? other.getReplyId() == null : this.getReplyId().equals(other.getReplyId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getCreationTime() == null ? other.getCreationTime() == null : this.getCreationTime().equals(other.getCreationTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getArticleId() == null) ? 0 : getArticleId().hashCode());
        result = prime * result + ((getReplyId() == null) ? 0 : getReplyId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getCreationTime() == null) ? 0 : getCreationTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", articleId=").append(articleId);
        sb.append(", replyId=").append(replyId);
        sb.append(", userId=").append(userId);
        sb.append(", content=").append(content);
        sb.append(", creationTime=").append(creationTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", state=").append(state);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Comments stroke() {
        setId(null);
        setCreationTime(new Date());
        setUpdateTime(new Date());
        return this;
    }

    public boolean securityCheck() {
        return isAllNotNull(articleId,userId,content);
    }
}
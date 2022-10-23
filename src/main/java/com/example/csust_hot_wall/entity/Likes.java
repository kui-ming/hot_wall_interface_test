package com.example.csust_hot_wall.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

/**
 * 点赞
 * @TableName likes
 */
@TableName(value ="likes")
@Data
public class Likes extends Entity implements Serializable {
    /**
     * 用户id
     */
    @MppMultiId
    @TableField("user_id")
    private Integer userId;

    /**
     * 文章id
     */
    @MppMultiId
    @TableField("article_id")
    private Integer articleId;

    /**
     * 时间
     */
    private Date creationTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    // 点赞信息增加用户名、点赞文章名、文章作者名

    /**
     * 用户名
     */
    @TableField(exist = false)
    private String user;

    /**
     * 文章名
     */
    @TableField(exist = false)
    private String article;

    /**
     * 文章作者
     */
    @TableField(exist = false)
    private String author;

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
        Likes other = (Likes) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getArticleId() == null ? other.getArticleId() == null : this.getArticleId().equals(other.getArticleId()))
            && (this.getCreationTime() == null ? other.getCreationTime() == null : this.getCreationTime().equals(other.getCreationTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getArticleId() == null) ? 0 : getArticleId().hashCode());
        result = prime * result + ((getCreationTime() == null) ? 0 : getCreationTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", articleId=").append(articleId);
        sb.append(", creationTime=").append(creationTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Entity stroke() {
        setCreationTime(new Date());
        return this;
    }

    @Override
    public boolean securityCheck() {
        return isAllNotNull(userId,articleId);
    }
}
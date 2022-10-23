package com.example.csust_hot_wall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 文章
 * @TableName article
 */
@TableName(value ="article")
@Data
public class Article extends Entity implements Serializable {
    /**
     * 文章编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 类别id
     */
    private Integer categoryId;

    /**
     * 类别名
     */
    @TableField(exist = false)
    private String category;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名
     */
    @TableField(exist = false)
    private String author;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String intro;

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
     * 浏览人数
     */
    private Integer visitors;

    /**
     * 点赞人数
     */
    private Integer likes;

    /**
     * 图片列表
     */
    private String pictureList;

    /**
     * 状态
     */
    private String state;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Article stroke(){
        setId(null);
        this.setVisitors(null);
        this.setLikes(null);
        this.setCreationTime(new Date());
        this.setUpdateTime(new Date());
        this.setState(null);
        return this;
    }

    public boolean securityCheck() {
        return isAllNotNull(categoryId,userId,title,intro,content);
    }

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
        Article other = (Article) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getIntro() == null ? other.getIntro() == null : this.getIntro().equals(other.getIntro()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getCreationTime() == null ? other.getCreationTime() == null : this.getCreationTime().equals(other.getCreationTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getVisitors() == null ? other.getVisitors() == null : this.getVisitors().equals(other.getVisitors()))
            && (this.getLikes() == null ? other.getLikes() == null : this.getLikes().equals(other.getLikes()))
            && (this.getPictureList() == null ? other.getPictureList() == null : this.getPictureList().equals(other.getPictureList()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getIntro() == null) ? 0 : getIntro().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getCreationTime() == null) ? 0 : getCreationTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getVisitors() == null) ? 0 : getVisitors().hashCode());
        result = prime * result + ((getLikes() == null) ? 0 : getLikes().hashCode());
        result = prime * result + ((getPictureList() == null) ? 0 : getPictureList().hashCode());
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
        sb.append(", userId=").append(userId);
        sb.append(", title=").append(title);
        sb.append(", intro=").append(intro);
        sb.append(", content=").append(content);
        sb.append(", creationTime=").append(creationTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", visitors=").append(visitors);
        sb.append(", likes=").append(likes);
        sb.append(", pictureList=").append(pictureList);
        sb.append(", state=").append(state);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
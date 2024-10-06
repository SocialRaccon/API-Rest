package itst.social_raccoon_api.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity(name = "comment")
public class CommentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComment;
    private String comment;
    private Integer idUser;
    private Timestamp date;
    private Integer idPost;

    public CommentModel() {
    }

    public CommentModel(String comment, Integer idUser, Timestamp date, Integer idPost) {
        this.comment = comment;
        this.idUser = idUser;
        this.date = date;
        this.idPost = idPost;
    }

    public Integer getIdComment() {
        return idComment;
    }

    public void setIdComment(Integer idComment) {
        this.idComment = idComment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }

    @Override
    public String toString() {
        return "CommentModel{" + "idComment=" + idComment + ", comment='" + comment + '\'' + ", idUser=" + idUser + ", date=" + date + ", idPost=" + idPost + '}';
    }
}

package org.eric.telegrambots.model.pttnotify;

import org.eric.telegrambots.model.todobot.Chat;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "chats_boards")
public class ChatBoard implements Serializable {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private long id;

    @OneToOne
    private Chat chat;

    @OneToOne
    private Board board;

    @Column(name = "like_limit")
    private int likeLimit;

    @Column(name = "last_notify_post_id")
    private long lastNotifyPostId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getLikeLimit() {
        return likeLimit;
    }

    public void setLikeLimit(int likeLimit) {
        this.likeLimit = likeLimit;
    }

    public long getLastNotifyPostId() {
        return lastNotifyPostId;
    }

    public void setLastNotifyPostId(long lastNotifyPostId) {
        this.lastNotifyPostId = lastNotifyPostId;
    }
}

package org.eric.telegrambots.repository.pttnotify;

import org.eric.SuperTest;
import org.eric.telegrambots.model.pttnotify.Board;
import org.eric.telegrambots.model.pttnotify.ChatBoard;
import org.eric.telegrambots.model.todobot.Chat;
import org.eric.telegrambots.repository.todobot.ChatRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class ChatBoardRepositoryTest extends SuperTest {
    @Autowired
    private ChatBoardRepository chatBoardRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @Transactional
    @Rollback(true)
    public void test_query_chatboard_should_success() {

        Chat chat = new Chat();
        chat.setId(123);
        chat.setFirstName("eric");
        chat.setLastName("shih");
        chat.setUsername("eric_shih_is_good");
        chat = chatRepository.save(chat);

        Board board = new Board();
        board.setName("Gossiping");
        board = boardRepository.save(board);

        ChatBoard chatBoard = new ChatBoard();
        chatBoard.setBoard(board);
        chatBoard.setChat(chat);
        chatBoard.setLastNotifyPostId(12345678);
        chatBoard.setLikeLimit(50);
        chatBoard = chatBoardRepository.save(chatBoard);

        Optional<ChatBoard> chatBoardOpt = chatBoardRepository.findById(chatBoard.getId());
        ChatBoard result = chatBoardOpt.orElse(null);

        Assert.assertTrue(chatBoardOpt.isPresent());
        Assert.assertNotNull(result);

    }

    @Test
    @Transactional
    @Rollback(true)
    public void test_find_distinct_board_id_should_success() {

        Chat chat = new Chat();
        chat.setId(123);
        chat.setFirstName("eric");
        chat.setLastName("shih");
        chat.setUsername("eric_shih_is_good");
        chat = chatRepository.save(chat);

        Chat chat2 = new Chat();
        chat2.setId(456);
        chat2.setFirstName("kali");
        chat2.setLastName("kam");
        chat2.setUsername("kali_kam_is_good");
        chat2 = chatRepository.save(chat2);

        Board board = new Board();
        board.setName("aaa");
        board = boardRepository.save(board);

        Board board2 = new Board();
        board2.setName("bbb");
        board2 = boardRepository.save(board2);

        ChatBoard chatBoard = new ChatBoard();
        chatBoard.setBoard(board);
        chatBoard.setChat(chat);
        chatBoard.setLastNotifyPostId(12345678);
        chatBoard.setLikeLimit(50);
        chatBoard = chatBoardRepository.save(chatBoard);

        ChatBoard chatBoard2 = new ChatBoard();
        chatBoard2.setBoard(board2);
        chatBoard2.setChat(chat);
        chatBoard2.setLastNotifyPostId(12345678);
        chatBoard2.setLikeLimit(50);
        chatBoard2 = chatBoardRepository.save(chatBoard2);

        ChatBoard chatBoard3 = new ChatBoard();
        chatBoard3.setBoard(board);
        chatBoard3.setChat(chat2);
        chatBoard3.setLastNotifyPostId(12345678);
        chatBoard3.setLikeLimit(50);
        chatBoard3 = chatBoardRepository.save(chatBoard3);

        ChatBoard chatBoard4 = new ChatBoard();
        chatBoard4.setBoard(board2);
        chatBoard4.setChat(chat2);
        chatBoard4.setLastNotifyPostId(12345678);
        chatBoard4.setLikeLimit(50);
        chatBoard4 = chatBoardRepository.save(chatBoard4);

        List<Long> resultList = chatBoardRepository.findDistinctBoardId();

        Assert.assertNotNull(resultList);
        Assert.assertTrue(resultList.contains(board.getId()));
        Assert.assertTrue(resultList.contains(board2.getId()));
    }
}

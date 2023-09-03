package preonboard.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemoServiceTest {


    @Autowired
    private MemoService memoService;

    @Test
    public void testCreateMemo() {
        // given
        memoService.createMemo("TestMemo1 ", "Test Memo 1", 0);
        memoService.createMemo("TestMemo2 ", "Test Memo 2", 1);
        memoService.createMemo("TestMemo3 ", "Test Memo 3", 1);
        memoService.createMemo("TestMemo4 ", "Test Memo 4", 2);
        memoService.createMemo("TestMemo5 ", "Test Memo 5", 3);
        // when
        List<Memo> memos = memoService.getAllMemo();
        // 로그 출력
        for (Memo memo : memos) {
            System.out.println(memo);
            System.out.println(memo.getTitle());
            System.out.println(memo.getContent());
            System.out.println(memo.getParentId());
        }
        // then
//        assertEquals(1, memos.size());
//        Memo memo = memos.get(0);
//        assertEquals("Test Memo", memo.getTitle());
//        assertEquals("This is a test memo.", memo.getContent());

    }

    @Test
    public void testGetAllMemo() {
        // given
        // when
        List<Memo> memos = memoService.getAllMemo();

        // then
        for (Memo memo : memos) {
            System.out.println("ParentId :" + memo.getParentId());
            System.out.println( "Title :"+ memo.getTitle());
        }

    }


    @Test
    public void testGetPath() {
        //given
        int memberId = 5;

        //when
        String path = memoService.getPath(memberId);

        //then
        System.out.println(path);

    }
}


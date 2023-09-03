package preonboard.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class MemoService {

    @Autowired
    private DataSource dataSource;


    /**
     * Memo 생성
     *
     * @param title
     * @param content
     * @param parentId
     */
    public void createMemo(String title, String content, Integer parentId) {
        try (Connection connection = dataSource.getConnection()) {
            String insertQuery = "INSERT INTO Memo (title, content, parent_id) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, content);
                preparedStatement.setInt(3, parentId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Memo 전체 조회
     *
     * @return
     */
    public List<Memo> getAllMemo() {
        List<Memo> memos = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String selectQuery = "SELECT * FROM Memo";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Memo memo = new Memo();
                    memo.setId(resultSet.getInt("id"));
                    memo.setTitle(resultSet.getString("title"));
                    memo.setContent(resultSet.getString("content"));
                    memo.setParentId(resultSet.getInt("parent_id"));
                    memos.add(memo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memos;
    }


    /**
     * Memo Path 조회
     * @param memoId
     * @return
     */
    public String getPath(int memoId) {
        return getPathRecursive(memoId, "");
    }
    private String getPathRecursive(int memoId, String path) {
        try{
            Connection connection = dataSource.getConnection();
            String selectQuery = "SELECT * FROM Memo WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, memoId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int parentId = resultSet.getInt("parent_id");
                String title = resultSet.getString("title");
                if (path.isEmpty()) {
                    path = title;
                } else {
                    path = title + " / " + path;
                }
                if (parentId != 0) {
                    path = getPathRecursive(parentId, path);
                }
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return path;
    }
}

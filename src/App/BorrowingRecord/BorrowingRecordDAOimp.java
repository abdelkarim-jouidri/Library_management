package App.BorrowingRecord;

import App.Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class BorrowingRecordDAOimp implements BorrowingRecordDAO{
    @Override
    public BorrowingRecord get(int id) throws SQLException {

        return null;
    }

    @Override
    public ArrayList<BorrowingRecord> getAll() throws SQLException {
        return null;
    }

    @Override
    public int save(BorrowingRecord instance) throws SQLException {
        return 0;
    }

    @Override
    public BorrowingRecord insert(BorrowingRecord instance) throws SQLException {
        Connection con = Database.getConnection();
        String SQL = "INSERT INTO borrowing_record (book_copy_id, borrower_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, instance.getBookCopy().getId());
        ps.setInt(2, instance.getLibraryMember().getId());
        ps.setDate(3, instance.getBorrowDate());

        ps.setDate(4,instance.getReturnDate());
        int affectedRows = ps.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Inserting borrowing record failed, no rows affected.");
        }

        try (var generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                instance.getBookCopy().markAsBorrowed();
                instance.setId(generatedKeys.getInt(1));
                return instance;
            } else {
                throw new SQLException("Inserting borrowing record failed, no ID obtained.");
            }
        }
    }

    @Override
    public int update(BorrowingRecord instance) throws SQLException {
        return 0;
    }

    @Override
    public int delete(BorrowingRecord instance) throws SQLException {
        return 0;
    }
}

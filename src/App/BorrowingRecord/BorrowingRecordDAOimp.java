package App.BorrowingRecord;

import App.BookCopy.BookCopy;
import App.BookCopy.BookCopyDAOimp;
import App.Database.Database;
import App.LibraryMember.LibraryMember;
import App.LibraryMember.LibraryMemberDAOimp;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class BorrowingRecordDAOimp implements BorrowingRecordDAO{
    private BookCopyDAOimp bookCopyDAO = new BookCopyDAOimp();
    private LibraryMemberDAOimp libraryMemberDAO = new LibraryMemberDAOimp();
    @Override
    public BorrowingRecord get(int id) throws SQLException {

        return null;
    }

    // Overloading of the get method
    public BorrowingRecord get(int book_ID, int member_ID) throws SQLException {
        Connection con = Database.getConnection();
        BorrowingRecord record = null;
        String SQL = "SELECT record.* FROM borrowing_record AS record \n" +
                "INNER JOIN library_members AS membr ON membr.id = record.borrower_id\n" +
                "INNER JOIN books_copies AS copy ON copy.id = record.book_copy_id\n" +
                "INNER JOIN books ON copy.book_id = books.id\n" +
                "WHERE book_id = ?\n" +
                "AND membr.id = ?\n"+
                "LIMIT 1; ";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setInt(1, book_ID);
        ps.setInt(2, member_ID);

        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            int ID = rs.getInt("id");
            int book_copy_ID = rs.getInt("book_copy_id");
            System.out.println("book_copy_id : "+ book_copy_ID);
            BookCopy bookCopy = bookCopyDAO.get(book_copy_ID);
            int borrower_ID = rs.getInt("borrower_id");
            LibraryMember member = libraryMemberDAO.get(borrower_ID);
            Date borrow_date = rs.getDate("borrow_date");
            LocalDate currentDate = LocalDate.now();
            record = new BorrowingRecord(ID, bookCopy, member, borrow_date, Date.valueOf(currentDate));
            bookCopy.markAsAvailable();
            bookCopyDAO.updateStatus(bookCopy);
        }

        return record;
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
            throw new SQLException("Inserting borrowing record failed");
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
        Connection connection = Database.getConnection();
        String SQL = "UPDATE borrowing_record SET book_copy_id = ?, borrower_id = ?, borrow_date = ?, return_date = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setInt(1, instance.getBookCopy().getId());
        ps.setInt(2, instance.getLibraryMember().getId());
        ps.setDate(3, instance.getBorrowDate());
        ps.setDate(4, instance.getReturnDate());
        ps.setInt(5, instance.getId());

        int rowsAffected = ps.executeUpdate();

        return rowsAffected;
    }

    @Override
    public int delete(BorrowingRecord instance) throws SQLException {
        return 0;
    }
}

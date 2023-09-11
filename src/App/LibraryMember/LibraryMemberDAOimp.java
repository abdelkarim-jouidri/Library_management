package App.LibraryMember;

import App.DAO;
import App.Database.Database;

import java.sql.*;
import java.util.ArrayList;

public class LibraryMemberDAOimp implements LibraryMemberDAO{
    @Override
    public LibraryMember get(int id) throws SQLException {
        LibraryMember libraryMember = null;
        Connection con = Database.getConnection();
        String SQL = "SELECT * FROM library_members ";
        PreparedStatement ps = con.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            int member_ID = rs.getInt("id");
            String fname = rs.getString("fname");
            String lname = rs.getString("lname");
            libraryMember = new LibraryMember(member_ID, fname, lname);
        }
        return libraryMember;
    }

    @Override
    public ArrayList<LibraryMember> getAll() throws SQLException {
        return null;
    }

    @Override
    public int save(LibraryMember instance) throws SQLException {
        return 0;
    }

    @Override
    public LibraryMember insert(LibraryMember instance) throws SQLException {
        Connection con = Database.getConnection();
        String SQL = "INSERT INTO library_members(fname, lname) VALUES (? ,?)";
        PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, instance.getFirstName());
        ps.setString(2, instance.getLastName());
        int result = ps.executeUpdate();
        if (result == 1){
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()){
                int generatedID = generatedKeys.getInt(1);
                instance.setId(generatedID);
            }
        }
        return instance;
    }

    @Override
    public int update(LibraryMember instance) throws SQLException {
        return 0;
    }

    @Override
    public int delete(LibraryMember instance) throws SQLException {
        return 0;
    }
}

package App;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DAO<Type> {
    // Defining The Generic CRUD Operations
    Type get(int id) throws SQLException;
    ArrayList<Type> getAll() throws SQLException;
    int save(Type instance) throws SQLException;
    Type insert(Type instance) throws SQLException;
    int update(Type instance) throws SQLException;
    int delete(Type instance) throws SQLException;


}

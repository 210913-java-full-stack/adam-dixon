package DAOs;

import ListStructure.MyList;

import java.sql.SQLException;

public interface CrudOperations<E> {

//    CRUD operations will execute SQL commands within the SQL database
//    CRUD operations will include:
//    1. Create
//        Saves an object to the SQL database
        public void save(E e) throws SQLException;
//    2. Read
//        Queries the SQL database for information
        public E getItemByID(String e) throws SQLException;
        public E getItemByID(int e) throws SQLException;
        public E getItemByID(String e, String c) throws SQLException;
        public MyList<E> getAllItems() throws SQLException;
        public MyList<E> getAllItems(String e) throws SQLException;
//    3. Delete
        public void deleteByID(E e) throws SQLException;

}

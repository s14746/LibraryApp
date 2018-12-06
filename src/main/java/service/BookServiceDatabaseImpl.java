package service;

import domain.Book;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BookServiceDatabaseImpl implements BookService {

    private final DataSource dataSource;
    private final DateService dateService;

    private HashSet<Book> books = new HashSet<>();
    private boolean shouldSetCreateTime = true;
    private boolean shouldSetUpdateTime = true;
    private boolean shouldSetLastReadingTime = true;

    public BookServiceDatabaseImpl(DataSource dataSource, DateService dateService) {
        this.dataSource = dataSource;
        this.dateService = dateService;

        if (!tableExists("book")) {
            createBookTable();
        }
    }

    @Override
    public void create(Book newBook) {
        Optional<Book> book = readInternal(newBook.getId());

        if (book.isPresent()) {
            throw new IllegalArgumentException();
        }

        String sql = "INSERT INTO book (id, title, author_name, author_surname, year_of_publishment, publishing_house, availability, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newBook.getId());
            statement.setString(2, newBook.getTitle());
            statement.setString(3, newBook.getAuthorName());
            statement.setString(4, newBook.getAuthorSurname());
            statement.setInt(5, newBook.getYearOfPublishment());
            statement.setString(6, newBook.getPublishingHouse());
            statement.setBoolean(7, newBook.isAvailability());

            if (shouldSetCreateTime) {
                statement.setTimestamp(8, Timestamp.valueOf(dateService.now()));
            } else {
                statement.setTimestamp(8, null);
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Collection<Book> readAll() {
        for (Book book : books) {
            if (shouldSetLastReadingTime) {
                book.setLastReadingTime(dateService.now());
            }
        }
        return books;
    }

    @Override
    public Book read(int id) {
        return readInternal(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Book> readByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().startsWith(title))
                .collect(Collectors.toList());
    }

    @Override
    public void update(Book newBook) {
        Optional<Book> updateBook = readInternal(newBook.getId());
        if (!updateBook.isPresent()) {
            throw new IllegalArgumentException();
        }

        delete(newBook);

        if (shouldSetUpdateTime) {
            newBook.setUpdateTime(dateService.now());
        }

        create(newBook);
    }

    @Override
    public void delete(Book deleteBook) {
        Optional<Book> deleteBook2 = readInternal(deleteBook.getId());
        if (!deleteBook2.isPresent()) {
            throw new IllegalArgumentException();
        }

        String sql = "DELETE FROM book WHERE id = ?";

        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, deleteBook.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM book";

        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void deleteByPublishingHouseAndYearOfPublishment(String publishingHouse, int yearOfPublishment) {

        String regex = "^.*" + publishingHouse + ".*$";
        Pattern pattern = Pattern.compile(regex);
        books.removeIf(book -> pattern.matcher(book.getPublishingHouse()).find() && book.getYearOfPublishment() < yearOfPublishment);

    }

    public void setShouldSetCreateTime(boolean shouldSetCreateTime) {
        this.shouldSetCreateTime = shouldSetCreateTime;
    }

    public void setShouldSetUpdateTime(boolean shouldSetUpdateTime) {
        this.shouldSetUpdateTime = shouldSetUpdateTime;
    }

    public void setShouldSetLastReadingTime(boolean shouldSetLastReadingTime) {
        this.shouldSetLastReadingTime = shouldSetLastReadingTime;
    }

    private Optional<Book> readInternal(int id) {
        String sql = "SELECT * FROM book WHERE id = ?";

        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return Optional.empty();
            }

            Book book = new Book();
            book.setId(resultSet.getInt("id"));
            book.setTitle(resultSet.getString("title"));
            book.setAuthorName(resultSet.getString("author_name"));
            book.setAuthorSurname(resultSet.getString("author_surname"));
            book.setYearOfPublishment(resultSet.getInt("year_of_publishment"));
            book.setPublishingHouse(resultSet.getString("publishing_house"));
            book.setAvailability(resultSet.getBoolean("availability"));
            Timestamp create_time = resultSet.getTimestamp("create_time");
            if (create_time != null) {
                book.setCreateTime(create_time.toLocalDateTime());
            }

            Timestamp update_time = resultSet.getTimestamp("update_time");
            if (update_time != null) {
                book.setUpdateTime(update_time.toLocalDateTime());
            }

            book.setLastReadingTime(dateService.now());
            return Optional.of(book);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private boolean tableExists(String tableName) {
        try {
            ResultSet rs = dataSource.getConnection().getMetaData().getTables(null, null, null, null);
            boolean tableExists = false;
            while (rs.next()) {
                if (tableName.equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
                    tableExists = true;
                    break;
                }
            }
            return tableExists;
        } catch (SQLException e) {
            return false;
        }
    }

    private void createBookTable() {
        String sql = "CREATE TABLE book (\n" +
                "   id INT NOT NULL,\n" +
                "   title VARCHAR(255),\n" +
                "   author_name VARCHAR(255),\n" +
                "   author_surname VARCHAR(255),\n" +
                "   year_of_publishment INT,\n" +
                "   publishing_house VARCHAR(255),\n" +
                "   availability BOOLEAN,\n" +
                "   create_time TIMESTAMP,\n" +
                "   update_time TIMESTAMP,\n" +
                "   PRIMARY KEY (id)\n" +
                ");";

        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

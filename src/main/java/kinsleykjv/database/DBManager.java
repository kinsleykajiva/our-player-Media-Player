package kinsleykjv.database;


import java.io.File;
import java.sql.*;
import java.util.ArrayList;

/**
 * this class uses SQLite as a  database
 */
public class DBManager {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }


    public static class AllSongs {
        private String title;
        private String songPath;
        private String parentFolder;
        private int isFavorite = 0;

        public AllSongs(String title, String songPath, String parentFolder, int isFavorite) {
            this.title = title;
            this.songPath = songPath;
            this.parentFolder = parentFolder;
            this.isFavorite = isFavorite;
        }

        public String getTitle() {
            return title;
        }

        public String getSongPath() {
            return songPath;
        }

        public String getParentFolder() {
            return parentFolder;
        }

        public int getIsFavorite() {
            return isFavorite;
        }


    }

    public ArrayList<AllSongs> getAllOfParentFolder(String parentFolder) {
        ArrayList<AllSongs> allSongs = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            String query = "SELECT * FROM allSongs WHERE parentFolder = '" + parentFolder + "'";
            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                allSongs.add(new AllSongs(resultSet.getString("title"), resultSet.getString("songPath"), resultSet.getString("parentFolder"), resultSet.getInt("isFavorite")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allSongs;
    }

    public void addSong(AllSongs allSongs) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT  OR IGNORE INTO allSongs (title, songPath, parentFolder, isFavorite) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, allSongs.getTitle());
            preparedStatement.setString(2, allSongs.getSongPath());
            preparedStatement.setString(3, allSongs.getParentFolder());
            preparedStatement.setInt(4, allSongs.getIsFavorite());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
       /* try (Statement statement = connection.createStatement()) {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            var songPath = allSongs.getSongPath().replaceAll("'", "\\'");
            var query = "INSERT OR IGNORE INTO allSongs (title, songPath, parentFolder, isFavorite) VALUES " +
                    "('" + allSongs.getTitle().replaceAll("'", "\\'") + "', '" + songPath + "', '" +
                    allSongs.getParentFolder().replaceAll("'", "\\'") + "', "
                    + allSongs.getIsFavorite() + ");";
            System.out.println("query::" + query);
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    private void createTables() {
        var allSongsTable = """
                CREATE TABLE IF NOT EXISTS allSongs (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT,
                    songPath TEXT,
                    parentFolder TEXT,
                    isFavorite INTEGER,
                    UNIQUE(songPath)
                );
                    """;
        var playlistsNamesTable = """
                CREATE TABLE IF NOT EXISTS playlistsNames (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT
                );
                     """;
        var playlistTable = """
                    CREATE TABLE IF NOT EXISTS playlist (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT,
                        songs TEXT
                    );
                """;
        try (Statement statement = connection.createStatement()) {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.execute(allSongsTable);
            statement.execute(playlistsNamesTable);
            statement.execute(playlistTable);
            System.out.println("Tables created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connect() {

        try {
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + PathToUserDocumentsFolder() + "ourplayer.db");
            createTables();
        } catch (final SQLException ex) {
            System.err.println("Error connecting to database: " + ex.getMessage());
        }
    }

    private String PathToUserDocumentsFolder() {
        String path = System.getProperty("user.home") + File.separator + "Documents";
        path += File.separator + DATABASE_FOLDER_NAME;
        File customDir = new File(path);
        if (customDir.exists()) {
            System.out.println(customDir + " already exists");
        } else if (customDir.mkdirs()) {
            System.out.println(customDir + " was created");
        } else {
            System.out.println(customDir + " was not created");
        }
        return path + File.separator;
    }


    // ----


    /**
     * @return Database folder name <b>with out</b> separator [example:XR3DataBase]
     */
    public static String getDatabaseFolderName() {
        return DATABASE_FOLDER_NAME;
    }

    private static final String DATABASE_FOLDER_NAME = "ourPlayerDataBase";
}

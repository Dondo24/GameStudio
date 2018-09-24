package GameStudio.Service.score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import GameStudio.Entity.Score;


public class ScoreServiceJDBC implements ScoreService {
    private static final String URL = "jdbc:postgresql://localhost/gamestudio";
    private static final String USER = "postgres";
    private static final String PASS = "123";

    private static final String INSERT =
            "INSERT INTO score (ident,game, username, points, played_on) VALUES (?,?, ?, ?, ?)";

    private static final String SELECT =
            "SELECT game, username, points, played_on FROM score " +
                    "WHERE game = ? ORDER BY points DESC";

    private static final String Select_all ="Select count(*) from score";
    
    @Override
    public void addScore(Score score)   {
        try (Connection c = DriverManager.getConnection(URL, USER, PASS)) {
            try (PreparedStatement ps = c.prepareStatement(INSERT)) {
            	 ps.setString(1, score.getGame());
                 ps.setString(2, score.getUsername());
                 ps.setInt(3, score.getPoints());
                 ps.setTimestamp(4, new Timestamp(score.getPlayedOn().getTime()));
                ps.execute();
            } catch (SQLException e) {
              e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getLastId(Connection c) throws SQLException {
    	Statement stmt = c.createStatement();
		stmt = c.createStatement();
       ResultSet rs = stmt.executeQuery(Select_all);
       int LastId = 0 ;
       while (rs.next()) {
    	   LastId= rs.getInt(1);
    	   System.out.println(LastId);
       }
		return LastId;
		
	}

	@Override
    public List<Score> getBestScores(String gameName){
        List<Score> scores = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(URL, USER, PASS)) {
            try (PreparedStatement ps = c.prepareStatement(SELECT)) {
                ps.setString(1, gameName);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Score s = new Score(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getDate(4)
                    );
                    scores.add(s);
                }

          
            } catch (SQLException e) {
                e.printStackTrace();
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }
        
        return scores;
    }


	
}

package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	// All JDBC code here
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	String user = "student";
	String pass = "student";

	@Override
	public Film findFilmById(int filmId) throws SQLException {

		System.out.println("Enter the film ID you are looking for: ");

		Film film = null;

		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "select title, release_year, rating, description from film";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet filmResult = stmt.executeQuery();

		if (filmResult.next()) {
			System.out.println(filmResult.getString("title") + " " + filmResult.getInt("release_year") + " "
					+ filmResult.getString("rating") + " " + filmResult.getString("description"));

		}
		filmResult.close();
		stmt.close();

		conn.close();

		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		// TODO Auto-generated method stub
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {

		List<Actor> actors = new ArrayList<>();
		Actor actor = null;

		try {

			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT act.id, act.first_name, act.last_name"
					+ "FROM actor act JOIN film_actor fa ON act.id = fa.actor_id" + "WHERE film_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet actorsResult = stmt.executeQuery();

			while (actorsResult.next()) {

				System.out.println("Actor ID is " + actorsResult.getInt("id") + ", "
						+ actorsResult.getString("first_name") + " " + actorsResult.getString("last_name"));
				actors.add(actor);
			}

			actorsResult.close();

			stmt.close();

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}

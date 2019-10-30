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

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Film findFilmById(int filmId) {

		Film film = null;

		String sql = "SELECT * FROM film flm JOIN language lang ON flm.language_id = lang.id WHERE flm.id = ?";
		try {

			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet fr = stmt.executeQuery();

			if (fr.next()) {

				film = new Film();
				film.setId(fr.getInt("flm.id"));
				film.setTitle(fr.getString("flm.title"));
				film.setDescription(fr.getString("flm.description"));
				film.setReleaseYear(fr.getInt("flm.release_year"));
				film.setRating(fr.getString("flm.rating"));
				film.setLanguage(fr.getString("lang.name"));
				film.setCast(findActorsByFilmId(filmId));
				
			}

			fr.close();

			stmt.close();

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		String sql = "SELECT * FROM actor WHERE id = ?";

		try {

			Connection conn = DriverManager.getConnection(URL, user, pass);

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet ar = stmt.executeQuery();

			if (ar.next()) {
				actor = new Actor(ar.getInt("id"), ar.getString("first_name"), ar.getString("last_name"));
//				actor.setId(ar.getInt("id"));
//				actor.setFirstName(ar.getString("first_name"));
//				actor.setLastName(ar.getString("last_name"));
				
				
			}

			ar.close();

			stmt.close();

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {

		Actor actor = null;
		List<Actor> actorList = new ArrayList<>();
		
		String sql = "SELECT * FROM actor JOIN film_actor ON actor.id = film_actor.actor_id "
				+ " JOIN film on film_actor.film_id = film.id WHERE film.id = ?";

		try {

			Connection conn = DriverManager.getConnection(URL, user, pass);

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet ar = stmt.executeQuery();

			while (ar.next()) {
				actor = new Actor();
				actor.setId(ar.getInt("id"));
				actor.setFirstName(ar.getString("first_name"));
				actor.setLastName(ar.getString("last_name"));
				
				actorList.add(actor);

			}

			ar.close();

			stmt.close();

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actorList;
	}

	@Override
	public List<Film> findFilmByKeyword(String keyword) {

		Film film = null;
		List<Film> filmListKeyword = new ArrayList<>();
//		List<Actor> actors = new ArrayList<>();
		String sql = "SELECT *"
				+ " FROM film film JOIN language lang ON film.language_id = lang.id"
				+ " WHERE film.title LIKE ? OR film.description LIKE ?";
		try {

			Connection conn = DriverManager.getConnection(URL, user, pass);

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");
			ResultSet fr = stmt.executeQuery();

			while (fr.next()) {
				film = new Film();
				film.setId(fr.getInt("id"));
				film.setTitle(fr.getString("title"));
				film.setDescription(fr.getString("description"));
				film.setReleaseYear(fr.getInt("release_year"));
				film.setRating(fr.getString("rating"));
				film.setLanguage(fr.getString("lang.name"));
				
				findActorsByFilmId(fr.getInt("id"));
//				findActorById(fr.getInt("id"));
				filmListKeyword.add(film);
				

			}

			fr.close();

			stmt.close();

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return filmListKeyword;

	}

}

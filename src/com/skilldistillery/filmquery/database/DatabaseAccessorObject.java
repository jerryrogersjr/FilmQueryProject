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

		String sql = "SELECT film.id, film.title, film.release_year, film.rating, film.description, lang.name, flist.actors"
				+ " FROM film film JOIN language lang ON film.language_id = lang.id"
				+ " JOIN film_list flist ON film.id = flist.FID WHERE film.id = ?";
		try {

			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet fr = stmt.executeQuery();

			if (fr.next()) {

				film = new Film();
				film.setId(fr.getInt("id"));
				film.setTitle(fr.getString("title"));
				film.setDescription(fr.getString("description"));
				film.setReleaseYear(fr.getInt("release_year"));
				film.setRating(fr.getString("rating"));
				film.setLanguage(fr.getString("lang.name"));
				film.setActors(fr.getString("actors"));
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
		String sql = "SELECT act.id, act.first_name, act.last_name FROM actor act WHERE act.id = ?";

		try {

			Connection conn = DriverManager.getConnection(URL, user, pass);

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet ar = stmt.executeQuery();

			if (ar.next()) {

				int actId = ar.getInt("act.id");
				String firstName = ar.getString("act.first_name");
				String lastName = ar.getString("act.last_name");

				actor = new Actor(actId, firstName, lastName);
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
		actorList = null;

		String sql = "SELECT act.id, act.first_name, act.last_name"
				+ " FROM actor act JOIN film_actor film ON act.id = film.actor_id" + "WHERE film.id = ?";

		try {

			Connection conn = DriverManager.getConnection(URL, user, pass);

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet ar = stmt.executeQuery();

			while (ar.next()) {
				int actorId = ar.getInt("act.id");
				String firstName = ar.getString("act.first_name");
				String lastName = ar.getString("act.last_name");

				actor = new Actor(actorId, firstName, lastName);

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
		List<Film> filmListKeyword = null;

		String sql = "SELECT film.id, film.title, film.release_year, film.rating, film.description, lang.name, flist.actors"
				+ " FROM film film JOIN language lang ON film.language_id = lang.id"
				+ " JOIN film_list flist ON film.id = flist.FID WHERE film.title LIKE ? OR film.description LIKE ?";
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
				film.setActors(fr.getString("actors"));
				filmListKeyword = new ArrayList<>();
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

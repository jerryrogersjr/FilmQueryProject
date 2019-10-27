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
		List<Actor> actorList = new ArrayList<>();

		String sql = "SELECT film.id, film.title, film.release_year, film.rating, film.description, lang.name, actorList.actors"
				+ "FROM film film JOIN language lang ON film.language_id = language.id"
				+ "JOIN film_list actorList ON film.id = actorList.FID" + "WHERE film.id = ?";
		try {

			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet fr = stmt.executeQuery();

			if (fr.next()) {

				actorList = findActorsByFilmId(filmId);

				film = new Film(fr.getInt("id"), fr.getString("title"), fr.getString("description"),
						fr.getInt("release_year"), fr.getInt("language_id"), fr.getInt("rental_duration"),
						fr.getInt("length"), fr.getDouble("replacement_cost"), fr.getString("rating"),
						fr.getString("special_feautures"), actorList, fr.getString("lang.name"));
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
		String sql = "SELECT act.id, act.first_name, act.last_name FROM actor act WHERE id = ?";

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

		String sql = "SELECT act.id, act.first_name, act.last_name"
				+ "FROM actor act JOIN film_actor fa ON act.id = fa.actor_id" + "WHERE film_id = ?";

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
//		System.out.println("***");
		Film film = null;
		List<Film> filmList = new ArrayList<>();
		List<Actor> actorList = new ArrayList<>();

		String sql = "SELECT film.title, film.description, film.release_year,film.rating, language.name FROM film JOIN language ON film.language_id = language.id "
				+ "WHERE film.title LIKE ?" + "OR film.description LIKE ?";
		try {

			Connection conn = DriverManager.getConnection(URL, user, pass);

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");
			ResultSet fr = stmt.executeQuery();

			while (fr.next()) {
				actorList = findActorsByFilmId(fr.getInt("film.id"));
				film = new Film(fr.getInt("id"), fr.getString("title"), fr.getString("description"),
						fr.getInt("release_year"), fr.getInt("language_id"), fr.getInt("rental_duration"),
						fr.getInt("length"), fr.getDouble("replacement_cost"), fr.getString("rating"),
						fr.getString("special_feautures"), actorList, fr.getString("language.name"));

				filmList.add(film);

			}

			fr.close();

			stmt.close();

			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filmList;
	}

}

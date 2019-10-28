package com.skilldistillery.filmquery.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	private Film film;
	private List<Film> filmList = new ArrayList<>();
	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();

	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {

		do {

			System.out.println("+------------ MENU ------------+");
			System.out.println("|                              |");
			System.out.println("| 1. Search Film By ID         |");
			System.out.println("|                              |");
			System.out.println("| 2. Search Film By Keyword    |");
			System.out.println("|                              |");
			System.out.println("| 3. Exit App                  |");
			System.out.println("|                              |");
			System.out.println("+------------------------------+");
			System.out.println();

			String selection = input.next();
			switch (selection) {

			case "1":
				System.out.println("Enter the film ID you'd like to see: ");
				String idInput = input.next();
				try {
					film = db.findFilmById(Integer.parseInt(idInput));
				} catch (NumberFormatException e) {
					System.err.println("Please enter an appropriate ID NUMBER!");
				}

				if (film == null) {
					System.err.println("\nNo film found for that ID\n");
				} else {
					System.out.println(film);
//				submenu(input); //
				}
				break;
			case "2":
				System.out.println("Enter keyword(s) to search for films: ");
				String keyword = input.next();

				filmList = db.findFilmByKeyword(keyword);

				if (filmList.isEmpty()) {
					System.err.println("\nNo film found for that keyword entry\n");

				} else
					System.out.println(filmList);

				break;
			case "3":
				System.out.print("\nGoodbye!");
				System.exit(0);
				break;
			default:
				System.err.print("Wrong Entry, please enter an appropriate selection.\n");
				break;
			}

		} while (true);

	}

//	private void submenu(Scanner input) {
//		// TODO Auto-generated method stub
//		System.out.println("Would you like to see all column details of this movie?");
//		System.out.println("1: Yes");
//		System.out.println("2: No");
//		String idInput = input.next();
//
//		try {
//			film = db.findFilmById(Integer.parseInt(idInput));
//		} catch (NumberFormatException e) {
//			System.err.println("Please enter an appropriate NUMBER!");
//		}
//
////			System.out.println(film);
//		startUserInterface(input);
//
//	}

}

package com.skilldistillery.filmquery.app;

import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

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
		// menu goes here
		do {

			System.out.println("+------------ MENU ------------+");
			System.out.println("|                              |");
			System.out.println("| 1. Lookup Film By ID         |");
			System.out.println("|                              |");
			System.out.println("| 2. Lookup Film By Keyword    |");
			System.out.println("|                              |");
			System.out.println("| 3. Exit App                  |");
			System.out.println("|                              |");
			System.out.println("+------------------------------+");
			System.out.println();
			
			String userInput = input.next();
			switch (userInput) {
			
			case "1":
				
				break;
			case "2":
				
				break;
			case "3":
				System.out.println("Thanks for looking around.\nGoodbye!");
				System.exit(0);
				break;
			default:
				System.err.println("Wrong Entry, please enter a numerical selection from menu.\n");
				break;
			}

		} while (!input.hasNext("3"));

	}
}

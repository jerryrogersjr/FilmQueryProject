## Film Query Project

This is our week 7, end of week project. The program is a film
query from mySql via Java.

Created a command-line application that retrieves and displays film data.
It is menu-based, allowing the user to choose actions and submit query data.

Example:
```
+------------ MENU ------------+
|                              |
| 1. Search Film By ID         |
|                              |
| 2. Search Film By Keyword    |
|                              |
| 3. Exit App                  |
|                              |
+------------------------------+

1
Enter the film ID you'd like to see:
444

Film Title = HUSTLER PARTY
Description = A Emotional Reflection of a Sumo Wrestler And a Monkey who must Conquer a Robot in The Sahara Desert
ReleaseYear = 2011
Rating = NC17
Language = English
Cast = Alec Wayne, Angelina Astaire, Minnie Kilmer, Christopher West
```

### Overview

* User Story 1
The user is presented with a menu in which they can choose to:

Look up a film by its id.
Look up a film by a search keyword.
Exit the application.
* User Story 2
If the user looks up a film by id, they are prompted to enter the film id. If the film is not found, they see a message saying so. If the film is found, its title, year, rating, and description are displayed.

* User Story 3
If the user looks up a film by search keyword, they are prompted to enter it. If no matching films are found, they see a message saying so. Otherwise, they see a list of films for which the search term was found anywhere in the title or description, with each film displayed exactly as it is for User Story 2.

* User Story 4
When a film is displayed, its language (English,Japanese, etc.) is also displayed.

* User Story 5
When a film is displayed, the list of actors in its cast is displayed along with the title, year, rating, and description.


### Technologies Used
* MacBook Pro
* Eclipse
* Atom  
* Java
* mySql
* Command Line

### Topics

* SQL Queries - The SELECT Statement
* SQL Queries - Predicates and Functions
* Introduction to JDBC
* SQL Queries - Joins
* Object-Relational Mapping (ORM)

### Lessons Learned

Well, I still fill like I'm behind in all but I am progressing forward
and learning. In terms of this Film Query, I think probably my biggest
rabbit hole was the errors getting thrown on mySql syntax. However, I
would put the Exact same syntax in the command line, run, and success.
What I found was that as I formated the SQL statement in eclipse,

ex.
```
String sql = "SELECT film.id, film.title, film.release_year, film.rating, film.description, lang.name, flist.actors"
				+ " FROM film film JOIN language lang ON film.language_id = lang.id"
				+ " JOIN film_list flist ON film.id = flist.FID WHERE film.title LIKE ? OR film.description LIKE ?";
```
there would be an error with the '+' not leaving a space. So as you look
above, instead of 'lang.id JOIN film_list' it would crunch to
'lang.idJOIN film_list'. Notice the intentional space after the ' + " '
now. It's the little things, but like I said above, I am in school to
learn so whala!

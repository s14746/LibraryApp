Feature: LibraryApp
  As a: librarian
  In order to: delete a book from the book collection
  I: would like to delete books with a given publishing house and year of publication

  Scenario Outline: Delete book with given publishing house and before given year of publication

    Given list of books
      | 1 | Beskidzka Oficyna Wydawnicza | 1999 |
      | 2 | Wydawnictwo GREG             | 2010 |
      | 3 | Świat Książki                | 1990 |
      | 4 | Świat Książki                | 2005 |
      | 5 | Beskidzka Oficyna Wydawnicza | 2005 |
      | 6 | Wydawnictwo GREG             | 2012 |

    When we delete books published by house <publishingHouse> before <date>
    Then book with id <bookId> should be deleted
    But book with id <bookId2> should not be deleted
    Examples:
      | publishingHouse    | date | bookId | bookId2 |
      | "Oficyn."          | 2000 | 1      | 5       |
      | "Wydawnictwo GREG" | 2011 | 2      | 6       |
      | "Świat Książ*"     | 1995 | 3      | 4       |
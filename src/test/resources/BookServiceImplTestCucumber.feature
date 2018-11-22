Feature: LibraryApp
  As a: reader
  In order to: find the right books
  I: would like to find books with a given title

  Scenario Outline: Find books with given title

    Given the list of books
      | 1 | Pan Tadeusz     |
      | 2 | Dziady cz. 1    |
      | 3 | Hobbit          |
      | 4 | Dzieci Hurina   |
      | 5 | Dawca przysiegi |

    When we find books starting with title <title>
    Then  we have a result list <result>

    Examples:
      | title    | result |
      | "Pan"    | 1      |
      | "Dziady" | 2      |
      | "D"      | 2      |
      | "D"      | 4      |
      | "D"      | 5      |
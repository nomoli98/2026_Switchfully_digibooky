# Story 1: Get all books

As a user I want to get all books registered in the system, so I can see which books are offered by
Digibooky.

These books should contain an ISBN, title and the author's last and first name Prioritization:
Must-Have

## Our decision : 

# Story 2: Show details of book

As a user I want to get the details of a single book, so I can inspect a single book more closely.

ISBN, title, author & a small summary make up all the details of a single book. Prioritization:
Must-Have

## Our decision :

# Story 3: Search a book by ISBN

As a user I want to search for a book by entering a part of the ISBN, so I can efficiently find a
specific book.

I should be able to enter wildcards if I don't know the complete ISBN. Hint: use regex for the
wildcards Prioritization: Must-Have

## Our decision :

# Story 4: Search a book by title

As a user I want to search for a book by entering a part of the title, so I can efficiently find a
specific book.

I should be able to enter wildcards if I don't know the complete title. Prioritization: Must-Have

## Our decision :

# Story 5: Search a book by author

As a user I want to search for a book by entering a part of the author, so I can efficiently find a
specific book. I should be able to enter wildcards if I don't know the complete name of the author.
It should be possible to use only the first name, only the last name or both.

Prioritization: Must-Have

## Our decision :

# Story 6A: Register as member

As a user I want to be able to register myself as a new Digibooky member, so I can perform
additional actions. This member should have a unique INSS (social security number), last name, first
name, email, street name, street number, postal code and city.

A member should contain a unique user identification number (create/choose your own, don't use INSS)
The INSS should be filled in and unique. The email should be filled in and unique. Also validate
that the email is of the form x@x.x, where x can be any type and amount of letters and numbers. The
last name and city should be filled in. Out of scope: validation of the postal code and the city.
Prioritization: Must-Have

## Our decision :

# Story 6B: external mail validation upon registration

Instead of doing the email validation yourself, rely on the external mail validation service
described below.

Prioritization: Nice-To-Have SOAP

https://cdyne.com/downloads/SPECS_Email-Verification.pdf
http://ws.cdyne.com/emailverify/Emailvernotestemail.asmx?wsdl
REST

https://trumail.io/documentation

## Our decision :

# Story 7: View members as Admin

As an Admin user I want to view all members within the system, so I can keep track of all the
members.

An Admin user contains a unique user identification number, last name, first name and email. One
Admin user should exist on startup. If any other user tries to view all member within the system,
the server should respond with 403 Forbidden and a custom message. Hint: provide the unique user
identification number as a means of authentication and use it to validate authorization. The INSS
should be excluded from the members when returned (sensitive information)
Prioritization: Must-Have

## Our decision :

# Story 8: Register Admin

As an Admin user I want to register a new Admin user, so I can have multiple users with
Administrator access.

If any other user tries to register an Admin user, the server should respond with 403 Forbidden and
a custom message. Prioritization: Nice-To-Have

## Our decision :

# Story 9: Register Librarian

As an Admin user I want to register a new Librarian user, so I can have users with Moderator access.

A Librarian user contains the same information as an Admin user. If any other user tries to register
a Librarian user, the server should respond with 403 Forbidden and a custom message. Prioritization:
Must-Have

## Our decision :

# Story 10A: Register a new book

As a Librarian I want to register a new book, so I can keep the collection of books relevant.

The ISBN, title and author's last name are required. If any other user besides a Librarian tries to
register a new book, the server should respond with 403 Forbidden and a custom message.
Prioritization: Must-Have

## Our decision :

# Story 10B: Update a book

As a Librarian I want to update an existing book, so I can correct mistakes that occurred during
creation.

The ISBN should not be modifiable Prioritization: Must-Have

## Our decision :

# Story 10C: Delete a book

As a Librarian I want to delete an existing book, so I can restrict members from borrowing books we
no longer have.

Books should be soft-deleted. (Not sure what soft-delete means? Google or ask!)
These books should no longer show up in any result!
These books can no longer be borrowed!
These books can be updated, they can be restored (un-deleted). Prioritization: Must-Have

## Our decision :

# Story 11: Lending a book

As a member I want to be able to borrow a book, so that I can allocate a book to myself for a
certain duration.

The member's user identification number and the book's ISBN should be provided. A unique lending
identification number and a due date should be registered, by default this date is TODAY + 3 WEEKS A
book can only be lent once at a time. Prioritization: Must-Have

## Our decision :

# Story 12: Return a book

As a member I can return a book. If the book return is late, show a corresponding message.

The unique lending identification number should be provided If the book return is late, show a
corresponding message. Prioritization: Must-Have

## Our decision :

# Story 13: Lent books

As a Librarian I want to see all the books lent to a member, so I can keep track of how many books a
member borrowed.

Prioritization: Must-Have

## Our decision :

# Story 14: Overdue books

As a Librarian I want to see all the books that are overdue, so I can keep track of all books that
are overdue.

Prioritization: Must-Have

## Our decision :

# Story 15: Enhance book details

As a member I want to be able to see if a book is borrowed, and if so by whom, in the details of a
book,

Expand the result returned when requesting the details of a book (# Story 2)
Prioritization: Must-Have

## Our decision :

# Story 16: ISBN validation

As a Librarian I want to be able to only provide valid ISBNs when creating (or updating) a book, so
I can never create a book with an incorrect ISBN.

Use the ISBN-13 type for validation Prioritization: Nice-To-Have

## Our decision :

# Story 17: Overdue Fines

As a Librarian I want to create a fine when a member returns a book that is overdue, so I can
penalize this behavior.

The maximum loan period is 3 weeks The formula to calculate the fine is: €5 + €2 *
FULL_WEEKS_OVERDUE E.g. 3 weeks: no fine 3 weeks and 3 days: €5 4 weeks: €7 Prioritization:
Nice-To-Have

## Our decision :

# Story 18: Enhance members overview with fines

As an Admin I want to see an extra field containing the total amount of fines a member has, when
viewing the members.

Enhances # Story 7 (View members as Admin)
Prioritization: Nice-To-Have

## Our decision :

# Story 19: Damage Fines

As a Librarian I want to create a fine when a member returns a damaged book, so I can penalize this
behavior.

The formula to calculate the fine is: (INITIAL_PRICE) * (1 - ((AGE_OF_BOOK_IN_YEARS) * 0.2) with a
minimum of €4. Make sure these fines are included in # Story 18. Prioritization: Nice-To-Have

## Our decision :

# Story 20: Multiple copies of a book

As a Librarian I want to have multiple copies of a book, so multiple members can borrow it at the
same time.

Make sure a copy of a book is available before lending. Make sure you can set the number of copies
of a book when creating (or updating) a book. The total number of copies should be included when
getting a book's details. Prioritization: Nice-To-Have

## Our decision :

# Story 21: Lending hi# Story

As a Librarian I want to be able to get a report on the lending hi# Story of a book.

This report should contain the different periods the book was borrowed and by whom. Try to suggest
some additional data to your customer that could be useful for inclusion in the report.
Prioritization: Nice-To-Have

## Our decision :
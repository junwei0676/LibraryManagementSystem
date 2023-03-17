# LibraryManagementSystem
Book lending application to support the workflow of the library

IS3106 ASSIGNMENT 1 AY22/23S2
LIBRARY MANAGEMENT SYSTEM (LMS)
NG JUN WEI 

DESCRIPTION
LMS consisting of 
i) a core backend to be developed with a component-based architecture
ii) a rich frontend to be developed with JSF and Prime Faces 
iii) a book lending application to support the workflow of the library.

LMS is designed for staff usage, with the necessary lending and returning functions and necessary validations only as staff is assumed to be able to filter out unrealistic inputs.

ASSUMPTIONS
 • Staff will verify member particulars, filtering out unrealistic inputs without the need of bean validation.
 • Staff will ensure that fine amount collected is correct and key it into the fine amount input box before submitting return request.
 • It is not possible to add a new staff through the LMS system as it requires administrator rights.

ADDITIONAL FEATURES

1. Book entity has an additional attribute, boolean onLoan, for easy tracking of availability of book.
2. View All Books page, where staff can view all books and check availability of book. Staff can perform a global search for book by its author, title and isbn.
3. View All Members page, where staff can view all member details. Staff can perform a global search for member by first name, last name, identity number, phone number and address
4. View All Loans page, where staff can view all lend and return details, tracking the fine amount and whether a loan is ongoing or not.
5. Home landing page dashboard containing details such as total number of books, members and ongoing loans as well as overdue loans and total fine amount owed.
6. Add Book page, where staff can add new books to the catalogue.
7. Fine Not Paid Exception, which will be thrown when fine collected is not equals to the fine amount due when returning book. Error message will show the correct fine amount to be collected
8. Book Returned Exception, which will be thrown when a loan whose book has been returned is being returned again.
9. Book Unavailable Exception, which will be thrown when there is an attempt to borrow a book that is already on loan.
10. Member Already Exists Exception, which will be thrown when there is an attempt to register a member with the same identification number.

# ExpenseManager2.1
Sem Project for 1st Year

# Technology Used

Android Studio - IDE by JetBrains for android app development.

Java - General purpose programming language.

# Flow of Use / How to Use
The app has two main screens, first is to login and second is after the user has logged in.

## Login and Registration
The application uses Firebase Auth methods to verify if the person has correct login details or not.
If the person is a new user, the link to the registration page is provided in the form on tap-able text just below the
login button.
The registration page provides the ability to the user to register his/her email id with valid email-id and 8 or more
digits password.

## Expense Management Screen
After logging in, person encounters the main expense overview screen. Here is the list of all the past incomes added
and payments made in a List-View.
The top bar has three buttons that make a segue from one function to the other providing access to the income adding page,
and payment adding page.

# Complete Makeover of the Application
The app was completely redesigned in order to make it look more professional.

The methods and the flow of code was kept almost same, most of the modifications were in the User Interface. 

## Major Changes in Code
Other than the User Interface, the code has also been made better.

#### Addition of Pie Chart
Addition of pie chart gives the application a more visually urging look. This makes it better visualise the changes that the user has made over the past.
To add a pie chart, an unknown library was used, with reference to an article.
The pie chart library uses a Array List, to which items are added, with various other arguments.
The pie chart shows the total number of the income/payment(actvities) made by the user in the past.

#### Addition of Data Picker Dialog
Addition of date picker dialog allows user to chose the dates easily and automatically adds the change to the added expense
or payment.
The date picker dialog is an already available library in the Android Studio.

#### Reforms in the structure of the code
The structure of the data in the database has been changed. Earlier there were seperate branches in the database, one for income and other for payments, which resulted in the bugs. To overcome this, a new structure was introduced and both the activities were combined to form one activity.

This was achieved using a temp variable in the data which indicated whether the activity was a payment or an income.
This improved the functioning of the code very much and made the storage of data better in all sorts.

#### Addition of Information Text Views
Two text views were added that tell how much income and how much payment in total the user has made.
The method of calculation of total sum is in the method of retrieving information for the pie chart.
This improves efficiency of the code.


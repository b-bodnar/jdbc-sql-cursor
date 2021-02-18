## JDBS-SQL project

In this project, we learn basic **SQL** and **JDBC**. 

We create `dev_profiles_db` database and two tables: 
- `accounts`, 
- `profiles_table`

Then we rename table `profiles_table` to `profiles`

### Part 1 SQL:

1. Display all profiles from one `department` and sort by `skill`
2. Display `job_title` that accur more than 3 times
3. Display `first_name`, `last_nmae`, `job_title`, `company` for account from one city
4. Find and display the most popular `department` among `profiles` and count of the employees there (define with `username`)
5. Define count of profiles for one `job_title` and `city` for all accounts
6. Display all user accounts by grouping them by `city`
7. Update profiles for accounts where `job_title` contains word _engineer_ and replace it with _developer_

> All SQL scripts are save in dev_profiles.sql file. 

### Part 2 JDBC:

1. Create class `DbUtil`, configure work with BD. Create a method, that will be a manage connection to DB.
2. Create four CRUD methods for interaction with DB
    - `insert`
    - `select`
    - `update`
    - `delete`


These methods should be parametrized (these methods should be universal according to tables in DB)

3. Create the models according to the tables in DB, CRUD method should return (depending on the purpose of method) exactly `model`, but not just `ResultSet`.
Be sure to use `Lombok` for creating the models.
4. In `main` method create an instance of the class `DbUtil`, open `connection`, and invoke each method for two tables.

* Universal means that depending on the parameters, namely turning model will work method, no need to create several separate methods for each table.

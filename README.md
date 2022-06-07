
# Insurance offers and services management system.

> Desktop management application for insurance agency built with JAVA Swing, AWT and MySQL

![screenshot](https://github.com/AbdeAMNR/AssuranceBenSaid/blob/master/Interfaces%20Graphiques/demo_app.gif)

## Features

- Manage customers.
- Manage vehicles.
- Manage the receipts made.
- System for registering users and assigning roles.
- Application registration for the purpose of preventing its unauthorized use.
- Analyze everyday activities with the use of a dashboard.
- Printing reports using JasperReports.
- Database creation using SQL script (tables and dummy data).

## Note on Issues

Please do not post issues here that are related to your own code. If you clone THIS repo and there are issues, then you can submit

## Usage

###
```
Registration key:
HRYASU-HRYASU-HRYASU-HRYASU-HRYASU
```
### Env Variables

```
DB_URL = jdbc:mysql://localhost:3306/agenceassurance
DB_USER = root
DB_PASSWORD = amanar
```


#### Setup MySQL database
```
### MySQL database
Create a database, tables and triggers, and dummy data 
by configuring MySQL and running the SQL script 
provided in "database/insuranceAgencyDB v2.sql"

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'amanar';
Then run this query to refresh privileges:
flush privileges;


```

#### Run

```
# Compile and run
make sure Database instance is runing

#  Sample User Logins
username: a
password: a
```


## License

### Copyright (c) 2021 Abderrahim AMANAR

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

Database setup in MySQL:

1) username: root - password: yourPassword.
2) CREATE DATABASE LIA2;
3) In LIA2_backend project configure access to MySQL jdbc:mysql://localhost:3306/lia2
4) Run project - the table structure will be created with schema.sql and the data will be inserted with data.sql. If you reboot the project the schema.sql will drop all tables and recreate them.
4.1) Dependencies used:
   Java 17
   Spring Boot
   Maven
   MySQL

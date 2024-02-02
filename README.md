Database setup in MySQL:

1) username: root - password: Yatterman1 (or change application.properties to your password).
2) CREATE DATABASE LIA2;
3) In LIA2_backend project configure access to MySQL jdbc:mysql://localhost:3306/lia2
4) Run project - the table structure will be created with schema.sql and the data will be inserted with data.sql. If you reboot the project the schema.sql will drop all tables and recreate them.

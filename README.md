Jama
====

1) deployare su JBoss AS 7.1 mysql-connector-5.x.jar e cambiare il runtime-name in "mysql-connector.jar".


2) creare un nuovo database con:

  database_name: JamaDB

3) aggiungere un nuovo utente da server administration con:

  login_name: jama
  limit_connectivity to Hosts Matching: localhost
  password: jama

4) aggiungere tutti i permessi per il db JamaDB all'utente appena creato(dal tab schema privileges)

5) importare il progetto in eclipse

6) have fun!




# payment-system  Spring Boot, Postgres, ShardingSphere
Swagger link: http://localhost:8080/swagger-ui.html

The app works under 2 profiles: sharding and not-sharding(default)
The app under shardign profile requires creating 3 databases: payment_db1, payment_db2, payment_db3 with schema creating and addign payer and payee into the tables

API tests work without sharding with H2 database

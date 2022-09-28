# Testcontainer for Oracle connectivity Java JDBC drivers

Start container with required environment variables to test connectivity.

### Build
```
docker build --tag oracleconnectivity:1.0 .
```

### Usage
```
docker run -e ORACLE_URL="jdbc:oracle:thin:@localhost:1521/XE" \
 -e ORACLE_USER=user \
 -e ORACLE_PASSWORD=password \
 -e ORACLE_DRIVER="oracle.jdbc.OracleDriver" \
 -e ORACLE_SQL="select * from user_objects" \
 oracleconnectivity:1.0
```

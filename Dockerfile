FROM maven:3-jdk-11

#copy code
COPY src src
COPY pom.xml pom.xml

#build code
RUN mvn clean
RUN mvn install

#environment
ENV ORACLE_URL="jdbc:oracle:thin:@oracle:1521/XE"
ENV ORACLE_DRIVER="oracle.jdbc.OracleDriver"
ENV ORACLE_USER=user
ENV ORACLE_PASSWORD=password
ENV ORACLE_SQL="select * from user_objects"

#run code as entrypoint
ENTRYPOINT java -cp /target/oracle-connectivity-java-jdbc-jar-with-dependencies.jar com.twodigits.oracleconnectivity.Executer "$ORACLE_URL" "$ORACLE_DRIVER" $ORACLE_USER $ORACLE_PASSWORD "$ORACLE_SQL"

docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
cd ../
mvn sonar:sonar \
  -Dsonar.projectKey=test-configs \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=026d2bd740ced7b11c9037b529c7427f084cf749

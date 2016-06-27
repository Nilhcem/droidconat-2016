FROM java:8
MAINTAINER Nilhcem

RUN DEBIAN_FRONTEND=noninteractive apt update
RUN DEBIAN_FRONTEND=noninteractive apt install -y wget unzip
RUN wget -q https://sonarsource.bintray.com/Distribution/sonarqube/sonarqube-5.6.zip
RUN unzip -qq sonarqube-5.6.zip -d /opt/
RUN rm sonarqube-5.6.zip

EXPOSE 9000

CMD ["/opt/sonarqube-5.6/bin/linux-x86-64/sonar.sh", "console"]

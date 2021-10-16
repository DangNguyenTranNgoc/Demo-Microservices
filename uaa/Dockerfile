FROM openjdk:18-alpine
LABEL author="Nguyen Ngoc Dang Tran <dangnguyentranngoc@gmail.com>"

ADD ./target/uaa-*.jar uaa.jar
RUN /bin/sh -c "touch uaa.jar"

ENTRYPOINT ["java","-jar","uaa.jar"]

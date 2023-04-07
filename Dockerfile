ARG baseImage
ARG baseImageTag
FROM ${baseImage}:${baseImageTag}

COPY target/Boostify*.jar boostify.jar

ENTRYPOINT ["java","-jar","/boostify.jar"]
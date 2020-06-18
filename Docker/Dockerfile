FROM 604753321405.dkr.ecr.us-east-1.amazonaws.com/centos7-java8-eap72-base:1.0.0.0

MAINTAINER  sbecker sbecker@tgr.cl

env container docker
ENV JAVA_HOME /usr/java/jdk1.8.0_241-amd64/jre
RUN java -version

COPY cl.tar.gz /root/docker/cl.tar.gz
RUN cd /opt/eap/modules && tar -xvf /root/docker/cl.tar.gz

COPY com.tar.gz /root/docker/com.tar.gz
RUN cd /opt/eap/modules && tar -xvf /root/docker/com.tar.gz

COPY *.properties /opt/eap/modules/com/monex/configuration/main/properties/

COPY oracle.tar.gz /root/docker/oracle.tar.gz
RUN cd /opt/eap/modules/system/layers/base/com && tar -xvf /root/docker/oracle.tar.gz

COPY standalone.xml /opt/eap/standalone/configuration/standalone.xml

COPY RentaMonedaExtranjera-2.0.0.0.ear /opt/eap/standalone/deployments/RentaMonedaExtranjera-2.0.0.0.ear


EXPOSE 8080
ENTRYPOINT ["/bin/bash", "/opt/eap/bin/standalone.sh"]



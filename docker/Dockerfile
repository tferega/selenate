FROM debian:jessie

# Firefox/selenate dependencies
RUN apt-get update && apt-get install -y \
    bzip2 \
    icewm \
    libdbus-glib-1-2 \
    libgtk2.0 \
    tesseract-ocr \
    wget \
    x11vnc \
    Xvfb \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

# Java
RUN wget -O /opt/java-8.tar.gz --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u65-b17/server-jre-8u65-linux-x64.tar.gz \
 && tar zxf /opt/java-8.tar.gz -C /opt \
 && mv /opt/jdk1.8.0_65/ /opt/jdk \
 && ln -s /opt/jdk/jre/bin/java /usr/bin/java \
 && rm /opt/java-8.tar.gz

ENV JAVA_HOME /opt/jdk

# Firefox
RUN wget -O /opt/firefox-31.8.0.tar.bz2 https://ftp.mozilla.org/pub/firefox/releases/31.8.0esr/linux-x86_64/en-US/firefox-31.8.0esr.tar.bz2 \
 && tar jxf /opt/firefox-31.8.0.tar.bz2 -C /opt \
 && ln -s /opt/firefox/firefox-bin /usr/bin/firefox \
 && rm /opt/firefox-31.8.0.tar.bz2


# Config
COPY payload/ /

EXPOSE 9072

CMD [ "/root/pack/bin/start" ]

FROM mentatlabs/oracle-jdk8

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

# Firefox
RUN wget -O /opt/firefox-31.8.0.tar.bz2 https://ftp.mozilla.org/pub/firefox/releases/31.8.0esr/linux-x86_64/en-US/firefox-31.8.0esr.tar.bz2 \
 && tar jxf /opt/firefox-31.8.0.tar.bz2 -C /opt \
 && ln -s /opt/firefox/firefox-bin /usr/bin/firefox \
 && rm /opt/firefox-31.8.0.tar.bz2


# Config
COPY config/ /root

COPY pack/ /opt/selenate

CMD /opt/selenate/bin/start
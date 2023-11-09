FROM jetty:9.4-jre11

LABEL "Organization"="geOrchestra"
LABEL "Description"="CAS server webapp"

RUN java -jar "$JETTY_HOME/start.jar" --create-startd --add-to-start=jmx,jmx-remote,stats,gzip,http-forwarded

VOLUME [ "/tmp", "/run/jetty" ]

EXPOSE 8080

COPY --chown=jetty:jetty build/cas /var/lib/jetty/webapps/cas
COPY --chown=jetty:jetty docker-entrypoint.sh /
COPY --chown=jetty:jetty docker-entrypoint.d /docker-entrypoint.d/

ENV XMS=256M XMX=1G

ENTRYPOINT [ "/docker-entrypoint.sh" ]

CMD ["sh", "-c", "exec java \
        -Djava.io.tmpdir=/tmp/jetty \
        -Dgeorchestra.datadir=/etc/georchestra \
        -Xms$XMS -Xmx$XMX \
        -XX:-UsePerfData \
        ${JAVA_OPTIONS} \
        -DCAS_BANNER_SKIP=true \
        -Dcas.standalone.configurationDirectory=/etc/georchestra/cas/config \
        -Djetty.httpConfig.sendServerVersion=false \
        -Djetty.jmxremote.rmiregistryhost=0.0.0.0 \
        -Djetty.jmxremote.rmiserverhost=0.0.0.0 \
        -jar /usr/local/jetty/start.jar"]


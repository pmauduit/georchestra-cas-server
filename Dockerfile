FROM jetty:9.4-jre17

LABEL "Organization"="geOrchestra"
LABEL "Description"="CAS server webapp"

USER root
RUN mkdir -p /docker-entrypoint.d
RUN chown jetty:jetty /docker-entrypoint.d
USER jetty

RUN java -jar "$JETTY_HOME/start.jar" --create-startd --add-to-start=jmx,jmx-remote,stats,gzip,http-forwarded

VOLUME [ "/tmp", "/run/jetty" ]

EXPOSE 8080

COPY --chown=jetty:jetty build/cas /var/lib/jetty/webapps/cas
COPY --chown=jetty:jetty docker/docker-entrypoint.sh /

ENTRYPOINT [ "/docker-entrypoint.sh" ]

CMD ["sh", "-c", "exec java \
        -Djava.io.tmpdir=/tmp/jetty \
        -Dgeorchestra.datadir=/etc/georchestra \
        -XX:MaxRAMPercentage=80 -XX:+UseParallelGC \
        -XX:-UsePerfData \
        ${JAVA_OPTIONS} \
        -DCAS_BANNER_SKIP=true \
        -Dcas.standalone.configurationDirectory=/etc/georchestra/cas/config \
        -Djetty.httpConfig.sendServerVersion=false \
        -Djetty.jmxremote.rmiregistryhost=0.0.0.0 \
        -Djetty.jmxremote.rmiserverhost=0.0.0.0 \
        -jar /usr/local/jetty/start.jar"]


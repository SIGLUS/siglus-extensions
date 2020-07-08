FROM gliderlabs/alpine:3.4

COPY build/libs/*.jar /etc/extensions/
COPY extensions.properties /etc/extensions/

CMD cp /etc/extensions/* /extensions/

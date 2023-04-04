FROM redis:7.0.9

EXPOSE 6379

RUN mkdir /var/run/redis; \
    mkdir /var/run/sentinel;

COPY ./6379.conf /etc/redis/6379.conf

CMD ["redis-server", "/etc/redis/6379.conf"]

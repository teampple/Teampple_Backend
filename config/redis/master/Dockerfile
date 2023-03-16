FROM redis:7.0.9

RUN mkdir /var/run/redis

RUN mv /etc/redis/redis.conf /etc/redis/redis.conf.bak
COPY ./6379.conf /etc/redis/6379.conf

CMD ["redis-server", "/etc/redis/6379.conf", "--daemonize", "yes"]

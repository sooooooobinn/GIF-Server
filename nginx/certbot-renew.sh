#!/bin/sh
set -e

DOMAIN=${DOMAIN:?"DOMAIN 환경변수가 필요합니다"}

while :; do
    certbot renew --webroot -w /var/www/certbot --quiet \
        --deploy-hook "cp /etc/letsencrypt/live/$DOMAIN/fullchain.pem /nginx/ssl/fullchain.pem && \
                       cp /etc/letsencrypt/live/$DOMAIN/privkey.pem /nginx/ssl/privkey.pem && \
                       chmod 600 /nginx/ssl/privkey.pem"
    sleep 12h
done

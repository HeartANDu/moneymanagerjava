FROM node:lts-alpine as build
WORKDIR /app
COPY package*.json babel.config.js .browserslistrc /app/
RUN npm ci
COPY ./src /app/src
COPY ./public /app/public
RUN npm run build

FROM node:lts-alpine as front
RUN npm install -g http-server
COPY --from=build /app/dist /app/dist
WORKDIR /app
ENTRYPOINT ["http-server", "dist", "-p", "8000"]

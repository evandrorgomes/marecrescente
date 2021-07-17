FROM node:6.10.2

WORKDIR /src

ADD package.json /src

RUN npm i --silent

ADD . /src

RUN npm run build

CMD npm start
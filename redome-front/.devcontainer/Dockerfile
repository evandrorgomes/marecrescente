# ARG VARIANT="6-buster"
# FROM mcr.microsoft.com/vscode/devcontainers/javascript-node:0-${VARIANT}

FROM node:6.10.2

COPY package.json ./

RUN npm install -g npm@3.10.10

COPY . .

EXPOSE 4200

ENTRYPOINT ["npm", "start"]

# RUN useradd --user-group --create-home --shell /bin/false app &&\
#   npm install -g npm@3.10.10

# ENV HOME=/home/app

# COPY package.json $HOME/library/

# RUN chown -R app:app $HOME/*

# USER app
# WORKDIR $HOME/library
# # RUN npm cache clean && npm install --silent --progress=false

# USER root
# COPY . $HOME/library
# RUN chown -R app:app $HOME/*
# USER app

# EXPOSE 4200

#CMD ["npm", "start"]
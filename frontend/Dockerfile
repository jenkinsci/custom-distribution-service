# pull official base image
FROM node:13.12.0-alpine

# set working directory
# pull official base image
FROM node:13.12.0-alpine  AS builder
WORKDIR /app
COPY . ./
RUN yarn install
RUN yarn build

# second file actually running the situation
FROM node:13.12.0-alpine
RUN yarn global add serve
WORKDIR /app
COPY --from=builder /app/build .
CMD ["serve", "-p", "80", "-s", "."]doc

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# install app dependencies
COPY package.json ./
COPY package-lock.json ./
RUN npm install --silent

# add app
COPY . ./

# start app
CMD ["npm", "start"]

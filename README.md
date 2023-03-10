# thesis-api

[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)
[![Java](https://img.shields.io/badge/-Java-red?logo=java)](https://www.java.com)
[![Spring](https://img.shields.io/badge/-Spring-white?logo=spring)](https://spring.io/)

## Table of contents
* [Introduction](#introduction)
* [Features](#features)
* [Technologies](#technologies)
* [Usage](#usage)
* [Authors](#authors)
* [Sources](#sources)

## Introduction

The API is a Spring Boot Gradle project written with Java. Spring security is used for authentication
and access-control in a stateless manner with JWTs. Dockerized Keycloak run on Heroku acts as
OAuth 2.0 authorization server & identity provider.

## Features
- REST API
- Hibernate
- Protected endpoints, auth with JWT

## Technologies
- Java
- Spring
- Spring security
- OAuth 2.0
- PostgreSQL

## Usage
#### Requirements
- Postgres db

#### Configurations
- Configure application.properties so it matches your db and auth server.
- Do also necessary configurations to files in configs package.
- Hibernate is configured to create and db is seeded with mock data, change this behaviour as needed.

Gradle will automatically initialize itself and download necessary dependencies the first time the wrapper is run.


## Authors
[@Jani](https://gitlab.com/janijk)<br />

## Sources
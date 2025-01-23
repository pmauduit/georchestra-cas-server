geOrchestra CAS customizations
===============================

This project has been created from the [suggested github template](https://travis-ci.org/apereo/cas-overlay-template).

It contains some customizations to suit with the conventions followed by the geOrchestra project.

geOrchestra customizations
=============================

* configuration into `/etc/georchestra/cas` instead of `/etc/cas`.
* not building a runnable artifact, nor including any tomcat or jetty dependencies
  (see the `gradle.properties` file)
* A basic custom theme named "georchestra" has been generated using `./gradlew generateTheme`
* ... more to come

Custom georchestra build
===========================

## Building a simple war

```
./gradlew build
```

## Building a debian package

```
./gradlew deb
```

The resulting deb is in `build/distributions/`

## Building a docker image

Creating the exploded webapp with the following:

```
$ ./gradlew explodeWarOnly
```

Then Build a docker image using:

```
$ docker build -t georchestra/cas:6.6 .
```

Contrary to upstream, we don't make use of the jib gradle plugin to build the docker image.


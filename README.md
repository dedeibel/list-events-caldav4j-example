
# list-events-caldav #

A small example on how to access a caldav calendar using caldav4j.

I created this since I couldn't find one and had to extract it from
the unit tests.

Configure ./src/test/java/benjaminpeter/name/ListCalendarTest.java

*Usage:* mvn test

[ListCalendarTest.java](https://github.com/dedeibel/list-events-caldav4j-example/blob/master/src/test/java/benjaminpeter/name/ListCalendarTest.java)

*yeah ... it's not actually a test*

## Dependencies: ##

*caldav4j 0.8-SNAPSHOT*, best ist to install it on yourself

https://code.google.com/p/caldav4j/source/checkout

You can simply try:

$ svn checkout http://caldav4j.googlecode.com/svn/trunk
$ cd trunk
$ mvn -Dmaven.test.skip=true install

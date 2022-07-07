The plan is to have all of the connection types and init of connection 
and establishment of the bus in SystemBus.java

Have a class for each instrument that extends Instrument.java which will
have all of the basics of connecting to the bus and that stuff.

Most likely do some form of constants file to hold all of the GPIB commands.

This shit sucks, all existing resources suck and are overly complicated.

Create SystemBus list for all instruments currently added and do all
required methods for that. Create classes for each SystemBus type.
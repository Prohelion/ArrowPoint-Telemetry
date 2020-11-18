# ArrowPoint-Telemetry
The ArrowPoint Telemetry Solution is designed to provide telemetry capture and management information for managing a race car.

The out of the box implementation is designed for use in Solar or Electric racing cars that use technology from 
Prohelion (www.prohelion.com) or Tritium (www.tritium.com.au).  This solution was origionally developed by TeamArrow (www.teamarrow.com.au)
and has been used in racing environments since 2013.  TeamArrow continue to use this application as the heart of its telemetry and strategy
management solutions.

The solution captures and stores all telemetry data in a race environment and has been used to capture over 60,000,000 data points in 
previous events and testing and can manage, track and store around 400 data points a second when running on a i7 laptop.
It provide reporting (via Splunk) across full data sets via a web interface and allows teams to build and track massive data sets 
via dashboards and dynamically modifyable searches.

The ArrowPoint-Telemetry system is very light weight (considering what it is doing!) and TeamArrow have historically run it on a single CPU laptop, which we tuck on the seat in the chase car or can be run in the cloud (we use Amazon EC2) when coupled with the ArrowPoint-Tablet for data relay.

Examples of the software in use can be seen here - https://www.youtube.com/watch?reload=9&v=lWkXEb8v1tk

Documentation for the Prohelion ArrowPoint Telemetry solution can be found on the Prohelion Documentation website which is located here
https://docs.prohelion.com

If you are interested in contributing to the solution, please see our contribution file here
https://github.com/Prohelion/ArrowPoint-Telemetry/blob/master/CONTRIBUTING.md

The application provides

- CANbus data capture and reporting either directly off the car, via the ArrowPoint-Tablet or a JSON stream
- Alerting via a visual alert (USB light), when key data points go out of range
- Storage of CANbus data in a relational data set for later report
- Relaying of data to other ArrowPoint-Telemetry instances
- Integration with SPLUNK for larger scale data capture and realtime reporting

The application is compatible with
 
- Prohelion Battery Packs
- Tritium WaveSculpters
- Tritium BMUs and CMUs

The Wifi connection should be broadcasting CANbus data using the Tritium CANbus identifiers.

Any issues please or question, please raise them on our GitHub account at https://github.com/Prohelion/ArrowPoint-Telemetry

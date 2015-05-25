# XFLR5 to STL Translator

[![Build Status](https://travis-ci.org/TimJay/xflr5-stl.svg?branch=master)](https://travis-ci.org/TimJay/xflr5-stl)

The XFLR5 to STL translator bridges the design space with the CAD/CAM world. A wing exported from XFLR5 is converted to an ASCII STL file which can be imported in various CAD and CAM systems. 

## Limitations

Currently only flat wings are generated, any values for dihedral or twist are ignored.

## Installation

```
git clone https://github.com/TimJay/xflr5-stl.git  
cd xflr5-stl  
mvn package  
java -jar ./target/xflr5-stl-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

## Usage

Option (* = required) | Description                       
--------------------- | -----------                       
-?, -h, --help        |                                   
--input \<File\> *      | Wing file exported from XFLR5.    
--multiplier \<Double\> | Scale the resulting STL with this multiplier.                     
--output \<File\> *     | STL file generated from wing file.

## Credits

The XFLR5 to STL translator uses [JOpt Simple](https://pholser.github.io/jopt-simple/) for parsing command line options.

## License

Copyright Tim Jagenberg <tim@jagenberg.info>  
Licensed under the Apache License, Version 2.0  
http://www.apache.org/licenses/LICENSE-2.0.html

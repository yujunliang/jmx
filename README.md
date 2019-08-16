Scala JMX Utility Library
=========================

This library is provided for Scala 2.11 applications that want to connect to JMX.

It illustrates how to use AKKA actor to develop reactive applications. 


To Run It
---------

1. Start JMX1Spec with the following VM Options,

        -Dcom.sun.management.jmxremote
        -Dcom.sun.management.jmxremote.port=11111
        -Dcom.sun.management.jmxremote.authenticate=false
        -Dcom.sun.management.jmxremote.ssl=false
        -Xms128M
        -Xmx1G

2. Start JMX2Spec with the following VM Options,

        -Dcom.sun.management.jmxremote
        -Dcom.sun.management.jmxremote.port=11112
        -Dcom.sun.management.jmxremote.authenticate=false
        -Dcom.sun.management.jmxremote.ssl=false
        -Xms64M
        -Xmx258M

3. Start JMX3Spec with the following VM Options,

        -Dcom.sun.management.jmxremote
        -Dcom.sun.management.jmxremote.port=11113
        -Dcom.sun.management.jmxremote.authenticate=false
        -Dcom.sun.management.jmxremote.ssl=false
        -Xms256M
        -Xmx512M

4. Start JMX4Spec with the following VM Options,

        -Dcom.sun.management.jmxremote
        -Dcom.sun.management.jmxremote.port=11114
        -Dcom.sun.management.jmxremote.authenticate=false
        -Dcom.sun.management.jmxremote.ssl=false
        -Xms192M
        -Xmx768M


And then run Main class.

Presentation
------------

https://drive.google.com/file/d/0B7Hy3zuc2uCwRXhyM0ZDNUlxblk/view?usp=sharing


Output
------

    jmx.hosts : [localhost:11111, localhost:11112, localhost:11113, localhost:11114]
    Memory:---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+
    Memory:_                          2             4  2       1                        3     3                        4                                                                      1
    Memory:_                         2                24  1                                  3     3                       4                                                                1
    Memory:_                    2                2       _                                 3       3                           4                                                            1
    Memory:_                     2                2      1      4                    3       3                                      4                                                        1
    Memory:_                    2                2         1        4              3       3                                            4                                                   1
    Memory:_                    2                2        1          4            3       3                                               4                                                 1
    Memory:_                   2                2        1        4                3        3                                           4                                                   1
    Memory:_                   2                2           1        4           3       3                                                4                                                  1
    Memory:_                   2                2         1            4          3       3                                                4                                                1
    Memory:_                   2                2        1              4        3       3                                                  4                                              1
    Memory:_                   2                2        1                  4  3        3                                                      4                                            1
    Memory:_                  2                2         1                    34       3                                                         4                                         1
    Memory:_                   2                2       1                     43        3                                                        4                                      1
    Memory:_                   2                2         1                   43        3                                                        4                                       1
    Memory:_                    2                2      1                    4   3        3                                                    4                                        1
    Memory:_                   2                2       1                    4 3         3                                                     4                                        1
    Memory:_                    2                2        1                  43        3                                                     4                                           1
    Memory:_                     2                2      1                 4     3        3                                                 4                                           1
    Memory:_                      2                2        1             4   3         3                                                   4                                            1
    Memory:_                      2                2        1           4       3         3                                              4                                              1
    Memory:_                     2                2        1        4             3        3                                          4                                               1
    Memory:_                     2                2        1       4             3         3                                         4                                                 1
    Memory:_                     2                2          1        4          3         3                                           4                                                  1
    Memory:_                     2                2       1          4           3         3                                          4                                                 1
    Memory:_                     2                2       1         4            3         3                                         4                                                  1
    Memory:_                     2                2        1        4             3         3                                        4                                                   1
    Memory:_                     2                2      1         4                3          3                                    4                                                 1
    Memory:_                    2                2     1          4                3          3                                     4                                                1
    Memory:_                   2                2  1              4                   3         3                                   4                                             1
    Memory:_                  2                2  1              4                   3         3                                    4                                            1
    Memory:_                   2                2   1              4                 3         3                                    4                                             1
    Memory:_                  2                2   1                4               3        3                                      4                                            1
    Memory:_                  2                2   1               4              3        3                                         4                                          1
    Memory:_                 2                2    1              4                3         3                                     4                                             1
    Memory:_                 2                2   1               4              3        3                                         4                                            1
    Memory:_                 2                2    1              4             3         3                                       4                                              1
    Memory:_                 2                2    1                4             3        3                                         4                                             1
    Memory:_                  2                2    1                 4           3        3                                          4                                             1
    Memory:_                  2                2       1              4         3        3                                            4                                                1
    Memory:_                  2                2       1              4          3        3                                            4                                               1
    Memory:_                  2                2       1                4       3        3                                                4                                           1
    Memory:_                   2                2       1             4         3         3                                             4                                              1
    Memory:_                  2                2        1            4            3         3                                         4                                                 1
    Memory:_                   2                2      1              4         3         3                                             4                                           1
    Memory:_                   2                2      1            4            3         3                                         4                                           1
    Memory:_                    2                2    1              4            3         3                                          4                                       1
    Memory:_                    2                2    1             4           3         3                                          4                                         1
    Memory:_                    2                2   1             4            3         3                                         4                                        1
    Memory:_                     2                21               4           3         3                                          4                                        1
    Memory:_                    2                2     1          4          3         3                                         4                                             1
    Memory:_                     2                2   1           4          3        3                                        4                                                 1
    Memory:_                     2                2   1          4           3        3                                      4                                                   1
    Memory:_                     2                2    1          4         3         3                                       4                                                1
    Memory:_                     2                2     1        4            3        3                                    4                                                      1
    Memory:_                     2                2     1        4           3        3                                     4                                                        1
    Memory:_                     2                2    1         4          3       3                                       4                                                       1
    Memory:_                   2                2    1           4        3       3                                          4                                                     1
    Memory:_                  2                2       1          4       3       3                                          4                                                     1
    Memory:_                   2                2          1     4        3      3                                           4                                                     1
    Memory:_                   2                2       1        4        3      3                                             4                                                  1
    Memory:_                  2                2      1            4      3      3                                               4                                             1
    Memory:_                   2                2      1             4     3      3                                                 4                                           1
    Memory:_                   2                2     1              4       3      3                                                 4                                            1
    Memory:_                    2                2   1                4       3      3                                                 4                                          1
    Memory:_                     2                2   1              4          3      3                                             4                                            1
    Memory:_                     2                2      1           4           3       3                                            4                                              1
    Memory:_                      2                2     1             4           3       3                                            4                                           1
    Memory:_                      2                2   1             4             3       3                                         4                                            1
    Memory:_                     2                21                 4               3       3                                       4                                          1
    Memory:_                     2                2   1               4                3       3                                    4                                            1
    Memory:_                     2                2     1            4                 3        3                                    4                                               1
    Memory:_                     2                2    1            4                   3        3                                  4                                              1
    Memory:_                      2                2 1              4                 3       3                                     4                                           1
    Memory:_                     2                2    1              4            3        3                                          4                                        1
    Memory:_                    2                2    1                  4       3        3                                               4                                      1
    Memory:_                     2                2  1                     4     3       3                                                  4                                    1
    Memory:_                    2                2  1                    4       3       3                                                4                                    1
    Memory:_                     2                2  1                     4    3       3                                                    4                                     1
    Memory:_                     2                2    1                     _       3                                                        4                                     1
    Memory:_                     2                2  1                       43       3                                                        4                                  1
    Memory:_                      2                2 1                      4  3       3                                                      4                                   1
    Memory:_                     2                2      1                    _       3                                                       4                                        1
    Memory:_                    2                2          1                4 3       3                                                     4                                           1
    Memory:_                     2                2        1               4  3      3                                                     4                                            1
    Memory:_                      2                2          1           4   3       3                                                  4                                                1
    Memory:_                    2                2           1           4    3       3                                                  4                                              1
    Memory:_                   2                2              1          4  3       3                                                    4                                                1
    Memory:_                  2                2              1         4    3      3                                                   4                                                 1
    Memory:_                  2                2             1         4     3       3                                                  4                                                  1
    Memory:_                  2                2            1        4      3       3                                                4                                                   1
    Memory:_                  2                2           1        4     3        3                                                4                                                    1
    Memory:_                  2                2        1         4        3        3                                               4                                                  1
    Memory:_                   2                2    1             4       3        3                                                4                                              1
    Memory:_                  2                2    1                 4     3        3                                                  4                                           1
    Memory:_                   2                2   1               4       3        3                                                 4                                            1
    Memory:_                   2                2   1               4      3        3                                                  4                                            1
    Memory:_                  2                2        1          4        3        3                                                4                                               1
    Memory:_                   2                2       1              4     3        3                                                   4                                           1
    Memory:_                    2                2         1         4      3       3                                                    4                                             1
    Memory:_                    2                2            1      4      3       3                                                     4                                                 1
    Memory:_                     2                2         1        4        3       3                                                  4                                              1
    Memory:_                     2                2          1      4         3       3                                                 4                                               1
    Memory:_                     2                2          1       4         3      3                                                  4                                             1
    Memory:_                      2                2             1 4           3      3                                                4                                                  1
    Memory:_                      2                2             1 4          3      3                                                 4                                                1
    Memory:_                      2                2              14           3      3                                                4                                                1
    Memory:_                       2                2         1   4           3       3                                               4                                                1
    Memory:_                        2                2        1   4          3      3                                                4                                               1
    Memory:_                       2                2        1   4             3      3                                            4                                                 1
    Memory:_                        2                2    1      4             3       3                                         4                                                1
    Memory:_                        2                2     1      4         3       3                                             4                                                  1
    Memory:_                       2                2       1   4          3       3                                           4                                                      1
    Memory:_                      2                2        1     4       3       3                                            4                                                        1
    Memory:_                     2                2       1         4       3       3                                            4                                                      1
    Memory:_                     2                2      1            4     3       3                                             4                                                       1
    Memory:_                     2                2    1           4           3       3                                      4                                                        1
    Memory:_                     2                2      1           4          3      3                                       4                                                         1
    Memory:_                    2                2       1         4             3       3                                     4                                                           1
    Memory:_                    2                2    1            4             3       3                                       4                                                      1
    Memory:_                   2                2    1             4            3       3                                          4                                                   1
    Memory:_                  2                2      1             4              3       3                                       4                                                   1
    Memory:_                  2                2       1            4             3       3                                         4                                                 1
    Memory:_                  2                2        1       4                 3        3                                     4                                                     1
    Memory:_                  2                2          1   4                   3        3                                    4                                                      1
    Memory:_                  2                2            _                       3        3                                4                                                         1
    Memory:_                   2                2            14                  3        3                                     4                                                        1
    Memory:_                   2                2           1 4                3         3                                       4                                                       1
    Memory:_                  2                2          1    4                3         3                                        4                                                   1
    Memory:_                   2                2          1     4               3        3                                        4                                                    1
    Memory:_                    2                2         1      4             3        3                                          4                                                   1
    Memory:_                    2                2        1     4            3       3                                               4                                                  1
    Memory:_                    2                2      1         4           3       3                                              4                                                  1
    Memory:_                    2                2       1          4         3        3                                                4                                               1
    Memory:_                     2                2       1           4     3       3                                                   4                                               1
    Memory:_                     2                2   1               4  3        3                                                     4                                            1
    Memory:_                    2                2   1              4    3         3                                                   4                                              1
    Memory:_                    2                2  1              4      3        3                                                  4                                             1
    Memory:_                   2                2   1             4       3        3                                                4                                               1
    Memory:_                    2                2   1           4     3         3                                                   4                                              1
    Memory:_                   2                2     1           4     3         3                                                 4                                               1
    Memory:_                    2                2  1            4      3          3                                              4                                             1
    Memory:_                    2                2     1         4     3          3                                               4                                               1
    Memory:_                    2                2  1            4     3          3                                              4                                            1
    Memory:_1                   2                21          4         3          3                                          4                                               1
    Memory:_                   2                2  1         4           3          3                                         4                                               1
    Memory:_                   2                2      1         4          3         3                                           4                                              1
    Memory:_                   2                2       1        4           3         3                                          4                                              1
    Memory:_                   2                2          1        4        3         3                                           4                                               1
    Memory:_                  2                2         1          4        3         3                                           4                                              1
    Memory:_                   2                2           1        4       3          3                                           4                                                 1
    Memory:_                   2                2            1        4        3         3                                          4                                                    1
    Memory:_                   2                2          1          4          3          3                                        4                                                 1
    Memory:_                    2                2          1        4         3          3                                           4                                                1
    Memory:_                    2                2          1        4           3          3                                         4                                                 1
    Memory:_                     2                2       1            4       3         3                                               4                                             1
    Memory:_                     2                2     1            4         3         3                                             4                                              1
    Memory:_                     2                2  1                4      3          3                                               4                                          1
    Memory:_                     2                2 1                4      3          3                                                 4                                         1
    Memory:_                     2                2 1                 4     3          3                                                 4                                        1
    Memory:_                    2               12                  4       3         3                                                 4                                     1
    Memory:_                    2                _                   4     3          3                                                    4                                  1
    Memory:_                   2                2 1                   4  3          3                                                       4                                    1
    Memory:_                  2                2  1                     43         3                                                         4                                    1
    Memory:_                 2                2  1                      _         3                                                            4                                  1
    Memory:_                 2                2 1                      4  3          3                                                      4                                    1
    Memory:_                  2                21                      4  3         3                                                       4                                   1
    Memory:_                   2                2 1                 4      3         3                                                   4                                      1
    Memory:_                   2                21                    4   3         3                                                    4                                    1
    Memory:_                    2                2 1               4         3        3                                               4                                        1
    Memory:_                    2                2  1               4         3        3                                              4                                         1
    Memory:_                    2                2       1        4             3        3                                          4                                             1
    Memory:_                     2                2     1         4             3       3                                          4                                            1
    Memory:_                       2                2  1        4                  3        3                                     4                                             1
    Memory:_                       2                2      1      4                 3       3                                     4                                              1
    Memory:_                        2                2      1     4                 3       3                                     4                                               1
    Memory:_                      2                2         1     4               3        3                                        4                                           1
    Memory:_                       2                2      1         4               3        3                                        4                                          1
    Memory:_                       2                2         1   4                  3         3                                     4                                              1
    Memory:_                       2                2          1    4              3         3                                        4                                              1
    Memory:_                        2                2         1   4             3         3                                         4                                                 1
    Memory:_                       2                2        1     4              3        3                                         4                                                1
    Memory:_                      2                2         1  4                3         3                                     4                                                      1
    Memory:_                      2                2           1   4              3        3                                       4                                                      1
    Memory:_                    2                2          1      4               3         3                                      4                                                  1
    Memory:_                   2                2            1     4              3         3                                     4                                                      1
    Memory:_                    2                2         1        4             3        3                                     4                                                      1
    Memory:_                   2                2            1       4          3         3                                       4                                                       1
    Memory:_                    2                2        1         4            3         3                                      4                                                   1
    Memory:_                    2                2       1           4             3         3                                      4                                                 1
    Memory:_                    2                2      1             4           3          3                                         4                                               1
    Memory:_                     2                2   1                  4        3          3                                            4                                          1
    Memory:_                     2                2      1                  4      3          3                                             4                                        1
    Memory:_                     2                2     1                   4      3         3                                               4                                      1
    Memory:_                     2                2         1                4    3         3                                                  4                                       1
    Memory:_                     2                2        1                 4     3        3                                                     4                                   1
    Memory:_                    2                2         1              4        3        3                                                 4                                      1
    Memory:_                    2                2       1              4          3       3                                                 4                                     1
    Memory:_                    2                2       1               4         3       3                                                 4                                      1
    Memory:_                    2                2      1               4        3      3                                                    4                                     1
    Memory:_                   2                2        1            4            3      3                                                4                                       1
    Memory:_                   2                2         1           4           3       3                                               4                                         1
    Memory:_                   2                2      1            4               3       3                                            4                                         1
    Memory:_                   2                2     1            4                3       3                                            4                                       1
    Memory:_1                  2                2 1              4                 3       3                                         4                                       1
    Memory:_                     2               12             4                 3       3                                         4                                        1
    Memory:_                     2               12               4                3       3                                          4                                       1
    Memory:_                     2                _              4                3       3                                          4                                          1
    Memory:_                     2                _              4                3       3                                         4                                           1
    Memory:_                     2              1 2              4                 3       3                                       4                                          1
    Memory:_                      2             1  2              4                3       3                                      4                                           1
    Memory:_                     2            1   2             4                  3       3                                     4                                            1
    Memory:_1                    2            1   2            4                 3        3                                    4                                             1
    Memory:_                     2               12             4                3       3                                     4                                                1
    Memory:_                    2                21                4               3        3                                      4                                               1
    Memory:_                   2                2 1                  4              3        3                                      4                                             1
    Memory:_                   2                2 1               4                 3        3                                    4                                             1
    Memory:_                     2                _                   4               3        3                                     4                                          1
    Memory:_                     2                2  1                 4              3         3                                      4                                         1
    Memory:_                   2                2       1             4              3        3                                        4                                           1
    Memory:_                    2                2       1          4              3         3                                        4                                             1
    Memory:_                    2                2         1           4          3        3                                              4                                          1
    Memory:_                    2                2         1           4           3       3                                              4                                          1
    Memory:_                    2                2      1            4            3        3                                          4                                            1
    Memory:_                      2                2   1           4              3        3                                         4                                             1
    Memory:_                     2                2   1               4          3        3                                            4                                        1
    Memory:_                      2                2  1               4        3        3                                              4                                           1
    Memory:_                      2                2 1             4           3        3                                            4                                            1
    Memory:_                    2                2   1             4           3         3                                          4                                               1
    Memory:_                      2                2 1              4           3         3                                         4                                              1
    Memory:_                      2                2  1                 4       3         3                                            4                                            1
    Memory:_                      2                2 1                 4      3         3                                             4                                          1
    Memory:_                      2                2     1             4      3         3                                             4                                               1
    Memory:_                     2                2      1                4  3        3                                                   4                                            1
    Memory:_                     2                2      1                43        3                                                     4                                             1
    Memory:_                     2                2       1           4     3        3                                                 4                                                1
    Memory:_                     2                2       1              4 3        3                                                     4                                             1
    Memory:_                    2                2         1               43       3                                                      4                                           1
    Memory:_                     2                2         1             4   3      3                                                     4                                           1
    Memory:_                     2                2      1               4     3      3                                                    4                                       1
    Memory:_                    2                2         1          4          3      3                                                4                                       1
    Memory:_                     2                2         1           4          3      3                                              4                                         1
    Memory:_                     2                2        1             4        3      3                                               4                                         1
    Memory:_                      2                2          1           4         3      3                                             4                                          1
    Memory:_                    2                2               1      4            3       3                                         4                                            1
    Memory:_                    2                2                1       4           3       3                                           4                                            1
    Memory:_                   2                2                   1     4              3       3                                         4                                              1
    Memory:_                   2                2                 1     4                 3       3                                     4                                               1
    Memory:_                   2                2              1          4             3       3                                        4                                             1
    Memory:_                  2                2                1          4            3       3                                          4                                               1
    Memory:_                   2                2             1             4            3       3                                          4                                                1
    Memory:_                  2                2            1            4            3        3                                          4                                                 1
    Memory:_                   2                2           1           4              3        3                                          4                                                1
    Memory:_                  2                2        1             4             3        3                                            4                                            1
    Memory:_                   2                2     1               4            3        3                                             4                                            1
    Memory:_                   2                2  1                   4          3        3                                             4                                         1
    Memory:_                   2                2     1             4           3        3                                             4                                           1
    Memory:_                    2                2     1           4            3        3                                             4                                             1
    Memory:_                     2                2     1       4               3        3                                           4                                                1
    Memory:_                      2                2    1         4            3        3                                              4                                              1
    Memory:_                     2                2     1        4             3        3                                           4                                               1
    Memory:_                    2                2         1        4          3       3                                               4                                             1
    Memory:_                     2                2     1          4          3       3                                              4                                           1
    Memory:_                     2                2     1         4            3       3                                             4                                              1
    Memory:_                     2                2      1          4          3       3                                             4                                               1
    Memory:_                    2                2       1       4              3       3                                          4                                                  1
    Memory:_                    2                2       1         4           3       3                                           4                                                   1
    Memory:_                   2                2        1           4         3       3                                            4                                                  1
    Memory:_                   2                2      1                4     3       3                                                4                                             1
    Memory:_                  2                2      1                 4   3       3                                                   4                                          1
    Memory:_                   2                2     1                 4   3       3                                                     4                                         1
    Memory:_                    2                2   1                   4      3       3                                                 4                                         1
    Memory:_                    2                2   1                   4       3       3                                                  4                                       1
    Memory:_                     2                2  1                  4          3        3                                              4                                        1
    Memory:_                     2                2   1                4            3       3                                              4                                        1
    Memory:_                     2                2    1               4             3       3                                             4                                        1
    Memory:_                    2                2   1               4                3       3                                          4                                           1
    Memory:_                    2                2   1             4                   3       3                                         4                                          1
    Memory:_                     2                2    1            4                  3       3                                         4                                           1
    Memory:_                     2                2      1        4                      3       3                                     4                                                1
    Memory:_                     2                2     1      4                         3        3                                  4                                                1
    Memory:_                     2                2    1      4                        3        3                                   4                                                  1
    Memory:_                    2                2     1         4                    3        3                                     4                                                1
    Memory:_                    2                2      1          4                3        3                                         4                                                1

    Shutting down ..
    Stopped JMX

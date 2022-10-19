#!/usr/bin/env python3
'''
Reducer for MapReduce
'''
import sys


if __name__ == '__main__':
    mean = 0
    count = 0
    var = 0

    line = sys.stdin.__next__()
    line = line.strip().split('_')
    var, mean, count = float(line[1]), float(line[2]), int(line[3])

    for line in sys.stdin:
        line = line.strip().split('_')
        cur_var, cur_mean, cur_count = int(line[1]), int(line[2]), int(line[3])
        new_mean = (cur_count * cur_mean + count * mean) / (cur_count + count)
        var = (cur_count * cur_var + count * var) / (cur_count + count) + \
            count * cur_count * ((mean - cur_count) / (count + cur_count)) ** 2
        mean = new_mean
    
    print("Var:", var)

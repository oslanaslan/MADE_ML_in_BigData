#!/usr/bin/env python3
'''
Reducer for MapReduce
'''
import sys

if __name__ == '__main__':
    sum_val = 0
    count_val = 0

    for line in sys.stdin:
        line = line.strip().split('_')
        cur_sum, cur_count = float(line[1]), int(line[2])
        sum_val += cur_sum
        count_val += cur_count
    
    mean = sum_val / count_val
    print("Mean:", mean)

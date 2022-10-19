#!/usr/bin/env python3
'''
Mapper for MapReduce
'''
import sys
import re


if __name__ == '__main__':
    sum_vals = 0
    count_vals = 0

    for line in sys.stdin:
        line = re.sub(r'".*?"', '', line, flags=re.DOTALL)
        line = line.strip('\n').split(',')
        line = [itm.strip() for itm in line]

        if len(line) > 10 and line[0].isdigit() and line[9].isdigit():
            val = int(line[9].strip())
            sum_vals += val
            count_vals += 1
        else:
            pass

    print('pref' + '_' + str(sum_vals) + '_' + str(count_vals))

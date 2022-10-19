#!/usr/bin/env python3
'''
Mapper for MapReduce
'''
import sys
import re


if __name__ == '__main__':
    vals = []

    for line in sys.stdin:
        line = re.sub(r'".*?"', '', line, flags=re.DOTALL)
        line = line.strip('\n').split(',')
        line = [itm.strip() for itm in line]

        if len(line) > 10 and line[0].isdigit() and line[9].isdigit():
            val = int(line[9].strip())
            vals.append(val)
        else:
            pass

    mean = sum(vals) / len(vals)
    vals = [(itm - mean) ** 2 for itm in vals]
    val = sum(vals) / (len(vals) - 1)

    print('pref' + '_' + str(val) + '_' + str(mean) + '_' + str(len(vals)))

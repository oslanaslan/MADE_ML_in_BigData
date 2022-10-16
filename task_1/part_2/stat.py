import os

import pandas as pd


DATA_PATH = os.path.join("data", "AB_NYC_2019.csv")


if __name__ == '__main__':
    df = pd.read_csv(DATA_PATH)
    prices = df.price[df.price.notna()]
    mean = prices.mean()
    var = prices.var()

    print(f'Mean: {mean}, variance: {var}')

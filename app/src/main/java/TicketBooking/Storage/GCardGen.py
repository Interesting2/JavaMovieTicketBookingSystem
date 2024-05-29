import io 
import pandas as pd
import numpy as np
import random
boolean_list = ['true','false']
columns = ['Gift Card Number', 'amount', 'available']
df = pd.DataFrame(columns = columns)
for i in range(50):
    df.loc[i] = [np.random.randint(1**16,10**16), (i * 10) + 50, "false"]
df.to_csv('Giftcard.csv')
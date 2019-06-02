import pandas as pd
import numpy as np


survey_responses = pd.read_csv("survey_export_01.csv")
survey_responses = survey_responses.loc[1:,
                   'I am the life of the party.':'I do not have a good imagination.'].dropna(axis=0)

pers_questions = pd.DataFrame(data={'questions': survey_responses.columns.tolist()})

traits = ['E', 'A', 'C', 'N', 'I']

for index, row in pers_questions.iterrows():
    pers_questions.loc[index, 'trait'] = traits[index%5]
    pers_questions.loc[index, 'is_reversed'] = (5 <= index <= 9) or (15 <= index <= 19)

survey_responses = survey_responses.T
for col in survey_responses.columns:
    survey_responses[col] = survey_responses[col].map({"This is totally inaccurate": -1,
                                                        "This is quite inaccurate": -.5,
                                                        "This not accurate nor inaccurate": 0,
                                                        "This is quite accurate": .5,
                                                        "This is totally accurate": 1,
                                                       "This is neither accurate or inaccurate": 0
                                                        })
survey_responses = survey_responses.dropna(axis=1).reset_index().rename(columns={'index': 'questions'})

survey_responses = pd.merge(left=survey_responses,
                            right=pers_questions,
                            on='questions',
                            how='left')
cols = [x+1 for x in range(36)]
cols.remove(12)

for col in cols:
    survey_responses[col] = np.where(survey_responses['is_reversed'], -1*survey_responses[col]/4+.5, survey_responses[col]/4+.5)

survey_responses = survey_responses.groupby("trait").mean().as_matrix()
means = survey_responses.mean(axis=1)
stds_within_trait = survey_responses.std(axis=1)
stds_between_trait = survey_responses.std(axis=0)

print(means)
print(stds_within_trait)
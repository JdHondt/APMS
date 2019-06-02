import numpy as np
import random as rd
import math
import matplotlib.pyplot as plt
import time
from scipy.stats import beta

# plt.rcParams.update({'font.size': 16})
plt.rcParams['pdf.fonttype'] = 42
plt.rcParams['font.family'] = 'Calibri'

class APMSSimulation:
    def __init__(self, avg_mess: int = 4, weeks: int = 5, exp_shape_factor: float = 0.05):
        self.weeks = weeks
        self.avg_mess = avg_mess

        self.participants = 150
        self.samples = self.weeks*self.avg_mess*self.participants

        self.bias = np.array([0.61,
                              0.57625,
                              0.53375,
                              0.54625,
                              0.41625])
        self.bias_stds = np.array([0.07077694,
                                   0.07909646,
                                   0.1015197,
                                   0.0545722,
                                   0.07380295])


        self.probs = np.array([tuple([rd.betavariate(alpha=max(self.calculate_alpha(self.bias[i], self.bias_stds[i]), 0.0000001),
                                                             beta=max(self.calculate_beta(self.bias[i], self.bias_stds[i]), 0.00000001))
                                      for i in range(5)]) for j in range(self.participants)])

        self.exp_shape_factor = exp_shape_factor

        self.feedback_rate = 0.5

        self.avg_params = np.ones((2,5))

    def calculate_alpha(self, mean, std):
        return (((1-mean)/std**2) - (1/mean))*mean**2

    def calculate_beta(self, mean, std):
        return self.calculate_alpha(mean, std)*(1/mean - 1)

    def simulate_intervention(self, development_plot: bool = False):
        # Initiate variable indicating the number of profile updates per participant
        part_samples = np.zeros(self.participants)

        # Initiate variable indicating the parameters of the Beta-Binomial distributions serving as profile estimates
        beta_params = np.ones((self.participants, 2, 5))

        successes = np.zeros((2, self.weeks))
        responses = np.zeros((2, self.weeks))

        for sample in range(self.samples):
            part = rd.randint(0, self.participants - 1)

            # Bernoulli experiment to determine if participant responds to message
            response_bool = np.random.binomial(size=1, n=1, p=self.feedback_rate)[0]

            if response_bool == 1:

                # Random
                if part < self.participants//2:
                    i = 0
                    draws = np.array([rd.betavariate(1, 1) for i in range(5)])

                # Adaptive
                else:
                    i = 1
                    # Apply shrinkage to individual Beta()
                    shrink_factor = 1 - math.exp(-self.exp_shape_factor * part_samples[part])
                    temp_alphas = np.array(
                        [self.avg_params[0, i] + shrink_factor * (beta_params[part, 0, i] - self.avg_params[0, i])
                         for i in range(5)])
                    temp_betas = np.array(
                        [self.avg_params[1, i] + shrink_factor * (beta_params[part, 1, i] - self.avg_params[1, i])
                         for i in range(5)])

                    # Draw from Beta()
                    draws = np.array([rd.betavariate(temp_alphas[i], temp_betas[i]) for i in range(5)])

                max_profile = np.argmax(draws)

                # Find outcome
                outcome = np.random.binomial(size=1, n=1, p=self.probs[part, max_profile])[0]

                successes[i, sample // (self.avg_mess*self.participants)] += outcome
                responses[i, sample // (self.avg_mess*self.participants)] += 1

                # Update general Beta() priors
                self.avg_params[0, max_profile] += outcome
                self.avg_params[1, max_profile] += 1 - outcome

                part_samples[part] += 1

                if i == 1:
                    # Update individual Beta() priors
                    beta_params[part, 0, max_profile] += outcome
                    beta_params[part, 1, max_profile] += 1 - outcome

        if development_plot:
            return np.divide(successes, responses)

        return (successes[1,:].sum() / responses[1,:].sum()) - (successes[0,:].sum() / responses[0,:].sum())
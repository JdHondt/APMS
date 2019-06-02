import numpy as np
import matplotlib.pyplot as plt
from datetime import datetime as dt
from apms_simulation import APMSSimulation

plt.rcParams['pdf.fonttype'] = 42
plt.rcParams['font.family'] = 'Calibri'

def setUpPlot1(name, manipulated_vars, nr_simulations=10, **kwargs):
    result = np.empty((len(manipulated_vars), 2))

    diff_results = np.empty((len(manipulated_vars), nr_simulations))

    for i in range(len(manipulated_vars)):
        for j in range(nr_simulations):
            sim = APMSSimulation(exp_shape_factor=manipulated_vars[i], weeks=kwargs['weeks'], std_factor=kwargs['std_factor']
                                 )
            diff_results[i, j] = sim.simulate_intervention()

    result[:, 0] = diff_results.mean(axis=1)
    # result[:, 1] = shrink_results.mean(axis=1)

    plt.plot(manipulated_vars, result[:, 0], '.-', label="$\sigma_p$={}".format(round(kwargs['std_factor']*0.8,2)), linewidth=4)
    plt.xlabel(name)
    plt.ylabel('Performance diff. [% pos. feedback]')
    plt.yticks(fontsize=18)
    plt.title("$\lambda$ optimization", weight='bold')
    plt.legend(loc="best", fontsize=18)

def setUpPlot2(fig, exp_shape_factor: float = 0.05, weeks: int = 32, nr_simulations: int = 10):
    mean_performances = np.zeros((2, weeks))
    for i in range(nr_simulations):
        sim = APMSSimulation(exp_shape_factor = exp_shape_factor, weeks = weeks)
        mean_performances += sim.sim_adaptshrink_random(True)
    mean_performances = mean_performances/nr_simulations

    x = [x for x in range(1, weeks+1)]


    plt.rc('font', family='serif')
    plt.rc('font', size=22)

    fig.add_subplot(122)
    plt.plot(x, mean_performances.T[:,0], 'b.-', label="Random", linewidth=4)
    plt.plot(x, mean_performances.T[:, 1], 'r.-', label="Adaptive ($\lambda={}$)".format(exp_shape_factor),linewidth=4)
    plt.legend(loc="upper left")
    plt.title("Performance development", weight='bold')
    plt.xlabel("Weeks into intervention")
    plt.ylabel("Performance [% pos. feedback]")
    plt.yticks(fontsize=18)

def plot1(fig, nr_simulations):
    manipulated_exp_shape = np.linspace(start=0.05, stop=.3, num=10)
    weeks = [5, 32, 128]

    plt.rc('font', family='serif')
    plt.rc('font', size=22)
    fig.add_subplot(121)
    for i in range(len(weeks)):
        setUpPlot1(name="$\lambda$", title=True,
                  manipulated_vars=manipulated_exp_shape,
                  weeks=32, nr_simulations=nr_simulations
                  )

def combine_plots():
    fig = plt.figure()
    plot1(fig, nr_simulations=20)
    setUpPlot2(fig, weeks=32, nr_simulations=20)
    plt.subplots_adjust(wspace=0.4)

    plt.show()
    fig.savefig('simulation_results_{}.pdf'.format(dt.strftime(dt.now(), "%d-%m_%H-%M")))

combine_plots()


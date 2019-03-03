# AI Predator Agent

<p align="center"><img src="https://raw.githubusercontent.com/IbrahimMuzaferija/AI-capture/master/enviroment-picture.png"></p>

## Introduction

The main objective of this project is an JAVA application that uses genetic algorithms to evolve neural network based controllers for the agent of the AI capture problem. More specifically, it is intended that the weights of the neural network(s) are evolved, because their topology (number of layers, number and type of neurons in each layer) has been decided in ```DataSet1.txt``` file. 

The performance of the controllers should be evaluated taking in consideration the ability to perform the intended task: Predator agent should move, in a fastest manner, to capture the Prey agent. The enviroment is 10x10 grid, Predator agent is blue cell and Prey agent is red cell.

<p align="center"><img src="https://raw.githubusercontent.com/IbrahimMuzaferija/AI-capture/master/UI.png"></p>


## Enviroment

- Both  agents  are  able  to  perceive  whether  the  four  adjacent  cells  (N,  S,  E,  W)  are  occupied  or  not;  
- Both  agents  can  move  to  the  four  adjacent  squares  (N,  S,  E,  W)  on  a  toroidal  grid;  
- The  predator  moves  in  all  iterations;  
- In  each  iteration,  the  prey  moves  with  probability  of  0.6  and,  consequently, stays  still  with  probability  of 0.4;
- To  move,  the  prey  chooses  randomly  from  the  adjacent  cells;
- The  prey never  moves  to  the  cell  where  the  predator  is;  
- At  each  iteration,  the  predator  first  moves  and  then the  prey  moves;  
- The  predator  catches  prey  when  moving  to  a  cell  where it  is;  



## Neural network

The  structure  of  the  neural  network  is  defined as 4 inputs, 10 hidden neurons and 2 output neurons. Neural network inputs are defined as 2 for Predators relative distance to Prey and 2 for position (as above/below and right/left). Weights of the network are initialized randomly, and are evolved by genetic algorithm. Only forward propagation is done when agent wants to decide in which cell to move, backpropagation is not needed because agents with better performance are kept alive for evolving.


## Genetic algorithm

Genetic algorithm creates initial population of 200 agents randomly, evaluates them based on certain criteria and selects best ones for mutation and recombination. Each individual agent has neural network weights as genome on wich mutation and recombination is performed. After genetic operations the individuals are mixed with initial pupulation by replacing the worst and first generation is created. This application evolves 100 generations to perfectly solve the problem. In each generation the above steps are repeated. The evaluation of agents is done by simulating them in 20 different enviroments (keeping the same weights) and summing total relative distance to the prey in each iteration until prey is cought or the maximu number of iterations is reached (200).

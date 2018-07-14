# AI-capture

## Introduction

The main objective of this project is an application that uses genetic algorithms** to evolve neural network based controllers for the agent of the AI capture problem. More specifically, it is intended that the weights of the neural network(s) are evolved, because their topology (number of layers, number and type of neurons in each layer) has been decided in ```DataSet1.txt``` file. 

The performance of the controllers should be evaluated taking in consideration the ability to perform the intended task: Predator agent should move, in a fastest manner, to capture the Prey agent.


## Enviroment

- Both  agents  are  able  to  perceive  whether  the  four  adjacent  cells  (N,  S,  E,  W)  are  occupied  or  not;  
- Both  agents  can  move  to  the  four  adjacent  squares  (N,  S,  E,  W)  on  a  toroidal  grid;  
- The  predator  moves  in  all  iterations;  
- In  each  iteration,  the  prey  moves  with  probability  of  0.6  and,  consequently, stays  still  with  probability  of 0.4;
- To  move,  the  prey  chooses  randomly  from  the  adjacent  cells;
The  prey never  moves  to  the  cell  where  the  predator  is;  
At  each  iteration,  the  predator  first  moves  and  then the  prey  moves;  
The  predator  catches  prey  when  moving  to  a  cell  where it  is;  
The  structure  of  the  neural  network  is  predefined,  that is 4 inputs, 10 hidden neurons and 2 output neurons;


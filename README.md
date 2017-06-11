# Tower Defense - LPOO Final Project Delivery
## Mateus Pedroza Cortes Marques - up201601876

### Documentation
    Documentation is at "doc" folder.
    
### Unit tests
    The unit test class is on /tests/ folder.
    
### Setup
##### Executable:
The game executable is in the root folder, download this repository and execute <b>"TowerDefense.jar"</b> to play.

##### Development:
Just clone this git repository and import the graddle project to your favorite IDE.
    
### Development Documentation

##### Implementations:
* LibGDX as the main framework.
* Box2D to handle the game physics.
* LibGDX-AI to handle the artificial intelligence.

##### Package structure:
![alt text](/images/packageStructure.png)

##### Type Hierarchy:
![alt text](/images/TypeHierarchy.png)

##### Design patterns used:
The game was developed in the MVC arquitecture. 
All actors were divided between a model, a controller that handles physics an AI, and the view.
For utility functions I used static classes with static functions.

##### Major difficulties:
 The major difficulty and the most painful thing to work on was LibGDX-AI, it took me a lot of time to get it going since the documentation it's not that good with no examples and most of the things you have to discover by reverse engineering.
    Another difficulty was using the MVC pattern instead of ECS due to make it easy unit testing, it made appear bugs in the view.
      
##### Distribution among team members:
   I made it all by myself.
   
### User Manual
![alt text](/images/manual.png)
- 1: Show how many waves are left and the time to next wave.
- 2: Click in this button than select a place to build a new tower.
- 3: Show remaining player life.
- 4: Show player gold ( needed to build towers ).

> When the time to the next wave ends, a wave of monsters is summoned, each monster that completes the path damages the player health.
> Build towers along the path to kill the monsters before they complete their path and win the game!
    

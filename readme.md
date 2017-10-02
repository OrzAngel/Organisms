<!-- readme.md -->


________
Overview
	
	This project simulate a organism game on a 10 X 10 board.
	Organisms of different kinds explore the board, consume food, reproduce themselves. 
____________
Installation 

	Run the GameLauncher.java in the ./src folder
_______
Classes
	
	* Organism

		This class represents one organism, tracks its playerID, energy, moving status and a "brain" - a variable of type Player

		This class has two constructors
			- one takes the playerID and a Player as arguments
			- one takes an organism as argument, create a new Organism with the same playerID and the brain of the same kind.
		
	* Cell

		this class represent a cell on the board, tracks its food storage and organism on this cell.

		This class is responible for 
			- food blowing in & reproducing
			- food consuming
	
	* GameConfigImp
		implements the GameConfig

		Contains all the public parameters of the game

	* PlayerRoundDataImp
		implements the PlayerRoundData

		tracks the count and energy of the organisms with the same ID. 

	* OrganismsGameImp 
		implements the OrganismGameInterface

		This class simulate this game, In each time cycle it will prompt organisms of different kind to make a move, and generate report for outside callers.

		It has a 2D Cell array representing the board

		Implementation detail:

			- in the initialize(), each player in the argument "players" List got assigned a playerID. They would compete against each other even they are of the same clas. 

			- the playGame() simulate one time cycle. This method will return false when either of the following occurs
				- The 5000 round is reached
				- All organisms are extincted
				- An Exception is thrown
			And the game should end at the same time
			
			- At each time cycle, the playgame() go over the borad twice
				- First, food got blowed in and reproduced
				- Second, when an organism is found, it will be forced to consume food if hugury and then asked to make a move.

			- Assumptions about the rules
				1. the newly produced child organism is not able to move in this time cycle, regardless of its position.
				2. if an organism wants to move without enough energy (but still enough energy for staying), it dies.
				3. There is no food on the grid at start

		Helper functions

			- print() 
				print the board

			-hasNextRound()
				check whether the game ends		

	* PlayerHuman
		implements the Player

		When the PlayerHuman.move() is called, this class will print the surronding to the consloe and ask user to make a decision via command line.

		Its name is "Terren"

	* PlayerAI
		implements the Player

		Simplest AI player, It is super CONSERVATIVE. Following the strategy below
			- always move towards food
			- reproduce when reach the max energy 
		
		Its name is "Zergling"

	* GameLauncher

		Tester class

		It creates one random player and two AI player, let them compete against each other.
		And output the PlayerRoundData of each time cycle to the "OrganismGame.txt" 



/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @original author Michael Kölling and David J. Barnes
 * 
 * @author Marc Weitze
 * @version 3/22/2020
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room mainRoom1, room2, room3, room4, room5, room6, room7, room8,
        room9, room10, room11, room12, room13, room14, exitRoom15, room16;
      
        // create the rooms
        mainRoom1 = new Room("poorly lit room, that seems to be an office");
        room2 = new Room("a well lit library");
        room3 = new Room("a dim hallway");
        room4 = new Room("another office that seems to be a dead end");
        room5 = new Room("a dim hallway");
        room6 = new Room("a study with a fireplace");
        room7 = new Room("a dead end");
        room8 = new Room("a lit hallway");
        room9 = new Room("a dark dead end that\n seems to not have been touched"
        + " in a while.\n You can see cobwebs and a\n leak from the ceiling");
        room10 = new Room("what seems to be a dead end but you can just " +
        "barely make out another door");
        room11 = new Room("a very well lit and seemingly sparkling clean " +
        "study.\n You realize it has been used recently as you see a cup of "
        + "\ncoffee steaming on the desk");
        room12 = new Room("an unlit hallway that has\n a little light showing"
        + " in what seems\n to be in the shape of a door");
        room13 = new Room("a more well lit hallway than before");
        room14 = new Room("a pitch black room that your eyes have \n" +
        "seemingly adjusted to");
        exitRoom15 = new Room("you can start to see some light coming from \n"
            + "outside the room. You realize this is the exit,\n you are "
            + " free to leave");
        room16 = new Room("You have left the mysterious mansion");
        
        // initialise room exits
        mainRoom1.setExit("north", room12);
        mainRoom1.setExit("west", room6);
        mainRoom1.setExit("south", room2);
        
        room2.setExit("north", mainRoom1);
        room2.setExit("south", room3);
        room2.setExit("west", room5);
        room2.setExit("east", room8);
        
        room3.setExit("north", room2);
        room3.setExit("west", room4);
        
        room4.setExit("east", room3);
        
        room5.setExit("north", room6);
        room5.setExit("east", room2);
        
        room6.setExit("north", room7);
        room6.setExit("east", mainRoom1);
        room6.setExit("south", room5);
        
        room7.setExit("south", room6);
        
        room8.setExit("west", room2);
        room8.setExit("north", room10);
        room8.setExit("east", room9);
        
        room9.setExit("west", room8);
        
        room10.setExit("north", room11);
        room10.setExit("south", room8);
        
        room11.setExit("south", room10);
        
        room12.setExit("north", room13);
        room12.setExit("south", mainRoom1);
        
        room13.setExit("west", room14);
        room13.setExit("south", room12);
        
        room14.setExit("west", exitRoom15);
        room14.setExit("east", room13);
        
        exitRoom15.setExit("west", room16);
        exitRoom15.setExit("east", room14);
        
        //sets the names of the rooms
        mainRoom1.setName("mainRoom1");
        room2.setName("room2");
        room3.setName("room3");
        room4.setName("room4");
        room5.setName("room5");
        room6.setName("room6");
        room7.setName("room7");
        room8.setName("room8");
        room9.setName("room9");
        room10.setName("room10");
        room11.setName("room11");
        room12.setName("room12");
        room13.setName("room13");
        room14.setName("room14");
        exitRoom15.setName("exitRoom15");
        room16.setName("room16");
        
        
        currentRoom = mainRoom1;  // start game in the Main Room
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished)
        {
            if(currentRoom.getName().equals("room16"))
            {
                System.out.println("After you leave the mansion, you decide "
                + "to head home\n and go to sleep in what you believe to be"
                + " your house.\n You wake up and realize it was all a dream,"
                + " as your alarm\n clock starts ringing to wake you up.\n"
                + "You are late for work.");
                break;
            }
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new text adventure game!");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
            
            case LOOK:
                look();
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
            
            case TIME:
                checkTime();
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("what seems to be a creepy mansion.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    /**
     * "look" was entered. "look" outputs the current description of the room.
     */
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }
    /**
     * "time" was entered. "time" sends a message to check the time
     */
    private void checkTime()
    {
        System.out.println("You attempt to check the time.\n" +
            " You realize you are not wearing a watch \n and there are no "
            + "windows to look out of.\n You cannot tell what time " +
            "of day it is.");
    }
}

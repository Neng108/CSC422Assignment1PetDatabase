/*
Neng Yang - yangn20@csp.edu
CSC 422 Assignment 2, Part 2
Topics: Github Issue and project 
Program: Pet database program for managing information (name and age) about pets.
Allow the user to add pet information to the database, remove pet information, updating, and searching for a pet by name or by age. 
NOTE* Assume user only inputs pets name consisting of a single word.
Allow loading pet data from text file, and saving to text file.
Handle Errors
*/
package com.mycompany.csc422assignment1petdatabase;

import java.util.Scanner;
import java.io.*;

public class PetDatabase {
    
    public static Pet[] pets = new Pet[1000]; //The Pet array used to store pet objects 
    public static String[] inputSplit = new String[100];//array for splitting the user input of name and age. 
    public static int petCount = 0; //A pet counter variable 
    public static Scanner input = new Scanner(System.in); //Scanner object for reading the inputs from the user
    public static String fileName = "petFile.txt";
    
    public static void main(String[] args) throws Exception{
        System.out.println("Pet Database Program"); 
        loadPetDataFile();//load the pet data file 
        while(true){
            //Option menu for user 
            System.out.println("\nWhat would you like to do?");
            System.out.println(" 1) View all pets");
            System.out.println(" 2) Add more pets");
            //System.out.println(" 3) Update an existing pet"); 
            System.out.println(" 3) Remove an existing pet");
            //System.out.println(" 5) Search pets by name");
            //System.out.println(" 6) Search pets by age"); 
            System.out.println(" 4) Exit the program");
            System.out.println("Your choice: ");
            int choice = input.nextInt(); 
            input.nextLine();
            
            //Switch case statement to call each method depending on which option the user types in 
            switch(choice){
                case 1:{
                    showAllPets(); 
                    break;
                }
                case 2:{ 
                    addMorePets(); 
                    break;
                }
                case 3:{ 
                    removeAPet();
                    //updateAPet();
                    break;
                }
                case 4:{ 
                    System.out.println("Goodbye!");
                    savePetDataFile();//save pet data to the file when user quits. 
                    return;
                }
                /*
                case 5:{
                    searchPetsByName(); 
                    break;
                }
                case 6:{
                    searchPetsByAge(); 
                    break;
                }
                case 7:{
                    System.out.println("Goodbye!");
                    return;
                }
                */
            }//End of switch statement 
        }//End of while loop 
    }//end of main 
    
    public static void loadPetDataFile() throws Exception{
        try{
            File petDataFile = new File(fileName); 
            Scanner input = new Scanner(petDataFile);//Create the scanner object to read from file. 
            
            while(input.hasNext()){//while loop to read each line of the file 
                String data = input.nextLine();
                inputSplit = data.split(" ");//split the name and age 
                
                String name = inputSplit[0];
                int age = Integer.parseInt(inputSplit[1]);// convert the String age to int 
                
                //store into the array to be displayed 
                Pet petObject = new Pet(name, age);
                pets[petCount] = petObject; 
                petCount++; 
            }//end of while loop 
        }
        catch(FileNotFoundException ex){//catch for error handling if file doesn't exist yet. 
            System.out.println("Pet file does not exist yet");
        }
    }//end of loadPetDataFile
    
    public static void savePetDataFile() throws IOException{
        File petfile = new File(fileName);//create the file 
        
        if (petfile.exists()) {//Display to user if file exists already when saving/exit program
          System.out.println("File already exists");
        }
        
        //Write to the file using PrintWriter object
        PrintWriter output = new PrintWriter(petfile);
        
        //add each of the pet's name and age to the file 
        for(int i = 0; i < petCount; i++){
            output.println(pets[i].getName() + " " + pets[i].getAge());
        }
        // Close the file
        output.close();
    }
    
    public static void showAllPets(){
        printTableHeader();//Call printTableHeader method to print header
        
        for(int i = 0; i < petCount; i++){//using a for loop to access each pet object in "pets" Array and print information using printTableRow method 
            printTableRow(i, pets[i].getName(), pets[i].getAge());//using getters in Pet class
        }
        
        printTableFooter(petCount);//call printTableFooter method to print how many pets are in the set
        //NOTE* petCount keeps track of how many pets are the in the array/database
    }//end of showAllPets()
    
    public static void addMorePets(){
        int numberOfPetsAdded = 0; //Variable to keep track of number of pets to be printed for the user 
        while(true){//while loop for user input for adding pets 
            System.out.println("add pet (name, age): ");
            String userPet = input.nextLine(); 
        
            if(userPet.equalsIgnoreCase("done")){ //if user types "done" break out of loop 
                break; 
            }
            //Need to extract out the name and age 
            //String name; 
            int space = 0;//creating the int index for the space in which the name and age will be split at  
                while(userPet.charAt(space) != ' '){//while loop to add 1 to space index for every index of the single string 
                    space++;//add one to int space until the space separating the name and age is reached 
                }//end of while loop finding the int index for the space between the name and age 
        
            String name = userPet.substring(0, space); //substring(starting index at 0, to index of the space found above) to get pet name 
            int age = Integer.parseInt(userPet.substring(space+1)); //substring(starting index at the space index, to the rest of the string) to get age 
            
            Pet petObject = new Pet(name, age);//create a new pet object using the name and age found above 
            
            pets[petCount] = petObject; //iterate through each index of the array and store the object using petCount 
            petCount++; 
            
        numberOfPetsAdded++; //numberOfPetsAdded is needed to show the user how many pets they added once addPets() is entered and existed 
        //So if the user only adds 1 or 2 pets this will number will be displayed instead. Using petCount will display number of pets in the entire array/database
        }//end of while loop for adding pets 
        
        System.out.println(numberOfPetsAdded + " pet(s) added.");
    }//end of addMorePets()
    
    public static void updateAPet(){
        showAllPets();//show the user the table of all the pets first
        
        System.out.println("Enter the pet ID you want to update: ");
        int petID = input.nextInt();//save user's input of the ID, which is the object's index of the array 
        input.nextLine(); 
        
        System.out.println("Enter the new name and new age: ");
        String newPet = input.nextLine(); //save the string of the new name and age 
        
        //split up the user's new name and age with the space 
        int space = 0;
        while(newPet.charAt(space) != ' '){
            space++; //retrieve the space ' ' index 
        }
        //use substring to seperate and save new name and new age for pet 
        String name = newPet.substring(0, space); 
        int age = Integer.parseInt(newPet.substring(space+1)); 
        
        //Save the name and age of the pet the user is about to update/or known as the the original name and age
        String previousPetName = pets[petID].getName();
        int previousPetAge = pets[petID].getAge();
        
        //Call both the setName and setAge method in the Pet Class to set and update the pet 
        pets[petID].setName(name);
        pets[petID].setAge(age); 
        
        //Display the message after updating 
        System.out.println(previousPetName+ " " + previousPetAge + " has changed to " + name + " " + age + ".");
    }//end of updatePet()
    
    public static void removeAPet(){
        showAllPets();//Display the table of pets first 
        
        System.out.println("Enter the pet ID to remove: ");//prompts the user for an index or known as the ID of the pet 
        int petID = input.nextInt(); 
        
        //Saving the petID as a new object. This is to display to the user what pet they removed. 
        Pet petObject = pets[petID]; 
        
        for(int i = petID; i < petCount; i++){//for loop to iterate through the pets array 
            pets[i] = pets[i+1];//replacing the pet object that was deleted with the following pets after it. Keeping the indexes in the correct position
        }
        
        System.out.println(petObject.getName() + " " + petObject.getAge() + " is removed.");//Print out the pet's name and age that was deleted 
        
        petCount--;//Subtract one to petCount since a pet was removed. 
    }//end of removeAPet()
    
    public static void searchPetsByName(){
        System.out.println("Enter a name to search: ");
        String name = input.nextLine(); 
        
        //Once user inputs the name, display the pet's information 
        printTableHeader();//header first 
        
        int numberOfRows = 0; //Declare and initialize numberOfRows to 0 to get the # of rows for each pet with a certain name. numberOfRows will be used for the footer
        
        for(int i = 0; i < petCount; i++){//Iterate through the pets array 
            if(pets[i].getName().equalsIgnoreCase(name)){//Using .equalsIgnoreCase because name will be case insensitive. 
            printTableRow(i, pets[i].getName(), pets[i].getAge());//Print the row(s)
            numberOfRows++;//Adding 1 to numberOfRows everytime the user's input of the name equals a name in the pets array 
            } 
        }//end of for loop 
        
        printTableFooter(numberOfRows);//Call printTabelFooter method with the numberOfRows 
    }//end of searchPetsByName()
    
    public static void searchPetsByAge(){
        System.out.println("Enter age to search: ");
        int age = input.nextInt(); 
        
        printTableHeader(); 
        
        int numberOfRows = 0; 
        
        for(int i = 0; i < petCount; i++){ 
            if(pets[i].getAge() == age){//compare the pet's (int) age to user's input        
            printTableRow(i, pets[i].getName(), pets[i].getAge()); 
            numberOfRows++;
            } 
        }//end of for loop 
        
        printTableFooter(numberOfRows);
    }//end of searchPetsByAge()
    
    public static void printTableHeader(){//Prints the header of the table
        System.out.println("+------------------------------+");
        System.out.printf("|%3s %2s %-10s %5s %2s %3s\n", "ID", "|", "NAME", "|", "AGE", "|");
        System.out.println("+------------------------------+");
    }//end of printTableHeader()
    
    
    public static void printTableRow(int ID, String name, int age){//Prints rows of table 
            System.out.printf("|%3d %2s %-10s %5s %2d %4s\n", ID, "|", name, "|", age, "|");
    }//end of printTableRow() 
    
    
    public static void printTableFooter(int rowCount){//Prints the footer/how many rows in the set 
        System.out.println("+------------------------------+");
        System.out.println(rowCount + " row(s) in set. ");
    }//end of printTableFooter
    
}//end of class CSC422PetDatabase


//Pet class to represent possible pet objects. 
class Pet{
    //Properties 
    private String name; //Name of pet
    private int age; //Age of pet 
    
    public Pet(String name, int age){ //Constructor for the Pet Class
        this.name = name; 
        this.age = age;
    }
    
    //getters and setters
    public String getName(){ 
        return this.name; 
    }
    
    public int getAge(){ 
        return this.age; 
    }
    
    public void setName(String name){ 
        this.name = name; 
    }
    
    public void setAge(int age){ 
        this.age = age;
    }
}//End of Pet Class

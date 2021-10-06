import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Driver {
    public static void main (String[] args){
        boolean on = true;
        boolean loop;

        //List<ToDoItem> toDoItemList = new ArrayList<>();
        List<ToDoItem> toDoItemList = new LinkedList<>();

        //Take the user to the proper menu based on the given selection
        while(on){
            //Present user with a Main Menu where they can choose what they would like to do
            System.out.println("===========Main Menu===========\n");
            System.out.println("1) Add items to your To-Do List\n2) Mark items as completed\n3) Display To-Do List\nQ) Quit");
            System.out.println("Make A Selection:");
            Scanner user = new Scanner(System.in);
            String selection = user.nextLine();
            loop = true;
            while (loop){
                switch(selection){
                    case "1":
                        //Ask user to input their To-Do items
                        System.out.println("Type your to-do item (if no more items to add, Type 'Done'): ");
                        Scanner newItem = new Scanner(System.in);
                        String addition = newItem.nextLine();
                        //Check if user inputs keyword, "Done". If not, add to-do item to toDoItemList
                        if (addition.equalsIgnoreCase("Done")) {
                            loop = false;
                            break;
                        } else {
                            ToDoItem addItem = new ToDoItem();
                            addItem.setMessage(addition);
                            toDoItemList.add(addItem);
                        }
                    case "2":
                        //Mark To-Do items as complete
                        /*
                        Prompts user to provide console with the index of the completed task
                        The task that corresponds to that index will be marked complete with an asterisk
                        If the index that is provided does not exist, the user will receive a message stating,
                        "This to-do item does not exist"
                         */
                        System.out.println("What items have you completed?");
                        Scanner itemIndex = new Scanner(System.in);
                        int completed = itemIndex.nextInt();
                        int index = completed-=1;
                        break;
                    case "3":
                        //Prints the List of To Do Items to the console
                        System.out.println("===========To-Do List==========");
                        if (toDoItemList.size() == 0){
                            System.out.println("Empty");
                        }
                        for (int i = 0; i < toDoItemList.size(); i++) {
                            System.out.println((i + 1) + ") " + "[ ] " + toDoItemList.get(i));
                        }
                        loop = false;
                           break;
                    case "Q":
                    case "q":
                        on = false;
                        loop = false;
                        break;
                }
            }

        }



    }
}

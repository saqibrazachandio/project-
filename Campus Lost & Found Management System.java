package CampusLostAndFound;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Student student = new Student(
            13, "Saqib Raza", "03347889820"
        );

        ItemManager manager = new ItemManager();

        System.out.println("======================================");
        System.out.println("       CAMPUS LOST & FOUND SYSTEM");
        System.out.println("======================================");
        System.out.println("Welcome to our University System!");

        student.displayStudentInfo();

        int choice;

        do {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Report Lost Item");
            System.out.println("2. Report Found Item");
            System.out.println("3. View All Lost Items");
            System.out.println("4. View All Found Items");
            System.out.println("5. Search Item");
            System.out.println("6. Find Matching Items");
            System.out.println("7. Delete Item");
            System.out.println("8. Exit");
            System.out.println("===============================");

            choice = readNumber("Enter your choice: ");

            switch (choice) {

                case 1:
                    System.out.print("Enter item name: ");
                    String lostName = sc.nextLine();

                    System.out.print("Enter category: ");
                    String lostCategory = sc.nextLine();

                    System.out.print("Enter lost location: ");
                    String lostLocation = sc.nextLine();

                    manager.addLostItem(
                        lostName, lostCategory, lostLocation,
                        student.getStudentName(),
                        student.getContactNumber()
                    );
                    break;

                case 2:
                    System.out.print("Enter item name: ");
                    String foundName = sc.nextLine();

                    System.out.print("Enter category: ");
                    String foundCategory = sc.nextLine();

                    System.out.print("Enter found location: ");
                    String foundLocation = sc.nextLine();

                    manager.addFoundItem(
                        foundName, foundCategory, foundLocation,
                        student.getStudentName(),
                        student.getContactNumber()
                    );
                    break;

                case 3:
                    manager.displayItems("Lost");
                    break;

                case 4:
                    manager.displayItems("Found");
                    break;

                case 5:
                    System.out.print("Enter item name or keyword: ");
                    String keyword = sc.nextLine();
                    manager.searchItem(keyword);
                    break;

                case 6:
                    manager.findMatches();
                    break;

                case 7:
                    int deleteId = readNumber("Enter item ID to delete: ");
                    manager.deleteItem(deleteId);
                    break;

                case 8:
                    System.out.println("Thank you for using Campus Lost & Found System!");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 8);

        sc.close();
    }

    static int readNumber(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // STUDENT CLASS

    static class Student {

        private int studentId;
        private String studentName;
        private String contactNumber;

        public Student(int studentId, String studentName,
                       String contactNumber) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.contactNumber = contactNumber;
        }

        public int getStudentId() {
            return studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public void displayStudentInfo() {
            System.out.println("\nStudent ID: " + studentId);
            System.out.println("Student Name: " + studentName);
            System.out.println("Contact: " + contactNumber);
        }
    }

    // ABSTRACT ITEM CLASS

    static abstract class Item {

        private int itemId;
        private String itemName;
        private String category;
        private String location;
        private String reportedBy;
        private String contact;

        public Item(int itemId, String itemName, String category,
                    String location, String reportedBy, String contact) {

            this.itemId = itemId;
            this.itemName = itemName;
            this.category = category;
            this.location = location;
            this.reportedBy = reportedBy;
            this.contact = contact;
        }

        public int getItemId() {
            return itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public String getCategory() {
            return category;
        }

        public String getLocation() {
            return location;
        }

        public String getType() {
            return "Item";
        }

        public void displayDetails() {
            System.out.println("\nItem ID: " + itemId);
            System.out.println("Item Name: " + itemName);
            System.out.println("Category: " + category);
            System.out.println("Location: " + location);
            System.out.println("Reported By: " + reportedBy);
            System.out.println("Contact: " + contact);
            System.out.println("Type: " + getType());
        }
    }

    // LOST ITEM CLASS

    static class LostItem extends Item {

        public LostItem(int id, String name, String category,
                        String location, String reporter, String contact) {

            super(id, name, category, location, reporter, contact);
        }

        @Override
        public String getType() {
            return "Lost";
        }
    }

    // FOUND ITEM CLASS

    static class FoundItem extends Item {

        public FoundItem(int id, String name, String category,
                         String location, String reporter, String contact) {

            super(id, name, category, location, reporter, contact);
        }

        @Override
        public String getType() {
            return "Found";
        }
    }

    // ITEM MANAGER CLASS

    static class ItemManager {

        private ArrayList<Item> items = new ArrayList<>();

        private int nextId = 1;

        public void addLostItem(String name, String category,
                                String location, String reporter,
                                String contact) {

            Item item = new LostItem(
                nextId++, name, category, location, reporter, contact
            );

            items.add(item);
            System.out.println("Lost item reported successfully!");
            System.out.println("Your Item ID is: " + item.getItemId());
        }

        public void addFoundItem(String name, String category,
                                 String location, String reporter,
                                 String contact) {

            Item item = new FoundItem(
                nextId++, name, category, location, reporter, contact
            );

            items.add(item);
            System.out.println("Found item reported successfully!");
            System.out.println("Your Item ID is: " + item.getItemId());
        }

        public void displayItems(String type) {

            boolean found = false;

            System.out.println("\n========== " + type.toUpperCase()
                               + " ITEMS ==========");

            for (Item item : items) {

                if (item.getType().equals(type)) {
                    item.displayDetails();
                    System.out.println("----------------------------");
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No " + type + " items available.");
            }
        }

        public void searchItem(String keyword) {

            boolean found = false;

            for (Item item : items) {

                if (item.getItemName().toLowerCase()
                        .contains(keyword.toLowerCase())
                    || item.getCategory().toLowerCase()
                        .contains(keyword.toLowerCase())
                    || item.getLocation().toLowerCase()
                        .contains(keyword.toLowerCase())) {

                    item.displayDetails();
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No matching item found.");
            }
        }

        public void findMatches() {

            boolean matchFound = false;

            System.out.println("\n========== POSSIBLE MATCHES ==========");

            for (Item lost : items) {

                if (lost.getType().equals("Lost")) {

                    for (Item found : items) {

                        if (found.getType().equals("Found")) {

                            if (lost.getItemName().equalsIgnoreCase(
                                    found.getItemName())
                                || lost.getCategory().equalsIgnoreCase(
                                    found.getCategory())) {

                                System.out.println("\nPossible Match Found!");

                                System.out.println("Lost Item:");
                                lost.displayDetails();

                                System.out.println("\nFound Item:");
                                found.displayDetails();

                                matchFound = true;
                            }
                        }
                    }
                }
            }

            if (!matchFound) {
                System.out.println("No possible matches found.");
            }

            System.out.println("Note: These are possible matches, not confirmed matches.");
        }

        public void deleteItem(int id) {

            boolean removed = false;

            for (int i = 0; i < items.size(); i++) {

                if (items.get(i).getItemId() == id) {

                    items.remove(i);
                    removed = true;

                    System.out.println("Item deleted successfully!");
                    break;
                }
            }

            if (!removed) {
                System.out.println("Item ID not found.");
            }
        }
    }
}
 import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

    public class HallBookingSystem {

        // Constants for showtimes
        private static final String MORNING = "Morning";
        private static final String AFTERNOON = "Afternoon";
        private static final String NIGHT = "Night";

        // Constants for seat status
        private static final String AVAILABLE = "AV";
        private static final String BOOKED = "BO";

        // 2D array to represent halls and their seats
        private static final String[][] halls = {
                {"Hall A", AVAILABLE, AVAILABLE, AVAILABLE},
                {"Hall B", AVAILABLE, AVAILABLE, AVAILABLE},
                {"Hall C", AVAILABLE, AVAILABLE, AVAILABLE}
        };

        // Booking history
        private static final String[][] bookingHistory = new String[10][5];
        private static int bookingCount = 0;

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nWelcome to Hall Booking System");
                System.out.println("1. Hall Booking");
                System.out.println("2. Hall Checking");
                System.out.println("3. Showtime Checking");
                System.out.println("4. Booking History");
                System.out.println("5. Rebooting Hall");
                System.out.println("6. Exit");
                System.out.print("Select an option (1-6): ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        bookHall(scanner);
                        break;
                    case 2:
                        displayHallStatus();
                        break;
                    case 3:
                        displayShowtimeSchedule();
                        break;
                    case 4:
                        displayBookingHistory();
                        break;
                    case 5:
                        rebootHall();
                        break;
                    case 6:
                        System.out.println("Exiting Hall Booking System. Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option. Please select again.");
                }
            }
        }

        private static void bookHall(Scanner scanner) {
            System.out.println("\nHall Booking:");
            System.out.println("Available Showtimes: 1. Morning 2. Afternoon 3. Night");
            System.out.print("Enter showtime number: ");

            int showtime = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String showtimeStr = getShowtimeString(showtime);

            System.out.println("\nAvailable Halls:");
            displayHallStatus();
            System.out.print("Enter hall name: ");
            String hallName = scanner.nextLine();

            int hallIndex = getHallIndex(hallName);

            if (hallIndex == -1) {
                System.out.println("Invalid hall name. Please try again.");
                return;
            }

            System.out.print("Enter seat number: ");
            int seatNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (seatNumber < 1 || seatNumber > halls[hallIndex].length - 1 || halls[hallIndex][seatNumber].equals(BOOKED)) {
                System.out.println("Invalid seat number or seat already booked. Please try again.");
                return;
            }

            System.out.print("Enter student card ID: ");
            String studentCardID = scanner.nextLine();

            // Validate student card ID using RegEx
            if (!validateStudentCardID(studentCardID)) {
                System.out.println("Invalid student card ID format. Please try again.");
                return;
            }

            // Perform booking
            halls[hallIndex][seatNumber] = BOOKED;

            // Update booking history
            bookingHistory[bookingCount++] = new String[]{hallName, String.valueOf(seatNumber), studentCardID, getCurrentDate(), showtimeStr};
            System.out.println("Booking successful!");
        }

        private static void displayHallStatus() {
            System.out.println("\nHall Status:");
            System.out.println("Seat legend: AV (Available), BO (Booked)");

            for (String[] hall : halls) {
                System.out.print(hall[0] + ": ");
                for (int i = 1; i < hall.length; i++) {
                    System.out.print(hall[i] + " ");
                }
                System.out.println();
            }
        }

        private static void displayShowtimeSchedule() {
            System.out.println("\nShowtime Schedule:");
            System.out.println("1. Morning 2. Afternoon 3. Night");
            System.out.println("Morning: 9 AM - 12 PM");
            System.out.println("Afternoon: 1 PM - 4 PM");
            System.out.println("Night: 7 PM - 10 PM");
        }

        private static void displayBookingHistory() {
            System.out.println("\nBooking History:");
            System.out.println("Hall Name | Seat Number | Student Card ID | Date | Showtime");

            for (int i = 0; i < bookingCount; i++) {
                for (int j = 0; j < bookingHistory[i].length; j++) {
                    System.out.print(bookingHistory[i][j] + " | ");
                }
                System.out.println();
            }
        }

        private static void rebootHall() {
            System.out.println("\nRebooting Hall...");
            // Reset all seats to available
            for (String[] hall : halls) {
                for (int i = 1; i < hall.length; i++) {
                    hall[i] = AVAILABLE;
                }
            }
            System.out.println("Hall rebooted successfully!");
        }

        private static String getShowtimeString(int showtime) {
            switch (showtime) {
                case 1:
                    return MORNING;
                case 2:
                    return AFTERNOON;
                case 3:
                    return NIGHT;
                default:
                    return "";
            }
        }

        private static int getHallIndex(String hallName) {
            for (int i = 0; i < halls.length; i++) {
                if (halls[i][0].equalsIgnoreCase(hallName)) {
                    return i;
                }
            }
            return -1; // Hall not found
        }

        private static String getCurrentDate() {
            // Simulate getting current date (for demonstration purposes)
            return "2024-01-01";
        }

        private static boolean validateStudentCardID(String studentCardID) {
            // Validate student card ID using RegEx
            String regex = "^[A-Za-z0-9]{8}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(studentCardID);
            return matcher.matches();
        }
    }

}

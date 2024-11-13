#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
    char input[100];
    char buffer[256];
    FILE *fp;

    while (1) {
        // Clear the console
        system("cls");

        // Execute the 'del' command to delete all .class files and redirect output to a file
        system("del *.class > output.txt 2>&1");
        // Compile the Java files and redirect output to the same file
        system("javac *.java Parsers/*.java >> output.txt 2>&1");

        // Open the output file for reading
        fp = fopen("output.txt", "r");
        if (fp == NULL) {
            perror("Error opening file");
            return 1;
        }

        // Read the file line by line and check for the word "error"
        int foundError = 0;
        while (fgets(buffer, sizeof(buffer), fp) != NULL) {
            if (strstr(buffer, "error") != NULL) {
                foundError = 1;
                break;
            }
        }

        // If an error is found, print the contents of the file
        if (foundError) {
            printf("Error detected in the output. Here are the details:\n");
            // Rewind the file pointer to the beginning of the file
            rewind(fp);
            // Print the file contents
            while (fgets(buffer, sizeof(buffer), fp) != NULL) {
                printf("%s", buffer);
            }
        }

        // Close the file
        fclose(fp);

        // Keep the command prompt window open until "exit" or "retry" is entered
        while (1) {
            if (foundError) {
                printf("\nType 'exit' to close the window or 'retry' to re-run the program: ");
            } else {
                printf("No errors detected. Type 'exit' to close the window or 'retry' to re-run the program: ");
            }
            fgets(input, sizeof(input), stdin);
            // Remove newline character from input
            input[strcspn(input, "\n")] = 0;
            if (strcmp(input, "exit") == 0) {
                // Delete the output file before exiting
                if (remove("output.txt") != 0) {
                    perror("Error deleting file");
                } else {
                    printf("Output file deleted successfully.\n");
                }
                return 0;
            } else if (strcmp(input, "retry") == 0) {
                break; // Break the inner loop to re-run the program
            }
        }
    }

    return 0;
}
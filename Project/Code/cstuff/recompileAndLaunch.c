#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
    char buffer[256];
    FILE *fp;

    while (1) {
        // Clear the console
        system("cls");

        // Execute the 'del' command to delete all .class files and redirect output to a file
        system("del OceansEdge/*.class > output.txt 2>&1");
        // Compile the Java files and redirect output to the same file
        system("javac OceansEdge/*.java OceansEdge/Parsers/*.java >> output.txt 2>&1");

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

        // Close the file
        fclose(fp);

        if (foundError) {
            printf("Compilation failed. Please fix the errors and try again.\n");
            // Print the contents of the file
            fp = fopen("output.txt", "r");
            if (fp != NULL) {
                while (fgets(buffer, sizeof(buffer), fp) != NULL) {
                    printf("%s", buffer);
                }
                fclose(fp);
            }
            // Wait for user input before exiting
            printf("Type 'retry' to try again or 'exit' to exit: ");
            char input[10];
            fgets(input, sizeof(input), stdin);
            input[strcspn(input, "\n")] = 0; // Remove newline character

            if (strcmp(input, "exit") == 0) {
                break;
            } else if (strcmp(input, "retry") == 0) {
                continue;
            }
        } else {
            printf("Compilation successful. Launching Java program...\n");
            system("title Ocean's Edge debug page && java OceansEdge.Client");
            break;
        }
    }

    // Delete the output file before exiting
    if (remove("output.txt") != 0) {
        perror("Error deleting file");
    } else {
        printf("Output file deleted successfully.\n");
    }

    return 0;
}
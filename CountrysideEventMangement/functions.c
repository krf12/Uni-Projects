#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "structs.h"
#include "functions.h"

/*
 * 
 */

/*This method is used to find out if the competitor exists within the entrants linked list*/
void findCompetitor(ENTRANT** head, int num, char name[]) {
    ENTRANT *current = *head;
    ENTRANT *next = current ->next;

    strcpy(name, "Not Found");


    if (current ->number == num) {
        strcpy(name, current ->name);
    } else {
        while (next != NULL) {
            current = next;
            next = current ->next;

            if (current ->number == num) {
                strcpy(name, current ->name);
            }
        }
    }
}

/*This method does the same as the above method but it also returns the course they were attached to, so it can be printed in
 the results*/

char findResultsCompetitor(ENTRANT** head, int num, char name[]) {
    ENTRANT *current = *head;
    ENTRANT *next = current ->next;

    char course;

    strcpy(name, "Not Found");


    if (current ->number == num) {
        strcpy(name, current ->name);
        course = current ->course;
    } else {
        while (next != NULL) {
            current = next;
            next = current ->next;

            if (current ->number == num) {
                strcpy(name, current ->name);
                course = current ->course;
            }
        }
    }

    return course;

}

/*This method is used to find the location of a competitor using their competitor number and the time that the user is querying
 against. Setting the node to 100 is used to determine is the competitor is finished. Potential problems
 for large amounts of nodes. */

int findLocation(TIME** head, int num, int h, int m) {
    //Sets the current to the head of the linked list and sets the next to what the head's pointer is pointing to.
    TIME *current = *head;
    TIME *next = current ->next;

    int node = 0;
    int count = 0;


    if ((current -> comp == num) && (current ->node == 1)) {
        count = count + 1;
    }
    /*Checks is the competitor numbers are the same. The compare time method checks if the hours and minutes given are greater than or equal to the times in the list*/
    if ((current ->comp == num) && (compareTime(h, m, current ->hours, current ->minutes) == 2)) {
        node = current ->node;
    } else {
        //Loops through the rest of the linked list by moving the pointers one along the list each time.
        while (next != NULL) {
            current = next;
            next = current ->next;

            if ((current -> comp == num) && (current ->node == 1)) {
                count = count + 1;
            }
            /*Checks is the competitor numbers are the same. The compare time method checks if the hours and minutes given are greater than or equal to the times in the list*/
            if ((current ->comp == num) && (compareTime(h, m, current ->hours, current ->minutes) == 2)) {
                node = current ->node;
            }
        }
    }

    //Checks is finished by checking if node one has been visited twice. Count is found while searching through the list
    if ((node == 1) && (count == 2)) {
        node = 100;
    }

    //If 0 is returned : Not Started, if anything but 100 is returned : On Course, if 100 is returned : Finished.
    return node;

}

/*This method is used to find the minutes of a competitor when they have finished  
 * using their competitor number and the time that the user is querying
 * against. Setting the node to 100 is used to determine is the competitor is finished. 
 * Potential problems for large amounts of nodes. This method is only in printing results.*/

int findMinutesTime(TIME** head, int num, int h, int m) {
    TIME *current = *head;
    TIME *next = current ->next;

    int node = 0;
    int qm = 0;
    int count = 0;


    if ((current -> comp == num) && (current ->node == 1)) {
        count = count + 1;
    }
    if ((current ->comp == num) && (compareTime(h, m, current ->hours, current ->minutes) == 2)) {
        node = current ->node;
        qm = current ->minutes;
    } else {
        while (next != NULL) {
            current = next;
            next = current ->next;

            if ((current -> comp == num) && (current ->node == 1)) {
                count = count + 1;
            }

            if ((current ->comp == num) && (compareTime(h, m, current ->hours, current ->minutes) == 2)) {
                node = current ->node;
                qm = current->minutes;
            }
        }
    }

    if ((node == 1) && (count == 2)) {
        node = 100;
        return qm;
    }

}

/*This method is used to find the hours of a competitor when they have finished  
 * using their competitor number and the time that the user is querying
 * against. Setting the node to 100 is used to determine is the competitor is finished. 
 * Potential problems for large amounts of nodes. This method is only in printing results.*/

int findHourTime(TIME** head, int num, int h, int m) {
    TIME *current = *head;
    TIME *next = current ->next;

    int node = 0;
    int qh = 0;
    int count = 0;


    if ((current -> comp == num) && (current ->node == 1)) {
        count = count + 1;
    }
    if ((current ->comp == num) && (compareTime(h, m, current ->hours, current ->minutes) == 2)) {
        node = current ->node;
        qh = current ->hours;
    } else {
        while (next != NULL) {
            current = next;
            next = current ->next;

            if ((current -> comp == num) && (current ->node == 1)) {
                count = count + 1;
            }

            if ((current ->comp == num) && (compareTime(h, m, current ->hours, current ->minutes) == 2)) {
                node = current ->node;
                qh = current->hours;
            }
        }
    }

    if ((node == 1) && (count == 2)) {
        node = 100;
        return qh;
    }

}


char findType(TIME** head, int num, int h, int m) {
    TIME *current = *head;
    TIME *next = current ->next;

    char type;
    int count = 0;


    if ((current -> comp == num) && (current ->node == 1)) {
        count = count + 1;
    }
    
    if ((current ->comp == num) && (compareTime(h, m, current ->hours, current ->minutes) == 2)) {
       type = current ->type;
    } 
    
    else {
        while (next != NULL) {
            current = next;
            next = current ->next;

            if ((current -> comp == num) && (current ->node == 1)) {
                count = count + 1;
            }

            if ((current ->comp == num) && (compareTime(h, m, current ->hours, current ->minutes) == 2)) {
                type = current -> type;
            }
        }
    }

        return type;

}
 

/*This is used to query which competitors have not yet started. It uses the Times and Entrants linked lists
 *to achieve this aim, and the time the user queried against.
 */

void queryNotStarted(TIME** t_head, ENTRANT** e_head, int h, int m) {
    char name[100];
    int number;
    int node;
    number = 1;

    while (strcmp(name, "Not Found") != 0) {

        findCompetitor(e_head, number, name);
        if (strcmp(name, "Not Found") != 0) {

            node = 0;

            node = findLocation(t_head, number, h, m);

            if (node == 0) {
                printf("Name : %s\n", name);
                printf("Status : Not Started\n");
                printf("\n");
            }
        }
        number += 1;
    }
}

/*This is used to query which competitors are out on course. It uses the Times and Entrants linked lists
 *to achieve this aim, and the time the user queried against.
 */

void queryOnCourse(TIME** t_head, ENTRANT** e_head, int h, int m) {
    char name[100];
    int number;

    int node;
    number = 1;

    while (strcmp(name, "Not Found") != 0) {

        findCompetitor(e_head, number, name);
        if (strcmp(name, "Not Found") != 0) {


            node = findLocation(t_head, number, h, m);

            if ((node != 0) && (node != 100)) {
                printf("Name : %s\n", name);
                printf("Status : On Course\n");
                printf("\n");
            }
        }
        number += 1;

    }
}

/*This is used to query which competitors have finished. It uses the Times and Entrants linked lists
 *to achieve this aim, and the time the user queried against.
 */

void queryFinished(TIME** t_head, ENTRANT** e_head, int h, int m) {
    char name[100];
    int number;

    int node;
    number = 1;

    while (strcmp(name, "Not Found") != 0) {

        findCompetitor(e_head, number, name);
        if (strcmp(name, "Not Found") != 0) {


            node = findLocation(t_head, number, h, m);
            if (node == 100) {
                printf("Name : %s\n", name);
                printf("Status : Finished\n");
                printf("\n");
            }
        }
        number += 1;

    }
}

/*This is used to query which competitors have finished and displays their results. It uses the Times and Entrants linked lists
 *to achieve this aim, and the current time, which is the last time entered into the Times linked list.
 */

void queryResults(TIME** t_head, ENTRANT** e_head, int qh, int qm) {

    char name[100];
    char course;
    int number;

    int node;
    int h;
    int m;

    number = 1;

    while (strcmp(name, "Not Found") != 0) {

        course = findResultsCompetitor(e_head, number, name);
        if (strcmp(name, "Not Found") != 0) {

            node = findLocation(t_head, number, qh, qm);
            h = findHourTime(t_head, number, qh, qm);
            m = findMinutesTime(t_head, number, qh, qm);

            if (node == 100) {
                printf("Name : %s\n", name);
                printf("Course: %c\n", course);
                printf("End Time : %02d:%02d\n", h, m);
                printf("\n");
            }

        }

        number += 1;

    }
}


void queryWrongExclusion(TIME** t_head, ENTRANT** e_head, int qh, int qm){
    
    char name[100];
    int number;

    char type;
    number = 1;

    while (strcmp(name, "Not Found") != 0) {

        findCompetitor(e_head, number, name);
        if (strcmp(name, "Not Found") != 0) {


            type = findType(t_head, number, qh, qm);

            if (type == 'I') {
                printf("Name : %s\n", name);
                printf("Status :  Excluded for Incorrect Checkpoint\n");
                printf("\n");
            }
        }
        number += 1;

    }
    
}

void queryMedicalExclusion(TIME** t_head, ENTRANT** e_head, int qh, int qm){
   
    char name[100];
    int number;

    char type;
    number = 1;

    while (strcmp(name, "Not Found") != 0) {

        findCompetitor(e_head, number, name);
        if (strcmp(name, "Not Found") != 0) {


            type = findType(t_head, number, qh, qm);

            if (type == 'E') {
                printf("Name : %s\n", name);
                printf("Status :  Excluded for Medical Reasons\n");
                printf("\n");
            }
        }
        number += 1;

    }
    
}

/*This is used to compare the times that are entered. This is used for validating the time in
 *  querying and comparing times in finding the last known  location of the competitor. */

int compareTime(int h, int m, int qh, int qm) {
    int qmins = (qh * 60) + qm;
    int mins = (h * 60) + m;

    if (qmins > mins) {
        return 0;
    } else if (qmins <= mins) {
        return 2;
    } else return 1;
}

/*Create a new TIME node*/

TIME* createTimeNode(char type, int node, int comp, int hours, int minutes) {
    TIME *nameNode = (TIME*) malloc(sizeof (TIME));

    nameNode ->type = type;
    nameNode ->node = node;
    nameNode ->comp = comp;
    nameNode ->hours = hours;
    nameNode ->minutes = minutes;

    return nameNode;
}

/*Create a new ENTRANT node*/

ENTRANT* createEntrantNode(int number, char course, char name[]) {
    ENTRANT *nameNode = (ENTRANT*) malloc(sizeof (ENTRANT));

    nameNode ->number = number;
    nameNode ->course = course;
    strcpy(nameNode ->name, name);

    return nameNode;
}

/*Create a new TRACK node*/

TRACK* createTrackNode(int number, int start, int end, int minutes) {
    TRACK *nameNode = (TRACK*) malloc(sizeof (TRACK));

    nameNode ->number = number;
    nameNode ->start = start;
    nameNode ->end = end;
    nameNode ->minutes = minutes;

    return nameNode;
}

/*Create a new NODE node*/

NODE* createNode(int number, char type[]) {
    NODE *Node = (NODE*) malloc(sizeof (NODE));

    Node ->number = number;
    strcpy(Node ->type, type);

    return Node;
}

/* Creates the linked list of TIMEs using variables read in from a file in the main method.*/

void createLinkedTimes(TIME** head, char type, int node, int comp, int hours, int minutes) {
    TIME* current = *head;
    TIME* next;

    TIME *nb = createTimeNode(type, node, comp, hours, minutes);


    if (current != NULL) {
        next = current ->next;
    }
    if (current == NULL) {
        current = nb;
        current->next = NULL;
        *head = current;
    } else if (nb ->hours < current ->hours) {
        nb->next = current;
        *head = nb;

    } else {
        while ((next != NULL) && (!((nb ->hours < next ->hours) && (nb ->hours >= current ->hours)))) {
            current = current ->next;
            next = current ->next;
        }
        if (next != NULL) {
            current ->next = nb;
            nb ->next = next;
        } else {
            current ->next = nb;
        }


    }

}

/*Creates the linked list of TRACKs from a file that has been entered into the main method*/

void createLinkedTracks(TRACK** head, FILE *node, char file[]) {
    TRACK* current = *head;
    TRACK* next;

    node = fopen(file, "r");

    char my_buffer[80];
    int n1, n2, n3, n4;

    while (fgets(my_buffer, 80, node)) {
        sscanf(my_buffer, "%d %d %d %d", &n1, &n2, &n3, &n4);

        TRACK *nb = createTrackNode(n1, n2, n3, n4);


        if (current != NULL) {
            next = current ->next;
        }
        if (current == NULL) {
            current = nb;
            current->next = NULL;
            *head = current;
        } else if (nb ->number < current ->number) {
            nb->next = current;
            *head = nb;

        } else {
            while ((next != NULL) && (!((nb ->number < next ->number) && (nb ->number > current ->number)))) {
                current = current ->next;
                next = current ->next;
            }
            if (next != NULL) {
                current ->next = nb;
                nb ->next = next;
            } else {
                current ->next = nb;
            }


        }

    }
}

/*Creates the linked list of NODEs from a file that has been entered into the main method*/

void createLinkedNodes(NODE** head, FILE *node, char file[]) {
    NODE* current = *head;
    NODE* next;

    node = fopen(file, "r");

    char my_buffer[80];
    int n;
    char type[3];

    while (fgets(my_buffer, 80, node)) {
        sscanf(my_buffer, "%d %s", &n, type);

        NODE *nb = createNode(n, type);


        if (current != NULL) {
            next = current ->next;
        }
        if (current == NULL) {
            current = nb;
            current->next = NULL;
            *head = current;
        } else if (nb ->number < current ->number) {
            nb->next = current;
            *head = nb;

        } else {
            while ((next != NULL) && (!((nb ->number < next ->number) && (nb ->number > current ->number)))) {
                current = current ->next;
                next = current ->next;
            }
            if (next != NULL) {
                current ->next = nb;
                nb ->next = next;
            } else {
                current ->next = nb;
            }


        }

    }
}

/*Creates the linked list of ENTRANTs from a file that has been entered into the main method*/

void createLinkedEntrants(ENTRANT** head, FILE *entrant, char file[]) {
    ENTRANT* current = *head;
    ENTRANT* next;

    entrant = fopen(file, "r");

    char my_buffer[100];
    int n;
    char c;
    char name[100];

    while (fgets(my_buffer, 80, entrant)) {
        sscanf(my_buffer, "%d %c %[a-z,A-Z,_, ]", &n, &c, name);

        ENTRANT *nb = createEntrantNode(n, c, name);


        if (current != NULL) {
            next = current ->next;
        }
        if (current == NULL) {
            current = nb;
            current->next = NULL;
            *head = current;
        } else if (nb ->number < current ->number) {
            nb->next = current;
            *head = nb;

        } else {
            while ((next != NULL) && (!((nb ->number < next ->number) && (nb ->number > current ->number)))) {
                current = current ->next;
                next = current ->next;
            }
            if (next != NULL) {
                current ->next = nb;
                nb ->next = next;
            } else {
                current ->next = nb;
            }


        }

    }
}

/*Print the NODE linked list*/

void printNodes(NODE** head) {
    NODE* current = *head;
    NODE* next = current ->next;

    printf("Number : %d\n", current ->number);
    printf("Type: %s\n", current ->type);


    while (next != NULL) {
        current = current ->next;
        next = current ->next;
        printf("Number : %d\n", current ->number);
        printf("Type: %s\n", current ->type);
    }
}

/*Print the ENTRANT linked list*/

void printEntrants(ENTRANT** head) {
    ENTRANT* current = *head;
    ENTRANT* next = current ->next;

    printf("Number : %d\n", current ->number);
    printf("Course: %c\n", current ->course);
    printf("Name : %s\n", current ->name);

    while (next != NULL) {
        current = current ->next;
        next = current ->next;
        printf("Number : %d\n", current ->number);
        printf("Course: %c\n", current ->course);
        printf("Name : %s\n", current ->name);
    }
}

/*Print the TIME linked list*/

void printTimes(TIME** head) {
    TIME* current = *head;
    TIME* next = current ->next;

    printf("Type : %c\n", current ->type);
    printf("Node: %d\n", current ->node);
    printf("Competitor : %d\n", current ->comp);
    printf("Hours: %d\n", current ->hours);
    printf("Minutes : %d\n", current -> minutes);

    while (next != NULL) {
        current = current ->next;
        next = current ->next;
        printf("Type : %c\n", current ->type);
        printf("Node: %d\n", current ->node);
        printf("Competitor : %d\n", current ->comp);
        printf("Hours: %d\n", current ->hours);
        printf("Minutes : %d\n", current -> minutes);
    }
}

/*Print the TRACK linked list*/

void printTracks(TRACK** head) {
    TRACK* current = *head;
    TRACK* next = current ->next;

    printf("Number : %d\n", current ->number);
    printf("Start Node: %d\n", current ->start);
    printf("End Node : %d\n", current ->end);
    printf("Maximum Time : %d\n", current ->minutes);

    while (next != NULL) {
        current = current ->next;
        next = current ->next;
        printf("Number : %d\n", current ->number);
        printf("Start Node: %d\n", current ->start);
        printf("End Node : %d\n", current ->end);
        printf("Maximum Time : %d\n", current ->minutes);
    }
}

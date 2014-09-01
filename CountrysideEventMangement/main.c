/* 
 * File:   main.c
 * Author: Kit
 *
 * Created on December 5, 2012, 5:51 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "structs.h"
#include "functions.h"

int main(int argc, char** argv) {
    
    /*File Pointers*/
        FILE *namef;
        FILE *entrantf;
        FILE *nodef;
        FILE *trackf;
        FILE *timef;
        FILE *coursef;
        FILE *logf;
    
    /*Filenames*/
    char namefile[30];
    char nodesfile[30];
    char tracksfile[30];
    char entrantsfile[30];
    char coursesfile[30];
    char cp_timesfile1[30];
    char logfile[30];
    
    /*These are the structures that will contain all the data read in*/
    NAME title[3];
    COURSE courses[26];
    ENTRANT *entrant_head = NULL;
    NODE *node_head = NULL;
    TRACK *track_head = NULL;
    TIME *time_head = NULL;

    char my_buffer[100];
    
    /*These variables are used when reading in files and calculating queries*/
    char name[100];
    int i, j, n1, n2, n3, h, m, timesanswer, answer, qh, qm, node;
    char t;

    i = 0;
    answer = 0;
    timesanswer = 0;
    
    
    /*This is where the user is asked to enter all the files that are needed to run the program They are entered sequentially
          to ensure that all files are read in and that none conflict with each other.*/
    
    printf("Please enter the name of the log file: \n");
    scanf(" %[a-z,A-Z,.,_,0-9]", logfile);
    
    logf = fopen(logfile, "a");
    
    fprintf(logf, "Log File Opened\n");

    printf("Please enter the names file: \n");
    scanf(" %[a-z,A-Z,.,_,0-9]", namefile);

    namef = fopen(namefile, "r");

    while (fgets(my_buffer, 80, namef) != NULL) {
        strcpy(title[i].line, my_buffer);
        i++;
    }

    fclose(namef);

    
    printf("Please enter the nodes file: \n");
    scanf(" %[a-z,A-Z,.,_,0-9]", nodesfile);

    createLinkedNodes(&node_head, nodef, nodesfile);
     




    printf("Please enter the tracks file: \n");
    scanf(" %[a-z,A-Z,.,_,0-9]", tracksfile);

    createLinkedTracks(&track_head, trackf, tracksfile);




    printf("Please enter the entrants file: \n");
    scanf(" %[a-z,A-Z,.,_,0-9]", entrantsfile);

    createLinkedEntrants(&entrant_head, entrantf, entrantsfile);





    printf("Please enter the courses file: \n");
    scanf(" %[a-z,A-Z,.,_,0-9]", coursesfile);

    coursef = fopen(coursesfile, "r");


    while (!feof(coursef)) {
        i = 0;
        if (fscanf(coursef, "%c %d", &(courses[i].name),
                &(courses[i].amount)) == 2) {

            for (j = 0; j < courses[i].amount; j++) {
                while (fscanf(coursef, " %d", &(courses[i].nodes[j])) == 1);

            }
            fscanf(coursef, "\n");

            i++;
        } else {
            fprintf(stderr, "Error: file format wrong.\n");
            break;
        }
    }

    /*This prompts the user to choose how they will enter the first competitor's time. There is an
        option to add more later.*/

    printf(" Choose how times for competitors are entered.\n");
    printf(" 1. Enter a pre-prepared file of all competitors' times.\n");
    printf(" 2. Manually enter more times for competitors.\n");

    scanf("%d", &timesanswer);

    if (timesanswer == 1) {

        printf("Please enter the times file: \n");
        scanf(" %[a-z,A-Z,.,_,0-9]", cp_timesfile1);


        timef = fopen(cp_timesfile1, "r");

        while (fgets(my_buffer, 100, timef) != NULL) {
            sscanf(my_buffer, "%c %d %d %d:%d", &t, &n1, &n2, &h, &m);
            createLinkedTimes(&time_head, t, n1, n2, h, m);
        }

        fclose(timef);

    } else if (timesanswer == 2) {
        printf("How many times do you wish to enter? ");
        scanf(" %d", &i);
        int count = 0;
        while (count != i) {
            printf("Please enter the type of checkpoint :");
            scanf(" %[A-Z]", &t);
            printf("Please  enter the checkpoint node :");
            scanf(" %d", &n2);
            printf("Please enter the competitor number :");
            scanf(" %d", &n3);
            printf("Please enter the hours :");
            scanf(" %d", &h);
            printf("Please enter the minutes :");
            scanf(" %d", &m);

            createLinkedTimes(&time_head, t, n2, n3, h, m);

            count += 1;
        }
        printTimes(&time_head);
    }

    while (answer != 11) {

        for (i = 0; i < 3; i++) {
            printf("%s", title[i].line);
        }
        /*This prints out the last time entered into the linked list of times. This is assumed to be the current time.*/
        printf("Current Time : %02d:%02d\n", h, m);
        
        /*MENU*/
        printf("Please choose an option :\n");
        printf("1. Query the status and location of a competitor.\n");
        printf("2. Query how many competitors have not yet started.\n");
        printf("3. Query how many competitors are out on the course.\n");
        printf("4. Query how many competitors are finished.\n");
        printf("5. Add more times\n");
        printf("6. Results\n");
        printf("7. Query which competitors were excluded for going to an incorrect checkpoint\n");
        printf("8. Query which competitors were excluded for medical reasons\n");
        printf("9. Query when a competitor entered a medical checkpoint\n");
        printf("10. Query when a competitor left a medical checkpoint\n");
        printf("11. Quit\n");

        scanf("%d", &answer);
        
        /*Allows the user to query the location and status of one competitor*/
        if (answer == 1) {
            
            fprintf(logf, "Time :  %02d:%02d  Date : %s Activity : Queried location and status of a competitor\n", h, m, title[1].line);
            
            printf("Enter a time you wish to query for : \n");
            scanf("%d:%d", &qh, &qm);

            if (compareTime(h, m, qh, qm) == 0) {
                printf("Time entered is after current time, please enter an appropriate time\n");
            } else {
                printf("Enter competitor's  number: \n");
                scanf("%d", &n1);

                findCompetitor(&entrant_head, n1, name);
                if ((strcmp(name, "Not Found")) != 0) {
                    printf("Name : %s\n", name);

                    node = 0;

                    node = findLocation(&time_head, n1, qh, qm);
                    if (node == 100) {
                        printf("Location : %d\n", node);
                        printf("Status : Finished\n");
                        printf("\n");
                    }
                    if (node == 0) {
                        printf("Status : Not Started\n");
                        printf("\n");
                    } else {
                        printf("Location : %d\n", node);
                        printf("Status : On Course\n");
                        printf("\n");
                    }

                }
                else {
                    printf("%s\n", name);
                }


            }
            
            /*Allows the user to query all the competitors that have not started*/
        } else if (answer == 2) {
            
             fprintf(logf, "Time :  %02d:%02d  Date : %s Activity : Queried which competitors had not started\n", h, m, title[1].line);

            printf("Enter a time for you wish to query for : \n");
            scanf("%d:%d", &qh, &qm);

            queryNotStarted(&time_head, &entrant_head, qh, qm);

              /*Allows the user to query all the competitors that are out on course*/
        } else if (answer == 3) {
            
             fprintf(logf, "Time :  %02d:%02d  Date : %s Activity : Queried which competitors are out on course\n", h, m, title[1].line);
            
            printf("Enter a time for you wish to query for : \n");
            scanf("%d:%d", &qh, &qm);

            queryOnCourse(&time_head, &entrant_head, qh, qm);
            
            
             /*Allows the user to query all the competitors that are finished*/
        }else if (answer == 4) {
            
            fprintf(logf, "Time :  %02d:%02d  Date : %s Activity : Queried which competitors are finished\n", h, m, title[1].line);
            
            printf("Enter a time for you wish to query for : \n");
            scanf("%d:%d", &qh, &qm);

            queryFinished(&time_head, &entrant_head, qh, qm);
            
            
            /*Allows the user to enter more times*/
        } else if (answer == 5) {
            
            printf(" Choose how times for competitors are entered.\n");
            printf(" 1. Enter a pre-prepared file of all competitors' times.\n");
            printf(" 2. Manually enter more times for competitors.\n");

            scanf("%d", &timesanswer);

            if (timesanswer == 1) {
                
                 fprintf(logf, "Time :  %02d:%02d  Date : %s Activity : Added more times from a file\n", h, m, title[1].line);

                printf("Please enter the times file: \n");
                scanf(" %[a-z,A-Z,.,_,0-9]", cp_timesfile1);


                timef = fopen(cp_timesfile1, "r");

                while (fgets(my_buffer, 100, timef) != NULL) {
                    sscanf(my_buffer, "%c %d %d %d:%d", &t, &n1, &n2, &h, &m);
                    createLinkedTimes(&time_head, t, n1, n2, h, m);
                }

                fclose(timef);

                printTimes(&time_head);

            } else if (timesanswer == 2) {
                
                 fprintf(logf, "Time :  %02d:%02d  Date : %s Activity : Manually entered more times\n", h, m, title[1].line);
                
                printf("How many times do you wish to enter? ");
                scanf(" %d", &i);
                int count = 0;
                while (count != i) {
                    printf("Please enter the type of checkpoint :");
                    scanf(" %[A-Z]", &t);
                    printf("Please  enter the checkpoint node :");
                    scanf(" %d", &n2);
                    printf("Please enter the competitor number :");
                    scanf(" %d", &n3);
                    printf("Please enter the hours :");
                    scanf(" %d", &h);
                    printf("Please enter the minutes :");
                    scanf(" %d", &m);

                    createLinkedTimes(&time_head, t, n2, n3, h, m);

                    count += 1;
                }
                printTimes(&time_head);
            }
        }
        /*Allows the user to print out the results*/
        else if (answer == 6) {
            
             fprintf(logf, "Time :  %02d:%02d  Date : %s Activity : Checked results\n", h, m, title[1].line);
            
            queryResults(&time_head, &entrant_head, h, m);
        }
        
        
        
        else if(answer == 7){
            
             fprintf(logf, "Time :  %02d:%02d  Date : %s Activity : Queried which competitors were excluded for incorrect checkpoints \n", h, m, title[1].line);
            
              printf("Enter a time for you wish to query for : \n");
              scanf("%d:%d", &qh, &qm);

            queryWrongExclusion(&time_head, &entrant_head, qh, qm);
        }
        
        
        
        else if(answer == 8){
            
             fprintf(logf, "Time :  %02d:%02d  Date : %s Activity : Queried which competitors were excluded for medical reasons \n", h, m, title[1].line);
            
              printf("Enter a time for you wish to query for : \n");
            scanf("%d:%d", &qh, &qm);

            queryMedicalExclusion(&time_head, &entrant_head, qh, qm);
        }

        else if (answer == 9) {
            
             fprintf(logf, "Time :  %02d:%02d  Date : %s Activity : Queried a competitor's entry into a medical checkpoint \n", h, m, title[1].line);
            
            int compNum = 0;
            
            
            printf("Enter a time you wish to query for : \n");
            scanf("%d:%d", &qh, &qm);

            if (compareTime(h, m, qh, qm) == 0) {
                printf("Time entered is after current time, please enter an appropriate time\n");
            } else {
                printf("Enter competitor's  number: \n");
                scanf("%d", &compNum);

                findCompetitor(&entrant_head, compNum, name);
                if ((strcmp(name, "Not Found")) != 0) {
                    printf("Name : %s\n", name);

                    char type;
                    
                    type = findType(&time_head, compNum, qh, qm);
                    
                    if(type == 'A'){
                        int h = findHourTime(&time_head, compNum, qh, qm);
                        int m = findMinutesTime(&time_head, compNum, qh, qm);
                        printf("Time : %d:%d", h, m);
                    }

                }
                else {
                    printf("%s\n", name);
                }
        }
        
        }
        
        else if(answer == 10){
            
             fprintf(logf, "Time :  %02d:%02d  Date : %s Activity : Queried a competitor's departure from a medical checkpoint \n", h, m, title[1].line);
            
            int compNum = 0;
            
            printf("Enter a time you wish to query for : \n");
            scanf("%d:%d", &qh, &qm);

            if (compareTime(h, m, qh, qm) == 0) {
                printf("Time entered is after current time, please enter an appropriate time\n");
            } else {
                printf("Enter competitor's  number: \n");
                scanf("%d", &compNum);

                findCompetitor(&entrant_head, compNum, name);
                if ((strcmp(name, "Not Found")) != 0) {
                    printf("Name : %s\n", name);

                    char type;
                    
                    type = findType(&time_head, compNum, qh, qm);
                    
                    if(type == 'D'){
                        int h = findHourTime(&time_head, compNum, qh, qm);
                        int m = findMinutesTime(&time_head, compNum, qh, qm);
                        printf("Time : %d:%d \n", h, m);
                    }

                }
                else {
                    printf("%s\n", name);
                }
            
        }
        
    }        
        
        }


    return (EXIT_SUCCESS);

}

/* 
 * File:   functions.h
 * Author: Kit
 *
 * Created on March 19, 2013, 3:31 PM
 */

#ifndef FUNCTIONS_H
#define	FUNCTIONS_H

#ifdef	__cplusplus
extern "C" {
#endif

void findCompetitor(ENTRANT** head, int num, char name[]);
    
char findResultsCompetitor(ENTRANT** head, int num, char name[]);
    
int findLocation(TIME** head, int num, int h, int m);
    
int findMinutesTime(TIME** head, int num, int h, int m);
    
int findHourTime(TIME** head, int num, int h, int m);
    
char findType(TIME** head, int num, int h, int m);
    
void queryNotStarted(TIME** t_head, ENTRANT** e_head, int h, int m);

void queryOnCourse(TIME** t_head, ENTRANT** e_head, int h, int m);

void queryFinished(TIME** t_head, ENTRANT** e_head, int h, int m);

void queryResults(TIME** t_head, ENTRANT** e_head, int qh, int qm);

void queryWrongExclusion(TIME** t_head, ENTRANT** e_head, int qh, int qm);

void queryMedicalArrivalStatus(int compNum, TIME** t_head, ENTRANT** e_head, int qh, int qm);

void queryMedicalDepartureStatus(int compNum, TIME** t_head, ENTRANT** e_head, int qh, int qm);

void queryMedicalExclusion(TIME** t_head, ENTRANT** e_head, int qh, int qm);

int compareTime(int h, int m, int qh, int qm);

TIME* createTimeNode(char type, int node, int comp, int hours, int minutes);

ENTRANT* createEntrantNode(int number, char course, char name[]);

TRACK* createTrackNode(int number, int start, int end, int minutes);

NODE* createNode(int number, char type[]);

void createLinkedTimes(TIME** head, char type, int node, int comp, int hours, int minutes);

void createLinkedTracks(TRACK** head, FILE *node, char file[]);

void createLinkedNodes(NODE** head, FILE *node, char file[]);

void createLinkedEntrants(ENTRANT** head, FILE *entrant, char file[]); 

void printNodes(NODE** head) ;

void printEntrants(ENTRANT** head);

void printTimes(TIME** head);

void printTracks(TRACK** head);
    
    


#ifdef	__cplusplus
}
#endif

#endif	/* FUNCTIONS_H */


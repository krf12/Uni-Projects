/* 
 * File:   structs.h
 * Author: Kit
 *
 * Created on December 9, 2012, 2:00 PM
 */

#ifndef STRUCTS_H
#define	STRUCTS_H

#ifdef	__cplusplus
extern "C" {
#endif

typedef struct name{
    char line[100];
    struct name* next;
}NAME;

typedef struct entrant{
    int number;
    char course;
    char name[100];
    struct entrant* next;
}ENTRANT;

typedef struct node{
    int number;
    char type[2];
    struct node *next;
}NODE;

typedef struct track{
    int number;
    int start;
    int end;
    int minutes;
    struct track *next;
}TRACK;

typedef struct time{
    char type;
    int node;
    int comp;
    int hours;
    int minutes;
    struct time *next;
}TIME;

typedef struct course{
    char name;
    int amount;
    int nodes[50];
}COURSE;


#ifdef	__cplusplus
}
#endif

#endif	/* STRUCTS */


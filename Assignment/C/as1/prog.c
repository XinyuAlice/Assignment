//created by Alice Liu
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#define MAX_LINE 80 /*MAXIMUM length command*/
//function to separate user input and parsing
int separate(char *string, char *args[]){
    int i=0;
    char *delim=" \t\n";
    args[0]=strtok(string, delim); //use strtok function to split string into tokens using delim
    while(args[i]!=NULL){
        args[++i]=strtok(NULL,delim);//add NULL to last segment
    }
    return 0;
}
//fork a child process
//pass to execvp function

int forkChild(char *args[],int *pidNumber){
    //fork a child process
    pid_t pid;
    pid=fork();
    if(pid<0){//error occur
        fprintf(stderr,"Fork failed");
        return 1;
    }
    else if (pid==0){/*child process*/
        //int execvp(const char* args[], args)
        int result=execvp(args[0],args);
        if(result==-1){
            printf("Command goes wrong\n");
            exit(0);
        }
    }
    else{
        //parent process wit until child process completes
        wait(NULL);
        *pidNumber=pid;
        printf("Child process has finished\n");
    }
    return 0;
}
//use struct to store pid, and command
struct historyData{
    int pid;
    char command[80];
};

int main(void) {
    char *args[MAX_LINE / 2 + 1];/*command line arguments*/
    int commandNum=0;//sizeofcommand
    char str[80];//form the string
    char cpystr[80];//use to store the copy
    int should_run = 1;//function continues run and if equals 0 then terminate; flag to teriminate the program
    struct historyData history[10]={0};
    while (should_run) {
        printf("CSCI3120>");
        fflush(stdout);
        fgets(str, 80, stdin);//use fgets to read inputs from user
        int pid;
        int i;
        if(str[0]=='\n'){
            continue;
        }
        strcpy(cpystr, str);
        separate(str, args);
        if (strcmp(args[0], "exit") == 0) {
            should_run = 0;
        }
        else if ((strcmp(args[0], "history")) == 0) {
            printf("ID\tPID\tCommand\n");
            if(commandNum==0){
                printf("No command in history.\n");
                return 0;
            }
            for (i = 1; i<=commandNum; i++) {
                printf("%d\t%d\t%s\n", i, history[i-1].pid, history[i-1].command);
            }
        }//excute most recent comamnd
         else if (strcmp(args[0], "!!") == 0) {
            if (commandNum == 0) {
                printf("No command in history");
            } else {
                strcpy(cpystr, history[0].command);
                separate(cpystr, args);
                forkChild(args, &pid);
                for (i = commandNum; i > 0; i--) {
                    strcpy(history[i].command, history[i - 1].command);
                    history[i].pid = history[i - 1].pid;
                }
                strcpy(history[0].command, str);
                history[0].pid = pid;
                if (commandNum < 10) {
                    commandNum++;
                }
            }
        }
         //excute when input "!'
        else if (str[0] == '!') {
            int num = (int) str[1] - '0';
            if (num - 1 > commandNum) {
                printf("No such command in history\n");
            }
            if(str[1]=='1'&str[2]=='0'){
                num=10;
            }
            strcpy(str, history[num-1].command);
            strcpy(cpystr, history[num-1].command);
            separate(str, args);
            forkChild(args, &pid);
            for (i = commandNum; i > 0; i--) {
                strcpy(history[i].command, history[i - 1].command);
                history[i].pid = history[i - 1].pid;
            }
            strcpy(history[0].command, str);
            history[0].pid = pid;
            if(commandNum < 10){
                commandNum++;
            }
        }
       else{
            forkChild(args,&pid);
            for (i = commandNum; i > 0; i--) {
                strcpy(history[i].command, history[i - 1].command);
                history[i].pid = history[i - 1].pid;
            }
            strcpy(history[0].command, str);
            history[0].pid = pid;
            if (commandNum < 10) {
                commandNum++;
            }
        }

    }
    return 0;
}
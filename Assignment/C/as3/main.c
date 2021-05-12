#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//use struct to store the three elements
typedef struct {
    char *tasknum;
    int at;//arrival time
    int bt;//burst time
    int wt;//wait time: Turn Around Time – Burst Time
    int st;//start time
    int tat;//turn around time:Time Difference between end time and arrival time. Turn Around Time = Completion Time – Arrival Time
    int et;//end time
} task;

typedef struct{
    char *tasknum;
    int st;//start time
    int et;//end time
    int wt;//wait time
}scheduledtask;
//FCFS algorithm: follow by arrival time
//first one does not need to wait
//input the processes along with their burst time and arrival time
//total wt[i]=(bt[0]+bt[1]+..+bt[i-1]-at[i]
void FCFS(task task1[],int taskNum,FILE *ofp){
    int i;
    //use current time to record the process
    int currt=task1[0].at;
    int total_wt=0;
    //first come first start
    task1[0].st=0;
    for(i=0;i<taskNum;i++)
    {   if(task1[i].at<currt){
            task1[i].st=currt;//to easy calculate the wait time later
        }else {
            currt=task1[i].at;
           task1[i].st=currt;
        }
        task1[i].wt=task1[i].st-task1[i].at;
        task1[i].et = currt+task1[i].bt;
        currt+=task1[i].bt;
        total_wt+=task1[i].wt;
    }
    float avgt=(float)total_wt/(float)taskNum;
    for (i=0;i<taskNum;i++) {
        fprintf(ofp,"%s\t%d\t%d\n", task1[i].tasknum, task1[i].st, task1[i].et);
        printf("%s\t%d\t%d\n", task1[i].tasknum, task1[i].st, task1[i].et);
    }
    fprintf(ofp,"Average Wait time is %.2f\n",avgt);
}
//RR algorithm
//I feels like I cannot do RR
/*
void RR(task task1[],int taskNum,FILE *ofp){
    int currt=0;
    int i;
    int total_wt=0;
    int remain[350];
    int completetask=0;
    while (1)
    {
        bool done = true;
        int i;
        // Traverse all processes one by one repeatedly
        for (i = 0 ; i < taskNum; i++)
        {
            // If burst time of a process is greater than 0
            // then only need to process further
            if (remain[i] > 0)
            {
                done = false; // There is a pending process

                if (remain[i] > 4)
                {
                    // Increase the value of t i.e. shows
                    // how much time a process has been processed
                    currt += 4;

                    // Decrease the burst_time of current process
                    // by quantum
                   remain[i] -= 4;
                }

                    // If burst time is smaller than or equal to
                    // quantum. Last cycle for this process
                else
                {
                    // Increase the value of t i.e. shows
                    // how much time a process has been processed
                    currt += remain[i];

                    // Waiting time is current time minus time
                    // used by this process
                    task1[i].wt=currt - task1[i].bt;
                    total_wt+=task1[i].wt;
                    // As the process gets fully executed
                    // make its remaining burst time = 0
                   remain[i] = 0;
                }

            }
        }

        // If all processes are done
        if (done == true)
            break;
    }

    float avgt=(float)total_wt/(float)taskNum;
    for (i=0;i<taskNum;i++) {
        fprintf(ofp,"%s\t%d\t%d\n", task1[i].tasknum, task1[i].st, task1[i].et);
        printf("%s\t%d\t%d\n", task1[i].tasknum, task1[i].st,task1[i].et);
    }
    fprintf(ofp,"Average Wait time is %.2f\n",avgt);

}*/

//Sort all the process according to the arrival time.
//Then select that process which has minimum arrival time and minimum Burst time.
//After completion of process make a pool of process which after till the completion of previous process and select that process among the pool which is having minimum Burst time.
void NPSJF(task task1[],int taskNum,FILE *ofp){
    int i=0;
    int j=0;
    int cpyt=0;
    int currt=task1[0].at;
    int completetask=0;
    int total_wt=0;
    //sort by arrival time first
    for(i=0;i<taskNum;i++) {
        if (task1[i].at > task1[i + 1].at) {
            task1[i].at = cpyt;
            task1[i].at = task1[i + 1].at;
            task1[i + 1].at = cpyt;
        }
    }
    //use the comparison between completed task and task num
    while(taskNum>completetask) {
        if (task1[completetask].at < currt) {
            task1[completetask].st = currt;
        } else {
            currt = task1[completetask].at;
            task1[completetask].st = currt;
        }
        task1[completetask].et = currt + task1[completetask].bt;
        currt += task1[completetask].bt;
        completetask++;//add complete task number

        //sort by burst time
        for (i = 1; i < taskNum; i++) {
            if (task1[i].at < task1[i - 1].et) {
                for (j = 1; j < taskNum - i; j++) {
                    if (task1[j].bt > task1[j + 1].bt) {
                        cpyt = task1[j].bt;
                        task1[j].bt = task1[j + 1].bt;
                        task1[j + 1].bt = cpyt;
                    }
                }
            }
        }
        task1[i].wt=task1[i].et-task1[i].at-task1[i].bt;
        total_wt+=task1[i].wt;
    }
    float avgt=(float)total_wt/(float)taskNum;
    for (i=0;i<taskNum;i++) {
        fprintf(ofp,"%s\t%d\t%d\n", task1[i].tasknum, task1[i].st, task1[i].et);
        printf("%s\t%d\t%d\n", task1[i].tasknum, task1[i].st, task1[i].et);
    }
    fprintf(ofp,"Average Wait time is %.2f\n",avgt);
}
//PSJF
void PSJF(task task1[],scheduledtask scheduledtask1[],int taskNum,FILE *ofp){
    int currt = task1[0].at;
    int completetask=0;
    task currentTask = task1[0];
    task cpytask;
    int i=0;
    int j=0;
    int cpyt=0;
    //keep track of how many steps we process to finish.
    int total=0;
    int total_wt=0;
    //copy the first into schedule
    strcpy(scheduledtask1[total].tasknum,currentTask.tasknum);
    while(taskNum>completetask){
        currt++;
        currentTask.bt--;
        task1[completetask].bt--;
        if(currentTask.bt==0){
           completetask++;
        }
        //sort by burst time
        for (i = 1; i < taskNum; i++) {
            if (task1[i].at < task1[i - 1].et) {
                for (j = 1; j < taskNum - i; j++) {
                    if (task1[j].bt > task1[j + 1].bt) {
                        cpyt = task1[j].bt;
                        task1[j].bt = task1[j + 1].bt;
                        task1[j + 1].bt = cpyt;
                    }
                }
            }
        }//use cpytask to store
        cpytask=task1[completetask];
        if(taskNum>total){
                //save the content
                strcpy(scheduledtask1[total].tasknum,currentTask.tasknum);
               scheduledtask1[total].et=currt;
               scheduledtask1[total].st=currentTask.at;
                total++;
                currentTask=cpytask;
                currentTask.at=currt;//update the time

            }else{
            //reallocate since space is not enough
             
                strcpy(scheduledtask1[total].tasknum,currentTask.tasknum);
               scheduledtask1[total].et=currt;
                scheduledtask1[total].st=currentTask.at;
                total++;
                currentTask=cpytask;
                currentTask.at=currt;
        }
    }
    float avgt=(float)total_wt/(float)taskNum;
    for (i=0;i<taskNum;i++) {
        fprintf(ofp,"%s\t%d\t%d\n", scheduledtask1[i].tasknum, scheduledtask1[i].st, scheduledtask1[i].et);
        printf("%s\t%d\t%d\n", scheduledtask1[i].tasknum, scheduledtask1[i].st, scheduledtask1[i].et);
    }
    fprintf(ofp,"Average Wait time is %.2f\n",avgt);
}

int main(int argc, const char * argv[]) {
    //use file stream to read input line by line
    //use fgets to read line and store in the array: string[50][64]
    FILE *ifp, *ofp;
    int i;
    char *tok;
    char string[350];
    //to store the separate string and then could arrange its value to the according value in struct
    //use four struct array to store four algorithms
    task task_FCFS [50];
    task task_RR [50];
    task task_NPSJF [50];
    task task_PSJF[50];
    ifp = fopen("TaskSpec.txt", "r");
    //fail to open the file
    if (ifp == NULL) {
        fprintf(stderr, "Can't open input file TaskSpec.txt!\n");
        exit(-1);
    }
    //delimeter for separate by comma
    //use strtok function to split string into tokens using delim
    int taskNum=0;
   while(fgets(string, sizeof(string),ifp)!=NULL) {
       printf("%s", string);
       tok = strtok(string, ",");
       if (string[0]=='T') {
           for (i = 0; i < 3; i++) {
               if (i == 0) {
                   task_FCFS[taskNum].tasknum=(char*) malloc(10);
                   task_FCFS[taskNum].tasknum = strdup(tok);
                   task_NPSJF[taskNum].tasknum=(char*) malloc(10);
                   task_NPSJF[taskNum].tasknum = strdup(tok);
                   task_PSJF[taskNum].tasknum=(char*) malloc(10);
                   task_PSJF[taskNum].tasknum = strdup(tok);
                   printf("Task num: %s \n", task_FCFS[taskNum].tasknum);
                   tok = strtok(NULL, ",");
               } else if (i == 1) {
                   task_FCFS[taskNum].at = atoi(strdup(tok));
                   task_NPSJF[taskNum].at = atoi(strdup(tok));
                   task_PSJF[taskNum].at = atoi(strdup(tok));
                   printf("arrival time: %d \n", task_FCFS[taskNum].at);
                   tok = strtok(NULL, ",");
               } else {
                   task_FCFS[taskNum].bt = atoi(strdup(tok));
                   task_NPSJF[taskNum].bt = atoi(strdup(tok));
                   task_PSJF[taskNum].bt = atoi(strdup(tok));
                   printf("burst time: %d \n", task_FCFS[taskNum].bt);

               }
           }
           taskNum++;
       }
   }
    fclose(ifp);//close the input file
    scheduledtask scheduledtask_FCFS [50];
    scheduledtask scheduledtask_RR [50];
    scheduledtask scheduledtask_NPSJF[50] ;
    scheduledtask scheduledtask_PSJF[50];
    ofp = fopen("output.txt", "w");
    if (ofp == NULL) {
        printf("File cannot open!");
        exit(-1);
    }
    fprintf(ofp,"FCFS:\n");
    FCFS(task_FCFS,taskNum,ofp);
    fprintf(ofp,"RR:\n");
    // RR(task_RR,4,ofp);
    fprintf(ofp,"NPSJF:\n");
    NPSJF(task_NPSJF,taskNum,ofp);
    fprintf(ofp,"PSJF:\n");
    PSJF(task_PSJF,scheduledtask_PSJF,taskNum,ofp);
    fclose(ofp);

    return 0;
}